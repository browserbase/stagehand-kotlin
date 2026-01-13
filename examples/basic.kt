package com.browserbase.api.example

import com.browserbase.api.client.StagehandClient
import com.browserbase.api.client.okhttp.StagehandOkHttpClient
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.SessionNavigateParams
import com.browserbase.api.models.sessions.SessionObserveParams
import com.browserbase.api.models.sessions.SessionActParams
import com.browserbase.api.models.sessions.SessionExtractParams
import com.browserbase.api.models.sessions.SessionExecuteParams
import com.browserbase.api.models.sessions.SessionEndParams
import com.browserbase.api.models.sessions.ActionParam

/**
 * Basic Stagehand Kotlin SDK Example
 *
 * This example demonstrates the complete workflow of using Stagehand:
 * 1. Start a browser session
 * 2. Navigate to a webpage
 * 3. Observe to find possible actions
 * 4. Act on an element
 * 5. Extract data from the page
 * 6. Execute an autonomous agent
 * 7. End the session
 *
 * Set these environment variables before running:
 *   BROWSERBASE_API_KEY     - Your Browserbase API key
 *   BROWSERBASE_PROJECT_ID  - Your Browserbase project ID
 *   MODEL_API_KEY           - Your AI model API key (e.g., OpenAI)
 */
fun main() {
    // Create a new Stagehand client using environment variables
    // Configures using BROWSERBASE_API_KEY, BROWSERBASE_PROJECT_ID, and MODEL_API_KEY
    val client: StagehandClient = StagehandOkHttpClient.fromEnv()

    // Start a new browser session
    val startParams = SessionStartParams.builder()
        .modelName("openai/gpt-4o")
        .build()

    val startResponse = client.sessions().start(startParams)
    println("Session started: ${startResponse.data.sessionId}")

    val sessionId = startResponse.data.sessionId

    // Navigate to Hacker News
    val navigateParams = SessionNavigateParams.builder()
        .id(sessionId)
        .url("https://news.ycombinator.com")
        .build()

    client.sessions().navigate(navigateParams)
    println("Navigated to Hacker News")

    // Use Observe to find possible actions on the page
    val observeParams = SessionObserveParams.builder()
        .id(sessionId)
        .instruction("find the link to view comments for the top post")
        .build()

    val observeResponse = client.sessions().observe(observeParams)
    val actions = observeResponse.data.result
    println("Found ${actions.size} possible actions")

    if (actions.isEmpty()) {
        println("No actions found")
        return
    }

    // Take the first action returned by Observe
    val action = actions[0]
    println("Acting on: ${action.description}")

    // Pass the structured action to Act
    val actParams = SessionActParams.builder()
        .id(sessionId)
        .input(
            SessionActParams.Input.ofAction(
                ActionParam.builder()
                    .description(action.description)
                    .selector(action.selector)
                    .method(action.method ?: "click")
                    .arguments(action.arguments)
                    .build()
            )
        )
        .build()

    val actResponse = client.sessions().act(actParams)
    println("Act completed: ${actResponse.data.result.message}")

    // Extract data from the page
    // We're now on the comments page, so extract the top comment text
    val extractParams = SessionExtractParams.builder()
        .id(sessionId)
        .instruction("extract the text of the top comment on this page")
        .schema(
            mapOf(
                "type" to "object",
                "properties" to mapOf(
                    "commentText" to mapOf(
                        "type" to "string",
                        "description" to "The text content of the top comment"
                    ),
                    "author" to mapOf(
                        "type" to "string",
                        "description" to "The username of the comment author"
                    )
                ),
                "required" to listOf("commentText")
            )
        )
        .build()

    val extractResponse = client.sessions().extract(extractParams)
    println("Extracted data: ${extractResponse.data.result}")

    // Get the author from the extracted data
    @Suppress("UNCHECKED_CAST")
    val extractedData = extractResponse.data.result as Map<String, Any>
    val author = extractedData["author"] as String
    println("Looking up profile for author: $author")

    // Use the Agent to find the author's profile
    // Execute runs an autonomous agent that can navigate and interact with pages
    val executeParams = SessionExecuteParams.builder()
        .id(sessionId)
        .executeOptions(
            SessionExecuteParams.ExecuteOptions.builder()
                .instruction(
                    "Find any personal website, GitHub, LinkedIn, or other best profile URL for the Hacker News user '$author'. " +
                    "Click on their username to go to their profile page and look for any links they have shared. " +
                    "Use Google Search with their username or other details from their profile if you don't find any direct links."
                )
                .maxSteps(15.0)
                .build()
        )
        .agentConfig(
            SessionExecuteParams.AgentConfig.builder()
                .model(
                    SessionExecuteParams.ModelConfig.ofModelConfigObject(
                        SessionExecuteParams.ModelConfig.ModelConfigObject.builder()
                            .modelName("openai/gpt-4.1-mini")
                            .apiKey(System.getenv("MODEL_API_KEY"))
                            .build()
                    )
                )
                .cua(false)
                .build()
        )
        .build()

    val executeResponse = client.sessions().execute(executeParams)
    println("Agent completed: ${executeResponse.data.result.message}")
    println("Agent success: ${executeResponse.data.result.success}")
    println("Agent actions taken: ${executeResponse.data.result.actions?.size ?: 0}")

    // End the session to cleanup browser resources
    val endParams = SessionEndParams.builder()
        .id(sessionId)
        .build()

    client.sessions().end(endParams)
    println("Session ended")
}

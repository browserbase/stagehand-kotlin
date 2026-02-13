package com.browserbase.api.example

import com.browserbase.api.client.StagehandClient
import com.browserbase.api.client.okhttp.StagehandOkHttpClient
import com.browserbase.api.core.http.StreamResponse
import com.browserbase.api.models.sessions.Action
import com.browserbase.api.models.sessions.ModelConfig
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.StreamEvent
import com.fasterxml.jackson.core.type.TypeReference
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
    Env.load()
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

    // Use Observe to find possible actions on the page (streaming)
    val observeParams = SessionObserveParams.builder()
        .id(sessionId)
        .instruction("find the link to view comments for the top post")
        .xStreamResponse(SessionObserveParams.XStreamResponse.TRUE)
        .build()

    val actions =
        client.sessions().observeStreaming(observeParams).use { stream ->
            collectStreamingResult(
                label = "observe",
                stream = stream,
                typeRef = object : TypeReference<List<Action>>() {},
            ) ?: emptyList()
        }
    println("Found ${actions.size} possible actions")

    if (actions.isEmpty()) {
        println("No actions found")
        return
    }

    // Take the first action returned by Observe
    val action = actions[0]
    println("Acting on: ${action.description}")

    // Pass the structured action to Act
    val actParams =
        SessionActParams.builder()
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
            .xStreamResponse(SessionActParams.XStreamResponse.TRUE)
            .build()

    client.sessions().actStreaming(actParams).use { stream ->
        collectStreamingResult(
            label = "act",
            stream = stream,
            typeRef = object : TypeReference<Any>() {},
        )
    }

    // Extract data from the page
    // We're now on the comments page, so extract the top comment text
    val extractParams =
        SessionExtractParams.builder()
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
            .xStreamResponse(SessionExtractParams.XStreamResponse.TRUE)
            .build()

    val extractResult =
        client.sessions().extractStreaming(params = extractParams).use { stream ->
            collectStreamingResult(
                label = "extract",
                stream = stream,
                typeRef = object : TypeReference<Map<String, Any>>() {},
            )
        }
    println("Extracted data: $extractResult")

    // Get the author from the extracted data
    @Suppress("UNCHECKED_CAST")
    val extractedData = extractResponse.data.result as Map<String, Any>
    val author = extractedData["author"] as String
    println("Looking up profile for author: $author")

    // Use the Agent to find the author's profile
    // Execute runs an autonomous agent that can navigate and interact with pages
    val executeParams =
        SessionExecuteParams.builder()
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
                        ModelConfig.builder()
                            .modelName("openai/gpt-4.1-mini")
                            .apiKey(Env.require("MODEL_API_KEY"))
                            .build()
                    )
                    .cua(false)
                    .build()
            )
            .xStreamResponse(SessionExecuteParams.XStreamResponse.TRUE)
            .build()

    val executeResult =
        client.sessions().executeStreaming(executeParams).use { stream ->
            collectStreamingResult(
                label = "execute",
                stream = stream,
                typeRef = object : TypeReference<Map<String, Any>>() {},
            )
        }
    val executeMessage = executeResult?.get("message")
    val executeSuccess = executeResult?.get("success")
    val executeActions = executeResult?.get("actions") as? List<*>
    println("Agent completed: $executeMessage")
    println("Agent success: $executeSuccess")
    println("Agent actions taken: ${executeActions?.size ?: 0}")

    // End the session to cleanup browser resources
    val endParams = SessionEndParams.builder()
        .id(sessionId)
        .build()

    client.sessions().end(endParams)
    println("Session ended")
}

private fun Env.load() {
    val envPath = findEnvPath() ?: return
    try {
        for (line in Files.readAllLines(envPath)) {
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue
            }
            val parts = trimmed.split("=", limit = 2)
            if (parts.size != 2) {
                continue
            }
            val key = parts[0]
            val value = parts[1]
            when (key) {
                "BROWSERBASE_API_KEY" -> System.setProperty("stagehand.browserbaseApiKey", value)
                "BROWSERBASE_PROJECT_ID" -> System.setProperty("stagehand.browserbaseProjectId", value)
                "MODEL_API_KEY" -> System.setProperty("stagehand.modelApiKey", value)
                "STAGEHAND_BASE_URL" -> System.setProperty("stagehand.baseUrl", value)
            }
        }
    } catch (_: Exception) {
        // Best-effort env loading
    }
}



private fun <T> collectStreamingResult(
    label: String,
    stream: StreamResponse<StreamEvent>,
    typeRef: TypeReference<T>,
): T? {
    var result: T? = null
    stream.asSequence().forEach { event ->
        println("[$label] ${event.type()} ${event.data()}")
        if (event.type() != StreamEvent.Type.SYSTEM) {
            return@forEach
        }
        val system = event.data().asStreamEventSystemDataOutput()
        when (system.status()) {
            StreamEvent.Data.StreamEventSystemDataOutput.Status.FINISHED -> {
                val payload = system._result()
                result = payload.convert(typeRef)
            }
            StreamEvent.Data.StreamEventSystemDataOutput.Status.ERROR -> {
                val errorMessage = system.error() ?: "unknown error"
                throw IllegalStateException("[$label] stream error: $errorMessage")
            }
            else -> Unit
        }
    }
    return result
}

package com.browserbase.api.example

import com.browserbase.api.client.StagehandClient
import com.browserbase.api.client.okhttp.StagehandOkHttpClient
import com.browserbase.api.core.JsonValue
import com.browserbase.api.core.http.StreamResponse
import com.browserbase.api.models.sessions.ModelConfig
import com.browserbase.api.models.sessions.SessionActParams
import com.browserbase.api.models.sessions.SessionEndParams
import com.browserbase.api.models.sessions.SessionExecuteParams
import com.browserbase.api.models.sessions.SessionExtractParams
import com.browserbase.api.models.sessions.SessionObserveParams
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.StreamEvent
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.LoadState

/**
 * Remote Browserbase + Playwright example (SSE streaming by default).
 *
 * This example demonstrates the full Stagehand flow: start -> observe -> act -> extract -> execute
 * -> end
 *
 * Set these environment variables before running:
 * - BROWSERBASE_API_KEY
 * - BROWSERBASE_PROJECT_ID
 * - MODEL_API_KEY
 */
fun main() {
    val modelApiKey = System.getenv("MODEL_API_KEY")
    val browserbaseApiKey = System.getenv("BROWSERBASE_API_KEY")
    val browserbaseProjectId = System.getenv("BROWSERBASE_PROJECT_ID")

    val missing = mutableListOf<String>()
    if (modelApiKey.isNullOrBlank()) missing.add("MODEL_API_KEY")
    if (browserbaseApiKey.isNullOrBlank()) missing.add("BROWSERBASE_API_KEY")
    if (browserbaseProjectId.isNullOrBlank()) missing.add("BROWSERBASE_PROJECT_ID")

    if (missing.isNotEmpty()) {
        println("Missing required environment variables: ${missing.joinToString(", ")}")
        return
    }

    val client: StagehandClient = StagehandOkHttpClient.fromEnv()
    var sessionId: String? = null

    try {
        val startParams =
            SessionStartParams.builder()
                .modelName("openai/gpt-5-nano")
                .browser(
                    SessionStartParams.Browser.builder()
                        .type(SessionStartParams.Browser.Type.BROWSERBASE)
                        .build()
                )
                .build()

        val startResponse = client.sessions().start(startParams)
        sessionId = startResponse.data().sessionId()
        val cdpUrl = startResponse.data().cdpUrl()

        if (cdpUrl.isNullOrBlank()) {
            println("No cdpUrl returned from the API for session $sessionId.")
            return
        }

        println("Session started: $sessionId")
        println("Connecting Playwright over CDP...")

        Playwright.create().use { playwright ->
            val browser = playwright.chromium().connectOverCDP(cdpUrl)
            browser.use {
                val context = browser.contexts().firstOrNull() ?: browser.newContext()
                val page = context.pages().firstOrNull() ?: context.newPage()

                page.navigate("https://example.com")
                page.waitForLoadState(LoadState.DOMCONTENTLOADED)

                // Use Playwright to interact with the same remote browser.
                page.click("a")
                println("Playwright click completed")

                val observeParams =
                    SessionObserveParams.builder()
                        .id(sessionId)
                        .instruction("Find the most relevant click target on this page")
                        .xStreamResponse(SessionObserveParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().observeStreaming(observeParams).use { stream ->
                    printStreamEvents("observe", stream)
                }

                val actParams =
                    SessionActParams.builder()
                        .id(sessionId)
                        .input("Click the 'Learn more' link")
                        .xStreamResponse(SessionActParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().actStreaming(actParams).use { stream ->
                    printStreamEvents("act", stream)
                }

                val extractParams =
                    SessionExtractParams.builder()
                        .id(sessionId)
                        .instruction("Extract the page title and the primary heading (h1) text")
                        .schema(
                            SessionExtractParams.Schema.builder()
                                .putAdditionalProperty("type", JsonValue.from("object"))
                                .putAdditionalProperty(
                                    "properties",
                                    JsonValue.from(
                                        mapOf(
                                            "title" to mapOf("type" to "string"),
                                            "h1" to mapOf("type" to "string"),
                                        )
                                    ),
                                )
                                .putAdditionalProperty(
                                    "required",
                                    JsonValue.from(listOf("title", "h1")),
                                )
                                .putAdditionalProperty(
                                    "additionalProperties",
                                    JsonValue.from(false),
                                )
                                .build()
                        )
                        .xStreamResponse(SessionExtractParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().extractStreaming(params = extractParams).use { stream ->
                    printStreamEvents("extract", stream)
                }

                val executeParams =
                    SessionExecuteParams.builder()
                        .id(sessionId)
                        .executeOptions(
                            SessionExecuteParams.ExecuteOptions.builder()
                                .instruction("Click the 'Learn more' link if available")
                                .maxSteps(3.0)
                                .build()
                        )
                        .agentConfig(
                            SessionExecuteParams.AgentConfig.builder()
                                .model(
                                    ModelConfig.builder()
                                        .modelName("openai/gpt-5-nano")
                                        .apiKey(modelApiKey)
                                        .build()
                                )
                                .cua(false)
                                .build()
                        )
                        .xStreamResponse(SessionExecuteParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().executeStreaming(executeParams).use { stream ->
                    printStreamEvents("execute", stream)
                }
            }
        }
    } finally {
        if (!sessionId.isNullOrBlank()) {
            val endParams = SessionEndParams.builder().id(sessionId).build()
            client.sessions().end(endParams)
            println("Session ended")
        }
    }
}

private fun printStreamEvents(label: String, stream: StreamResponse<StreamEvent>) {
    stream.asSequence().forEach { event -> println("[$label] ${event.type()} ${event.data()}") }
    println("[$label] stream complete")
}

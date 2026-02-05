package com.browserbase.api.example

import com.browserbase.api.client.StagehandClient
import com.browserbase.api.client.okhttp.StagehandOkHttpClient
import com.browserbase.api.core.http.StreamResponse
import com.browserbase.api.models.sessions.SessionActParams
import com.browserbase.api.models.sessions.SessionEndParams
import com.browserbase.api.models.sessions.SessionExecuteParams
import com.browserbase.api.models.sessions.SessionExtractParams
import com.browserbase.api.models.sessions.SessionObserveParams
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.StreamEvent
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.CDPSession
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import com.microsoft.playwright.options.LoadState

/**
 * Remote Browserbase + Playwright example (SSE streaming by default).
 *
 * This example demonstrates the full Stagehand flow: start -> observe -> act -> extract -> execute
 * -> end
 *
 * Set these environment variables before running: BROWSERBASE_API_KEY - Your Browserbase API key
 * BROWSERBASE_PROJECT_ID - Your Browserbase project ID MODEL_API_KEY - Your AI model API key (e.g.,
 * OpenAI)
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
        sessionId = startResponse.data.sessionId
        val cdpUrl = startResponse.data.cdpUrl

        if (cdpUrl.isNullOrBlank()) {
            println("No cdpUrl returned from the API for session $sessionId.")
            return
        }

        println("Session started: $sessionId")
        println("Connecting Playwright over CDP...")

        Playwright.create().use { playwright ->
            val browser = playwright.chromium().connectOverCDP(cdpUrl)
            try {
                val context = firstContext(browser)
                val page = firstPage(context)

                page.navigate("https://example.com")
                page.waitForLoadState(LoadState.DOMCONTENTLOADED)

                val cdpSession = context.newCDPSession(page)
                val pageTargetId = resolvePageTargetId(cdpSession, page.url())
                if (pageTargetId.isNullOrBlank()) {
                    println("No target id found for the Playwright page.")
                    return
                }

                val observeParams =
                    SessionObserveParams.builder()
                        .id(sessionId)
                        .frameId(pageTargetId)
                        .instruction("Find the most relevant click target on this page")
                        .xStreamResponse(SessionObserveParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().observeStreaming(observeParams).use { stream ->
                    printStreamEvents("observe", stream)
                }

                val actParams =
                    SessionActParams.builder()
                        .id(sessionId)
                        .frameId(pageTargetId)
                        .input("Click the 'Learn more' link")
                        .xStreamResponse(SessionActParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().actStreaming(actParams).use { stream ->
                    printStreamEvents("act", stream)
                }

                val extractParams =
                    SessionExtractParams.builder()
                        .id(sessionId)
                        .frameId(pageTargetId)
                        .instruction("Extract the page title and the primary heading (h1) text")
                        .schema(
                            mapOf(
                                "type" to "object",
                                "properties" to
                                    mapOf(
                                        "title" to mapOf("type" to "string"),
                                        "h1" to mapOf("type" to "string"),
                                    ),
                                "required" to listOf("title", "h1"),
                                "additionalProperties" to false,
                            )
                        )
                        .xStreamResponse(SessionExtractParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().extractStreaming(extractParams).use { stream ->
                    printStreamEvents("extract", stream)
                }

                val executeParams =
                    SessionExecuteParams.builder()
                        .id(sessionId)
                        .frameId(pageTargetId)
                        .executeOptions(
                            SessionExecuteParams.ExecuteOptions.builder()
                                .instruction("Click the 'Learn more' link if available")
                                .maxSteps(3.0)
                                .build()
                        )
                        .agentConfig(
                            SessionExecuteParams.AgentConfig.builder()
                                .model(
                                    SessionExecuteParams.ModelConfig.ofModelConfigObject(
                                        SessionExecuteParams.ModelConfig.ModelConfigObject.builder()
                                            .modelName("openai/gpt-5-nano")
                                            .apiKey(modelApiKey)
                                            .build()
                                    )
                                )
                                .cua(false)
                                .build()
                        )
                        .xStreamResponse(SessionExecuteParams.XStreamResponse.TRUE)
                        .build()

                client.sessions().executeStreaming(executeParams).use { stream ->
                    printStreamEvents("execute", stream)
                }
            } finally {
                browser.close()
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

private fun firstContext(browser: Browser): BrowserContext {
    val contexts = browser.contexts()
    return if (contexts.isNotEmpty()) contexts[0] else browser.newContext()
}

private fun firstPage(context: BrowserContext): Page {
    val pages = context.pages()
    return if (pages.isNotEmpty()) pages[0] else context.newPage()
}

private fun resolvePageTargetId(cdpSession: CDPSession, pageUrl: String): String? {
    val targetInfoResult = cdpSession.send("Target.getTargetInfo")
    val targetInfo = (targetInfoResult["targetInfo"] as? Map<*, *>)
    val targetId = targetInfo?.get("targetId") as? String
    if (!targetId.isNullOrBlank()) {
        return targetId
    }

    val targetsResult = cdpSession.send("Target.getTargets")
    val targetInfos = targetsResult["targetInfos"] as? List<*> ?: emptyList<Any>()
    val normalized = normalizeUrl(pageUrl)

    val exactMatch =
        targetInfos.firstOrNull { entry ->
            val info = entry as? Map<*, *>
            val type = info?.get("type") as? String
            val url = info?.get("url") as? String
            type == "page" && normalizeUrl(url ?: "") == normalized
        }

    val fallbackMatch =
        targetInfos.firstOrNull { entry ->
            val info = entry as? Map<*, *>
            info?.get("type") == "page"
        }

    val match = exactMatch ?: fallbackMatch
    return (match as? Map<*, *>)?.get("targetId") as? String
}

private fun normalizeUrl(url: String): String = url.trimEnd('/')

package com.browserbase.api.example

import java.nio.file.Files
import java.nio.file.Paths

object Env {
    private val requiredKeys = listOf("MODEL_API_KEY", "BROWSERBASE_API_KEY")

    fun load() {
        val envPath =
            findEnvPath()
                ?: throw IllegalStateException(
                    "Missing examples/.env (expected in repo examples/ directory)."
                )

        try {
            Files.readAllLines(envPath).forEach { line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEach
                val parts = trimmed.split("=", limit = 2)
                if (parts.size != 2) return@forEach
                val (key, value) = parts
                when (key) {
                    "BROWSERBASE_API_KEY" ->
                        System.setProperty("stagehand.browserbaseApiKey", value)
                    "MODEL_API_KEY" -> System.setProperty("stagehand.modelApiKey", value)
                    "STAGEHAND_API_URL" -> System.setProperty("stagehand.baseUrl", value)
                    "STAGEHAND_BASE_URL" ->
                        if (System.getProperty("stagehand.baseUrl").isNullOrBlank()) {
                            System.setProperty("stagehand.baseUrl", value)
                        }
                }
            }
        } catch (e: Exception) {
            throw IllegalStateException("Failed to read examples/.env: ${e.message}", e)
        }

        val missing = requiredKeys.filter { key -> System.getProperty(mapKey(key)).isNullOrBlank() }
        if (missing.isNotEmpty()) {
            throw IllegalStateException(
                "Missing required env vars: ${missing.joinToString(", ")} (from examples/.env)"
            )
        }
    }

    fun require(key: String): String =
        System.getProperty(mapKey(key))
            ?: throw IllegalStateException(
                "Missing required env var: $key (expected in examples/.env)"
            )

    private fun mapKey(key: String): String =
        when (key) {
            "BROWSERBASE_API_KEY" -> "stagehand.browserbaseApiKey"
            "MODEL_API_KEY" -> "stagehand.modelApiKey"
            "STAGEHAND_API_URL" -> "stagehand.baseUrl"
            else -> key
        }

    private fun findEnvPath(): java.nio.file.Path? {
        var current = Paths.get(System.getProperty("user.dir"))
        while (true) {
            val candidate = current.resolve("examples").resolve(".env")
            if (Files.exists(candidate)) return candidate
            val parent = current.parent ?: return null
            current = parent
        }
    }
}

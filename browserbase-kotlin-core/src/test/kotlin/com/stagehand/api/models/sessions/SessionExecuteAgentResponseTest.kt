// File generated from our OpenAPI spec by Stainless.

package com.stagehand.api.models.sessions

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.stagehand.api.core.JsonValue
import com.stagehand.api.core.jsonMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SessionExecuteAgentResponseTest {

    @Test
    fun create() {
        val sessionExecuteAgentResponse =
            SessionExecuteAgentResponse.builder()
                .message("message")
                .addStep(JsonValue.from(mapOf<String, Any>()))
                .build()

        assertThat(sessionExecuteAgentResponse.message()).isEqualTo("message")
        assertThat(sessionExecuteAgentResponse.steps())
            .containsExactly(JsonValue.from(mapOf<String, Any>()))
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val sessionExecuteAgentResponse =
            SessionExecuteAgentResponse.builder()
                .message("message")
                .addStep(JsonValue.from(mapOf<String, Any>()))
                .build()

        val roundtrippedSessionExecuteAgentResponse =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(sessionExecuteAgentResponse),
                jacksonTypeRef<SessionExecuteAgentResponse>(),
            )

        assertThat(roundtrippedSessionExecuteAgentResponse).isEqualTo(sessionExecuteAgentResponse)
    }
}

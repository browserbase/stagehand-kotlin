// File generated from our OpenAPI spec by Stainless.

package com.stagehand.api.models.sessions

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.stagehand.api.core.jsonMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ModelConfigTest {

    @Test
    fun create() {
        val modelConfig =
            ModelConfig.builder()
                .apiKey("apiKey")
                .baseUrl("https://example.com")
                .model("model")
                .provider(ModelConfig.Provider.OPENAI)
                .build()

        assertThat(modelConfig.apiKey()).isEqualTo("apiKey")
        assertThat(modelConfig.baseUrl()).isEqualTo("https://example.com")
        assertThat(modelConfig.model()).isEqualTo("model")
        assertThat(modelConfig.provider()).isEqualTo(ModelConfig.Provider.OPENAI)
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val modelConfig =
            ModelConfig.builder()
                .apiKey("apiKey")
                .baseUrl("https://example.com")
                .model("model")
                .provider(ModelConfig.Provider.OPENAI)
                .build()

        val roundtrippedModelConfig =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(modelConfig),
                jacksonTypeRef<ModelConfig>(),
            )

        assertThat(roundtrippedModelConfig).isEqualTo(modelConfig)
    }
}

// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.models.sessions

import com.browserbase.api.core.JsonValue
import com.browserbase.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ModelConfigTest {

    @Test
    fun create() {
        val modelConfig =
            ModelConfig.builder()
                .modelName("openai/gpt-5-nano")
                .apiKey("sk-some-openai-api-key")
                .baseUrl("https://api.openai.com/v1")
                .headers(
                    ModelConfig.Headers.builder()
                        .putAdditionalProperty("X-Custom-Header", JsonValue.from("value"))
                        .build()
                )
                .provider(ModelConfig.Provider.OPENAI)
                .providerOptions(
                    ModelConfig.ProviderOptions.BedrockApiKeyProviderOptions.builder()
                        .region("us-east-1")
                        .build()
                )
                .skipApiKeyFallback(true)
                .build()

        assertThat(modelConfig.modelName()).isEqualTo("openai/gpt-5-nano")
        assertThat(modelConfig.apiKey()).isEqualTo("sk-some-openai-api-key")
        assertThat(modelConfig.baseUrl()).isEqualTo("https://api.openai.com/v1")
        assertThat(modelConfig.headers())
            .isEqualTo(
                ModelConfig.Headers.builder()
                    .putAdditionalProperty("X-Custom-Header", JsonValue.from("value"))
                    .build()
            )
        assertThat(modelConfig.provider()).isEqualTo(ModelConfig.Provider.OPENAI)
        assertThat(modelConfig.providerOptions())
            .isEqualTo(
                ModelConfig.ProviderOptions.ofBedrockApiKey(
                    ModelConfig.ProviderOptions.BedrockApiKeyProviderOptions.builder()
                        .region("us-east-1")
                        .build()
                )
            )
        assertThat(modelConfig.skipApiKeyFallback()).isEqualTo(true)
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val modelConfig =
            ModelConfig.builder()
                .modelName("openai/gpt-5-nano")
                .apiKey("sk-some-openai-api-key")
                .baseUrl("https://api.openai.com/v1")
                .headers(
                    ModelConfig.Headers.builder()
                        .putAdditionalProperty("X-Custom-Header", JsonValue.from("value"))
                        .build()
                )
                .provider(ModelConfig.Provider.OPENAI)
                .providerOptions(
                    ModelConfig.ProviderOptions.BedrockApiKeyProviderOptions.builder()
                        .region("us-east-1")
                        .build()
                )
                .skipApiKeyFallback(true)
                .build()

        val roundtrippedModelConfig =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(modelConfig),
                jacksonTypeRef<ModelConfig>(),
            )

        assertThat(roundtrippedModelConfig).isEqualTo(modelConfig)
    }
}

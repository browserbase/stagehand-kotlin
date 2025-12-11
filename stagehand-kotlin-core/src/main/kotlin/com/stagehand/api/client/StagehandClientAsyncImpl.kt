// File generated from our OpenAPI spec by Stainless.

package com.stagehand.api.client

import com.stagehand.api.core.ClientOptions
import com.stagehand.api.core.getPackageVersion
import com.stagehand.api.services.async.SessionServiceAsync
import com.stagehand.api.services.async.SessionServiceAsyncImpl

class StagehandClientAsyncImpl(private val clientOptions: ClientOptions) : StagehandClientAsync {

    private val clientOptionsWithUserAgent =
        if (clientOptions.headers.names().contains("User-Agent")) clientOptions
        else
            clientOptions
                .toBuilder()
                .putHeader("User-Agent", "${javaClass.simpleName}/Kotlin ${getPackageVersion()}")
                .build()

    // Pass the original clientOptions so that this client sets its own User-Agent.
    private val sync: StagehandClient by lazy { StagehandClientImpl(clientOptions) }

    private val withRawResponse: StagehandClientAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    private val sessions: SessionServiceAsync by lazy {
        SessionServiceAsyncImpl(clientOptionsWithUserAgent)
    }

    override fun sync(): StagehandClient = sync

    override fun withRawResponse(): StagehandClientAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: (ClientOptions.Builder) -> Unit): StagehandClientAsync =
        StagehandClientAsyncImpl(clientOptions.toBuilder().apply(modifier).build())

    override fun sessions(): SessionServiceAsync = sessions

    override fun close() = clientOptions.close()

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        StagehandClientAsync.WithRawResponse {

        private val sessions: SessionServiceAsync.WithRawResponse by lazy {
            SessionServiceAsyncImpl.WithRawResponseImpl(clientOptions)
        }

        override fun withOptions(
            modifier: (ClientOptions.Builder) -> Unit
        ): StagehandClientAsync.WithRawResponse =
            StagehandClientAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier).build()
            )

        override fun sessions(): SessionServiceAsync.WithRawResponse = sessions
    }
}

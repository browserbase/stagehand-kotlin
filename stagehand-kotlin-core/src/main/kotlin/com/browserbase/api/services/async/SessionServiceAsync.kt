// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.services.async

import com.browserbase.api.core.ClientOptions
import com.browserbase.api.core.RequestOptions
import com.browserbase.api.core.http.HttpResponseFor
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.SessionStartResponse
import com.google.errorprone.annotations.MustBeClosed

interface SessionServiceAsync {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: (ClientOptions.Builder) -> Unit): SessionServiceAsync

    /**
     * Creates a new browser session with the specified configuration. Returns a session ID used for
     * all subsequent operations.
     */
    suspend fun start(
        params: SessionStartParams = SessionStartParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionStartResponse

    /** @see start */
    suspend fun start(requestOptions: RequestOptions): SessionStartResponse =
        start(SessionStartParams.none(), requestOptions)

    /**
     * A view of [SessionServiceAsync] that provides access to raw HTTP responses for each method.
     */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(
            modifier: (ClientOptions.Builder) -> Unit
        ): SessionServiceAsync.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /sessions/start`, but is otherwise the same as
         * [SessionServiceAsync.start].
         */
        @MustBeClosed
        suspend fun start(
            params: SessionStartParams = SessionStartParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionStartResponse>

        /** @see start */
        @MustBeClosed
        suspend fun start(requestOptions: RequestOptions): HttpResponseFor<SessionStartResponse> =
            start(SessionStartParams.none(), requestOptions)
    }
}

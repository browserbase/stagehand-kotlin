// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.services.async

import com.browserbase.api.core.ClientOptions
import com.browserbase.api.core.RequestOptions
import com.browserbase.api.core.http.HttpResponseFor
import com.browserbase.api.models.sessions.Action
import com.browserbase.api.models.sessions.SessionActParams
import com.browserbase.api.models.sessions.SessionActResponse
import com.browserbase.api.models.sessions.SessionEndParams
import com.browserbase.api.models.sessions.SessionEndResponse
import com.browserbase.api.models.sessions.SessionExecuteAgentParams
import com.browserbase.api.models.sessions.SessionExecuteAgentResponse
import com.browserbase.api.models.sessions.SessionExtractParams
import com.browserbase.api.models.sessions.SessionExtractResponse
import com.browserbase.api.models.sessions.SessionNavigateParams
import com.browserbase.api.models.sessions.SessionNavigateResponse
import com.browserbase.api.models.sessions.SessionObserveParams
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
     * Performs a browser action based on natural language instruction or a specific action object
     * returned by observe().
     */
    suspend fun act(
        sessionId: String,
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse = act(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see act */
    suspend fun act(
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse

    /** Closes the browser and cleans up all resources associated with the session. */
    suspend fun end(
        sessionId: String,
        params: SessionEndParams = SessionEndParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse = end(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see end */
    suspend fun end(
        params: SessionEndParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse

    /** @see end */
    suspend fun end(sessionId: String, requestOptions: RequestOptions): SessionEndResponse =
        end(sessionId, SessionEndParams.none(), requestOptions)

    /** Runs an autonomous agent that can perform multiple actions to complete a complex task. */
    suspend fun executeAgent(
        sessionId: String,
        params: SessionExecuteAgentParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteAgentResponse =
        executeAgent(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see executeAgent */
    suspend fun executeAgent(
        params: SessionExecuteAgentParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteAgentResponse

    /**
     * Extracts data from the current page using natural language instructions and optional JSON
     * schema for structured output.
     */
    suspend fun extract(
        sessionId: String,
        params: SessionExtractParams = SessionExtractParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse =
        extract(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see extract */
    suspend fun extract(
        params: SessionExtractParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse

    /** @see extract */
    suspend fun extract(sessionId: String, requestOptions: RequestOptions): SessionExtractResponse =
        extract(sessionId, SessionExtractParams.none(), requestOptions)

    /** Navigates the browser to the specified URL and waits for page load. */
    suspend fun navigate(
        sessionId: String,
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse? =
        navigate(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see navigate */
    suspend fun navigate(
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse?

    /**
     * Returns a list of candidate actions that can be performed on the page, optionally filtered by
     * natural language instruction.
     */
    suspend fun observe(
        sessionId: String,
        params: SessionObserveParams = SessionObserveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): List<Action> = observe(params.toBuilder().sessionId(sessionId).build(), requestOptions)

    /** @see observe */
    suspend fun observe(
        params: SessionObserveParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): List<Action>

    /** @see observe */
    suspend fun observe(sessionId: String, requestOptions: RequestOptions): List<Action> =
        observe(sessionId, SessionObserveParams.none(), requestOptions)

    /**
     * Initializes a new Stagehand session with a browser instance. Returns a session ID that must
     * be used for all subsequent requests.
     */
    suspend fun start(
        params: SessionStartParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionStartResponse

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
         * Returns a raw HTTP response for `post /sessions/{sessionId}/act`, but is otherwise the
         * same as [SessionServiceAsync.act].
         */
        @MustBeClosed
        suspend fun act(
            sessionId: String,
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse> =
            act(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see act */
        @MustBeClosed
        suspend fun act(
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse>

        /**
         * Returns a raw HTTP response for `post /sessions/{sessionId}/end`, but is otherwise the
         * same as [SessionServiceAsync.end].
         */
        @MustBeClosed
        suspend fun end(
            sessionId: String,
            params: SessionEndParams = SessionEndParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse> =
            end(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see end */
        @MustBeClosed
        suspend fun end(
            params: SessionEndParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse>

        /** @see end */
        @MustBeClosed
        suspend fun end(
            sessionId: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionEndResponse> =
            end(sessionId, SessionEndParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{sessionId}/agentExecute`, but is
         * otherwise the same as [SessionServiceAsync.executeAgent].
         */
        @MustBeClosed
        suspend fun executeAgent(
            sessionId: String,
            params: SessionExecuteAgentParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteAgentResponse> =
            executeAgent(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see executeAgent */
        @MustBeClosed
        suspend fun executeAgent(
            params: SessionExecuteAgentParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteAgentResponse>

        /**
         * Returns a raw HTTP response for `post /sessions/{sessionId}/extract`, but is otherwise
         * the same as [SessionServiceAsync.extract].
         */
        @MustBeClosed
        suspend fun extract(
            sessionId: String,
            params: SessionExtractParams = SessionExtractParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse> =
            extract(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see extract */
        @MustBeClosed
        suspend fun extract(
            params: SessionExtractParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse>

        /** @see extract */
        @MustBeClosed
        suspend fun extract(
            sessionId: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionExtractResponse> =
            extract(sessionId, SessionExtractParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{sessionId}/navigate`, but is otherwise
         * the same as [SessionServiceAsync.navigate].
         */
        @MustBeClosed
        suspend fun navigate(
            sessionId: String,
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse?> =
            navigate(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see navigate */
        @MustBeClosed
        suspend fun navigate(
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse?>

        /**
         * Returns a raw HTTP response for `post /sessions/{sessionId}/observe`, but is otherwise
         * the same as [SessionServiceAsync.observe].
         */
        @MustBeClosed
        suspend fun observe(
            sessionId: String,
            params: SessionObserveParams = SessionObserveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<List<Action>> =
            observe(params.toBuilder().sessionId(sessionId).build(), requestOptions)

        /** @see observe */
        @MustBeClosed
        suspend fun observe(
            params: SessionObserveParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<List<Action>>

        /** @see observe */
        @MustBeClosed
        suspend fun observe(
            sessionId: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<List<Action>> =
            observe(sessionId, SessionObserveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/start`, but is otherwise the same as
         * [SessionServiceAsync.start].
         */
        @MustBeClosed
        suspend fun start(
            params: SessionStartParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionStartResponse>
    }
}

// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.services.async

import com.browserbase.api.core.ClientOptions
import com.browserbase.api.core.JsonValue
import com.browserbase.api.core.RequestOptions
import com.browserbase.api.core.http.HttpResponseFor
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
import com.browserbase.api.models.sessions.SessionObserveResponse
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
     * Executes a browser action using natural language instructions or a predefined Action object.
     */
    suspend fun act(
        id: JsonValue,
        params: SessionActParams = SessionActParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse = act(params.toBuilder().id(id).build(), requestOptions)

    /** @see act */
    suspend fun act(
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse

    /** @see act */
    suspend fun act(id: JsonValue, requestOptions: RequestOptions): SessionActResponse =
        act(id, SessionActParams.none(), requestOptions)

    /** Terminates the browser session and releases all associated resources. */
    suspend fun end(
        id: JsonValue,
        params: SessionEndParams = SessionEndParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse = end(params.toBuilder().id(id).build(), requestOptions)

    /** @see end */
    suspend fun end(
        params: SessionEndParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse

    /** @see end */
    suspend fun end(id: JsonValue, requestOptions: RequestOptions): SessionEndResponse =
        end(id, SessionEndParams.none(), requestOptions)

    /** Runs an autonomous AI agent that can perform complex multi-step browser tasks. */
    suspend fun executeAgent(
        id: JsonValue,
        params: SessionExecuteAgentParams = SessionExecuteAgentParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteAgentResponse = executeAgent(params.toBuilder().id(id).build(), requestOptions)

    /** @see executeAgent */
    suspend fun executeAgent(
        params: SessionExecuteAgentParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteAgentResponse

    /** @see executeAgent */
    suspend fun executeAgent(
        id: JsonValue,
        requestOptions: RequestOptions,
    ): SessionExecuteAgentResponse =
        executeAgent(id, SessionExecuteAgentParams.none(), requestOptions)

    /** Extracts structured data from the current page using AI-powered analysis. */
    suspend fun extract(
        id: JsonValue,
        params: SessionExtractParams = SessionExtractParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse = extract(params.toBuilder().id(id).build(), requestOptions)

    /** @see extract */
    suspend fun extract(
        params: SessionExtractParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse

    /** @see extract */
    suspend fun extract(id: JsonValue, requestOptions: RequestOptions): SessionExtractResponse =
        extract(id, SessionExtractParams.none(), requestOptions)

    /** Navigates the browser to the specified URL. */
    suspend fun navigate(
        id: JsonValue,
        params: SessionNavigateParams = SessionNavigateParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse = navigate(params.toBuilder().id(id).build(), requestOptions)

    /** @see navigate */
    suspend fun navigate(
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse

    /** @see navigate */
    suspend fun navigate(id: JsonValue, requestOptions: RequestOptions): SessionNavigateResponse =
        navigate(id, SessionNavigateParams.none(), requestOptions)

    /**
     * Identifies and returns available actions on the current page that match the given
     * instruction.
     */
    suspend fun observe(
        id: JsonValue,
        params: SessionObserveParams = SessionObserveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse = observe(params.toBuilder().id(id).build(), requestOptions)

    /** @see observe */
    suspend fun observe(
        params: SessionObserveParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse

    /** @see observe */
    suspend fun observe(id: JsonValue, requestOptions: RequestOptions): SessionObserveResponse =
        observe(id, SessionObserveParams.none(), requestOptions)

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
         * Returns a raw HTTP response for `post /sessions/{id}/act`, but is otherwise the same as
         * [SessionServiceAsync.act].
         */
        @MustBeClosed
        suspend fun act(
            id: JsonValue,
            params: SessionActParams = SessionActParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse> =
            act(params.toBuilder().id(id).build(), requestOptions)

        /** @see act */
        @MustBeClosed
        suspend fun act(
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse>

        /** @see act */
        @MustBeClosed
        suspend fun act(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionActResponse> = act(id, SessionActParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{id}/end`, but is otherwise the same as
         * [SessionServiceAsync.end].
         */
        @MustBeClosed
        suspend fun end(
            id: JsonValue,
            params: SessionEndParams = SessionEndParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse> =
            end(params.toBuilder().id(id).build(), requestOptions)

        /** @see end */
        @MustBeClosed
        suspend fun end(
            params: SessionEndParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse>

        /** @see end */
        @MustBeClosed
        suspend fun end(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionEndResponse> = end(id, SessionEndParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{id}/agentExecute`, but is otherwise the
         * same as [SessionServiceAsync.executeAgent].
         */
        @MustBeClosed
        suspend fun executeAgent(
            id: JsonValue,
            params: SessionExecuteAgentParams = SessionExecuteAgentParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteAgentResponse> =
            executeAgent(params.toBuilder().id(id).build(), requestOptions)

        /** @see executeAgent */
        @MustBeClosed
        suspend fun executeAgent(
            params: SessionExecuteAgentParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteAgentResponse>

        /** @see executeAgent */
        @MustBeClosed
        suspend fun executeAgent(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionExecuteAgentResponse> =
            executeAgent(id, SessionExecuteAgentParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{id}/extract`, but is otherwise the same
         * as [SessionServiceAsync.extract].
         */
        @MustBeClosed
        suspend fun extract(
            id: JsonValue,
            params: SessionExtractParams = SessionExtractParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse> =
            extract(params.toBuilder().id(id).build(), requestOptions)

        /** @see extract */
        @MustBeClosed
        suspend fun extract(
            params: SessionExtractParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse>

        /** @see extract */
        @MustBeClosed
        suspend fun extract(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionExtractResponse> =
            extract(id, SessionExtractParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{id}/navigate`, but is otherwise the same
         * as [SessionServiceAsync.navigate].
         */
        @MustBeClosed
        suspend fun navigate(
            id: JsonValue,
            params: SessionNavigateParams = SessionNavigateParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse> =
            navigate(params.toBuilder().id(id).build(), requestOptions)

        /** @see navigate */
        @MustBeClosed
        suspend fun navigate(
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse>

        /** @see navigate */
        @MustBeClosed
        suspend fun navigate(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionNavigateResponse> =
            navigate(id, SessionNavigateParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /sessions/{id}/observe`, but is otherwise the same
         * as [SessionServiceAsync.observe].
         */
        @MustBeClosed
        suspend fun observe(
            id: JsonValue,
            params: SessionObserveParams = SessionObserveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionObserveResponse> =
            observe(params.toBuilder().id(id).build(), requestOptions)

        /** @see observe */
        @MustBeClosed
        suspend fun observe(
            params: SessionObserveParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionObserveResponse>

        /** @see observe */
        @MustBeClosed
        suspend fun observe(
            id: JsonValue,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionObserveResponse> =
            observe(id, SessionObserveParams.none(), requestOptions)

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

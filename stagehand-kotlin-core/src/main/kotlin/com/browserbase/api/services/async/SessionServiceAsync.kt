// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.services.async

import com.browserbase.api.core.ClientOptions
import com.browserbase.api.core.RequestOptions
import com.browserbase.api.core.http.HttpResponseFor
import com.browserbase.api.models.sessions.SessionActParams
import com.browserbase.api.models.sessions.SessionActResponse
import com.browserbase.api.models.sessions.SessionEndParams
import com.browserbase.api.models.sessions.SessionEndResponse
import com.browserbase.api.models.sessions.SessionExecuteParams
import com.browserbase.api.models.sessions.SessionExecuteResponse
import com.browserbase.api.models.sessions.SessionExtractParams
import com.browserbase.api.models.sessions.SessionExtractResponse
import com.browserbase.api.models.sessions.SessionNavigateParams
import com.browserbase.api.models.sessions.SessionNavigateResponse
import com.browserbase.api.models.sessions.SessionObserveParams
import com.browserbase.api.models.sessions.SessionObserveResponse
import com.browserbase.api.models.sessions.SessionReplayParams
import com.browserbase.api.models.sessions.SessionReplayResponse
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
        id: String,
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse = act(params.toBuilder().id(id).build(), requestOptions)

    /** @see act */
    suspend fun act(
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse

    /** Terminates the browser session and releases all associated resources. */
    suspend fun end(
        id: String,
        params: SessionEndParams = SessionEndParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse = end(params.toBuilder().id(id).build(), requestOptions)

    /** @see end */
    suspend fun end(
        params: SessionEndParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse

    /** @see end */
    suspend fun end(id: String, requestOptions: RequestOptions): SessionEndResponse =
        end(id, SessionEndParams.none(), requestOptions)

    /** Runs an autonomous AI agent that can perform complex multi-step browser tasks. */
    suspend fun execute(
        id: String,
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteResponse = execute(params.toBuilder().id(id).build(), requestOptions)

    /** @see execute */
    suspend fun execute(
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteResponse

    /** Extracts structured data from the current page using AI-powered analysis. */
    suspend fun extract(
        id: String,
        params: SessionExtractParams = SessionExtractParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse = extract(params.toBuilder().id(id).build(), requestOptions)

    /** @see extract */
    suspend fun extract(
        params: SessionExtractParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse

    /** @see extract */
    suspend fun extract(id: String, requestOptions: RequestOptions): SessionExtractResponse =
        extract(id, SessionExtractParams.none(), requestOptions)

    /** Navigates the browser to the specified URL. */
    suspend fun navigate(
        id: String,
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse = navigate(params.toBuilder().id(id).build(), requestOptions)

    /** @see navigate */
    suspend fun navigate(
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse

    /**
     * Identifies and returns available actions on the current page that match the given
     * instruction.
     */
    suspend fun observe(
        id: String,
        params: SessionObserveParams = SessionObserveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse = observe(params.toBuilder().id(id).build(), requestOptions)

    /** @see observe */
    suspend fun observe(
        params: SessionObserveParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse

    /** @see observe */
    suspend fun observe(id: String, requestOptions: RequestOptions): SessionObserveResponse =
        observe(id, SessionObserveParams.none(), requestOptions)

    /** Retrieves replay metrics for a session. */
    suspend fun replay(
        id: String,
        params: SessionReplayParams = SessionReplayParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionReplayResponse = replay(params.toBuilder().id(id).build(), requestOptions)

    /** @see replay */
    suspend fun replay(
        params: SessionReplayParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionReplayResponse

    /** @see replay */
    suspend fun replay(id: String, requestOptions: RequestOptions): SessionReplayResponse =
        replay(id, SessionReplayParams.none(), requestOptions)

    /**
     * Creates a new browser session with the specified configuration. Returns a session ID used for
     * all subsequent operations.
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
         * Returns a raw HTTP response for `post /v1/sessions/{id}/act`, but is otherwise the same
         * as [SessionServiceAsync.act].
         */
        @MustBeClosed
        suspend fun act(
            id: String,
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse> =
            act(params.toBuilder().id(id).build(), requestOptions)

        /** @see act */
        @MustBeClosed
        suspend fun act(
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/end`, but is otherwise the same
         * as [SessionServiceAsync.end].
         */
        @MustBeClosed
        suspend fun end(
            id: String,
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
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionEndResponse> = end(id, SessionEndParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/agentExecute`, but is otherwise
         * the same as [SessionServiceAsync.execute].
         */
        @MustBeClosed
        suspend fun execute(
            id: String,
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteResponse> =
            execute(params.toBuilder().id(id).build(), requestOptions)

        /** @see execute */
        @MustBeClosed
        suspend fun execute(
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/extract`, but is otherwise the
         * same as [SessionServiceAsync.extract].
         */
        @MustBeClosed
        suspend fun extract(
            id: String,
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
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionExtractResponse> =
            extract(id, SessionExtractParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/navigate`, but is otherwise the
         * same as [SessionServiceAsync.navigate].
         */
        @MustBeClosed
        suspend fun navigate(
            id: String,
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse> =
            navigate(params.toBuilder().id(id).build(), requestOptions)

        /** @see navigate */
        @MustBeClosed
        suspend fun navigate(
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/observe`, but is otherwise the
         * same as [SessionServiceAsync.observe].
         */
        @MustBeClosed
        suspend fun observe(
            id: String,
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
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionObserveResponse> =
            observe(id, SessionObserveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `get /v1/sessions/{id}/replay`, but is otherwise the same
         * as [SessionServiceAsync.replay].
         */
        @MustBeClosed
        suspend fun replay(
            id: String,
            params: SessionReplayParams = SessionReplayParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionReplayResponse> =
            replay(params.toBuilder().id(id).build(), requestOptions)

        /** @see replay */
        @MustBeClosed
        suspend fun replay(
            params: SessionReplayParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionReplayResponse>

        /** @see replay */
        @MustBeClosed
        suspend fun replay(
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionReplayResponse> =
            replay(id, SessionReplayParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/start`, but is otherwise the same as
         * [SessionServiceAsync.start].
         */
        @MustBeClosed
        suspend fun start(
            params: SessionStartParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionStartResponse>
    }
}

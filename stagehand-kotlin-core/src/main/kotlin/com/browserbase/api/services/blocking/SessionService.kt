// File generated from our OpenAPI spec by Stainless.

package com.browserbase.api.services.blocking

import com.browserbase.api.core.ClientOptions
import com.browserbase.api.core.RequestOptions
import com.browserbase.api.core.http.HttpResponseFor
import com.browserbase.api.core.http.StreamResponse
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
import com.browserbase.api.models.sessions.SessionStartParams
import com.browserbase.api.models.sessions.SessionStartResponse
import com.browserbase.api.models.sessions.StreamEvent
import com.google.errorprone.annotations.MustBeClosed

interface SessionService {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: (ClientOptions.Builder) -> Unit): SessionService

    /**
     * Executes a browser action using natural language instructions or a predefined Action object.
     */
    fun act(
        id: String,
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse = act(params.toBuilder().id(id).build(), requestOptions)

    /** @see act */
    fun act(
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionActResponse

    /**
     * Executes a browser action using natural language instructions or a predefined Action object.
     */
    @MustBeClosed
    fun actStreaming(
        id: String,
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent> = actStreaming(params.toBuilder().id(id).build(), requestOptions)

    /** @see actStreaming */
    @MustBeClosed
    fun actStreaming(
        params: SessionActParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent>

    /** Terminates the browser session and releases all associated resources. */
    fun end(
        id: String,
        params: SessionEndParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse = end(params.toBuilder().id(id).build(), requestOptions)

    /** @see end */
    fun end(
        params: SessionEndParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionEndResponse

    /** Runs an autonomous AI agent that can perform complex multi-step browser tasks. */
    fun execute(
        id: String,
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteResponse = execute(params.toBuilder().id(id).build(), requestOptions)

    /** @see execute */
    fun execute(
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExecuteResponse

    /** Runs an autonomous AI agent that can perform complex multi-step browser tasks. */
    @MustBeClosed
    fun executeStreaming(
        id: String,
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent> =
        executeStreaming(params.toBuilder().id(id).build(), requestOptions)

    /** @see executeStreaming */
    @MustBeClosed
    fun executeStreaming(
        params: SessionExecuteParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent>

    /** Extracts structured data from the current page using AI-powered analysis. */
    fun extract(
        id: String,
        params: SessionExtractParams = SessionExtractParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse = extract(params.toBuilder().id(id).build(), requestOptions)

    /** @see extract */
    fun extract(
        params: SessionExtractParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionExtractResponse

    /** @see extract */
    fun extract(id: String, requestOptions: RequestOptions): SessionExtractResponse =
        extract(id, SessionExtractParams.none(), requestOptions)

    /** Extracts structured data from the current page using AI-powered analysis. */
    @MustBeClosed
    fun extractStreaming(
        id: String,
        params: SessionExtractParams = SessionExtractParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent> =
        extractStreaming(params.toBuilder().id(id).build(), requestOptions)

    /** @see extractStreaming */
    @MustBeClosed
    fun extractStreaming(
        params: SessionExtractParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent>

    /** @see extractStreaming */
    @MustBeClosed
    fun extractStreaming(id: String, requestOptions: RequestOptions): StreamResponse<StreamEvent> =
        extractStreaming(id, SessionExtractParams.none(), requestOptions)

    /** Navigates the browser to the specified URL. */
    fun navigate(
        id: String,
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse = navigate(params.toBuilder().id(id).build(), requestOptions)

    /** @see navigate */
    fun navigate(
        params: SessionNavigateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionNavigateResponse

    /**
     * Identifies and returns available actions on the current page that match the given
     * instruction.
     */
    fun observe(
        id: String,
        params: SessionObserveParams = SessionObserveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse = observe(params.toBuilder().id(id).build(), requestOptions)

    /** @see observe */
    fun observe(
        params: SessionObserveParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionObserveResponse

    /** @see observe */
    fun observe(id: String, requestOptions: RequestOptions): SessionObserveResponse =
        observe(id, SessionObserveParams.none(), requestOptions)

    /**
     * Identifies and returns available actions on the current page that match the given
     * instruction.
     */
    @MustBeClosed
    fun observeStreaming(
        id: String,
        params: SessionObserveParams = SessionObserveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent> =
        observeStreaming(params.toBuilder().id(id).build(), requestOptions)

    /** @see observeStreaming */
    @MustBeClosed
    fun observeStreaming(
        params: SessionObserveParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): StreamResponse<StreamEvent>

    /** @see observeStreaming */
    @MustBeClosed
    fun observeStreaming(id: String, requestOptions: RequestOptions): StreamResponse<StreamEvent> =
        observeStreaming(id, SessionObserveParams.none(), requestOptions)

    /**
     * Creates a new browser session with the specified configuration. Returns a session ID used for
     * all subsequent operations.
     */
    fun start(
        params: SessionStartParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): SessionStartResponse

    /** A view of [SessionService] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: (ClientOptions.Builder) -> Unit): SessionService.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/act`, but is otherwise the same
         * as [SessionService.act].
         */
        @MustBeClosed
        fun act(
            id: String,
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse> =
            act(params.toBuilder().id(id).build(), requestOptions)

        /** @see act */
        @MustBeClosed
        fun act(
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionActResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/act`, but is otherwise the same
         * as [SessionService.actStreaming].
         */
        @MustBeClosed
        fun actStreaming(
            id: String,
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            actStreaming(params.toBuilder().id(id).build(), requestOptions)

        /** @see actStreaming */
        @MustBeClosed
        fun actStreaming(
            params: SessionActParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/end`, but is otherwise the same
         * as [SessionService.end].
         */
        @MustBeClosed
        fun end(
            id: String,
            params: SessionEndParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse> =
            end(params.toBuilder().id(id).build(), requestOptions)

        /** @see end */
        @MustBeClosed
        fun end(
            params: SessionEndParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionEndResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/agentExecute`, but is otherwise
         * the same as [SessionService.execute].
         */
        @MustBeClosed
        fun execute(
            id: String,
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteResponse> =
            execute(params.toBuilder().id(id).build(), requestOptions)

        /** @see execute */
        @MustBeClosed
        fun execute(
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExecuteResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/agentExecute`, but is otherwise
         * the same as [SessionService.executeStreaming].
         */
        @MustBeClosed
        fun executeStreaming(
            id: String,
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            executeStreaming(params.toBuilder().id(id).build(), requestOptions)

        /** @see executeStreaming */
        @MustBeClosed
        fun executeStreaming(
            params: SessionExecuteParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/extract`, but is otherwise the
         * same as [SessionService.extract].
         */
        @MustBeClosed
        fun extract(
            id: String,
            params: SessionExtractParams = SessionExtractParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse> =
            extract(params.toBuilder().id(id).build(), requestOptions)

        /** @see extract */
        @MustBeClosed
        fun extract(
            params: SessionExtractParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionExtractResponse>

        /** @see extract */
        @MustBeClosed
        fun extract(
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionExtractResponse> =
            extract(id, SessionExtractParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/extract`, but is otherwise the
         * same as [SessionService.extractStreaming].
         */
        @MustBeClosed
        fun extractStreaming(
            id: String,
            params: SessionExtractParams = SessionExtractParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            extractStreaming(params.toBuilder().id(id).build(), requestOptions)

        /** @see extractStreaming */
        @MustBeClosed
        fun extractStreaming(
            params: SessionExtractParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>>

        /** @see extractStreaming */
        @MustBeClosed
        fun extractStreaming(
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            extractStreaming(id, SessionExtractParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/navigate`, but is otherwise the
         * same as [SessionService.navigate].
         */
        @MustBeClosed
        fun navigate(
            id: String,
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse> =
            navigate(params.toBuilder().id(id).build(), requestOptions)

        /** @see navigate */
        @MustBeClosed
        fun navigate(
            params: SessionNavigateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionNavigateResponse>

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/observe`, but is otherwise the
         * same as [SessionService.observe].
         */
        @MustBeClosed
        fun observe(
            id: String,
            params: SessionObserveParams = SessionObserveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionObserveResponse> =
            observe(params.toBuilder().id(id).build(), requestOptions)

        /** @see observe */
        @MustBeClosed
        fun observe(
            params: SessionObserveParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionObserveResponse>

        /** @see observe */
        @MustBeClosed
        fun observe(
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<SessionObserveResponse> =
            observe(id, SessionObserveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/{id}/observe`, but is otherwise the
         * same as [SessionService.observeStreaming].
         */
        @MustBeClosed
        fun observeStreaming(
            id: String,
            params: SessionObserveParams = SessionObserveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            observeStreaming(params.toBuilder().id(id).build(), requestOptions)

        /** @see observeStreaming */
        @MustBeClosed
        fun observeStreaming(
            params: SessionObserveParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<StreamResponse<StreamEvent>>

        /** @see observeStreaming */
        @MustBeClosed
        fun observeStreaming(
            id: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<StreamResponse<StreamEvent>> =
            observeStreaming(id, SessionObserveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `post /v1/sessions/start`, but is otherwise the same as
         * [SessionService.start].
         */
        @MustBeClosed
        fun start(
            params: SessionStartParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<SessionStartResponse>
    }
}

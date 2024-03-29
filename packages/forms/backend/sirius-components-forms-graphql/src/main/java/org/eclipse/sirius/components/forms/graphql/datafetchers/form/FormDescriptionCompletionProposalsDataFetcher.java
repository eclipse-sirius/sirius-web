/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.forms.graphql.datafetchers.form;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.forms.dto.CompletionRequestInput;
import org.eclipse.sirius.components.collaborative.forms.dto.CompletionRequestSuccessPayload;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the completion proposals for a text field which supports it.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type FormDescription {
 *   completionProposals(widgetId: ID!, currentText: String!, cursorPosition: Int!): [CompletionProposal!]!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "FormDescription", field = "completionProposals")
public class FormDescriptionCompletionProposalsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<CompletionProposal>>> {
    private static final String WIDGET_ID_ARGUMENT = "widgetId";

    private static final String CURRENT_TEXT_ARGUMENT = "currentText";

    private static final String CURSOR_POSITION_ARGUMENT = "cursorPosition";

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public FormDescriptionCompletionProposalsDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<CompletionProposal>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        var representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString);
        var widgetId = Optional.ofNullable(environment.getArgument(WIDGET_ID_ARGUMENT)).map(Object::toString);
        String currentText = Optional.ofNullable(environment.getArgument(CURRENT_TEXT_ARGUMENT)).map(Object::toString).orElse("");
        int cursorPosition = Optional.ofNullable(environment.getArgument(CURSOR_POSITION_ARGUMENT)).filter(Integer.class::isInstance).map(Integer.class::cast).orElse(0);

        if (editingContextId.isPresent() && representationId.isPresent() && widgetId.isPresent()) {
            var input = new CompletionRequestInput(UUID.randomUUID(), editingContextId.get(), representationId.get(), widgetId.get(), currentText, cursorPosition);
            return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input), input)
                    .filter(CompletionRequestSuccessPayload.class::isInstance)
                    .map(CompletionRequestSuccessPayload.class::cast)
                    .map(CompletionRequestSuccessPayload::proposals)
                    .toFuture();
        } else {
            return Mono.just(List.<CompletionProposal> of()).toFuture();
        }
    }

}

/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.forms.dto.HelpTextRequestInput;
import org.eclipse.sirius.components.collaborative.forms.dto.HelpTextSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the help text for widgets which define one.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type FormDescription {
 *   helpText(widgetId: ID!): String
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "FormDescription", field = "helpText")
public class FormDescriptionHelpTextDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {
    private static final String WIDGET_ID_ARGUMENT = "widgetId";

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public FormDescriptionHelpTextDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        var representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString);
        var widgetId = Optional.ofNullable(environment.getArgument(WIDGET_ID_ARGUMENT)).map(Object::toString);

        if (editingContextId.isPresent() && representationId.isPresent() && widgetId.isPresent()) {
            var input = new HelpTextRequestInput(UUID.randomUUID(), editingContextId.get(), representationId.get(), widgetId.get());
            return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input), input)
                    .filter(HelpTextSuccessPayload.class::isInstance)
                    .map(HelpTextSuccessPayload.class::cast)
                    .map(HelpTextSuccessPayload::text).toFuture();
        } else {
            return Mono.just("").toFuture();
        }
    }

}

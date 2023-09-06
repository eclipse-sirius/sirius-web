/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference.graphql.datafetchers.form;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher used to retrieve referenceValueOptions for a reference widget.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "FormDescription", field = "referenceValueOptions")
public class FormDescriptionReferenceValueOptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ReferenceValue>>> {

    private static final String REPRESENTATION_ID = "representationId";

    private static final String REFERENCE_WIDGET_ID = "referenceWidgetId";

    private final IExceptionWrapper exceptionWrapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public FormDescriptionReferenceValueOptionsDataFetcher(IExceptionWrapper exceptionWrapper, IEditingContextDispatcher editingContextDispatcher) {
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ReferenceValue>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String referenceWidgetId = environment.getArgument(REFERENCE_WIDGET_ID);
        ReferenceValueOptionsQueryInput input = new ReferenceValueOptionsQueryInput(UUID.randomUUID(), editingContextId, representationId, referenceWidgetId);

        return this.exceptionWrapper.wrapMono(() -> this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input), input)
                .filter(ReferenceValueOptionsQueryPayload.class::isInstance)
                .map(ReferenceValueOptionsQueryPayload.class::cast)
                .map(ReferenceValueOptionsQueryPayload::options)
                .toFuture();
    }
}

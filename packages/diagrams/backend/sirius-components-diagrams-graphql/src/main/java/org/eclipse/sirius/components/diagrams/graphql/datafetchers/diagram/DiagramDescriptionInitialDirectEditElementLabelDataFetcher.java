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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher to retrieve the initial label value when direct edit has been requested.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "DiagramDescription", field = "initialDirectEditElementLabel")
public class DiagramDescriptionInitialDirectEditElementLabelDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String LABEL_ID = "labelId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public DiagramDescriptionInitialDirectEditElementLabelDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String labelId = environment.getArgument(LABEL_ID);

        if (editingContextId != null && representationId != null && labelId != null) {
            var input = new InitialDirectEditElementLabelInput(UUID.randomUUID(), editingContextId, representationId, labelId);
            // @formatter:off
            return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                    .filter(InitialDirectEditElementLabelSuccessPayload.class::isInstance)
                    .map(InitialDirectEditElementLabelSuccessPayload.class::cast)
                    .map(InitialDirectEditElementLabelSuccessPayload::initialDirectEditElementLabel)
                    .toFuture();
            // @formatter:on
        }

        return Mono.<String> empty().toFuture();
    }

}

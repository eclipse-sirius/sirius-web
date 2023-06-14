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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The "initial direct edit element label" query input.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "TreeDescription", field = "initialDirectEditTreeItemLabel")
public class TreeItemInitialDirectEditLabelDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String INPUT_ARGUMENT = "treeItemId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public TreeItemInitialDirectEditLabelDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String treeItemId = environment.getArgument(INPUT_ARGUMENT);

        if (editingContextId != null && representationId != null && treeItemId != null) {
            var input = new InitialDirectEditElementLabelInput(UUID.randomUUID(), editingContextId, representationId, treeItemId, "");
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

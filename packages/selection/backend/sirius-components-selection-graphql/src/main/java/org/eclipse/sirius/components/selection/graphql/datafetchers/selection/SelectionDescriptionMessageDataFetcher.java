/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.selection.graphql.datafetchers.selection;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessagePayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher use to compute the message defined in the Selection Dialog Description.
 * @author fbarbin
 */
@QueryDataFetcher(type = "SelectionDescription", field = "message")
public class SelectionDescriptionMessageDataFetcher  implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public SelectionDescriptionMessageDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        SelectionDescription selectionDescription = environment.getSource();

        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.of(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        String targetObjectId = environment.getArgument(TARGET_OBJECT_ID);

        var input = new GetSelectionDescriptionMessageInput(UUID.randomUUID(), targetObjectId, selectionDescription);
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId.get(), input)
               .filter(GetSelectionDescriptionMessagePayload.class::isInstance)
               .map(GetSelectionDescriptionMessagePayload.class::cast)
               .map(GetSelectionDescriptionMessagePayload::message)
               .toFuture();
    }

}

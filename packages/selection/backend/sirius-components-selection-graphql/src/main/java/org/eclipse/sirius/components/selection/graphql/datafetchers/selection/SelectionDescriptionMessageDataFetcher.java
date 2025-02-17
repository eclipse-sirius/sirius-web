/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessagePayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher use to compute the message defined in the Selection Dialog Description.
 * @author fbarbin
 */
@QueryDataFetcher(type = "SelectionDescription", field = "message")
public class SelectionDescriptionMessageDataFetcher  implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String VARIABLES = "variables";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final ObjectMapper objectMapper;

    public SelectionDescriptionMessageDataFetcher(ObjectMapper objectMapper, IEditingContextDispatcher editingContextDispatcher) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        SelectionDescription selectionDescription = environment.getSource();

        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.of(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        List<Object> variablesObject = environment.getArgument(VARIABLES);
        List<SelectionDialogVariable> variables = variablesObject.stream()
                .map(object -> this.objectMapper.convertValue(object, SelectionDialogVariable.class))
                .toList();

        var input = new GetSelectionDescriptionMessageInput(UUID.randomUUID(), variables, selectionDescription);
        return this.editingContextDispatcher.dispatchQuery(editingContextId.get(), input)
               .filter(GetSelectionDescriptionMessagePayload.class::isInstance)
               .map(GetSelectionDescriptionMessagePayload.class::cast)
               .map(GetSelectionDescriptionMessagePayload::message)
               .toFuture();
    }

}

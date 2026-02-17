/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionNoSelectionLabelInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionNoSelectionLabelPayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field SelectionDescription#noSelectionLabel.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "SelectionDescription", field = "noSelectionLabel")
public class SelectionDescriptionNoSelectionLabelDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String VARIABLES = "variables";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final ObjectMapper objectMapper;

    public SelectionDescriptionNoSelectionLabelDataFetcher(IEditingContextDispatcher editingContextDispatcher, ObjectMapper objectMapper) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        SelectionDescription selectionDescription = environment.getSource();

        Map<String, Object> localContext = environment.getLocalContext();
        var optionalEditingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);

        if (optionalEditingContextId.isPresent()) {
            var editingContextId = optionalEditingContextId.get();
            List<Object> variablesObject = environment.getArgument(VARIABLES);
            List<SelectionDialogVariable> variables = variablesObject.stream()
                    .map(object -> this.objectMapper.convertValue(object, SelectionDialogVariable.class))
                    .toList();

            var input = new GetSelectionDescriptionNoSelectionLabelInput(UUID.randomUUID(), variables, selectionDescription);
            return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                    .filter(GetSelectionDescriptionNoSelectionLabelPayload.class::isInstance)
                    .map(GetSelectionDescriptionNoSelectionLabelPayload.class::cast)
                    .map(GetSelectionDescriptionNoSelectionLabelPayload::noSelectionLabel)
                    .toFuture();
        }
        return Mono.just("").toFuture();

    }
}

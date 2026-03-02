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
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogPayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.selection.description.SelectionDialog;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field SelectionDescription#dialog.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "SelectionDescription", field = "dialog")
public class SelectionDescriptionDialogDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<SelectionDialog>> {

    private static final String VARIABLES = "variables";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final ObjectMapper objectMapper;

    public SelectionDescriptionDialogDataFetcher(IEditingContextDispatcher editingContextDispatcher, ObjectMapper objectMapper) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<SelectionDialog> get(DataFetchingEnvironment environment) throws Exception {
        SelectionDescription selectionDescription = environment.getSource();
        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.of(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);

        List<Object> variablesObject = environment.getArgument(VARIABLES);
        List<SelectionDialogVariable> variables = variablesObject.stream()
                .map(object -> this.objectMapper.convertValue(object, SelectionDialogVariable.class))
                .toList();

        var input = new GetSelectionDialogInput(UUID.randomUUID(), variables, selectionDescription);
        return this.editingContextDispatcher.dispatchQuery(editingContextId.get(), input)
                .filter(GetSelectionDialogPayload.class::isInstance)
                .map(GetSelectionDialogPayload.class::cast)
                .map(GetSelectionDialogPayload::dialog)
                .toFuture();
    }
}

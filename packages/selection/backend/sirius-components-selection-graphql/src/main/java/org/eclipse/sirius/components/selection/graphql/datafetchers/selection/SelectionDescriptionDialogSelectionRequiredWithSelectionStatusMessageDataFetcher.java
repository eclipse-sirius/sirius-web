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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogSelectionRequiredWithSelectionStatusMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field SelectionDescription#dialogSelectionStatusMessageWithSelection.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "SelectionDescription", field = "dialogSelectionRequiredWithSelectionStatusMessage")
public class SelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<String>> {

    private static final String SELECTED_OBJECT_IDS = "selectedObjectIds";

    private final IEditingContextDispatcher editingContextDispatcher;

    public SelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<String> get(DataFetchingEnvironment environment) throws Exception {
        SelectionDescription selectionDescription = environment.getSource();

        Map<String, Object> localContext = environment.getLocalContext();
        var editingContextId = Optional.of(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString);
        List<String> selectedObjectIds = environment.getArgument(SELECTED_OBJECT_IDS);

        var input = new GetSelectionDialogSelectionRequiredWithSelectionStatusMessageInput(UUID.randomUUID(), selectedObjectIds, selectionDescription);
        return this.editingContextDispatcher.dispatchQuery(editingContextId.get(), input)
               .filter(GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload.class::isInstance)
               .map(GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload.class::cast)
               .map(GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload::statusMessage)
               .toFuture();
    }
}

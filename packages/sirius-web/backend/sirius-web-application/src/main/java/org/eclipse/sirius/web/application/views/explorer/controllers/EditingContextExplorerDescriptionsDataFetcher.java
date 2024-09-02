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
package org.eclipse.sirius.web.application.views.explorer.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextExplorerDescriptionsInput;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextExplorerDescriptionsPayload;
import org.eclipse.sirius.web.application.views.explorer.dto.ExplorerDescriptionMetadata;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the EditingContext#explorerDescriptions.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "EditingContext", field = "explorerDescriptions")
public class EditingContextExplorerDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ExplorerDescriptionMetadata>>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextExplorerDescriptionsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ExplorerDescriptionMetadata>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        var input = new EditingContextExplorerDescriptionsInput(UUID.randomUUID(), editingContextId);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextExplorerDescriptionsPayload.class::isInstance)
                .map(EditingContextExplorerDescriptionsPayload.class::cast)
                .map(EditingContextExplorerDescriptionsPayload::explorerDescriptionMetadata)
                .toFuture();
    }
}

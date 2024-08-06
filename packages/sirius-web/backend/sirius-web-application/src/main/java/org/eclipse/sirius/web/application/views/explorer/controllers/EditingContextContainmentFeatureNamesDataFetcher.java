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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextContainmentFeatureNamesInput;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#containmentFeatureNames.
 * It is used to find the containment feature names of a container object given the candidate contained object.
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "containmentFeatureNames")
public class EditingContextContainmentFeatureNamesDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {
    private static final String CONTAINER_ID = "containerId";

    private static final String CONTAINED_OBJECT_ID = "containedObjectId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextContainmentFeatureNamesDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String containerId = environment.getArgument(CONTAINER_ID);
        String containedObjectId = environment.getArgument(CONTAINED_OBJECT_ID);

        EditingContextContainmentFeatureNamesInput input = new EditingContextContainmentFeatureNamesInput(UUID.randomUUID(), editingContextId, containerId, containedObjectId);
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .toFuture();
    }
}

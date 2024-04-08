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
package org.eclipse.sirius.web.application.object.controllers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#rootObjectCreationDescriptions.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "rootObjectCreationDescriptions")
public class EditingContextRootObjectCreationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ChildCreationDescription>>> {

    private static final String DOMAIN_ID_ARGUMENT = "domainId";

    private static final String SUGGESTED_ARGUMENT = "suggested";

    private static final String REFERENCE_KIND_ARGUMENT = "referenceKind";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextRootObjectCreationDescriptionsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ChildCreationDescription>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String domainId = environment.getArgument(DOMAIN_ID_ARGUMENT);
        Boolean suggested = environment.getArgument(SUGGESTED_ARGUMENT);
        String referenceKind = environment.getArgument(REFERENCE_KIND_ARGUMENT);

        var input = new EditingContextRootObjectCreationDescriptionsInput(UUID.randomUUID(), editingContextId, domainId, suggested, referenceKind);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextRootObjectCreationDescriptionsPayload.class::isInstance)
                .map(EditingContextRootObjectCreationDescriptionsPayload.class::cast)
                .map(EditingContextRootObjectCreationDescriptionsPayload::childCreationDescriptions)
                .toFuture();
    }
}

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
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsPayload;
import org.eclipse.sirius.components.core.api.Domain;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#domains.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "EditingContext", field = "domains")
public class EditingContextDomainsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<Domain>>> {
    private static final String ROOT_DOMAINS_ONLY_ARGUMENT = "rootDomainsOnly";

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextDomainsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<Domain>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        boolean rootDomainsOnlyArgument = environment.getArgument(ROOT_DOMAINS_ONLY_ARGUMENT);

        EditingContextDomainsInput input = new EditingContextDomainsInput(UUID.randomUUID(), editingContextId, rootDomainsOnlyArgument);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextDomainsPayload.class::isInstance)
                .map(EditingContextDomainsPayload.class::cast)
                .map(EditingContextDomainsPayload::domains)
                .toFuture();
    }
}

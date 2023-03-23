/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextDomainsPayload;
import org.eclipse.sirius.components.core.api.Domain;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the domains accessible in an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   domains: [Domains!]!
 * }
 * </pre>
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "domains")
public class EditingContextDomainsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<Domain>>> {
    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextDomainsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<Domain>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();

        EditingContextDomainsInput input = new EditingContextDomainsInput(UUID.randomUUID(), editingContextId);

        return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                .filter(EditingContextDomainsPayload.class::isInstance)
                .map(EditingContextDomainsPayload.class::cast)
                .map(EditingContextDomainsPayload::domains)
                .toFuture();
    }
}

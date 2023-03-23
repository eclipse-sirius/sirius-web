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
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the root object creation descriptions accessible in an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   rootObjectCreationDescriptions(domainId: ID!, suggested: Boolean!): [ChildCreationDescription!]!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = "EditingContext", field = "rootObjectCreationDescriptions")
public class EditingContextRootObjectCreationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ChildCreationDescription>>> {

    private static final String DOMAIN_ID_ARGUMENT = "domainId";

    private static final String SUGGESTED_ARGUMENT = "suggested";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextRootObjectCreationDescriptionsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<ChildCreationDescription>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String domainId = environment.getArgument(DOMAIN_ID_ARGUMENT);
        Boolean suggested = environment.getArgument(SUGGESTED_ARGUMENT);

        EditingContextRootObjectCreationDescriptionsInput input = new EditingContextRootObjectCreationDescriptionsInput(UUID.randomUUID(), editingContextId, domainId, suggested);

        return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                .filter(EditingContextRootObjectCreationDescriptionsPayload.class::isInstance)
                .map(EditingContextRootObjectCreationDescriptionsPayload.class::cast)
                .map(EditingContextRootObjectCreationDescriptionsPayload::childCreationDescriptions)
                .toFuture();
    }
}

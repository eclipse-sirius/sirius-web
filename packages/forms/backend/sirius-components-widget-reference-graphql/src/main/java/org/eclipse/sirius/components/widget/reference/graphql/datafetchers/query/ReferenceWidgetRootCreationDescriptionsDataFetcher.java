/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.widget.reference.graphql.datafetchers.query;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceWidgetRootCreationDescriptionsInput;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve referenceWidgetRootCreationDescriptions.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "EditingContext", field = "referenceWidgetRootCreationDescriptions")
public class ReferenceWidgetRootCreationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ChildCreationDescription>>> {

    private static final String DOMAIN_ID_ARGUMENT = "domainId";

    private static final String REFERENCE_KIND_ARGUMENT = "referenceKind";
    private static final String WIDGET_DESCRIPTION_ID = "descriptionId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public ReferenceWidgetRootCreationDescriptionsDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<List<ChildCreationDescription>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String domainId = environment.getArgument(DOMAIN_ID_ARGUMENT);
        String referenceKind = environment.getArgument(REFERENCE_KIND_ARGUMENT);
        String descriptionId = environment.getArgument(WIDGET_DESCRIPTION_ID);

        ReferenceWidgetRootCreationDescriptionsInput input = new ReferenceWidgetRootCreationDescriptionsInput(UUID.randomUUID(), editingContextId, domainId,
                referenceKind, descriptionId);

        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(EditingContextRootObjectCreationDescriptionsPayload.class::isInstance)
                .map(EditingContextRootObjectCreationDescriptionsPayload.class::cast)
                .map(EditingContextRootObjectCreationDescriptionsPayload::childCreationDescriptions)
                .toFuture();
    }

}

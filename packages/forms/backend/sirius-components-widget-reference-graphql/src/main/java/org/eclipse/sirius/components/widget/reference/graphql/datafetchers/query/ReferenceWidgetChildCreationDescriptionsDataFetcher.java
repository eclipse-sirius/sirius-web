/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextChildObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceWidgetChildCreationDescriptionsInput;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve referenceWidgetChildCreationDescriptions.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "EditingContext", field = "referenceWidgetChildCreationDescriptions")
public class ReferenceWidgetChildCreationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ChildCreationDescription>>> {

    private static final String KIND_ARGUMENT = "kind";
    private static final String REFERENCE_KIND_ARGUMENT = "referenceKind";
    private static final String WIDGET_DESCRIPTION_ID_ARGUMENT = "descriptionId";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public ReferenceWidgetChildCreationDescriptionsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<ChildCreationDescription>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String kind = environment.getArgument(KIND_ARGUMENT);
        String referenceKind = environment.getArgument(REFERENCE_KIND_ARGUMENT);
        String descriptionId = environment.getArgument(WIDGET_DESCRIPTION_ID_ARGUMENT);

        ReferenceWidgetChildCreationDescriptionsInput input = new ReferenceWidgetChildCreationDescriptionsInput(UUID.randomUUID(), editingContextId, kind,
                referenceKind, descriptionId);

        return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                .filter(EditingContextChildObjectCreationDescriptionsPayload.class::isInstance)
                .map(EditingContextChildObjectCreationDescriptionsPayload.class::cast)
                .map(EditingContextChildObjectCreationDescriptionsPayload::childCreationDescriptions)
                .toFuture();
    }

}

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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionBorderNodeDescriptionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetNodeDescriptionsPayload;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher to retrieve the border node description list of a node.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "NodeDescription", field = "borderNodeDescriptions")
public class NodeDescriptionBorderNodeDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<NodeDescription>>> {


    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public NodeDescriptionBorderNodeDescriptionsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<NodeDescription>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        NodeDescription nodeDescription = environment.getSource();
        if (editingContextId != null && representationId != null) {
            var input = new GetNodeDescriptionBorderNodeDescriptionsInput(UUID.randomUUID(), editingContextId, representationId, nodeDescription);

            return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                    .filter(GetNodeDescriptionsPayload.class::isInstance)
                    .map(GetNodeDescriptionsPayload.class::cast)
                    .map(GetNodeDescriptionsPayload::nodeDescriptions)
                    .toFuture();
        }

        return Mono.<List<NodeDescription>>empty().toFuture();
    }
}

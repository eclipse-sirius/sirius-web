/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsSuccessPayload;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Datafetcher to retrieve the connector tools.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "DiagramDescription", field = "connectorTools")
public class DiagramDescriptionConnectorToolsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<ITool>>> {

    private static final String SOURCE_DIAGRAM_ELEMENT_ID = "sourceDiagramElementId"; //$NON-NLS-1$

    private static final String TARGET_DIAGRAM_ELEMENT_ID = "targetDiagramElementId"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public DiagramDescriptionConnectorToolsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<ITool>> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String sourceDiagramElementId = environment.getArgument(SOURCE_DIAGRAM_ELEMENT_ID);
        String targetDiagramElementId = environment.getArgument(TARGET_DIAGRAM_ELEMENT_ID);

        if (editingContextId != null && representationId != null) {
            GetConnectorToolsInput input = new GetConnectorToolsInput(UUID.randomUUID(), editingContextId, representationId, sourceDiagramElementId, targetDiagramElementId);

            // @formatter:off
            return this.editingContextEventProcessorRegistry.dispatchEvent(input.getEditingContextId(), input)
                    .filter(GetConnectorToolsSuccessPayload.class::isInstance)
                    .map(GetConnectorToolsSuccessPayload.class::cast)
                    .map(GetConnectorToolsSuccessPayload::getConnectorTools)
                    .toFuture();
            // @formatter:on
        }

        return Mono.<List<ITool>> empty().toFuture();
    }

}

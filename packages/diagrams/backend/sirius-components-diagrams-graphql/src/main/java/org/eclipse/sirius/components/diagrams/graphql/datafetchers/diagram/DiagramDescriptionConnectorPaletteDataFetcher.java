/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The data fetcher used to retrieve the connector palette from the diagram description.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "DiagramDescription", field = "connectorPalette")
public class DiagramDescriptionConnectorPaletteDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Palette>> {

    private static final String SOURCE_DIAGRAM_ELEMENT_ID = "sourceDiagramElementId";

    private static final String TARGET_DIAGRAM_ELEMENT_ID = "targetDiagramElementId";

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionConnectorPaletteDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<Palette> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String sourceDiagramElementId = environment.getArgument(SOURCE_DIAGRAM_ELEMENT_ID);
        String targetDiagramElementId = environment.getArgument(TARGET_DIAGRAM_ELEMENT_ID);

        if (editingContextId != null && representationId != null) {
            GetConnectorPaletteInput input = new GetConnectorPaletteInput(UUID.randomUUID(), editingContextId, representationId, sourceDiagramElementId, targetDiagramElementId);

            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(GetPaletteSuccessPayload.class::isInstance)
                    .map(GetPaletteSuccessPayload.class::cast)
                    .map(GetPaletteSuccessPayload::palette)
                    .toFuture();
        }

        return Mono.<Palette>empty().toFuture();
    }

}

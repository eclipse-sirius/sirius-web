/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeDiagramImpactAnalysisToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Used to retrieve the impact analysis on a tool execution.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "DiagramDescription", field = "diagramImpactAnalysisReport")
public class DiagramDescriptionDiagramImpactAnalysisReportDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<ImpactAnalysisReport>> {

    private static final String TOOL_ID = "toolId";
    private static final String DIAGRAM_ELEMENT_ID = "diagramElementId";

    private static final String VARIABLES = "variables";

    private final ObjectMapper objectMapper;

    private final IEditingContextDispatcher editingContextDispatcher;

    public DiagramDescriptionDiagramImpactAnalysisReportDataFetcher(ObjectMapper objectMapper, IEditingContextDispatcher editingContextDispatcher) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<ImpactAnalysisReport> get(DataFetchingEnvironment environment) throws Exception {
        CompletableFuture<ImpactAnalysisReport> result = Mono.<ImpactAnalysisReport>empty().toFuture();
        Map<String, Object> localContext = environment.getLocalContext();

        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        String toolId = environment.getArgument(TOOL_ID);
        String diagramElementId = environment.getArgument(DIAGRAM_ELEMENT_ID);
        var variables = Optional.ofNullable(environment.getArgument(VARIABLES))
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .map(this::convertToToolVariables)
                .orElseGet(List::of);
        if (editingContextId != null && representationId != null) {
            InvokeDiagramImpactAnalysisToolInput input = new InvokeDiagramImpactAnalysisToolInput(UUID.randomUUID(), editingContextId, representationId, toolId, diagramElementId, variables);
            result = this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .filter(InvokeImpactAnalysisSuccessPayload.class::isInstance)
                    .map(InvokeImpactAnalysisSuccessPayload.class::cast)
                    .map(InvokeImpactAnalysisSuccessPayload::impactAnalysisReport)
                    .toFuture();
        }
        return result;
    }

    private List<ToolVariable> convertToToolVariables(List<?> arguments) {
        return arguments.stream()
                .map(argument -> this.objectMapper.convertValue(argument, ToolVariable.class))
                .toList();
    }

}

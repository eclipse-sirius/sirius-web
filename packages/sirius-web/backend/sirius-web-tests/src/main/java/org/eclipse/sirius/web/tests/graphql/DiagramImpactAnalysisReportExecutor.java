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
package org.eclipse.sirius.web.tests.graphql;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

/**
 * Used to query a diagram impact analysis report and perform assertions on its result.
 *
 * @author frouene
 * @since v2026.12.0
 */
@Service
public class DiagramImpactAnalysisReportExecutor {

    private final DiagramImpactAnalysisReportQueryRunner diagramImpactAnalysisReportQueryRunner;

    private final ObjectMapper objectMapper;

    private final IMessageService messageService;

    public DiagramImpactAnalysisReportExecutor(DiagramImpactAnalysisReportQueryRunner diagramImpactAnalysisReportQueryRunner, ObjectMapper objectMapper, IMessageService messageService) {
        this.diagramImpactAnalysisReportQueryRunner = Objects.requireNonNull(diagramImpactAnalysisReportQueryRunner);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    public DiagramImpactAnalysisReportAssert execute(String editingContextId, String representationId, String toolId, String diagramElementId, List<ToolVariable> toolVariables) {
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId,
                "representationId", representationId,
                "toolId", toolId,
                "diagramElementId", diagramElementId,
                "variables", toolVariables);
        var result = this.diagramImpactAnalysisReportQueryRunner.run(variables);
        return new DiagramImpactAnalysisReportAssert(result, this.objectMapper, this.messageService);
    }
}

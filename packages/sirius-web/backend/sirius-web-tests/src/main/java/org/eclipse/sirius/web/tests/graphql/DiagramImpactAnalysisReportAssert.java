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

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import tools.jackson.databind.ObjectMapper;

/**
 * Custom assertions for a diagram impact analysis report.
 *
 * @author frouene
 * @since v2026.12.0
 */
public class DiagramImpactAnalysisReportAssert {

    private static final String REPORT_PATH = "$.data.viewer.editingContext.representation.description.diagramImpactAnalysisReport";

    private final GraphQLResult result;

    private final ObjectMapper objectMapper;

    private final IMessageService messageService;

    public DiagramImpactAnalysisReportAssert(GraphQLResult result, ObjectMapper objectMapper, IMessageService messageService) {
        this.result = Objects.requireNonNull(result);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.messageService = Objects.requireNonNull(messageService);
    }

    public DiagramImpactAnalysisReportAssert isSuccess() {
        assertThat(this.result.errors()).isEmpty();
        List<String> additionalReports = JsonPath.read(this.result.data(), REPORT_PATH + ".additionalReports");
        assertThat(additionalReports).isEmpty();
        return this;
    }

    public DiagramImpactAnalysisReportAssert hasElementsDeleted(int expectedCount) {
        int count = JsonPath.read(this.result.data(), REPORT_PATH + ".nbElementDeleted");
        assertThat(count).isEqualTo(expectedCount);
        return this;
    }

    public DiagramImpactAnalysisReportAssert hasElementsCreated(int expectedCount) {
        int count = JsonPath.read(this.result.data(), REPORT_PATH + ".nbElementCreated");
        assertThat(count).isEqualTo(expectedCount);
        return this;
    }

    public DiagramImpactAnalysisReportAssert hasElementsModified(int expectedCount) {
        int count = JsonPath.read(this.result.data(), REPORT_PATH + ".nbElementModified");
        assertThat(count).isEqualTo(expectedCount);
        return this;
    }

    public DiagramImpactAnalysisReportAssert hasExecutionFailure() {
        assertThat(this.result.errors()).isEmpty();
        List<String> additionalReports = JsonPath.read(this.result.data(), REPORT_PATH + ".additionalReports");
        assertThat(additionalReports).singleElement().satisfies(message -> assertThat(message).startsWith(this.messageService.operationExecutionFailed("")));
        return this;
    }

    public DiagramImpactAnalysisReportAssert assertImpactTree(Consumer<DataTree> consumer) {
        Object impactTree = JsonPath.read(this.result.data(), REPORT_PATH + ".impactTree");
        DataTree dataTree = this.objectMapper.convertValue(impactTree, DataTree.class);
        Objects.requireNonNull(consumer).accept(dataTree);
        return this;
    }
}

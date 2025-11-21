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
package org.eclipse.sirius.web.application.controllers.impactanalysis;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.datatree.DataTreeNode;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.TreeImpactAnalysisReportQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the impact analysis for tree contextual menu entries.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImpactAnalysisTreeContextualMenuEntryControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private TreeImpactAnalysisReportQueryRunner treeImpactAnalysisReportQueryRunner;

    @Autowired
    private DomainViewTreeDescriptionProvider domainViewTreeDescriptionProvider;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IMessageService messageService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain explorer representation, when an impact analysis report for one of its tool is queried, then the report is returned")
    public void givenADomainExplorerRepresentationWhenAnImpactAnalysisReportForAToolIsQueriedThenTheReportIsReturned() {
        List<String> expandedIds = List.of(
                StudioIdentifiers.DOMAIN_DOCUMENT.toString(),
                StudioIdentifiers.DOMAIN_OBJECT.toString());
        var representationId = this.representationIdBuilder.buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), expandedIds, List.of());
        var input = new ExplorerEventInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                representationId
        );

        var flux = this.treeEventSubscriptionRunner.run(input);

        Runnable invokeImpactAnalysisReport = () -> {
            Map<String, Object> invokeImpactAnalysisReportVariables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", representationId,
                    "treeItemId", StudioIdentifiers.ROOT_ENTITY_OBJECT,
                    "menuEntryId", this.domainViewTreeDescriptionProvider.getToggleAbstractMenuEntryId()
                    );
            String result = this.treeImpactAnalysisReportQueryRunner.run(invokeImpactAnalysisReportVariables);

            int nbElementDeleted = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementDeleted");
            assertThat(nbElementDeleted).isEqualTo(0);
            int nbElementCreated = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementCreated");
            assertThat(nbElementCreated).isEqualTo(0);
            int nbElementModified = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementModified");
            assertThat(nbElementModified).isEqualTo(1);
            List<String> additionalReports = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.additionalReports[*]");
            assertThat(additionalReports).isEmpty();

            Configuration configuration = Configuration.defaultConfiguration().mappingProvider(new JacksonMappingProvider(this.objectMapper));
            DataTree dataTree = JsonPath.parse(result, configuration).read("$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.impactTree", DataTree.class);

            assertThat(dataTree.id()).isEqualTo("impact_tree");

            assertThat(dataTree.nodes().stream().filter(node -> node.parentId() == null).toList()).hasSize(1);

            assertThat(dataTree.nodes()).anySatisfy(node -> {
                assertThat(node.label().toString()).isEqualTo("abstract: false -> true");
                assertThat(node.endIconsURLs()).hasSize(1);
                List<String> endIconsURL = node.endIconsURLs().get(0);
                assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureModification.svg"));
                Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                    .findFirst();
                assertThat(parentNode).isPresent()
                    .get()
                    .returns("Root", dataTreeNode -> dataTreeNode.label().toString());
            });
        };

        StepVerifier.create(flux)
            .expectNextMatches(TreeRefreshedEventPayload.class::isInstance)
            .then(invokeImpactAnalysisReport)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a domain explorer representation with a tool that fails, when an impact analysis report for the tool tool is queried, then the report contains an error message")
    public void givenADomainExplorerRepresentationWithAToolThatFailsWhenAnImpactAnalysisReportForTheToolIsQueriedThenTheReportContainsTheErrorMessage() {
        List<String> expandedIds = List.of(
                StudioIdentifiers.DOMAIN_DOCUMENT.toString(),
                StudioIdentifiers.DOMAIN_OBJECT.toString());
        var representationId = this.representationIdBuilder.buildExplorerRepresentationId(this.domainViewTreeDescriptionProvider.getRepresentationDescriptionId(), expandedIds, List.of());
        var input = new ExplorerEventInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                representationId
        );

        var flux = this.treeEventSubscriptionRunner.run(input);

        Runnable invokeImpactAnalysisReport = () -> {
            Map<String, Object> invokeImpactAnalysisReportVariables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", representationId,
                    "treeItemId", StudioIdentifiers.ROOT_ENTITY_OBJECT,
                    "menuEntryId", "failingNodeToolId"
            );
            String result = this.treeImpactAnalysisReportQueryRunner.run(invokeImpactAnalysisReportVariables);

            int nbElementDeleted = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementDeleted");
            assertThat(nbElementDeleted).isEqualTo(0);
            int nbElementCreated = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementCreated");
            assertThat(nbElementCreated).isEqualTo(0);
            int nbElementModified = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.nbElementModified");
            assertThat(nbElementModified).isEqualTo(0);
            List<String> additionalReports = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.additionalReports[*]");
            assertThat(additionalReports).hasSize(1);
            assertThat(additionalReports.get(0)).startsWith(this.messageService.operationExecutionFailed(""));

            Configuration configuration = Configuration.defaultConfiguration().mappingProvider(new JacksonMappingProvider(this.objectMapper));
            DataTree dataTree = JsonPath.parse(result, configuration).read("$.data.viewer.editingContext.representation.description.treeImpactAnalysisReport.impactTree", DataTree.class);

            assertThat(dataTree.id()).isEqualTo("impact_tree");
            assertThat(dataTree.nodes()).isEmpty();
        };

        StepVerifier.create(flux)
                .expectNextMatches(TreeRefreshedEventPayload.class::isInstance)
                .then(invokeImpactAnalysisReport)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

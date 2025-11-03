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
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.datatree.DataTreeNode;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.ModelOperationDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the impact analysis for diagram tools.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ImpactAnalysisDiagramToolControllerTests extends AbstractIntegrationTests {

    private static final String GET_IMPACT_ANALYSIS_REPORT = """
            query getImpactAnalysisReport(
              $editingContextId: ID!
              $representationId: ID!
              $toolId: ID!
              $diagramElementId: ID!
              $variables: [ToolVariable!]!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on DiagramDescription {
                        diagramImpactAnalysisReport(toolId: $toolId, diagramElementId: $diagramElementId, variables: $variables) {
                          nbElementDeleted
                          nbElementModified
                          nbElementCreated
                          additionalReports
                          impactTree {
                            id
                            nodes {
                              id
                              parentId
                              label {
                                styledStringFragments {
                                  text
                                }
                              }
                              iconURLs
                              endIconsURLs
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ModelOperationDiagramDescriptionProvider modelOperationDiagramDescriptionProvider;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToModelOperationDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.modelOperationDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "ModelOperationDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a tool, when asking for impact analysis on tool, then the report return is correct")
    public void givenDiagramWithToolWhenAskingForImpactAnalysisOnToolThenTheReportIsCorrect() {
        var flux = this.givenSubscriptionToModelOperationDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
        });

        Runnable invokeImpactAnalysisReport = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "toolId", this.modelOperationDiagramDescriptionProvider.getCreateNodeToolId(),
                    "diagramElementId", diagramId.get(),
                    "variables", List.of());
            var result = this.graphQLRequestor.execute(GET_IMPACT_ANALYSIS_REPORT, variables);

            int nbElementDeleted = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.diagramImpactAnalysisReport.nbElementDeleted");
            assertThat(nbElementDeleted).isEqualTo(0);

            int nbElementCreated = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.diagramImpactAnalysisReport.nbElementCreated");
            assertThat(nbElementCreated).isEqualTo(2);

            int nbElementModified = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.diagramImpactAnalysisReport.nbElementModified");
            assertThat(nbElementModified).isEqualTo(3);

            Configuration configuration = Configuration.defaultConfiguration().mappingProvider(new JacksonMappingProvider(this.objectMapper));
            DataTree dataTree = JsonPath.parse(result, configuration).read("$.data.viewer.editingContext.representation.description.diagramImpactAnalysisReport.impactTree", DataTree.class);

            assertThat(dataTree.id()).isEqualTo("impact_tree");
            assertThat(dataTree.nodes()).anySatisfy(node -> {
                assertThat(node.label().toString()).isEqualTo("elements: a");
                assertThat(node.endIconsURLs()).hasSize(1);
                List<String> endIconsURL = node.endIconsURLs().get(0);
                assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                    .findFirst();
                assertThat(parentNode).isPresent()
                    .get()
                    .returns("Sirius Web", dataTreeNode -> dataTreeNode.label().toString());
            });
            assertThat(dataTree.nodes()).anySatisfy(node -> {
                assertThat(node.label().toString()).isEqualTo("elements: c");
                assertThat(node.endIconsURLs()).hasSize(1);
                List<String> endIconsURL = node.endIconsURLs().get(0);
                assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                    .findFirst();
                assertThat(parentNode).isPresent()
                    .get()
                    .returns("Sirius Web", dataTreeNode -> dataTreeNode.label().toString());
            });
            assertThat(dataTree.nodes()).anySatisfy(node -> {
                assertThat(node.label().toString()).isEqualTo("name: a");
                assertThat(node.endIconsURLs()).hasSize(1);
                List<String> endIconsURL = node.endIconsURLs().get(0);
                assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                    .findFirst();
                assertThat(parentNode).isPresent()
                    .get()
                    .returns("a", dataTreeNode -> dataTreeNode.label().toString());
            });
            assertThat(dataTree.nodes()).anySatisfy(node -> {
                assertThat(node.label().toString()).isEqualTo("name: c");
                assertThat(node.endIconsURLs()).hasSize(1);
                List<String> endIconsURL = node.endIconsURLs().get(0);
                assertThat(endIconsURL).anyMatch(endIconURL -> endIconURL.contains("FeatureAddition.svg"));
                Optional<DataTreeNode> parentNode = dataTree.nodes().stream().filter(dataTreeNode -> Objects.equals(dataTreeNode.id(), node.parentId()))
                    .findFirst();
                assertThat(parentNode).isPresent()
                    .get()
                    .returns("c", dataTreeNode -> dataTreeNode.label().toString());
            });
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(invokeImpactAnalysisReport)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl;
import org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of NodeDescriptionLayoutStrategyMigrationParticipant.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagramNodeDescriptionLayoutStrategyMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an old model, NodeDescriptionLayoutStrategyMigrationParticipant migrates the model correctly")
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LAYOUT_STRATEGY_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LAYOUT_STRATEGY_STUDIO_DIAGRAM)).findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getNodeDescriptions()).hasSize(1);
                    assertThat(diagramDescription.getNodeDescriptions()).anySatisfy(nodeDescription -> {
                        assertThat(nodeDescription.getStyle().getChildrenLayoutStrategy()).isNotNull();
                        assertThat(nodeDescription.getStyle().getChildrenLayoutStrategy()).isInstanceOf(ListLayoutStrategyDescriptionImpl.class);
                        assertThat((ListLayoutStrategyDescriptionImpl) nodeDescription.getStyle().getChildrenLayoutStrategy()).satisfies(listLayoutStrategyDescription -> {
                            assertThat(listLayoutStrategyDescription.getAreChildNodesDraggableExpression()).isEqualTo("aql:false");
                            assertThat(listLayoutStrategyDescription.getTopGapExpression()).isEqualTo("10");
                            assertThat(listLayoutStrategyDescription.getBottomGapExpression()).isEqualTo("20");
                            assertThat(listLayoutStrategyDescription.getGrowableNodes()).hasSize(1);
                            assertThat(listLayoutStrategyDescription.getGrowableNodes()).allSatisfy(growableNode -> assertThat(growableNode.getName()).isEqualTo("Sub-node"));
                        });
                        assertThat(nodeDescription.getChildrenDescriptions()).anySatisfy(subNodeDescription -> {
                            assertThat(subNodeDescription.getStyle().getChildrenLayoutStrategy()).isNotNull();
                            assertThat(subNodeDescription.getStyle().getChildrenLayoutStrategy()).isInstanceOf(FreeFormLayoutStrategyDescriptionImpl.class);
                        });

                        assertThat(nodeDescription.getConditionalStyles()).allSatisfy(conditionalStyle -> {
                            assertThat(conditionalStyle.getStyle().getChildrenLayoutStrategy()).isNotNull();
                            assertThat(conditionalStyle.getStyle().getChildrenLayoutStrategy()).isInstanceOf(ListLayoutStrategyDescriptionImpl.class);
                            assertThat((ListLayoutStrategyDescriptionImpl) conditionalStyle.getStyle().getChildrenLayoutStrategy()).satisfies(listLayoutStrategyDescription -> {
                                assertThat(listLayoutStrategyDescription.getAreChildNodesDraggableExpression()).isEqualTo("aql:false");
                                assertThat(listLayoutStrategyDescription.getTopGapExpression()).isEqualTo("10");
                                assertThat(listLayoutStrategyDescription.getBottomGapExpression()).isEqualTo("20");
                                assertThat(listLayoutStrategyDescription.getGrowableNodes()).hasSize(1);
                                assertThat(listLayoutStrategyDescription.getGrowableNodes()).allSatisfy(growableNode -> assertThat(growableNode.getName()).isEqualTo("Sub-node"));
                            });
                        });
                    });
                }
            });
        }
    }

}

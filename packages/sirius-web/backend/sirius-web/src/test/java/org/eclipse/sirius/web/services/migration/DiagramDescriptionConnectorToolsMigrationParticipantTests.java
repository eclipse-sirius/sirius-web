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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of DiagramDescriptionConnectorToolsMigrationParticipant.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramDescriptionConnectorToolsMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given old node and edge palettes, when the diagram description is loaded, then connector tools are moved to their descriptions in order")
    public void givenOldNodeAndEdgePalettesWhenTheDiagramDescriptionIsLoadedThenConnectorToolsAreMovedToTheirDescriptionsInOrder() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_STUDIO_EDITING_CONTEXT_ID);
        assertThat(optionalEditingContext).isPresent().get().isInstanceOf(EditingContext.class);
        var editingContext = (EditingContext) optionalEditingContext.orElseThrow();
        var optionalDiagramDescription = editingContext.getViews().stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> MigrationIdentifiers.MIGRATION_DIAGRAM_DESCRIPTION_CONNECTOR_TOOLS_STUDIO_DIAGRAM.equals(description.getName()))
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .findFirst();
        assertThat(optionalDiagramDescription).isPresent();
        var diagramDescription = optionalDiagramDescription.orElseThrow();

        assertThat(diagramDescription.getNodeDescriptions()).hasSize(3);
        var nodeDescription = diagramDescription.getNodeDescriptions().get(0);
        assertThat(nodeDescription.getEdgeTools()).extracting(Tool::getName)
                .containsExactly("Direct node tool 1", "Direct node tool 2", "First section tool 1", "First section tool 2", "Second section tool");
        assertThat(nodeDescription.getEdgeTools()).allSatisfy(tool -> assertThat(tool.eContainer()).isSameAs(nodeDescription));
        assertThat(nodeDescription.getPalette().getNodeTools()).extracting(Tool::getName).containsExactly("Palette node tool");
        assertThat(nodeDescription.getPalette().getToolSections()).hasSize(2);
        assertThat(nodeDescription.getPalette().getToolSections().get(0).getNodeTools()).extracting(Tool::getName).containsExactly("Section node tool");

        var childNodeDescription = nodeDescription.getChildrenDescriptions().get(0);
        var borderNodeDescription = nodeDescription.getBorderNodesDescriptions().get(0);
        assertThat(childNodeDescription.getEdgeTools()).singleElement().satisfies(tool -> {
            assertThat(tool.getName()).isEqualTo("Child node tool");
            assertThat(tool.eContainer()).isSameAs(childNodeDescription);
            assertThat(tool.getTargetElementDescriptions()).containsExactly(nodeDescription);
        });
        assertThat(borderNodeDescription.getEdgeTools()).singleElement().satisfies(tool -> {
            assertThat(tool.getName()).isEqualTo("Border node tool");
            assertThat(tool.eContainer()).isSameAs(borderNodeDescription);
        });
        assertThat(diagramDescription.getNodeDescriptions().get(1).getEdgeTools()).isEmpty();
        assertThat(diagramDescription.getNodeDescriptions().get(1).getPalette()).isNull();
        assertThat(diagramDescription.getNodeDescriptions().get(2).getEdgeTools()).isEmpty();
        assertThat(diagramDescription.getNodeDescriptions().get(2).getPalette()).isNotNull();

        assertThat(diagramDescription.getEdgeDescriptions()).hasSize(2);
        var edgeDescription = diagramDescription.getEdgeDescriptions().get(0);
        assertThat(edgeDescription.getEdgeTools()).extracting(Tool::getName).containsExactly("Edge tool 1", "Edge tool 2");
        assertThat(edgeDescription.getEdgeTools()).allSatisfy(tool -> {
            assertThat(tool.eContainer()).isSameAs(edgeDescription);
            assertThat(tool.getTargetElementDescriptions()).containsExactly(nodeDescription);
        });
        assertThat(edgeDescription.getPalette().getEdgeReconnectionTools()).isEmpty();
        assertThat(diagramDescription.getEdgeDescriptions().get(1).getEdgeTools()).isEmpty();
        assertThat(diagramDescription.getEdgeDescriptions().get(1).getPalette()).isNull();

        var nodeTool = nodeDescription.getEdgeTools().get(0);
        assertThat(nodeTool.eResource().getURIFragment(nodeTool)).isEqualTo("ac704000-0000-0000-0000-000000000010");
        assertThat(nodeTool.getTargetElementDescriptions()).containsExactly(childNodeDescription, edgeDescription);
        assertThat(nodeTool.getPreconditionExpression()).isEqualTo("aql:self <> target");
        assertThat(nodeTool.getIconURLsExpression()).isEqualTo("/icons/connector.svg");
        assertThat(nodeTool.getElementsToSelectExpression()).isEqualTo("aql:newFlow");
        assertThat(nodeTool.getBody()).singleElement().isInstanceOfSatisfying(ChangeContext.class, changeContext -> {
            assertThat(changeContext.getExpression()).isEqualTo("aql:self.eContainer()");
            assertThat(changeContext.getChildren()).singleElement().isInstanceOfSatisfying(CreateInstance.class, createInstance -> {
                assertThat(createInstance.getTypeName()).isEqualTo("flow::DataFlow");
                assertThat(createInstance.getReferenceName()).isEqualTo("elements");
                assertThat(createInstance.getVariableName()).isEqualTo("newFlow");
            });
        });
    }
}

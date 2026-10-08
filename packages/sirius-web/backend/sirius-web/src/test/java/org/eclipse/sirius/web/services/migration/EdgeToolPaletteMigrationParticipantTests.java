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
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
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
 * Integration tests of EdgeToolPaletteMigrationParticipant.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EdgeToolPaletteMigrationParticipantTests extends AbstractIntegrationTests {

    private static final String NEW_FLOW_EXPRESSION = "aql:newFlow";

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
    @DisplayName("Given legacy connector tools, when the diagram is loaded, then each tool has a same-named palette node tool with its former body")
    public void givenLegacyConnectorToolsWhenTheDiagramIsLoadedThenEachToolHasASameNamedPaletteNodeToolWithItsFormerBody() {
        var editingContext = (EditingContext) this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_STUDIO_EDITING_CONTEXT_ID).orElseThrow();
        var diagramDescription = editingContext.getViews().stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> MigrationIdentifiers.MIGRATION_EDGE_TOOL_PALETTE_STUDIO_DIAGRAM.equals(description.getName()))
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .findFirst()
                .orElseThrow();

        var nodeDescription = diagramDescription.getNodeDescriptions().get(0);
        var edgeDescription = diagramDescription.getEdgeDescriptions().get(0);
        assertThat(nodeDescription.getEdgeTools()).hasSize(2);
        assertThat(edgeDescription.getEdgeTools()).hasSize(2);
        assertThat(nodeDescription.getEdgeTools()).allSatisfy(this::assertPaletteNodeTool);
        assertThat(edgeDescription.getEdgeTools()).allSatisfy(this::assertPaletteNodeTool);

        var firstNodeTool = nodeDescription.getEdgeTools().get(0);
        assertThat(firstNodeTool.getPreconditionExpression()).isEqualTo("aql:self <> target");
        assertThat(firstNodeTool.getPalette().getNodeTools().get(0).getPreconditionExpression()).isEqualTo("aql:self <> target");
        assertThat(firstNodeTool.getIconURLsExpression()).isEqualTo("/icons/connector.svg");
        assertThat(firstNodeTool.getPalette().getNodeTools().get(0).getIconURLsExpression()).isEqualTo("/icons/connector.svg");
        assertThat(firstNodeTool.getElementsToSelectExpression()).isEqualTo(NEW_FLOW_EXPRESSION);
        assertThat(firstNodeTool.getPalette().getNodeTools().get(0).getElementsToSelectExpression()).isEqualTo(NEW_FLOW_EXPRESSION);
        assertThat(firstNodeTool.getDialogDescription()).isNull();
        assertThat(firstNodeTool.getPalette().getNodeTools().get(0).getDialogDescription()).isInstanceOfSatisfying(SelectionDialogDescription.class,
                dialogDescription -> assertThat(dialogDescription.getDescriptionExpression()).isEqualTo("Select a target"));
        assertThat(firstNodeTool.getPalette().getNodeTools().get(0).getBody())
                .extracting(operation -> ((ChangeContext) operation).getExpression())
                .containsExactly("aql:source", "aql:target");
        var firstEdgeTool = edgeDescription.getEdgeTools().get(0);
        assertThat(firstEdgeTool.getPalette().getNodeTools().get(0).getBody())
                .extracting(operation -> ((ChangeContext) operation).getExpression())
                .containsExactly("aql:first", "aql:second");
        assertThat(nodeDescription.getEdgeTools().get(1).getPalette().getNodeTools().get(0).getBody()).isEmpty();
        assertThat(edgeDescription.getEdgeTools().get(1).getPalette().getNodeTools().get(0).getBody()).isEmpty();
    }

    private void assertPaletteNodeTool(EdgeTool edgeTool) {
        assertThat(edgeTool.getBody()).isEmpty();
        assertThat(edgeTool.getPalette()).isNotNull();
        assertThat(edgeTool.getPalette().eContainer()).isSameAs(edgeTool);
        assertThat(edgeTool.getPalette().getNodeTools()).singleElement().satisfies(nodeTool -> {
            assertThat(nodeTool.getName()).isEqualTo(edgeTool.getName());
            assertThat(nodeTool.eContainer()).isSameAs(edgeTool.getPalette());
        });
    }
}

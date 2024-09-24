/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.emf.ViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Unit tests of the ViewPaletteProvider.
 *
 * @author frouene
 */
public class ViewPaletteProviderTests {

    public static final String PRECONDITION_EXPRESSION_FALSE = "false";

    private static EdgeDescription getEdgeDescription() {
        EdgeDescription edgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        EdgePalette edgePalette = DiagramFactory.eINSTANCE.createEdgePalette();
        edgeDescription.setPalette(edgePalette);
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        edgePalette.getNodeTools().add(nodeTool);
        NodeTool nodeToolWithFalsePrecondition = DiagramFactory.eINSTANCE.createNodeTool();
        nodeToolWithFalsePrecondition.setPreconditionExpression(PRECONDITION_EXPRESSION_FALSE);
        edgePalette.getNodeTools().add(nodeToolWithFalsePrecondition);
        EdgeToolSection toolSection = DiagramFactory.eINSTANCE.createEdgeToolSection();
        edgePalette.getToolSections().add(toolSection);
        NodeTool nodeToolInToolSection = DiagramFactory.eINSTANCE.createNodeTool();
        toolSection.getNodeTools().add(nodeToolInToolSection);
        return edgeDescription;
    }

    private static org.eclipse.sirius.components.view.diagram.NodeDescription getNodeDescription() {
        org.eclipse.sirius.components.view.diagram.NodeDescription nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        NodePalette nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        NodeTool nodeToolWithFalsePrecondition = DiagramFactory.eINSTANCE.createNodeTool();
        nodeToolWithFalsePrecondition.setPreconditionExpression(PRECONDITION_EXPRESSION_FALSE);
        EdgeTool edgeTool = DiagramFactory.eINSTANCE.createEdgeTool();
        EdgeTool edgeToolWithFalsePrecondition = DiagramFactory.eINSTANCE.createEdgeTool();
        edgeToolWithFalsePrecondition.setPreconditionExpression(PRECONDITION_EXPRESSION_FALSE);
        nodePalette.getNodeTools().add(nodeTool);
        nodePalette.getEdgeTools().add(edgeTool);
        nodePalette.getNodeTools().add(nodeToolWithFalsePrecondition);
        nodePalette.getEdgeTools().add(edgeToolWithFalsePrecondition);
        NodeToolSection toolSection = DiagramFactory.eINSTANCE.createNodeToolSection();
        nodePalette.getToolSections().add(toolSection);
        NodeTool nodeToolInToolSection = DiagramFactory.eINSTANCE.createNodeTool();
        toolSection.getNodeTools().add(nodeToolInToolSection);
        EdgeTool edgeToolInToolSection = DiagramFactory.eINSTANCE.createEdgeTool();
        toolSection.getEdgeTools().add(edgeToolInToolSection);
        return nodeDescription;
    }

    private static org.eclipse.sirius.components.view.diagram.DiagramDescription getDiagramDescription() {
        org.eclipse.sirius.components.view.diagram.DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        DiagramPalette diagramPalette = DiagramFactory.eINSTANCE.createDiagramPalette();
        diagramDescription.setPalette(diagramPalette);
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        diagramPalette.getNodeTools().add(nodeTool);
        NodeTool nodeToolWithFalsePrecondition = DiagramFactory.eINSTANCE.createNodeTool();
        nodeToolWithFalsePrecondition.setPreconditionExpression(PRECONDITION_EXPRESSION_FALSE);
        diagramPalette.getNodeTools().add(nodeToolWithFalsePrecondition);
        DiagramToolSection diagramToolSection = DiagramFactory.eINSTANCE.createDiagramToolSection();
        diagramPalette.getToolSections().add(diagramToolSection);
        NodeTool nodeToolInToolSection = DiagramFactory.eINSTANCE.createNodeTool();
        diagramToolSection.getNodeTools().add(nodeToolInToolSection);
        return diagramDescription;
    }

    @Test
    public void getDiagramPaletteTest() {
        ViewPaletteProvider viewPaletteProvider = this.createViewPaletteProvider();

        DiagramDescription diagramDescription = this.createDiagramDescription();

        var diagram = new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString());
        var result = viewPaletteProvider.handle(null, diagram, diagramDescription, diagramDescription, new IEditingContext.NoOp());

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("siriusComponents://diagramPalette?diagramId=sourceElementId");
        assertThat(result.paletteEntries()).filteredOn(ITool.class::isInstance).hasSize(1);
        var tools = result.paletteEntries().stream().filter(ITool.class::isInstance).map(ITool.class::cast).toList();
        assertThat(tools.get(0)).isInstanceOf(SingleClickOnDiagramElementTool.class);
        assertThat(((SingleClickOnDiagramElementTool) tools.get(0)).appliesToDiagramRoot()).isTrue();
        assertThat(result.paletteEntries()).filteredOn(ToolSection.class::isInstance).hasSize(1);
        var toolSections = result.paletteEntries().stream().filter(ToolSection.class::isInstance).map(ToolSection.class::cast).toList();
        assertThat(toolSections.get(0).tools()).hasSize(1);
        assertThat(toolSections.get(0).tools().get(0)).isInstanceOf(SingleClickOnDiagramElementTool.class);
        assertThat(((SingleClickOnDiagramElementTool) toolSections.get(0).tools().get(0)).appliesToDiagramRoot()).isTrue();
    }

    @Test
    public void getNodePaletteTest() {
        ViewPaletteProvider viewPaletteProvider = this.createViewPaletteProvider();

        var node = new TestDiagramBuilder().getNode(UUID.randomUUID().toString(), true);
        var result = viewPaletteProvider.handle(null, node, this.createNodeDescription(), this.createDiagramDescription(), new IEditingContext.NoOp());

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("siriusComponents://nodePalette?nodeId=sourceElementId");
        assertThat(result.paletteEntries()).filteredOn(ITool.class::isInstance).hasSize(2);
        var tools = result.paletteEntries().stream().filter(ITool.class::isInstance).map(ITool.class::cast).toList();
        assertThat(tools.get(0)).isInstanceOf(SingleClickOnDiagramElementTool.class);
        assertThat(((SingleClickOnDiagramElementTool) tools.get(0)).appliesToDiagramRoot()).isFalse();
        assertThat(tools.get(1)).isInstanceOf(SingleClickOnTwoDiagramElementsTool.class);
        assertThat(((SingleClickOnTwoDiagramElementsTool) tools.get(1)).candidates()).isNotEmpty();
        assertThat(result.paletteEntries()).filteredOn(ToolSection.class::isInstance).hasSize(2);
        var toolSections = result.paletteEntries().stream().filter(ToolSection.class::isInstance).map(ToolSection.class::cast).toList();
        assertThat(toolSections.get(0).tools()).hasSize(2);
    }

    @Test
    public void getEdgePaletteTest() {
        ViewPaletteProvider viewPaletteProvider = this.createViewPaletteProvider();
        org.eclipse.sirius.components.diagrams.description.EdgeDescription edgeDescription =
                org.eclipse.sirius.components.diagrams.description.EdgeDescription.newEdgeDescription("edgeDescriptionId")
                        .deleteHandler(vm -> new Success())
                        .labelEditHandler((vm, edgeKind, newLabel) -> new Success())
                        .semanticElementsProvider(vm -> List.of(new Object()))
                        .sourceNodeDescriptions(List.of())
                        .sourceNodesProvider(vm -> List.of())
                        .styleProvider(vm -> null)
                        .targetNodeDescriptions(List.of())
                        .targetNodesProvider(vm -> List.of())
                        .targetObjectIdProvider(vm -> "")
                        .targetObjectKindProvider(vm -> "")
                        .targetObjectLabelProvider(vm -> "")
                        .build();

        var edge = new TestDiagramBuilder().getEdge(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        var result = viewPaletteProvider.handle(null, edge, edgeDescription, this.createDiagramDescription(), new IEditingContext.NoOp());

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("siriusComponents://edgePalette?edgeId=sourceElementId");
        assertThat(result.paletteEntries()).filteredOn(ITool.class::isInstance).hasSize(1);
        var tool = result.paletteEntries().stream().filter(ITool.class::isInstance).map(ITool.class::cast).findFirst().orElse(null);
        assertThat(tool).isInstanceOf(SingleClickOnDiagramElementTool.class);
        assertThat(((SingleClickOnDiagramElementTool) tool).appliesToDiagramRoot()).isFalse();
        assertThat(result.paletteEntries()).filteredOn(ToolSection.class::isInstance).hasSize(2);
        var toolSection = result.paletteEntries().stream().filter(ToolSection.class::isInstance).map(ToolSection.class::cast).findFirst().orElse(null);
        assertThat(toolSection.tools()).hasSize(1);
    }

    private ViewPaletteProvider createViewPaletteProvider() {
        IURLParser urlParser = new URLParser() {
            @Override
            public Map<String, List<String>> getParameterValues(String kind) {
                return Map.of(IDiagramIdProvider.SOURCE_ELEMENT_ID, List.of("sourceElementId"));
            }
        };

        IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService = new IViewDiagramDescriptionSearchService() {

            @Override
            public Optional<org.eclipse.sirius.components.view.diagram.DiagramDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                org.eclipse.sirius.components.view.diagram.DiagramDescription diagramDescription = getDiagramDescription();
                return Optional.of(diagramDescription);
            }

            @Override
            public Optional<org.eclipse.sirius.components.view.diagram.NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String nodeDescriptionId) {
                org.eclipse.sirius.components.view.diagram.NodeDescription nodeDescription = getNodeDescription();
                return Optional.of(nodeDescription);
            }

            @Override
            public Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {
                EdgeDescription edgeDescription = getEdgeDescription();
                return Optional.of(edgeDescription);
            }
        };

        return new ViewPaletteProvider(urlParser, representationDescription -> true, viewDiagramDescriptionSearchService, new IDiagramDescriptionService.NoOp(),
                new IDiagramIdProvider.NoOp(), new ViewAQLInterpreterFactory(List.of(), new StaticApplicationContext()));
    }

    private DiagramDescription createDiagramDescription() {
        return DiagramDescription.newDiagramDescription("diagramDescriptionID")
                .label("DiagramDescriptionTest")
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .canCreatePredicate(variableManager -> true)
                .labelProvider(variableManager -> "Diagram")
                .palettes(List.of())
                .nodeDescriptions(List.of())
                .edgeDescriptions(List.of())
                .dropHandler(variableManager -> new Failure(""))
                .build();
    }

    private NodeDescription createNodeDescription() {
        LabelStyleDescription styleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "")
                .fontSizeProvider(variableManager -> 0)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .maxWidthProvider(variableManager -> null)
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("nodeId")
                .idProvider(variableManager -> "")
                .textProvider(variableManager -> "")
                .styleDescriptionProvider(variableManager -> styleDescription)
                .isHeaderProvider(vm -> false)
                .headerSeparatorDisplayModeProvider(vm -> HeaderSeparatorDisplayMode.NEVER)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        return NodeDescription.newNodeDescription("nodeId")
                .typeProvider(variableManager -> "")
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .semanticElementsProvider(variableManager -> List.of())
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(variableManager -> null)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
    }
}

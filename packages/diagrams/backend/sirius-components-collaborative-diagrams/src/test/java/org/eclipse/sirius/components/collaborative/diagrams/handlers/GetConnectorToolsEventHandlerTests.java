/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.Palette;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Test for {@link GetConnectorToolsEventHandler}.
 *
 * @author nvannier
 */
public class GetConnectorToolsEventHandlerTests {

    private static final String NODE_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final UUID DIAGRAM_DESCRIPTION_ID = UUID.randomUUID();

    private static final String DIAGRAM_LABEL = "DiagramLabel";

    private static final String TOOLSECTION_ID = "ToolSection";

    private static final String CONNECTOR_TOOL_ID = "ConnectorTool";

    private static final String NOT_CONNECTOR_TOOL_ID = "NotConnectorTool";

    private static final String TOOLSECTION_LABEL = "ToolSectionLabel";

    private static final String CONNECTOR_TOOL_LABEL = "ConnectorToolLabel";

    private static final String NOT_CONNECTOR_TOOL_LABEL = "NotConnectorToolLabel";

    private static final String DIAGRAM_ID = "diagramId";

    private static final String SOURCE_NODE_ID = "sourceNodeId";

    private static final String TARGET_NODE_ID = "targetNodeId";

    private static final String SOURCE_NODE_TARGET_ID = "sourceNodeTargetId";

    private static final String TARGET_NODE_TARGET_ID = "targetNodeTargetId";

    private Node getNode(String id, String targetObjectId) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("#000000")
                .fontSize(16)
                .iconURL(List.of())
                .build();
        InsideLabel insideLabel = InsideLabel.newLabel(UUID.randomUUID().toString())
                .text("text")
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(false)
                .displayHeaderSeparator(false)
                .build();

        return Node.newNode(id)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId(targetObjectId)
                .targetObjectKind("")
                .targetObjectLabel("")
                .descriptionId(NODE_DESCRIPTION_ID)
                .insideLabel(insideLabel)
                .style(new TestDiagramBuilder().getRectangularNodeStyle())
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .borderNodes(List.of())
                .childNodes(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .build();
    }

    @Test
    public void testGetConnectorTools() {
        NodeDescription nodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(NODE_DESCRIPTION_ID, variableManager -> List.of());

        SingleClickOnTwoDiagramElementsCandidate candidates = SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(nodeDescription))
                .targets(List.of(nodeDescription))
                .build();

        SingleClickOnTwoDiagramElementsTool connectorTool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(CONNECTOR_TOOL_ID)
                .candidates(List.of(candidates))
                .handler(variableManager -> new Success())
                .label(CONNECTOR_TOOL_LABEL)
                .iconURL(List.of())
                .build();

        SingleClickOnTwoDiagramElementsTool notConnectorTool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(NOT_CONNECTOR_TOOL_ID)
                .candidates(List.of(candidates))
                .handler(variableManager -> new Success())
                .label(NOT_CONNECTOR_TOOL_LABEL)
                .iconURL(List.of())
                .build();

        Palette palette = Palette.newPalette(TOOLSECTION_ID)
                .tools(List.of(connectorTool, notConnectorTool))
                .toolSections(List.of())
                .build();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(DIAGRAM_DESCRIPTION_ID.toString())
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> DIAGRAM_LABEL)
                .nodeDescriptions(List.of(nodeDescription))
                .edgeDescriptions(new ArrayList<>())
                .palettes(List.of(palette))
                .dropHandler(variableManager -> new Failure(""))
                .build();

        Node sourceNode = this.getNode(SOURCE_NODE_ID, SOURCE_NODE_TARGET_ID);
        Node targetNode = this.getNode(TARGET_NODE_ID, TARGET_NODE_TARGET_ID);
        Diagram diagram = this.getDiagram(DIAGRAM_ID, List.of(sourceNode, targetNode));

        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String id) {
                return Optional.of(diagramDescription);
            }
        };

        ICollaborativeMessageService messageService = new ICollaborativeMessageService.NoOp();

        IDiagramContext diagramContext = new IDiagramContext.NoOp() {
            @Override
            public Diagram getDiagram() {
                return diagram;
            }
        };

        IConnectorToolsProvider connectorToolsProvider = new IConnectorToolsProvider() {

            @Override
            public List<ITool> getConnectorTools(Object sourceDiagramElement, Object targetDiagramElement, Diagram diagram, IEditingContext editingContext) {
                return List.of(connectorTool);
            }

            @Override
            public boolean canHandle(DiagramDescription diagramDescription) {
                return diagramDescription.getId().equals(DIAGRAM_DESCRIPTION_ID.toString());
            }
        };

        var handler = new GetConnectorToolsEventHandler(representationDescriptionSearchService, new DiagramQueryService(), List.of(connectorToolsProvider), messageService, new SimpleMeterRegistry());

        /*
         * Testing the valid tool.
         */
        var input = new GetConnectorToolsInput(UUID.randomUUID(), UUID.randomUUID().toString(), DIAGRAM_ID, SOURCE_NODE_ID, TARGET_NODE_ID);

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetConnectorToolsSuccessPayload.class);
        List<ITool> connectorTools = ((GetConnectorToolsSuccessPayload) payload).connectorTools();
        assertThat(connectorTools.size()).isEqualTo(1);
        assertThat(connectorTools.get(0)).isEqualTo(connectorTool);
    }

    private Diagram getDiagram(String id, List<Node> nodes) {
        return Diagram.newDiagram(id)
                .label(DIAGRAM_LABEL)
                .descriptionId(DIAGRAM_DESCRIPTION_ID.toString())
                .targetObjectId("diagramTargetObjectId")
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .nodes(nodes)
                .edges(List.of())
                .build();
    }
}

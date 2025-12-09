/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LabelVisibility;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.BorderNodePosition;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
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
                .background("transparent")
                .borderColor("black")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .visibility(LabelVisibility.visible)
                .build();
        InsideLabel insideLabel = InsideLabel.newLabel(UUID.randomUUID().toString())
                .text("text")
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(false)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .customizedStyleProperties(Set.of())
                .build();

        return Node.newNode(id)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId(targetObjectId)
                .targetObjectKind("")
                .targetObjectLabel("")
                .descriptionId(NODE_DESCRIPTION_ID)
                .insideLabel(insideLabel)
                .style(new TestDiagramBuilder().getRectangularNodeStyle())
                .borderNodes(List.of())
                .childNodes(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .customizedStyleProperties(Set.of())
                .initialBorderNodePosition(BorderNodePosition.EAST)
                .build();
    }

    @Test
    public void testGetConnectorTools() {
        NodeDescription nodeDescription = new TestDiagramDescriptionBuilder().getNodeDescription(NODE_DESCRIPTION_ID, variableManager -> List.of());
        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(DIAGRAM_DESCRIPTION_ID.toString())
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> DIAGRAM_LABEL)
                .nodeDescriptions(List.of(nodeDescription))
                .edgeDescriptions(new ArrayList<>())
                .dropHandler(variableManager -> new Failure(""))
                .iconURLsProvider(variableManager -> List.of())
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

        IConnectorToolsProvider connectorToolsProvider = new IConnectorToolsProvider() {

            @Override
            public List<ITool> getConnectorTools(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement, Object targetDiagramElement) {
                SingleClickOnTwoDiagramElementsCandidate candidates = SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                        .sources(List.of(nodeDescription))
                        .targets(List.of(nodeDescription))
                        .build();

                SingleClickOnTwoDiagramElementsTool connectorTool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(CONNECTOR_TOOL_ID)
                        .candidates(List.of(candidates))
                        .label(CONNECTOR_TOOL_LABEL)
                        .iconURL(List.of())
                        .build();
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

        assertThat(handler.canHandle(null, input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, new DiagramContext(diagram), input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetConnectorToolsSuccessPayload.class);
        List<ITool> connectorTools = ((GetConnectorToolsSuccessPayload) payload).connectorTools();
        assertThat(connectorTools.size()).isEqualTo(1);
    }

    private Diagram getDiagram(String id, List<Node> nodes) {
        return Diagram.newDiagram(id)
                .descriptionId(DIAGRAM_DESCRIPTION_ID.toString())
                .targetObjectId("diagramTargetObjectId")
                .nodes(nodes)
                .edges(List.of())
                .build();
    }
}

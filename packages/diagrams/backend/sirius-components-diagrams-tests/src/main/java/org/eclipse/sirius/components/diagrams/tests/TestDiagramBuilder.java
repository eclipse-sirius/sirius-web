/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.tests;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.Success;

/**
 * Utility class used to help build diagrams for unit tests.
 *
 * @author sbegaudeau
 */
public class TestDiagramBuilder {

    public static final String IMAGE_PNG = "/image.png";

    public static final String TOOL_IMAGE_URL = IMAGE_PNG;

    public static final String TOOL_LABEL = "toolLabel";

    public Diagram getDiagram(String id) {
        return Diagram.newDiagram(id)
                .label("diagramLabel")
                .descriptionId(UUID.randomUUID().toString())
                .targetObjectId("diagramTargetObjectId")
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .nodes(List.of())
                .edges(List.of())
                .build();
    }

    public RectangularNodeStyle getRectangularNodeStyle() {
        return RectangularNodeStyle.newRectangularNodeStyle()
                .borderColor("#000000")
                .borderSize(1)
                .borderStyle(LineStyle.Solid)
                .color("#FFFFFF")
                .build();
    }

    public ImageNodeStyle getImageNodeStyle() {
        return ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(-1)
                .build();
    }

    public Node getNode(String id, boolean withLabel) {
        Node.Builder nodeBuilder = Node.newNode(id)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId("nodeTargetObjectId")
                .targetObjectKind("")
                .targetObjectLabel("")
                .descriptionId(UUID.randomUUID().toString())
                .style(this.getRectangularNodeStyle())
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .borderNodes(List.of())
                .childNodes(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED);

        if (withLabel) {
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
            nodeBuilder.insideLabel(insideLabel);
        }

        return nodeBuilder.build();
    }

    public Edge getEdge(String id, String sourceId, String targetId) {
        EdgeStyle style = EdgeStyle.newEdgeStyle()
                .size(1)
                .lineStyle(LineStyle.Solid)
                .sourceArrow(ArrowStyle.None)
                .targetArrow(ArrowStyle.InputArrow)
                .color("#FFFFFF")
                .build();

        return Edge.newEdge(id)
                .type("edgeType")
                .targetObjectId("edgeTargetObjectId")
                .targetObjectKind("")
                .targetObjectLabel("")
                .descriptionId(UUID.randomUUID().toString())
                .sourceId(sourceId)
                .targetId(targetId)
                .style(style)
                .routingPoints(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .sourceAnchorRelativePosition(Ratio.UNDEFINED)
                .targetAnchorRelativePosition(Ratio.UNDEFINED)
                .build();
    }

    public SingleClickOnDiagramElementTool getNodeTool(String id) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(TOOL_LABEL)
                .iconURL(List.of(TOOL_IMAGE_URL))
                .handler(variableManager -> new Success())
                .targetDescriptions(List.of())
                .build();
    }
}

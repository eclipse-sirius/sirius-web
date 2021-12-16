/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.web.diagrams.tests;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.tools.CreateNodeTool;
import org.eclipse.sirius.web.representations.Success;

/**
 * Utility class used to help build diagrams for unit tests.
 *
 * @author sbegaudeau
 */
public class TestDiagramBuilder {

    public static final String IMAGE_PNG = "/image.png"; //$NON-NLS-1$

    public static final String TOOL_IMAGE_URL = IMAGE_PNG;

    public static final String TOOL_LABEL = "toolLabel"; //$NON-NLS-1$

    public Diagram getDiagram(String id) {
        // @formatter:off
        return Diagram.newDiagram(id)
                .targetObjectId("diagramTargetObjectId") //$NON-NLS-1$
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .nodes(List.of())
                .edges(List.of())
                .build();
        // @formatter:on
    }

    public RectangularNodeStyle getRectangularNodeStyle() {
        // @formatter:off
        return RectangularNodeStyle.newRectangularNodeStyle()
                .borderColor("#000000") //$NON-NLS-1$
                .borderSize(1)
                .borderStyle(LineStyle.Solid)
                .color("#FFFFFF") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    public ImageNodeStyle getImageNodeStyle() {
        // @formatter:off
        return ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(-1)
                .build();
        // @formatter:on
    }

    public Node getNode(String id) {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("#000000") //$NON-NLS-1$
                .fontSize(16)
                .iconURL("") //$NON-NLS-1$
                .build();
        Label label = Label.newLabel(UUID.randomUUID().toString())
                .type("labelType") //$NON-NLS-1$
                .text("text") //$NON-NLS-1$
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(labelStyle)
                .build();

        return Node.newNode(id)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId("nodeTargetObjectId") //$NON-NLS-1$
                .targetObjectKind("") //$NON-NLS-1$
                .targetObjectLabel("") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID())
                .label(label)
                .style(this.getRectangularNodeStyle())
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .borderNodes(List.of())
                .childNodes(List.of())
                .build();
        // @formatter:on
    }

    public Edge getEdge(String id, String sourceId, String targetId) {
        // @formatter:off
        EdgeStyle style = EdgeStyle.newEdgeStyle()
                .size(1)
                .lineStyle(LineStyle.Solid)
                .sourceArrow(ArrowStyle.None)
                .targetArrow(ArrowStyle.InputArrow)
                .color("#FFFFFF") //$NON-NLS-1$
                .build();

        return Edge.newEdge(id)
                .type("edgeType") //$NON-NLS-1$
                .targetObjectId("edgeTargetObjectId") //$NON-NLS-1$
                .targetObjectKind("") //$NON-NLS-1$
                .targetObjectLabel("") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID())
                .sourceId(sourceId)
                .targetId(targetId)
                .style(style)
                .routingPoints(List.of())
                .build();
        // @formatter:on
    }

    public CreateNodeTool getNodeTool(String id) {
        // @formatter:off
        return CreateNodeTool.newCreateNodeTool(id)
                .label(TOOL_LABEL)
                .imageURL(TOOL_IMAGE_URL)
                .handler(variableManager -> new Success())
                .targetDescriptions(List.of())
                .build();
        // @formatter:on
    }
}

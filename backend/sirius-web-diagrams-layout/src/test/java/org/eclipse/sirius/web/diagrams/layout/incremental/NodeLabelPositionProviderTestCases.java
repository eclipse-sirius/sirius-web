/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.TextBoundsProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.junit.Test;

/**
 * Test cases for {@link NodeLabelPositionProvider}.
 *
 * @author wpiers
 */
public class NodeLabelPositionProviderTestCases {

    private static final int FONT_SIZE = 16;

    private static final String LABEL_COLOR = "#000000"; //$NON-NLS-1$

    private static final String ICON_URL = ""; //$NON-NLS-1$

    private static final Size DEFAULT_NODE_SIZE = Size.of(150, 70);

    @Test
    public void testNodeImageLabelBoundsPosition() {
        DiagramLayoutData createDiagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(0, 0), DEFAULT_NODE_SIZE, createDiagramLayoutData, NodeType.NODE_IMAGE);
        NodeLabelPositionProvider labelBoundsProvider = new NodeLabelPositionProvider();
        LabelLayoutData labelLayoutData = this.createLabelLayoutData();

        Position position = labelBoundsProvider.getPosition(nodeLayoutData, labelLayoutData);
        assertThat(position).extracting(Position::getX).isEqualTo(Double.valueOf(42.5390625));
        assertThat(position).extracting(Position::getY).isEqualTo(Double.valueOf(-23.3984375));
    }

    @Test
    public void testNodeRectangleLabelBoundsPosition() {
        DiagramLayoutData createDiagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(0, 0), DEFAULT_NODE_SIZE, createDiagramLayoutData, NodeType.NODE_RECTANGLE);
        NodeLabelPositionProvider labelBoundsProvider = new NodeLabelPositionProvider();
        LabelLayoutData labelLayoutData = this.createLabelLayoutData();
        Position position = labelBoundsProvider.getPosition(nodeLayoutData, labelLayoutData);
        assertThat(position).extracting(Position::getX).isEqualTo(Double.valueOf(42.5390625));
        assertThat(position).extracting(Position::getY).isEqualTo(Double.valueOf(5));
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private LabelLayoutData createLabelLayoutData() {
        LabelLayoutData labelLayoutData = new LabelLayoutData();
        labelLayoutData.setId(UUID.randomUUID());
        labelLayoutData.setPosition(Position.UNDEFINED);
        //@formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(LABEL_COLOR)
                .fontSize(FONT_SIZE)
                .iconURL(ICON_URL)
                .build();
        //@formatter:on
        TextBounds textBounds = new TextBoundsProvider().computeBounds(labelStyle, "labelText"); //$NON-NLS-1$
        labelLayoutData.setTextBounds(textBounds);
        return labelLayoutData;
    }

    private NodeLayoutData createNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(nodeType);
        return nodeLayoutData;
    }

}

/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.updater;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link ContainmentUpdater}.
 *
 * @author gcoutable
 */
public class ContainmentUpdaterTests {

    private static final double LABEL_HEIGHT = 20d;

    private static final double NODE_LIST_ITEM_LABEL_PADDING_LEFT = 6d;

    private final ContainmentUpdater containmentUpdater = new ContainmentUpdater();

    @Test
    public void testIncreaseNodeListSizeBecauseNodeListLabelBecomeTheWidest() {
        // Create a consistent node list with two list items
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeListLayoutData(20, new double[] { 40, 30 }, diagramLayoutData);
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(58, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(nodeListLayoutData.getChildrenNodes()).hasSize(2);
        NodeLayoutData firstNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(0);
        NodeLayoutData secondNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(1);
        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(58, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(58, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();

        // Simulate the the node list label size increase to makes it the widest label
        nodeListLayoutData.getLabel().setTextBounds(new TextBounds(Size.of(90, LABEL_HEIGHT), Position.UNDEFINED));

        // Update the node list content
        this.containmentUpdater.update(nodeListLayoutData);

        // Check the update
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(100, 96));
        assertThat(nodeListLayoutData.hasChanged()).isTrue();

        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(100, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isTrue();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(100, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isTrue();
    }

    @Test
    public void testDecreaseNodeListSizeBecauseNodeListLabelBecomeTheWidest() {
        // Create a consistent node list with two list items
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeListLayoutData(70, new double[] { 40, 100 }, diagramLayoutData);
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(118, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(nodeListLayoutData.getChildrenNodes()).hasSize(2);
        NodeLayoutData firstNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(0);
        NodeLayoutData secondNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(1);
        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();

        // Simulate the second node list item label size decrease to makes the node list label the widest label
        nodeListLayoutData.getChildrenNodes().get(1).getLabel().setTextBounds(new TextBounds(Size.of(30, LABEL_HEIGHT), Position.UNDEFINED));

        // Update the node list content
        this.containmentUpdater.update(nodeListLayoutData);

        // Check the update
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(80, 96));
        assertThat(nodeListLayoutData.hasChanged()).isTrue();

        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isTrue();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isTrue();
    }

    @Test
    public void testIncreaseNodeListSizeBecauseNodeListItemLabelBecomeTheWidest() {
        // Create a consistent node list with two list items
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeListLayoutData(70, new double[] { 40, 30 }, diagramLayoutData);
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(80, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(nodeListLayoutData.getChildrenNodes()).hasSize(2);
        NodeLayoutData firstNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(0);
        NodeLayoutData secondNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(1);
        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();

        // Simulate the first node list item label size increase to makes it the widest label
        nodeListLayoutData.getChildrenNodes().get(0).getLabel().setTextBounds(new TextBounds(Size.of(100, LABEL_HEIGHT), Position.UNDEFINED));

        // Update the node list content
        this.containmentUpdater.update(nodeListLayoutData);

        // Check the update
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(118, 96));
        assertThat(nodeListLayoutData.hasChanged()).isTrue();

        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isTrue();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isTrue();

    }

    @Test
    public void testDecreaseNodeListSizeBecauseNodeListItemLabelBecomeTheWidest() {
        // Create a consistent node list with two list items
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeListLayoutData(70, new double[] { 40, 30 }, diagramLayoutData);
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(80, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(nodeListLayoutData.getChildrenNodes()).hasSize(2);
        NodeLayoutData firstNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(0);
        NodeLayoutData secondNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(1);
        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(80, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();

        // Simulate the node list label size decrease to make the first node list item label the widest label
        nodeListLayoutData.getLabel().setTextBounds(new TextBounds(Size.of(20, LABEL_HEIGHT), Position.UNDEFINED));

        // Update the node list content
        this.containmentUpdater.update(nodeListLayoutData);

        // Check the update
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(58, 96));
        assertThat(nodeListLayoutData.hasChanged()).isTrue();

        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(58, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isTrue();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(58, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isTrue();
    }

    @Test
    public void testTheChangeDoesNotUpdateTheNodeListSize() {
        // Create a consistent node list with two list items
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeListLayoutData(70, new double[] { 40, 100 }, diagramLayoutData);
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(118, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(nodeListLayoutData.getChildrenNodes()).hasSize(2);
        NodeLayoutData firstNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(0);
        NodeLayoutData secondNodeListItemLayoutData = nodeListLayoutData.getChildrenNodes().get(1);
        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();

        // Decrease the node list label size
        nodeListLayoutData.getLabel().setTextBounds(new TextBounds(Size.of(60, LABEL_HEIGHT), Position.UNDEFINED));

        // Update the node list content
        this.containmentUpdater.update(nodeListLayoutData);

        // Check the update
        assertThat(nodeListLayoutData.getSize()).isEqualTo(Size.of(118, 96));
        assertThat(nodeListLayoutData.hasChanged()).isFalse();

        assertThat(firstNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(firstNodeListItemLayoutData.hasChanged()).isFalse();
        assertThat(secondNodeListItemLayoutData.getSize()).isEqualTo(Size.of(118, LABEL_HEIGHT));
        assertThat(secondNodeListItemLayoutData.hasChanged()).isFalse();
    }

    private NodeLayoutData createNodeListItem(double labelWidth, NodeLayoutData parent, Optional<NodeLayoutData> optionalPreviousSibling) {
        LabelLayoutData nodeListItemLabel = this.createLabelLayoutData(Size.of(labelWidth, LABEL_HEIGHT), Position.at(NODE_LIST_ITEM_LABEL_PADDING_LEFT, 0));
        Size listItemSize = this.getListItemSize(nodeListItemLabel);

        Position listItemPosition = Position.at(0, 5 + parent.getLabel().getTextBounds().getSize().getHeight() + 13);
        if (optionalPreviousSibling.isPresent()) {
            NodeLayoutData previousSibling = optionalPreviousSibling.get();
            listItemPosition = Position.at(0, previousSibling.getPosition().getY() + previousSibling.getLabel().getTextBounds().getSize().getHeight() + 6);
        }

        NodeLayoutData listItemLayoutData = new NodeLayoutData();
        listItemLayoutData.setId(UUID.randomUUID().toString());
        listItemLayoutData.setParent(parent);
        listItemLayoutData.setSize(listItemSize);
        listItemLayoutData.setLabel(nodeListItemLabel);
        listItemLayoutData.setNodeType(NodeType.NODE_LIST_ITEM);
        listItemLayoutData.setPosition(listItemPosition);
        return listItemLayoutData;
    }

    private Size getListItemSize(LabelLayoutData label) {
        return Size.of(label.getPosition().getX() + label.getTextBounds().getSize().getWidth() + 12, label.getTextBounds().getSize().getHeight());
    }

    private LabelLayoutData createLabelLayoutData(Size labelSize, Position labelPosition) {
        LabelLayoutData labelLayoutData = new LabelLayoutData();
        labelLayoutData.setId(UUID.randomUUID().toString());
        labelLayoutData.setLabelType(LabelType.INSIDE_CENTER.getValue());
        labelLayoutData.setPosition(labelPosition);
        TextBounds textBounds = new TextBounds(labelSize, Position.UNDEFINED);
        labelLayoutData.setTextBounds(textBounds);
        return labelLayoutData;
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private NodeLayoutData createNodeListLayoutData(double labelWidth, double[] listItemLabelsWidth, IContainerLayoutData parent) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(Position.at(10, 10));
        nodeLayoutData.setNodeType(NodeType.NODE_LIST);

        LabelLayoutData nodeListLabel = this.createLabelLayoutData(Size.of(labelWidth, LABEL_HEIGHT), Position.at(5, 5));
        nodeLayoutData.setLabel(nodeListLabel);

        List<NodeLayoutData> nodeChildren = new ArrayList<>();
        double nodeHeight = LABEL_HEIGHT + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        double nodeWidth = labelWidth + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING * 2;
        Optional<NodeLayoutData> previousSibling = Optional.empty();
        for (int i = 0; i < listItemLabelsWidth.length; i++) {
            double listItemLabelWidth = listItemLabelsWidth[i];
            NodeLayoutData nodeListItemLayoutData = this.createNodeListItem(listItemLabelWidth, nodeLayoutData, previousSibling);
            nodeChildren.add(nodeListItemLayoutData);
            previousSibling = Optional.of(nodeListItemLayoutData);

            if (i == 0) {
                nodeHeight += LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP;
            }
            if (i > 0 && i < listItemLabelsWidth.length) {
                nodeHeight += LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
            }
            if (i == listItemLabelsWidth.length - 1) {
                nodeHeight += LayoutOptionValues.DEFAULT_ELK_PADDING;
            }
            nodeHeight += nodeListItemLayoutData.getLabel().getTextBounds().getSize().getHeight();

            if (nodeWidth < nodeListItemLayoutData.getSize().getWidth()) {
                nodeWidth = nodeListItemLayoutData.getSize().getWidth();
            }
        }
        nodeLayoutData.setChildrenNodes(nodeChildren);

        if (nodeHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            nodeHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }
        if (nodeWidth < LayoutOptionValues.MIN_WIDTH_CONSTRAINT) {
            nodeWidth = LayoutOptionValues.MIN_WIDTH_CONSTRAINT;
        }

        nodeLayoutData.setSize(Size.of(nodeWidth, nodeHeight));
        nodeChildren.stream().forEach(listItem -> listItem.setSize(Size.of(nodeLayoutData.getSize().getWidth(), listItem.getSize().getHeight())));

        return nodeLayoutData;
    }

}

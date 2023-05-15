/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;

/**
 * Detailed layout information for a node, its label and children.
 *
 * @author pcdavid
 */
public class NodeBox {
    private final Node node;

    private final Rectangle nodeArea;

    private final double labelHeight = 20.0;

    private final Map<String, NodeBox> subNodeBoxes = new HashMap<>();

    // These should be taken from the style
    private final Offsets border = Offsets.empty();

    private final Offsets margin;

    public NodeBox(Node node, Rectangle nodeArea, Offsets margin) {
        this.node = Objects.requireNonNull(node);
        this.nodeArea = Objects.requireNonNull(nodeArea);
        this.margin = Objects.requireNonNull(margin);
    }

    private NodeBox(Node node, Rectangle nodeArea, Offsets margin, Map<String, NodeBox> subNodeBoxes) {
        this.node = Objects.requireNonNull(node);
        this.nodeArea = Objects.requireNonNull(nodeArea);
        this.margin = Objects.requireNonNull(margin);
        this.subNodeBoxes.putAll(subNodeBoxes);
    }

    public String getNodeId() {
        return this.node.getId();
    }

    public Offsets getBorder() {
        return this.border;
    }

    public Offsets getMargin() {
        return this.margin;
    }

    public Rectangle getNodeArea() {
        return this.nodeArea;
    }

    public Rectangle getBorderArea() {
        return this.nodeArea;
    }

    public Rectangle getInsideLabelArea() {
        return this.getBorderArea().shrink(this.border);
    }

    public Rectangle getContentArea() {
        // Temporary workaround to make room for the label
        Offsets insideLabelOffsets = new Offsets(this.labelHeight, 0.0, 0.0, 0.0);
        return this.getInsideLabelArea().shrink(insideLabelOffsets);
    }

    public Rectangle getMarginArea() {
        return this.getBorderArea().expand(this.margin);
    }

    public Rectangle getFootprint() {
        return this.getMarginArea();
    }

    public NodeBox moveTo(Position newPosition) {
        return new NodeBox(this.node, this.nodeArea.moveTo(newPosition), this.margin, this.subNodeBoxes);
    }

    public NodeBox moveBy(double dx, double dy) {
        Position position = this.getNodeArea().topLeft();
        return this.moveTo(new Position(position.x() + dx, position.y() + dy));
    }

    public void setSubNodeBox(String subNodeId, NodeBox subNodeBox) {
        this.subNodeBoxes.put(subNodeId, subNodeBox);
    }

    public Map<String, NodeBox> getSubNodeBoxes() {
        return this.subNodeBoxes;
    }

    public NodeBox expandToFitContent() {
        Rectangle newContentArea = this.subNodeBoxes.values().stream().map(NodeBox::getFootprint).reduce(this.getContentArea(), Rectangle::union);
        Offsets insideLabelOffsets = new Offsets(this.labelHeight, 0.0, 0.0, 0.0);
        Rectangle newNodeArea = newContentArea.expand(insideLabelOffsets).expand(this.border);
        return new NodeBox(this.node, newNodeArea, this.margin, this.subNodeBoxes);
    }

    public NodeLayoutData toNodeLayoutData() {
        return new NodeLayoutData(this.node.getId(), this.nodeArea.topLeft(), this.nodeArea.size());
    }

    @Override
    public String toString() {
        return this.getNodeArea().toString();
    }
}

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.Rectangle;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.DiagramLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layout.api.configuration.NodeLayoutConfiguration;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * All the input information needed to perform a diagram layout.
 *
 * @author pcdavid
 */
public class DiagramLayoutInput {
    private final Diagram diagram;

    private final DiagramLayoutData previousLayoutData;

    private final DiagramLayoutConfiguration diagramLayoutConfiguration;

    private final Optional<IDiagramEvent> optionalDiagramEvent;

    private final Map<String, Node> nodesById = new HashMap<>();

    private final Map<String, String> parentById = new HashMap<>();

    private final Map<String, NodeLayoutConfiguration> nodeLayoutConfigurationsById = new HashMap<>();

    public DiagramLayoutInput(Diagram diagram, DiagramLayoutData previousLayoutData, DiagramLayoutConfiguration diagramLayoutConfiguration, Optional<IDiagramEvent> optionalDiagramEvent) {
        this.diagram = Objects.requireNonNull(diagram);
        this.previousLayoutData = Objects.requireNonNull(previousLayoutData);
        this.diagramLayoutConfiguration = Objects.requireNonNull(diagramLayoutConfiguration);
        this.optionalDiagramEvent = Objects.requireNonNull(optionalDiagramEvent);
        this.indexNodes();
        this.indexNodeLayoutConfigurations();
    }

    private void indexNodes() {
        for (Node node : this.diagram.getNodes()) {
            this.nodesById.put(node.getId(), node);
            this.parentById.put(node.getId(), this.getDiagramId());
            this.indexNodes(node);
        }
    }

    private void indexNodes(Node parent) {
        for (Node node : parent.getChildNodes()) {
            this.nodesById.put(node.getId(), node);
            this.parentById.put(node.getId(), parent.getId());
            this.indexNodes(node);
        }
        for (Node node : parent.getBorderNodes()) {
            this.nodesById.put(node.getId(), node);
            this.parentById.put(node.getId(), parent.getId());
            this.indexNodes(node);
        }
    }

    private void indexNodeLayoutConfigurations() {
        for (NodeLayoutConfiguration config : this.diagramLayoutConfiguration.getChildNodeLayoutConfigurations()) {
            this.nodeLayoutConfigurationsById.put(config.getId(), config);
            this.indexNodeLayoutConfigurations(config);
        }
    }

    private void indexNodeLayoutConfigurations(NodeLayoutConfiguration parentConfig) {
        for (NodeLayoutConfiguration config : parentConfig.getChildNodeLayoutConfigurations()) {
            this.nodeLayoutConfigurationsById.put(config.getId(), config);
            this.indexNodeLayoutConfigurations(config);
        }
        for (NodeLayoutConfiguration config : parentConfig.getBorderNodeLayoutConfigurations()) {
            this.nodeLayoutConfigurationsById.put(config.getId(), config);
            this.indexNodeLayoutConfigurations(config);
        }
    }

    public String getDiagramId() {
        return this.diagram.getId();
    }

    public List<String> getChildNodeIds(String diagramElementId) {
        List<String> result = List.of();
        if (this.diagram.getId().equals(diagramElementId)) {
            result = this.diagram.getNodes().stream().map(Node::getId).toList();
        } else if (this.nodesById.containsKey(diagramElementId)) {
            result = this.nodesById.get(diagramElementId).getChildNodes().stream().map(Node::getId).toList();
        }
        return result;
    }

    private Optional<NodeLayoutData> getPreviousNodeLayout(String nodeId) {
        return Optional.ofNullable(this.previousLayoutData.nodeLayoutData().get(nodeId));
    }

    public Optional<Rectangle> getPreviousBounds(String nodeId) {
        return this.getPreviousNodeLayout(nodeId).map(this::toRectangle);
    }

    public boolean hasPreviousBounds(String nodeId) {
        return this.getPreviousNodeLayout(nodeId).isPresent();
    }

    public Set<String> getNodesWithPreviousBounds(List<String> nodeIds) {
        return nodeIds.stream()
                .filter(this::hasPreviousBounds)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Rectangle toRectangle(NodeLayoutData nodeLayoutData) {
        Position position = nodeLayoutData.position();
        Size size = nodeLayoutData.size();
        return new Rectangle(position.x(), position.y(), size.width(), size.height());
    }

    public NodeLayoutConfiguration getNodeLayoutConfiguration(String nodeId) {
        return this.nodeLayoutConfigurationsById.get(nodeId);
    }

    public Offsets getInternalOffsets(String nodeId) {
        NodeLayoutConfiguration nodeConfiguration = this.getNodeLayoutConfiguration(nodeId);
        if (nodeConfiguration == null) {
            return Offsets.empty();
        } else {
            return nodeConfiguration.getBorder().combine(nodeConfiguration.getPadding());
        }
    }

    public Optional<String> getParentId(String diagramElementId) {
        return Optional.ofNullable(this.parentById.get(diagramElementId));
    }

    public Position getRelativePosition(String parentElementId, Position absolutePosition) {
        if (parentElementId.equals(this.getDiagramId())) {
            return absolutePosition;
        } else {
            Position relativeToParent = this.getRelativePosition(this.getParentId(parentElementId).get(), absolutePosition);
            Position origin = this.getPreviousBounds(parentElementId).get().topLeft();
            return new Position(relativeToParent.x() - origin.x(), relativeToParent.y() - origin.y());
        }
    }

    public Optional<IDiagramEvent> getOptionalDiagramEvent() {
        return this.optionalDiagramEvent;
    }

    public Optional<MoveEvent> getOptionalMoveEvent(String nodeId) {
        return this.optionalDiagramEvent.filter(MoveEvent.class::isInstance).map(MoveEvent.class::cast).filter(moveEvent -> moveEvent.nodeId().equals(nodeId));
    }

    public Optional<ResizeEvent> getOptionalResizeEvent(String nodeId) {
        return this.optionalDiagramEvent.filter(ResizeEvent.class::isInstance).map(ResizeEvent.class::cast).filter(resizeEvent -> resizeEvent.nodeId().equals(nodeId));
    }

    public Optional<SinglePositionEvent> getOptionalSinglePositionEvent(String diagramElementId) {
        return this.optionalDiagramEvent.filter(SinglePositionEvent.class::isInstance).map(SinglePositionEvent.class::cast).filter(singlePositionEvent -> singlePositionEvent.diagramElementId().equals(diagramElementId));
    }

}

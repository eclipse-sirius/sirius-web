/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.experimental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;

/**
 * Aggregates all configuration information which are relevant for the layout of a diagram.
 *
 * @author pcdavid
 */
public record DiagramLayoutConfiguration(
        String id,
        String displayName,
        List<NodeLayoutConfiguration> childNodeLayoutConfigurations,
        List<EdgeLayoutConfiguration> edgeLayoutConfigurations,
        Map<String, NodeLayoutConfiguration> nodeLayoutConfigurationsById,
        Map<String, EdgeLayoutConfiguration> edgeLayoutConfigurationsById,
        DiagramLayoutData previousLayoutData,
        Optional<IDiagramEvent> optionalDiagramEvent
) implements IParentLayoutConfiguration {

    public DiagramLayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(displayName);
        Objects.requireNonNull(childNodeLayoutConfigurations);
        Objects.requireNonNull(edgeLayoutConfigurations);
        Objects.requireNonNull(nodeLayoutConfigurationsById);
        Objects.requireNonNull(edgeLayoutConfigurationsById);
        Objects.requireNonNull(previousLayoutData);
        Objects.requireNonNull(optionalDiagramEvent);
    }

    public Optional<NodeLayoutConfiguration> optionalNodeLayoutConfiguration(String nodeId) {
        return Optional.ofNullable(this.nodeLayoutConfigurationsById.get(nodeId));
    }

    public Optional<Rectangle> optionalPreviousFootprint(String nodeId) {
        return Optional.ofNullable(this.previousLayoutData.nodeLayoutData().get(nodeId))
                .map(nodeLayoutData -> new Rectangle(
                        nodeLayoutData.position().x(),
                        nodeLayoutData.position().y(),
                        nodeLayoutData.size().width(),
                        nodeLayoutData.size().height()
                ));
    }

    public boolean hasPreviousFootprint(String nodeId) {
        return this.previousLayoutData.nodeLayoutData().containsKey(nodeId);
    }

    public Optional<ResizeEvent> optionalResizeEvent(String nodeId) {
        return this.optionalDiagramEvent.filter(ResizeEvent.class::isInstance)
                .map(ResizeEvent.class::cast)
                .filter(resizeEvent -> resizeEvent.nodeId().equals(nodeId));
    }

    public Optional<MoveEvent> optionalMoveEvent(String nodeId) {
        return this.optionalDiagramEvent.filter(MoveEvent.class::isInstance)
                .map(MoveEvent.class::cast)
                .filter(moveEvent -> moveEvent.nodeId().equals(nodeId));
    }

    public Optional<SinglePositionEvent> optionalSinglePositionEvent(String diagramElementId) {
        return this.optionalDiagramEvent.filter(SinglePositionEvent.class::isInstance)
                .map(SinglePositionEvent.class::cast)
                .filter(singlePositionEvent -> singlePositionEvent.diagramElementId().equals(diagramElementId));

    }

    public static Builder newDiagramLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Used to create diagram layout configurations.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {
        private String id;

        private String displayName;

        private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();

        private List<EdgeLayoutConfiguration> edgeLayoutConfigurations = new ArrayList<>();

        private DiagramLayoutData previousLayoutData;

        private IDiagramEvent diagramEvent;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder displayName(String displayName) {
            this.displayName = Objects.requireNonNull(displayName);
            return this;
        }

        public Builder childNodeLayoutConfigurations(List<NodeLayoutConfiguration> childNodeLayoutConfigurations) {
            this.childNodeLayoutConfigurations = Objects.requireNonNull(childNodeLayoutConfigurations);
            return this;
        }

        public Builder edgeLayoutConfigurations(List<EdgeLayoutConfiguration> edgeLayoutConfigurations) {
            this.edgeLayoutConfigurations = Objects.requireNonNull(edgeLayoutConfigurations);
            return this;
        }

        public Builder previousLayoutData(DiagramLayoutData previousLayoutData) {
            this.previousLayoutData = Objects.requireNonNull(previousLayoutData);
            return this;
        }

        public Builder diagramEvent(IDiagramEvent diagramEvent) {
            this.diagramEvent = Objects.requireNonNull(diagramEvent);
            return this;
        }

        public DiagramLayoutConfiguration build() {
            return new DiagramLayoutConfiguration(
                    this.id,
                    this.displayName,
                    this.childNodeLayoutConfigurations,
                    this.edgeLayoutConfigurations,
                    new HashMap<>(),
                    new HashMap<>(),
                    this.previousLayoutData,
                    Optional.ofNullable(this.diagramEvent)
            );
        }
    }
}

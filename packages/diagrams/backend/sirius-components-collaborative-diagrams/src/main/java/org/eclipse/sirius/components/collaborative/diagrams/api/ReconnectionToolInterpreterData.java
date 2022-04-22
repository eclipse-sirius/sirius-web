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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * Used to transfer reconnection data to the variable manager in the compatibility layer to reconnect an edge.
 *
 * @author gcoutable
 */
@Immutable
public final class ReconnectionToolInterpreterData {

    private Diagram diagram;

    private Object semanticReconnectionSource;

    private Object reconnectionSourceView;

    private Object semanticReconnectionTarget;

    private Object reconnectionTargetView;

    private Object semanticElement;

    private Object otherEdgeEnd;

    private Object edgeView;

    private ReconnectionToolInterpreterData() {
        // Prevent instantiation
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    public Object getSemanticReconnectionSource() {
        return this.semanticReconnectionSource;
    }

    public Object getReconnectionSourceView() {
        return this.reconnectionSourceView;
    }

    public Object getSemanticReconnectionTarget() {
        return this.semanticReconnectionTarget;
    }

    public Object getReconnectionTargetView() {
        return this.reconnectionTargetView;
    }

    public Object getSemanticElement() {
        return this.semanticElement;
    }

    public Object getOtherEdgeEnd() {
        return this.otherEdgeEnd;
    }

    public Object getEdgeView() {
        return this.edgeView;
    }

    public static Builder newReconnectionToolInterpreterData() {
        return new Builder();
    }

    /**
     * Used to build a reconnection data.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private Diagram diagram;

        private Object semanticReconnectionSource;

        private Object reconnectionSourceView;

        private Object semanticReconnectionTarget;

        private Object reconnectionTargetView;

        private Object semanticElement;

        private Object otherEdgeEnd;

        private Object edgeView;

        public Builder diagram(Diagram diagram) {
            this.diagram = Objects.requireNonNull(diagram);
            return this;
        }

        public Builder semanticReconnectionSource(Object semanticReconnectionSource) {
            this.semanticReconnectionSource = Objects.requireNonNull(semanticReconnectionSource);
            return this;
        }

        public Builder reconnectionSourceView(Object reconnectionSourceView) {
            this.reconnectionSourceView = Objects.requireNonNull(reconnectionSourceView);
            return this;
        }

        public Builder semanticReconnectionTarget(Object semanticReconnectionTarget) {
            this.semanticReconnectionTarget = Objects.requireNonNull(semanticReconnectionTarget);
            return this;
        }

        public Builder reconnectionTargetView(Object reconnectionTargetView) {
            this.reconnectionTargetView = Objects.requireNonNull(reconnectionTargetView);
            return this;
        }

        public Builder semanticElement(Object semanticElement) {
            this.semanticElement = Objects.requireNonNull(semanticElement);
            return this;
        }

        public Builder otherEdgeEnd(Object otherEdgeEnd) {
            this.otherEdgeEnd = Objects.requireNonNull(otherEdgeEnd);
            return this;
        }

        public Builder edgeView(Object edgeView) {
            this.edgeView = Objects.requireNonNull(edgeView);
            return this;
        }

        public ReconnectionToolInterpreterData build() {
            ReconnectionToolInterpreterData reconnectionToolInterpreterData = new ReconnectionToolInterpreterData();
            reconnectionToolInterpreterData.diagram = Objects.requireNonNull(this.diagram);
            reconnectionToolInterpreterData.semanticReconnectionSource = Objects.requireNonNull(this.semanticReconnectionSource);
            reconnectionToolInterpreterData.reconnectionSourceView = Objects.requireNonNull(this.reconnectionSourceView);
            reconnectionToolInterpreterData.semanticReconnectionTarget = Objects.requireNonNull(this.semanticReconnectionTarget);
            reconnectionToolInterpreterData.reconnectionTargetView = Objects.requireNonNull(this.reconnectionTargetView);
            reconnectionToolInterpreterData.semanticElement = Objects.requireNonNull(this.semanticElement);
            reconnectionToolInterpreterData.otherEdgeEnd = Objects.requireNonNull(this.otherEdgeEnd);
            reconnectionToolInterpreterData.edgeView = Objects.requireNonNull(this.edgeView);
            return reconnectionToolInterpreterData;
        }

    }

}

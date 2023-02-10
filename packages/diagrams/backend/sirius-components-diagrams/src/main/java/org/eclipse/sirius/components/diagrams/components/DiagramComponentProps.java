/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of a diagram component.
 *
 * @author sbegaudeau
 */
@Immutable
public final class DiagramComponentProps implements IProps {

    public static final String PREVIOUS_DIAGRAM = "previousDiagram";

    private VariableManager variableManager;

    private DiagramDescription diagramDescription;

    private Optional<Diagram> previousDiagram;

    private List<ViewCreationRequest> viewCreationRequests;

    private List<ViewDeletionRequest> viewDeletionRequests;

    private Optional<IDiagramEvent> diagramEvent;

    private DiagramComponentProps() {
        // Prevent instantiation
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public DiagramDescription getDiagramDescription() {
        return this.diagramDescription;
    }

    public Optional<Diagram> getPreviousDiagram() {
        return this.previousDiagram;
    }

    public List<ViewCreationRequest> getViewCreationRequests() {
        return this.viewCreationRequests;
    }

    public List<ViewDeletionRequest> getViewDeletionRequests() {
        return this.viewDeletionRequests;
    }

    public Optional<IDiagramEvent> getDiagramEvent() {
        return this.diagramEvent;
    }

    public static Builder newDiagramComponentProps() {
        return new Builder();
    }

    /**
     * The Builder to create a new {@link DiagramComponentProps}.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private VariableManager variableManager;

        private DiagramDescription diagramDescription;

        private Optional<Diagram> previousDiagram;

        private List<ViewCreationRequest> viewCreationRequests;

        private List<ViewDeletionRequest> viewDeletionRequests;

        private Optional<IDiagramEvent> diagramEvent;

        public Builder variableManager(VariableManager variableManager) {
            this.variableManager = Objects.requireNonNull(variableManager);
            return this;
        }

        public Builder diagramDescription(DiagramDescription diagramDescription) {
            this.diagramDescription = Objects.requireNonNull(diagramDescription);
            return this;
        }

        public Builder previousDiagram(Optional<Diagram> previousDiagram) {
            this.previousDiagram = Objects.requireNonNull(previousDiagram);
            return this;
        }

        public Builder viewCreationRequests(List<ViewCreationRequest> viewCreationRequests) {
            this.viewCreationRequests = Objects.requireNonNull(viewCreationRequests);
            return this;
        }

        public Builder viewDeletionRequests(List<ViewDeletionRequest> viewDeletionRequests) {
            this.viewDeletionRequests = Objects.requireNonNull(viewDeletionRequests);
            return this;
        }

        public Builder diagramEvent(Optional<IDiagramEvent> diagramEvent) {
            this.diagramEvent = Objects.requireNonNull(diagramEvent);
            return this;
        }

        public DiagramComponentProps build() {
            DiagramComponentProps diagramComponentProps = new DiagramComponentProps();
            diagramComponentProps.variableManager = Objects.requireNonNull(this.variableManager);
            diagramComponentProps.diagramDescription = Objects.requireNonNull(this.diagramDescription);
            diagramComponentProps.previousDiagram = Objects.requireNonNull(this.previousDiagram);
            diagramComponentProps.viewCreationRequests = List.copyOf(Objects.requireNonNull(this.viewCreationRequests));
            diagramComponentProps.viewDeletionRequests = List.copyOf(Objects.requireNonNull(this.viewDeletionRequests));
            diagramComponentProps.diagramEvent = Objects.requireNonNull(this.diagramEvent);
            return diagramComponentProps;
        }
    }

}

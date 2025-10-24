/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;

/**
 * The implementation of {@link IDiagramContext}.
 *
 * @author hmarchadour
 */
public record DiagramContext(
        Diagram diagram,
        List<ViewCreationRequest> viewCreationRequests,
        List<ViewDeletionRequest> viewDeletionRequests,
        List<IDiagramEvent> diagramEvents) implements IDiagramContext {

    /**
     * The name of the variable used to store and retrieve the diagram context from a variable manager.
     */
    public static final String DIAGRAM_CONTEXT = "diagramContext";

    public DiagramContext {
        Objects.requireNonNull(diagram);
        Objects.requireNonNull(viewCreationRequests);
        Objects.requireNonNull(viewDeletionRequests);
        Objects.requireNonNull(diagramEvents);
    }

    public DiagramContext(Diagram diagram) {
        this(diagram, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Deprecated(forRemoval = true)
    @Override
    public Diagram getDiagram() {
        return this.diagram;
    }

    @Deprecated(forRemoval = true)
    @Override
    public List<ViewCreationRequest> getViewCreationRequests() {
        return this.viewCreationRequests;
    }

    @Deprecated(forRemoval = true)
    @Override
    public List<ViewDeletionRequest> getViewDeletionRequests() {
        return this.viewDeletionRequests;
    }

    @Deprecated(forRemoval = true)
    @Override
    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }
}

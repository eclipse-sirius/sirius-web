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
public class DiagramContext implements IDiagramContext {

    private Diagram diagram;

    private final List<ViewCreationRequest> viewCreationRequests;

    private final List<ViewDeletionRequest> viewDeletionRequests;

    private final List<IDiagramEvent> diagramEvents;

    public DiagramContext(Diagram initialDiagram) {
        this.diagram = Objects.requireNonNull(initialDiagram);
        this.viewCreationRequests = new ArrayList<>();
        this.viewDeletionRequests = new ArrayList<>();
        this.diagramEvents = new ArrayList<>();
    }

    @Override
    public Diagram getDiagram() {
        return this.diagram;
    }

    @Override
    public void update(Diagram mutateDiagram) {
        this.diagram = Objects.requireNonNull(mutateDiagram);
    }

    @Override
    public List<ViewCreationRequest> getViewCreationRequests() {
        return this.viewCreationRequests;
    }

    @Override
    public List<ViewDeletionRequest> getViewDeletionRequests() {
        return this.viewDeletionRequests;
    }

    @Override
    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }

    @Override
    public void reset() {
        this.diagramEvents.clear();
        this.viewCreationRequests.clear();
        this.viewDeletionRequests.clear();
    }
}

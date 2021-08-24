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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;

/**
 * The implementation of {@link IDiagramContext}.
 *
 * @author hmarchadour
 */
public class DiagramContext implements IDiagramContext {

    private Diagram diagram;

    private final List<ViewCreationRequest> viewCreationRequests;

    private final List<ViewDeletionRequest> viewDeletionRequests;

    private IDiagramEvent diagramEvent;

    public DiagramContext(Diagram initialDiagram) {
        this.diagram = Objects.requireNonNull(initialDiagram);
        this.viewCreationRequests = new ArrayList<>();
        this.viewDeletionRequests = new ArrayList<>();
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
    public IDiagramEvent getDiagramEvent() {
        return this.diagramEvent;
    }

    @Override
    public void setDiagramEvent(IDiagramEvent diagramEvent) {
        this.diagramEvent = diagramEvent;
    }

    @Override
    public void reset() {
        this.diagramEvent = null;
        this.viewCreationRequests.clear();
        this.viewDeletionRequests.clear();
    }
}

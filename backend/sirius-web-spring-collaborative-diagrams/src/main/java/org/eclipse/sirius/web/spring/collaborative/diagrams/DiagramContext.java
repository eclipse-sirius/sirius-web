/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;

/**
 * The implementation of {@link IDiagramContext}.
 *
 * @author hmarchadour
 */
public class DiagramContext implements IDiagramContext {

    private Diagram diagram;

    private final List<ViewCreationRequest> viewCreationRequests;

    private final Map<UUID, Position> movedElementIDToNewPositionMap;

    private Position startingPosition;

    public DiagramContext(Diagram initialDiagram) {
        this.diagram = Objects.requireNonNull(initialDiagram);
        this.viewCreationRequests = new ArrayList<>();
        this.movedElementIDToNewPositionMap = new HashMap<>();
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
    public Map<UUID, Position> getMovedElementIDToNewPositionMap() {
        return this.movedElementIDToNewPositionMap;
    }

    @Override
    public Position getStartingPosition() {
        return this.startingPosition;
    }

    public void setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
    }
}

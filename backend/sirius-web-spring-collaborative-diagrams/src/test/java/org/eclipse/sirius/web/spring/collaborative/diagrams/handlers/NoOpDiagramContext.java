/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.List;

import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.IDiagramElementEvent;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;

/**
 * Implementation of the diagram context which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpDiagramContext implements IDiagramContext {

    @Override
    public Diagram getDiagram() {
        return null;
    }

    @Override
    public void update(Diagram updatedDiagram) {
    }

    @Override
    public List<ViewCreationRequest> getViewCreationRequests() {
        return List.of();
    }

    @Override
    public void reset() {
    }

    @Override
    public IDiagramElementEvent getDiagramElementEvent() {
        return null;
    }

    @Override
    public void setDiagramElementEvent(IDiagramElementEvent diagramElementEvent) {
    }

}

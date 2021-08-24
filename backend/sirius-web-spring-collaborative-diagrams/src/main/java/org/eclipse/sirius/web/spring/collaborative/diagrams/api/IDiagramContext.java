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
package org.eclipse.sirius.web.spring.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;

/**
 * Information used to perform some operations on the diagram.
 *
 * @author sbegaudeau
 */
public interface IDiagramContext {

    /**
     * The name of the variable used to store and retrieve the diagram context from a variable manager.
     */
    String DIAGRAM_CONTEXT = "diagramContext"; //$NON-NLS-1$

    Diagram getDiagram();

    void update(Diagram updatedDiagram);

    void reset();

    List<ViewCreationRequest> getViewCreationRequests();

    List<ViewDeletionRequest> getViewDeletionRequests();

    IDiagramEvent getDiagramEvent();

    void setDiagramEvent(IDiagramEvent diagramElementEvent);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDiagramContext {

        @Override
        public Diagram getDiagram() {
            return null;
        }

        @Override
        public void update(Diagram updatedDiagram) {
        }

        @Override
        public void reset() {
        }

        @Override
        public List<ViewCreationRequest> getViewCreationRequests() {
            return List.of();
        }

        @Override
        public List<ViewDeletionRequest> getViewDeletionRequests() {
            return List.of();
        }

        @Override
        public IDiagramEvent getDiagramEvent() {
            return null;
        }

        @Override
        public void setDiagramEvent(IDiagramEvent diagramElementEvent) {
        }

    }
}

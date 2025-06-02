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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;

/**
 * Information used to perform some operations on the diagram.
 *
 * @author sbegaudeau
 */
public interface IDiagramContext {

    /**
     * The name of the variable used to store and retrieve the diagram context from a variable manager.
     */
    String DIAGRAM_CONTEXT = "diagramContext";

    Diagram getDiagram();

    /**
     * Used to update the internal diagram.
     *
     * @param updatedDiagram The updated diagram which should replace the current one
     *
     * @technical-debt This method should probably stop being used and be removed to use instead an immutable data structure
     * for the diagram context. The interface itself could probably be removed entirely in favor of a simple record.
     */
    void update(Diagram updatedDiagram);

    /**
     * Used to remove all the view creation / deletion requests and the various diagram events.
     *
     * @technical-debt The method should be deleted in favor of an immutable data structure for the diagram context.
     */
    void reset();

    List<ViewCreationRequest> getViewCreationRequests();

    List<ViewDeletionRequest> getViewDeletionRequests();

    List<IDiagramEvent> getDiagramEvents();

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
        public List<IDiagramEvent> getDiagramEvents() {
            return List.of();
        }
    }
}

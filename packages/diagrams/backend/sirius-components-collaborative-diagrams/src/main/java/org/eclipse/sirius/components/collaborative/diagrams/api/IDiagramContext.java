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
 * @deprecated The diagram context is a data class used to contain some information relevant for the next rendering.
 * It does not need this interface which had only been introduced because it was not immutable. Now that this concept
 * is immutable, as it should have been from the start, this interface is useless.
 *
 * @author sbegaudeau
 */
@Deprecated(forRemoval = true)
public interface IDiagramContext {

    /**
     * The name of the variable used to store and retrieve the diagram context from a variable manager.
     */
    String DIAGRAM_CONTEXT = "diagramContext";

    Diagram getDiagram();

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

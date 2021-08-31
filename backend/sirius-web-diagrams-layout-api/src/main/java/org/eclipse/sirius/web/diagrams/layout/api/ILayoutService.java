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
package org.eclipse.sirius.web.diagrams.layout.api;

import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;

/**
 * Implementation of this interface will layout the given diagram.
 *
 * @author sbegaudeau
 */
public interface ILayoutService {
    Diagram layout(Diagram diagram, DiagramDescription diagramDescription);

    /**
     * A partial layout that layouts only impacted elements.
     *
     * @param diagram
     *            The diagram to layout.
     * @param diagramDescription
     *            the diagram description (which may specify custom layout options).
     * @param optionalDiagramElementEvent
     *            the {@link IDiagramEvent} that has trigger the new layout. Can be null if no event occurs. for
     *            instance.
     * @return the new layouted diagram.
     */
    Diagram incrementalLayout(Diagram diagram, DiagramDescription diagramDescription, Optional<IDiagramEvent> optionalDiagramElementEvent);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ILayoutService {

        @Override
        public Diagram layout(Diagram diagram, DiagramDescription diagramDescription) {
            return diagram;
        }

        @Override
        public Diagram incrementalLayout(Diagram diagram, DiagramDescription diagramDescription, Optional<IDiagramEvent> optionalDiagramElementEvent) {
            return diagram;
        }

    }
}

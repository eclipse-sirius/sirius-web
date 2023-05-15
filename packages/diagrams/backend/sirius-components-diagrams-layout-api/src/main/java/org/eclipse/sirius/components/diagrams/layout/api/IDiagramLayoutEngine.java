/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api;

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;

/**
 * Used to layout diagrams.
 *
 * @author pcdavid
 */
public interface IDiagramLayoutEngine {
    /**
     * Computes the new layout to be used for a diagram and its content.
     *
     * @param editingContext
     *            the editing context.
     * @param diagram
     *            the diagram's structure (nodes, edges, etc.).
     * @param previousLayoutData
     *            the layout data from the previous version of the diagram. This may be empty for the initial layout or
     *            for a "full" layout.
     * @param optionalEvent
     *            if the (incremental) layout was triggered by a user operation (e.g. the invocation of a tool at a
     *            specific position) the corresponding event with the information relevant for the layout will be
     *            available here.
     * @return the layout information to be used for the new version of the diagram.
     */
    DiagramLayoutData layout(IEditingContext editingContext, Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IDiagramLayoutEngine {

        @Override
        public DiagramLayoutData layout(IEditingContext editingContext, Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
            return Optional.ofNullable(previousLayoutData).orElse(new DiagramLayoutData(Map.of(), Map.of(), Map.of()));
        }

    }
}

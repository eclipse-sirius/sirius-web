/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.experimental;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;

/**
 * Used to provide the diagram layout configuration.
 *
 * @author sbegaudeau
 */
public interface IDiagramLayoutConfigurationProvider {
    DiagramLayoutConfiguration getDiagramLayoutConfiguration(Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements  IDiagramLayoutConfigurationProvider {

        @Override
        public DiagramLayoutConfiguration getDiagramLayoutConfiguration(Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
            return null;
        }
    }
}

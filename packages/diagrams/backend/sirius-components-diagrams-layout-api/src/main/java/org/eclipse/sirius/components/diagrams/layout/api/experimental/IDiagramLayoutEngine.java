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

import java.util.Map;

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
     * @param diagramLayoutConfiguration
     *            the configuration of the layout for the diagram
     * @return the layout information to be used for the new version of the diagram.
     */
    DiagramLayoutData layout(DiagramLayoutConfiguration diagramLayoutConfiguration);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IDiagramLayoutEngine {

        @Override
        public DiagramLayoutData layout(DiagramLayoutConfiguration diagramLayoutConfiguration) {
            return new DiagramLayoutData(Map.of(), Map.of(), Map.of());
        }

    }
}

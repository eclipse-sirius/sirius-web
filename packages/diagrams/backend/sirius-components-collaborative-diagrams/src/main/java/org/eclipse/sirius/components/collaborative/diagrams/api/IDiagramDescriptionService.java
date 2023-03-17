/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * Interface used to manipulate (mostly query) diagram descriptions.
 *
 * @author pcdavid
 */
public interface IDiagramDescriptionService {

    Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId);

    Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDiagramDescriptionService {

        @Override
        public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
            return Optional.empty();
        }

    }
}

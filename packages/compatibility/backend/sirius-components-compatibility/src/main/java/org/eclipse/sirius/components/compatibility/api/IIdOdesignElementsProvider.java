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
package org.eclipse.sirius.components.compatibility.api;

import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;

/**
 * Interface to provide ids for DiagramDescription & DiagramElementDescription.
 *
 * @author mcharfadi
 */
public interface IIdOdesignElementsProvider {

    String SIRIUS_PROTOCOL = "siriusComponents://";

    String SOURCE_KIND_VIEW = "?sourceKind=odesign";

    String DIAGRAM_PATH_VIEW = SIRIUS_PROTOCOL + "diagramDescription" + SOURCE_KIND_VIEW;

    String EDGE_PATH_VIEW = SIRIUS_PROTOCOL + "edgeDescription" + SOURCE_KIND_VIEW;

    String NODE_PATH_VIEW = SIRIUS_PROTOCOL + "nodeDescription" + SOURCE_KIND_VIEW;

    String SOURCE_ID = "sourceId=";

    String SOURCE_ELEMENT_ID = "sourceElementId=";

    String AMPERSAND = "&";

    String getIdDiagramDescription(DiagramDescription diagramDescription);

    String getIdElementDescription(AbstractNodeMapping abstractNodeMapping);

    String getIdEdgeMapping(EdgeMapping edgeMapping);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IIdOdesignElementsProvider {

        @Override
        public String getIdDiagramDescription(DiagramDescription diagramDescription) {
            return "";
        }

        @Override
        public String getIdElementDescription(AbstractNodeMapping abstractNodeMapping) {
            return "";
        }

        @Override
        public String getIdEdgeMapping(EdgeMapping edgeMapping) {
            return "";
        }

    }

}


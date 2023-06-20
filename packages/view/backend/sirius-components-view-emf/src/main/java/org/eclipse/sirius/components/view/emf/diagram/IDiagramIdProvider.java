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
package org.eclipse.sirius.components.view.emf.diagram;


import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;

/**
 * Interface to provide ids for DiagramDescription & DiagramElementDescription.
 *
 * @author mcharfadi
 */
public interface IDiagramIdProvider extends IRepresentationDescriptionIdProvider<DiagramDescription> {

    String DIAGRAM_DESCRIPTION_KIND = PREFIX + "?kind=diagramDescription";

    String NODE_DESCRIPTION_KIND = "siriusComponents://nodeDescription";

    String EDGE_DESCRIPTION_KIND = "siriusComponents://edgeDescription";

    @Override
    String getId(DiagramDescription diagramDescription);

    String getId(DiagramElementDescription diagramElementDescription);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IDiagramIdProvider {

        @Override
        public String getId(DiagramDescription diagramDescription) {
            return "";
        }

        @Override
        public String getId(DiagramElementDescription diagramElementDescription) {
            return "";
        }

    }
}


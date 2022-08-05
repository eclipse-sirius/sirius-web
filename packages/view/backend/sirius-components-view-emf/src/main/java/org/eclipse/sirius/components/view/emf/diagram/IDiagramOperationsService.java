/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;

/**
 * Provides useful APIs for studios which need tools that must modify the current diagram, most notably create or delete
 * views for unsynchronized elements. It can be injected into a Java service class returned from a
 * {@link IJavaServiceProvider}. All the arguments needed to invoke the methods in this service must be available from
 * the variables available to a studio's render expressions and tools.
 *
 * @author pcdavid
 */
public interface IDiagramOperationsService {

    void createView(IDiagramContext diagramContext, EObject semanticElement, Optional<Node> optionalParentNode, NodeDescription nodeDescription);

    void deleteView(IDiagramContext diagramContext, Node node);

    /**
     * Empty implementation that can be used or extended for testing.
     *
     * @author pcdavid
     */
    class NoOp implements IDiagramOperationsService {

        @Override
        public void createView(IDiagramContext diagramContext, EObject semanticElement, Optional<Node> optionalParentNode, NodeDescription nodeDescription) {
            // Do nothing
        }

        @Override
        public void deleteView(IDiagramContext diagramContext, Node node) {
            // Do nothing
        }

    }

}

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

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;

/**
 * Provides useful APIs for studios which need tools that must introspect the current diagram's structure and its
 * description. It can be injected into a Java service class returned from a {@link IJavaServiceProvider}. All the
 * arguments needed to invoke the methods in this service must be available from the variables available to a studio's
 * render expressions and tools.
 *
 * @author pcdavid
 */
public interface IDiagramNavigationService {

    Optional<DiagramDescription> getDiagramDescription(Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> capturedNodeDescriptions);

    // org.eclipse.sirius.components.view.NodeDescription
    // getChildrenCandidateOfType(org.eclipse.sirius.components.view.NodeDescription parent, EClass eClass);

    /**
     * Empty implementation that can be used or extended for testing.
     *
     * @author pcdavid
     */
    class NoOp implements IDiagramNavigationService {

        @Override
        public Optional<DiagramDescription> getDiagramDescription(Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> capturedNodeDescriptions) {
            return Optional.empty();
        }

    }
}

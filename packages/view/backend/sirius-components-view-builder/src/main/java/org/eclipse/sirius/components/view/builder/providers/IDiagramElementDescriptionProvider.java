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
package org.eclipse.sirius.components.view.builder.providers;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;

/**
 * Used to provide a diagram element description.
 *
 * @param <T>
 *            The type of the diagram element description
 *
 * @author sbegaudeau
 */
public interface IDiagramElementDescriptionProvider<T extends DiagramElementDescription> {
    T create();

    default void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        // Do nothing by default
    }
}

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
package org.eclipse.sirius.components.view.builder;

import java.util.Optional;

import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.NodeDescription;

/**
 * Used to retrieve diagram description elements created by providers.
 *
 * @author sbegaudeau
 */
public interface IViewDiagramElementFinder {
    Optional<NodeDescription> getNodeDescription(String name);
    Optional<EdgeDescription> getEdgeDescription(String name);
}

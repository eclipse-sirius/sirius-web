/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.Optional;

import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;

/**
 * Get a View representation description from its representation description Id.
 *
 * @author arichard
 */
public interface IViewRepresentationDescriptionSearchService {

    Optional<RepresentationDescription> findById(String representationDescriptionId);

    Optional<NodeDescription> findViewNodeDescriptionById(String nodeDescriptionId);

    Optional<EdgeDescription> findViewEdgeDescriptionById(String edgeDescriptionId);
}

/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * Interface used to manipulate (mostly query) diagram descriptions.
 *
 * @author pcdavid
 */
public interface IDiagramDescriptionService {

    Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, UUID nodeDescriptionId);

    Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, UUID edgeDescriptionId);

}

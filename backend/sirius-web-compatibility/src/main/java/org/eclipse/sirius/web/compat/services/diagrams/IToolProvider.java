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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;

/**
 * Used to compute the tools available.
 *
 * @author sbegaudeau
 */
public interface IToolProvider {
    List<ToolSection> getToolSections(Map<UUID, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions, DiagramDescription siriusDiagramDescription, List<Layer> layers);
}

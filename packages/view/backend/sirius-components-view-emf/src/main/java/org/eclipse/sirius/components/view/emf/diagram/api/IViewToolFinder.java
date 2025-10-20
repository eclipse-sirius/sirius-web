/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;

import java.util.Optional;

/**
 * Used to find a specific view tool for a specific diagramElementDescription.
 *
 * @author mcharfadi
 */
public interface IViewToolFinder {

    Optional<NodeTool> findNodeTool(IEditingContext editingContext, String diagramDescriptionId, String diagramElementDescriptionId, String toolId);

    Optional<EdgeTool> findEdgeTool(IEditingContext editingContext, String diagramDescriptionId, String diagramElementDescriptionId, String toolId);
}

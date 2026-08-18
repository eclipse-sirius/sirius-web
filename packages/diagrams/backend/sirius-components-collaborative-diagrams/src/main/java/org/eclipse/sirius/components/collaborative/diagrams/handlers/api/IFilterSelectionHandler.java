/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to return a new selection using a for a given ToolbarFilterSelectionMenuItem.
 *
 * @author mcharfadi
 */
public interface IFilterSelectionHandler {

    boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds);

    List<String> getNewSelection(IEditingContext editingContext, DiagramContext diagramContext, String toolbarFilterSelectionMenuItemId, List<String> diagramElementIds);
}

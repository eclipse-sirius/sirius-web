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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Handler for Actions.
 *
 * @author arichard
 */
public interface IActionHandler {

    boolean canHandle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramElement diagramElement, String actionId);

    IStatus handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramElement diagramElement, String actionId);
}

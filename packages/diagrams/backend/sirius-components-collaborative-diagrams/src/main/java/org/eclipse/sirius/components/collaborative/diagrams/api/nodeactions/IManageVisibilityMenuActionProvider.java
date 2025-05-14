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
package org.eclipse.sirius.components.collaborative.diagrams.api.nodeactions;

import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.ManageVisibilityAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

import java.util.List;

/**
 * Provide the menu actions for the manage visibility node action.
 *
 * @author mcharfadi
 */
public interface IManageVisibilityMenuActionProvider {

    boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement);

    List<ManageVisibilityAction> handle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement);
}

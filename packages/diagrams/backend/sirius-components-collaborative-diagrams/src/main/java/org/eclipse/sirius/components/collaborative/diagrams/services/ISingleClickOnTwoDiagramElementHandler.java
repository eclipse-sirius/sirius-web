/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.services;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Used to execute a connector tool on a diagram.
 *
 * @author mcharfadi
 */
public interface ISingleClickOnTwoDiagramElementHandler {

    boolean canHandle(IEditingContext editingContext, Diagram diagram, String toolId, String sourceDiagramElementId, String targetDiagramElementId);

    IStatus execute(IEditingContext editingContext, Diagram diagram, String toolId, String sourceDiagramElementId, String targetDiagramElementId, List<ToolVariable> variables);
}

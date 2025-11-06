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
package org.eclipse.sirius.components.collaborative.diagrams.services;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.IStatus;

import java.util.List;

/**
 * Used to execute a group tool on a diagram.
 *
 * @author mcharfadi
 */
public interface ISingleClickOnMultipleDiagramElementHandler {

    boolean canHandle(IEditingContext editingContext, Diagram diagram, String toolId, List<String> diagramElementIds);

    IStatus execute(IEditingContext editingContext, Diagram diagram, String toolId, List<String> diagramElementIds, List<ToolVariable> variables);
}

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
package org.eclipse.sirius.components.view.emf.diagram.tools.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to provide the variable manager used to evaluate single click on one diagram element tools.
 *
 * @author sbegaudeau
 */
public interface ISingleClickOnOneDiagramElementVariableManagerProvider {
    Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, String diagramElementId, List<ToolVariable> variables);
}

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
package org.eclipse.sirius.components.collaborative.diagrams.handlers.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the variable manager required to perform the drop nodes operation.
 *
 * @author sbegaudeau
 */
public interface IDropNodesVariableManagerProvider {
    Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, String targetElementId, List<String> droppedElementIds);
}

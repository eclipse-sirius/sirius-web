/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.core.api.IUndoableInput;

/**
 * The input for the "Invoke single click on diagram element tool" mutation.
 *
 * @author pcdavid
 */
public record InvokeSingleClickOnDiagramElementToolInput(UUID id, String editingContextId, String representationId, String diagramElementId, String toolId, double startingPositionX,
        double startingPositionY, List<ToolVariable> variables) implements IDiagramInput, IUndoableInput {
}

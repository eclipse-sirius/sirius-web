/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input for the "Invoke single click on two diagram elements tool" mutation.
 *
 * @author pcdavid
 * @author hmarchadour
 */
public record InvokeSingleClickOnTwoDiagramElementsToolInput(UUID id, String editingContextId, String representationId, String diagramSourceElementId, String diagramTargetElementId,
        double sourcePositionX, double sourcePositionY, double targetPositionX, double targetPositionY, String toolId) implements IDiagramInput {
}

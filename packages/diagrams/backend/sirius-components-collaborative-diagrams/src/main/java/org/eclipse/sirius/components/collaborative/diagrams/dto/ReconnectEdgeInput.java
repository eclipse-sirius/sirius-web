/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;

/**
 * The input for the reconnect edge mutation.
 *
 * @author gcoutable
 */
public record ReconnectEdgeInput(
        UUID id,
        String editingContextId,
        String representationId,
        String edgeId,
        String newEdgeEndId,
        ReconnectEdgeKind reconnectEdgeKind) implements IDiagramInput {
}

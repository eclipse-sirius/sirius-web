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
package org.eclipse.sirius.web.application.undo.services.changes;

import java.util.Set;
import java.util.UUID;

/**
 * Used to record changes for PinDiagramElementEvent.
 *
 * @author mcharfadi
 */
public record DiagramPinElementChange(UUID inputId, String representationId, Set<String> elementIds, boolean undoValue, boolean redoValue) implements IDiagramChange {
}

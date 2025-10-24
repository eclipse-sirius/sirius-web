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

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;

/**
 * Used to record changes for ResetViewModifiersEvent.
 *
 * @author mcharfadi
 */
public record ResetViewModifiersChange(UUID inputId, String representationId, Set<String> elementIds, List<IDiagramEvent> undoEvents) implements IDiagramChange {
}

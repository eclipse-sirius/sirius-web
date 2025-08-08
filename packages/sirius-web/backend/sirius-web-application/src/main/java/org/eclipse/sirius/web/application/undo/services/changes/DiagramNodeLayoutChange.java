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
package org.eclipse.sirius.web.application.undo.services.changes;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;

/**
 * Used to record changes for node layout.
 *
 * @author mcharfadi
 */
public record DiagramNodeLayoutChange(UUID inputId, String representationId, List<DiagramNodeLayoutEvent> undoEvents, List<DiagramNodeLayoutEvent> redoChanges) implements IDiagramChange {
}

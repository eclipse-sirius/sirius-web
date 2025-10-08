/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramLabelLayoutEvent;

import java.util.List;
import java.util.UUID;

/**
 * Used to record changes for label layout.
 *
 * @author mcharfadi
 */
public record DiagramLabelLayoutChange(UUID inputId, String representationId, List<DiagramLabelLayoutEvent> undoChanges, List<DiagramLabelLayoutEvent> redoChanges) implements IDiagramChange {
}

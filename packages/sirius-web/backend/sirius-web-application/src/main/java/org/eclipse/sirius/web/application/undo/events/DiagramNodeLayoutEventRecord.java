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
package org.eclipse.sirius.web.application.undo.events;

import org.eclipse.sirius.components.diagrams.events.undoredo.IDiagramNodeLayoutEvent;

import java.util.List;

/**
 * Event record for diagram node layout changes.
 *
 * @author mcharfadi
 */
public record DiagramNodeLayoutEventRecord(String mutationId, String representationId, List<IDiagramNodeLayoutEvent> undoChanges, List<IDiagramNodeLayoutEvent> redoChanges) implements IDiagramNodeEventRecord {

}

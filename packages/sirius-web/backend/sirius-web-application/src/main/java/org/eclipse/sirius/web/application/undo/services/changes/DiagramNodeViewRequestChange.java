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
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;

/**
 * Used to record changes for ViewCreationRequest and ViewDeletionRequest.
 *
 * @author mcharfadi
 */
public record DiagramNodeViewRequestChange(UUID inputId, String representationId, List<ViewCreationRequest> undoCreationRequest, List<ViewDeletionRequest> undoViewDeletionRequest, List<ViewCreationRequest> redoCreationRequest, List<ViewDeletionRequest> redoDeletionRequest) implements IDiagramChange {
}

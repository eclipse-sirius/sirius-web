/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * Input for "get connector tools" query.
 *
 * @author nvannier
 */
public class GetConnectorToolsInput implements IDiagramInput {

    private UUID id;

    private String editingContextId;

    private String representationId;

    private String sourceDiagramElementId;

    private String targetDiagramElementId;

    public GetConnectorToolsInput(UUID id, String editingContextId, String representationId, String sourceDiagramElementId, String targetDiagramElementId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.sourceDiagramElementId = Objects.requireNonNull(sourceDiagramElementId);
        this.targetDiagramElementId = Objects.requireNonNull(targetDiagramElementId);
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getSourceDiagramElementId() {
        return this.sourceDiagramElementId;
    }

    public String getTargetDiagramElementId() {
        return this.targetDiagramElementId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, sourceDiagramElementId: {4}, targetDiagramElementId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.sourceDiagramElementId, this.targetDiagramElementId);
    }
}

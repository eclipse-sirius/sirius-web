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
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The class of the inputs for the "fade element" mutation.
 *
 * @author tgiraudet
 */
public class FadeDiagramElementInput implements IDiagramInput {

    private UUID id;

    private String editingContextId;

    private String representationId;

    private Set<String> elementIds;

    private boolean fade;

    public FadeDiagramElementInput() {
        // Used by Jackson
    }

    public FadeDiagramElementInput(UUID id, String editingContextId, String representationId, Set<String> elementIds, boolean fade) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.elementIds = Objects.requireNonNull(elementIds);
        this.fade = Objects.requireNonNull(fade);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public Set<String> getElementIds() {
        return this.elementIds;
    }

    public boolean isFade() {
        return this.fade;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, fade: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.fade);
    }

}

/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object of the create representation mutation.
 *
 * @author sbegaudeau
 */
public final class CreateRepresentationInput implements IInput {

    private UUID id;

    private String editingContextId;

    private UUID representationDescriptionId;

    private String objectId;

    private String representationName;

    public CreateRepresentationInput() {
        // Used by Jackson
    }

    public CreateRepresentationInput(UUID id, String editingContextId, UUID representationDescriptionId, String objectId, String representationName) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationDescriptionId = Objects.requireNonNull(representationDescriptionId);
        this.objectId = Objects.requireNonNull(objectId);
        this.representationName = Objects.requireNonNull(representationName);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public UUID getRepresentationDescriptionId() {
        return this.representationDescriptionId;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public String getRepresentationName() {
        return this.representationName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationDescriptionId: {3}, objectId: {4}, representationName: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationDescriptionId, this.objectId, this.representationName);
    }
}

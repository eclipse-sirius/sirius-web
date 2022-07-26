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
 * The input object of the create root object mutation.
 *
 * @author lfasani
 */
public final class CreateRootObjectInput implements IInput {
    private UUID id;

    private String editingContextId;

    private UUID documentId;

    private String domainId;

    private String rootObjectCreationDescriptionId;

    public CreateRootObjectInput() {
        // Used by Jackson
    }

    public CreateRootObjectInput(UUID id, String editingContextId, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.documentId = Objects.requireNonNull(documentId);
        this.domainId = Objects.requireNonNull(domainId);
        this.rootObjectCreationDescriptionId = Objects.requireNonNull(rootObjectCreationDescriptionId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public UUID getDocumentId() {
        return this.documentId;
    }

    public String getDomainId() {
        return this.domainId;
    }

    public String getRootObjectCreationDescriptionId() {
        return this.rootObjectCreationDescriptionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, documentId: {3}, domainId: {4}, rootObjectCreationDescriptionId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.documentId, this.domainId, this.rootObjectCreationDescriptionId);
    }
}

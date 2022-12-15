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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object for the operation to rewrite broken proxy URIs in documents.
 *
 * @author pcdavid
 */
public final class RewriteProxiesInput implements IInput {
    private final UUID id;

    private final String editingContextId;

    private final Map<String, String> oldDocumentIdToNewDocumentId;

    public RewriteProxiesInput(UUID id, String editingContextId, Map<String, String> oldDocumentIdToNewDocumentId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.oldDocumentIdToNewDocumentId = Objects.requireNonNull(oldDocumentIdToNewDocumentId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public Map<String, String> getOldDocumentIdToNewDocumentId() {
        return this.oldDocumentIdToNewDocumentId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, oldDocumentIdToNewDocumentId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.oldDocumentIdToNewDocumentId);
    }
}

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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the upload document mutation.
 *
 * @author sbegaudeau
 */
public final class UploadDocumentSuccessPayload implements IPayload {

    private final UUID id;

    private final Document document;

    public UploadDocumentSuccessPayload(UUID id, Document document) {
        this.id = Objects.requireNonNull(id);
        this.document = Objects.requireNonNull(document);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Document getDocument() {
        return this.document;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, document: '{'id: {2}, name: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.document.getId(), this.getDocument().getName());
    }
}

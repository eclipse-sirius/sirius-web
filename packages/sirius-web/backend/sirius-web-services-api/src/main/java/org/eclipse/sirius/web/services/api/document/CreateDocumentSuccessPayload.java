/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the "create document" mutation.
 *
 * @author sbegaudeau
 */
public final class CreateDocumentSuccessPayload implements IPayload {

    private final UUID id;

    private final Document document;


    public CreateDocumentSuccessPayload(UUID id, Document document) {
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
}

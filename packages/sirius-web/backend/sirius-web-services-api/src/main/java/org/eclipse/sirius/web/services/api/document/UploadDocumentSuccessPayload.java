/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
 * The payload of the upload document mutation.
 *
 * @author sbegaudeau
 */
public record UploadDocumentSuccessPayload(UUID id, Document document, String report) implements IPayload {
    public UploadDocumentSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(document);
    }
}

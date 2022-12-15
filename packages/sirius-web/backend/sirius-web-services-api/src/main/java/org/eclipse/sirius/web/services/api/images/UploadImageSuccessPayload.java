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
package org.eclipse.sirius.web.services.api.images;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the upload image mutation.
 *
 * @author pcdavid
 */
public final class UploadImageSuccessPayload implements IPayload {

    private final UUID id;

    private final UUID imageId;

    public UploadImageSuccessPayload(UUID id, UUID imageId) {
        this.id = Objects.requireNonNull(id);
        this.imageId = Objects.requireNonNull(imageId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getImageId() {
        return this.imageId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, document: '{'id: {2} '}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.imageId);
    }
}

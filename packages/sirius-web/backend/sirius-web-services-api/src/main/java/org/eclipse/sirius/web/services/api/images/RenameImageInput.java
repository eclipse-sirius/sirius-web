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

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object for the rename image mutation.
 *
 * @author pcdavid
 */
public final class RenameImageInput implements IInput {

    private UUID id;

    private UUID imageId;

    private String newLabel;

    public RenameImageInput() {
        // Used by Jackson
    }

    public RenameImageInput(UUID id, UUID imageId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.imageId = Objects.requireNonNull(imageId);
        this.newLabel = Objects.requireNonNull(newLabel);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getImageId() {
        return this.imageId;
    }

    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, imageId: {2}, newLabel: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.imageId, this.newLabel);
    }

}

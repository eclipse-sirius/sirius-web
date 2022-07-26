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

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to indicate that the representation has been renamed.
 *
 * @author arichard
 */
public final class RepresentationRenamedEventPayload implements IPayload {
    private final UUID id;

    private final String representationId;

    private final String newLabel;

    public RepresentationRenamedEventPayload(UUID id, String representationId, String newLabel) {
        this.id = Objects.requireNonNull(id);
        this.representationId = Objects.requireNonNull(representationId);
        this.newLabel = Objects.requireNonNull(newLabel);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getRepresentationId() {
        return this.representationId;
    }

    public String getNewLabel() {
        return this.newLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representationId: {2}, newLabel: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.representationId, this.newLabel);
    }
}

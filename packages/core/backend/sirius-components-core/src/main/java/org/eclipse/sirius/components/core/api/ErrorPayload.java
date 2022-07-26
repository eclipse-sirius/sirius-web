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
package org.eclipse.sirius.components.core.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

/**
 * General purpose error payload.
 *
 * @author sbegaudeau
 */
public final class ErrorPayload implements IPayload {
    private final UUID id;

    private final String message;

    public ErrorPayload(UUID id, String message) {
        this.id = Objects.requireNonNull(id);
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, message: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.message);
    }
}

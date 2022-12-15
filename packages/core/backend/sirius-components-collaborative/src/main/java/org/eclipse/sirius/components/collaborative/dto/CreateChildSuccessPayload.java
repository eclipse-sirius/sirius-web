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
 * The payload of the create child mutation.
 *
 * @author sbegaudeau
 */
public final class CreateChildSuccessPayload implements IPayload {

    private final UUID id;

    private final Object object;

    public CreateChildSuccessPayload(UUID id, Object object) {
        this.id = Objects.requireNonNull(id);
        this.object = object;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Object getObject() {
        return this.object;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }
}

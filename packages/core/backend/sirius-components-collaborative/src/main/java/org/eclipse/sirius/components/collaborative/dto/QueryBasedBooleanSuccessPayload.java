/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
 * The payload of the queryBasedBoolean query.
 *
 * @author fbarbin
 */
public final class QueryBasedBooleanSuccessPayload implements IPayload {

    private final UUID id;

    private final boolean result;

    public QueryBasedBooleanSuccessPayload(UUID id, boolean result) {
        this.id = Objects.requireNonNull(id);
        this.result = result;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public boolean getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, result: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.result);
    }
}

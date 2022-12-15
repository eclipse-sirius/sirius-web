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
package org.eclipse.sirius.components.collaborative.validation.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.validation.Validation;

/**
 * Payload used to indicate that the validation has been refreshed.
 *
 * @author gcoutable
 */
public final class ValidationRefreshedEventPayload implements IPayload {
    private final UUID id;

    private final Validation validation;

    public ValidationRefreshedEventPayload(UUID id, Validation validation) {
        this.id = Objects.requireNonNull(id);
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Validation getValidation() {
        return this.validation;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, form: '{'id: {2}'}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.validation.getId());
    }
}

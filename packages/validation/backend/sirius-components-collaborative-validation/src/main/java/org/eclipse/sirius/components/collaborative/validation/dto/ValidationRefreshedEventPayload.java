/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.validation.Validation;

/**
 * Payload used to indicate that the validation has been refreshed.
 *
 * @author gcoutable
 */
public record ValidationRefreshedEventPayload(UUID id, Validation validation) implements IPayload {
    public ValidationRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(validation);
    }
}

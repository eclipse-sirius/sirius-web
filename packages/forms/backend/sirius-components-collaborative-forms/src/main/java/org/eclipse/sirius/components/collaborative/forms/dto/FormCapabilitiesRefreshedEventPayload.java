/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to indicate the capabilities of a form has been refreshed.
 *
 * @author gcoutable
 */
public record FormCapabilitiesRefreshedEventPayload(UUID id, String formId, FormCapabilitiesDTO capabilities) implements IPayload {
    public FormCapabilitiesRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(formId);
        Objects.requireNonNull(capabilities);
    }
}

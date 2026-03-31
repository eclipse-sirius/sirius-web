/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.collaborative.diagrams.dto;

import org.eclipse.sirius.components.core.api.IPayload;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The payload of the "Get Layout Configuration" query returned on success.
 *
 * @author ocailleau
 */

public record GetLayoutConfigurationSuccessPayload(UUID id, List<LayoutConfiguration> layoutConfigurations) implements IPayload {

    public GetLayoutConfigurationSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(layoutConfigurations);
    }
}

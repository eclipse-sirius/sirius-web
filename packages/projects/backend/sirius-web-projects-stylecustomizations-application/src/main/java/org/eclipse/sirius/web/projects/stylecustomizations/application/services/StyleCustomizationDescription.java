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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

/**
 * Description of a style customization.
 *
 * @author gcoutable
 */
public record StyleCustomizationDescription(@NotNull String id, @NotNull String label, @NotNull String description) {
    public StyleCustomizationDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(description);
    }
}

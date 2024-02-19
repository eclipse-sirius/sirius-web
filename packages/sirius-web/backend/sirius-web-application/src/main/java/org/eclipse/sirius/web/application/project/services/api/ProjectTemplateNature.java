/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

/**
 * The nature of a project template.
 *
 * @author sbegaudeau
 */
public record ProjectTemplateNature(@NotNull String id) {
    public ProjectTemplateNature {
        Objects.requireNonNull(id);
    }
}

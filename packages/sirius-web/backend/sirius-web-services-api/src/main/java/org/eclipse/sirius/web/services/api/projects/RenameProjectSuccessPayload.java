/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Represent the result returned when renaming a project through the graphql API.
 *
 * @author fbarbin
 */
public record RenameProjectSuccessPayload(UUID id, Project project) implements IPayload {
    public RenameProjectSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(project);
    }
}

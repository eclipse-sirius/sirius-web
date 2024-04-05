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
package org.eclipse.sirius.web.application.document.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * The DTO used to represent a document in the GraphQL API.
 *
 * @author sbegaudeau
 */
public record DocumentDTO(@NotNull UUID id, @NotNull String name, @NotNull String kind) {
}

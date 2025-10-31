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
package org.eclipse.sirius.web.application.views.search.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * Used to present search matches according to a specific grouping criterion.
 *
 * @author pcdavid
 */
public record SearchResultGroup(@NotNull String id, @NotNull String label, @NotNull String iconURL, @NotNull List<@NotNull SearchResultSection> sections) {

}

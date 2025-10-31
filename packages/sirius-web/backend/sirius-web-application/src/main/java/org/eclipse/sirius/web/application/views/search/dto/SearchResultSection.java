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

import jakarta.validation.constraints.NotNull;

/**
 * A section corresponds to a specific value for a given grouping.
 *
 * @author pcdavid
 */
public record SearchResultSection(@NotNull String id, @NotNull String label, @NotNull String iconURL) {

}

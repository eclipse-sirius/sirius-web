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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * An individual search match.
 *
 * @author pcdavid
 */
public record SearchResultItem(UUID id, String label, String kind, List<String> iconURL) {
    public SearchResultItem {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(iconURL);
    }

}

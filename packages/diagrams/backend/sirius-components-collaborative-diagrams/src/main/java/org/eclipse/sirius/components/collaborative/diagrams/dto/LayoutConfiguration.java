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

import java.util.List;
import java.util.Objects;

/**
 * A layout configuration, contains the identifier, the label, the icon URLs and the layout options.
 *
 * @author ocailleau
 * @since v2026.12.0
 */
public record LayoutConfiguration(String id, String label, List<String> iconURL, List<LayoutOptionEntry> layoutOptions) {

    public LayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURL);
        Objects.requireNonNull(layoutOptions);
    }
}

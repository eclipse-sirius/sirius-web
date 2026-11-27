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
package org.eclipse.sirius.components.trees;

import java.util.Objects;

/**
 * A passive visual cue displayed alongside a tree item's label.
 *
 * @param iconURL
 *            the required icon URL
 * @param label
 *            the optional text, or {@code null} for an icon-only decorator
 *
 * @author pcdavid
 */
public record TreeItemDecorator(String iconURL, String label) {

    public TreeItemDecorator {
        Objects.requireNonNull(iconURL);
    }
}

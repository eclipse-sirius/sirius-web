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
package org.eclipse.sirius.components.datatree;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * A data tree node.
 *
 * @author gdaniel
 */
public record DataTreeNode(String id, String parentId, StyledString label, List<String> iconURLs, List<List<String>> endIconsURLs) {

    public DataTreeNode {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURLs);
        Objects.requireNonNull(endIconsURLs);
    }

}

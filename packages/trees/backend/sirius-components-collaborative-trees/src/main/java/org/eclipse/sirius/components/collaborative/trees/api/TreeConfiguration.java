/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration of the tree event processor.
 *
 * @author sbegaudeau
 */
public class TreeConfiguration implements IRepresentationConfiguration {

    public static final String TREE_ID = "treeId";

    private final String treeId;

    private final List<String> activeFilterIds;

    private final List<String> expanded;

    public TreeConfiguration(String editingContextId, String treeId, List<String> expanded, List<String> activeFilterIds) {
        String uniqueId = editingContextId + expanded.toString() + activeFilterIds.toString();
        this.treeId = treeId + "&uniqueId=" + UUID.nameUUIDFromBytes(uniqueId.getBytes()).toString();
        this.activeFilterIds = Objects.requireNonNull(activeFilterIds);
        this.expanded = Objects.requireNonNull(expanded);
    }

    @Override
    public String getId() {
        return this.treeId;
    }

    public List<String> getActiveFilterIds() {
        return this.activeFilterIds;
    }

    public List<String> getExpanded() {
        return this.expanded;
    }

}

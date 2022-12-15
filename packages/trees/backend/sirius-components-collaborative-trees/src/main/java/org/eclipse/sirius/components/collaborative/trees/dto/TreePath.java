/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * The tree path to return.
 *
 * @author sbegaudeau
 */
public class TreePath {
    private final List<String> treeItemIdsToExpand;

    private final int maxDepth;

    public TreePath(List<String> treeItemIdsToExpand, int maxDepth) {
        this.treeItemIdsToExpand = List.copyOf(Objects.requireNonNull(treeItemIdsToExpand));
        this.maxDepth = maxDepth;
    }

    public List<String> getTreeItemIdsToExpand() {
        return this.treeItemIdsToExpand;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'treeItemIdsToExpand: {2}, maxDepth: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.treeItemIdsToExpand, this.maxDepth);
    }
}

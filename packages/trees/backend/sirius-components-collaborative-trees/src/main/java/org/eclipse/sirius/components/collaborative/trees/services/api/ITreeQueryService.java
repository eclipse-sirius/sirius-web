/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.services.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;

/**
 * Used to query a tree.
 *
 * @author sbegaudeau
 */
public interface ITreeQueryService {
    Optional<TreeItem> findTreeItem(Tree tree, UUID treeItemId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ITreeQueryService {

        @Override
        public Optional<TreeItem> findTreeItem(Tree tree, UUID treeItemId) {
            return Optional.empty();
        }

    }
}

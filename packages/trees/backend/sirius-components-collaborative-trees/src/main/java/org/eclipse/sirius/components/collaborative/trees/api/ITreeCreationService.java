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
package org.eclipse.sirius.components.collaborative.trees.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 * Service used to create tree representations from scratch.
 *
 * @author Jerome Gout
 */
public interface ITreeCreationService {

    /**
     * Creates a new tree representation using the given parameters.
     *
     * @param label
     *            The label of the tree representation
     * @param targetObject
     *            The object used as the target
     * @param treeDescription
     *            The description of the tree representation
     * @param editingContext
     *            The editing context
     * @return A new tree representation
     */
    Tree create(String label, Object targetObject, TreeDescription treeDescription, IEditingContext editingContext);

    /**
     * Refresh an existing tree.
     *
     * <p>
     * Refreshing a tree seems to always be possible but it may not be the case. In some situation, the semantic element
     * on which the previous tree has been created may not exist anymore and thus we can return an empty optional if we
     * are unable to refresh the tree.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param treeContext
     *            The tree representation context
     * @return An updated tree if we have been able to refresh it.
     */
    Optional<Tree> refresh(IEditingContext editingContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements ITreeCreationService {

        @Override
        public Tree create(String label, Object targetObject, TreeDescription treeDescription, IEditingContext editingContext) {
            return null;
        }

        @Override
        public Optional<Tree> refresh(IEditingContext editingContext) {
            return Optional.empty();
        }
    }

}

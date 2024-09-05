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
package org.eclipse.sirius.components.view.emf.tree;

import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;

/**
 * Interface to provide ids for TreeDescription.
 *
 * @author Jerome Gout
 */
public interface ITreeIdProvider extends IRepresentationDescriptionIdProvider<TreeDescription> {

    String TREE_DESCRIPTION_KIND = PREFIX + "?kind=treeDescription";

    @Override
    String getId(TreeDescription treeDescription);


    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author Jerome Gout
     */
    class NoOp implements ITreeIdProvider {

        @Override
        public String getId(TreeDescription treeDescription) {
            return "";
        }
    }

}

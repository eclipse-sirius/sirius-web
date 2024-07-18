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
package org.eclipse.sirius.components.collaborative.widget.reference.browser.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;

/**
 * Interface of the service for the navigation through the Widget Reference Tree.
 *
 * @author frouene
 */
public interface IModelBrowserNavigationService {

    List<String> getAncestors(IEditingContext editingContext, String treeItemId, Tree tree);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IModelBrowserNavigationService {

        @Override
        public List<String> getAncestors(IEditingContext editingContext, String treeItemId, Tree tree) {
            return List.of();
        }
    }

}

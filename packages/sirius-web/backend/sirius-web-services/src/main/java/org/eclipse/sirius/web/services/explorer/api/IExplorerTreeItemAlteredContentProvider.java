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
package org.eclipse.sirius.web.services.explorer.api;

import java.util.List;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Interface of the service providing an altered version of the tree item content of the Sirius Web Explorer.
 *
 * @author arichard
 */
public interface IExplorerTreeItemAlteredContentProvider {
    boolean canHandle(Object object, List<String> activeFilterIds);

    List<Object> apply(List<Object> computedChildren, VariableManager variableManager);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IExplorerTreeItemAlteredContentProvider {

        @Override
        public boolean canHandle(Object object, List<String> activeFilterIds) {
            return false;
        }

        @Override
        public List<Object> apply(List<Object> computedChildren, VariableManager variableManager) {
            return null;
        }
    }
}

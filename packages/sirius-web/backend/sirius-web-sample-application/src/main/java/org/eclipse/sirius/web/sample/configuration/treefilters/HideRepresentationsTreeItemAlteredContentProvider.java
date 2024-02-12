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
package org.eclipse.sirius.web.sample.configuration.treefilters;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.services.explorer.api.IExplorerTreeItemAlteredContentProvider;
import org.springframework.stereotype.Service;

/**
 * Sample {@link IExplorerTreeItemAlteredContentProvider} for the Hide Representations tree item filter.
 *
 * @author arichard
 */
@Service
public class HideRepresentationsTreeItemAlteredContentProvider implements IExplorerTreeItemAlteredContentProvider {

    @Override
    public boolean canHandle(Object object, List<String> activeFilterIds) {
        return activeFilterIds.contains(SampleTreeFilterProvider.HIDE_REPRESENTATIONS_TREE_FILTER_ID);
    }

    @Override
    public List<Object> apply(List<Object> computedChildren, VariableManager variableManager) {
        List<Object> alteredChildren = new ArrayList<>(computedChildren);
        alteredChildren.removeIf(child -> child instanceof RepresentationMetadata);
        return alteredChildren;
    }
}

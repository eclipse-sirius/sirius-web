/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.trees;

import org.eclipse.sirius.web.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.web.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.trees.Tree;
import org.eclipse.sirius.web.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * Services related to the trees.
 *
 * @author sbegaudeau
 */
@Service
public class TreeService implements ITreeService {

    @Override
    public Tree create(TreeCreationParameters treeCreationParameters) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, treeCreationParameters.getId());
        variableManager.put(IEditingContext.EDITING_CONTEXT, treeCreationParameters.getEditingContext());
        variableManager.put(TreeRenderer.EXPANDED, treeCreationParameters.getExpanded());

        TreeRenderer treeRenderer = new TreeRenderer(variableManager, treeCreationParameters.getTreeDescription());
        return treeRenderer.render();
    }

}

/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemTooltipProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.springframework.stereotype.Service;

/**
 * Provides tooltip for tree items from tree view models.
 *
 * @author gdaniel
 */
@Service
public class ViewTreeItemTooltipProvider implements ITreeItemTooltipProvider {

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    public ViewTreeItemTooltipProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory viewAQLInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return this.viewRepresentationDescriptionPredicate.test(treeDescription);
    }

    @Override
    public String handle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        String result = "";
        var optionalViewTreeDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, treeDescription.getId())
                .filter(org.eclipse.sirius.components.view.tree.TreeDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.tree.TreeDescription.class::cast);

        if (optionalViewTreeDescription.isPresent()) {
            var viewTreeDescription = optionalViewTreeDescription.get();

            AQLInterpreter interpreter = this.viewAQLInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.TREE, tree);
            variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
            variableManager.put(TreeDescription.ID, treeItem.getId());
            Object semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(variableManager);
            variableManager.put(VariableManager.SELF, semanticTreeItemObject);

            result = interpreter.evaluateExpression(variableManager.getVariables(), viewTreeDescription.getTreeItemTooltipExpression())
                    .asString()
                    .orElse("");

        }
        return result;
    }
}

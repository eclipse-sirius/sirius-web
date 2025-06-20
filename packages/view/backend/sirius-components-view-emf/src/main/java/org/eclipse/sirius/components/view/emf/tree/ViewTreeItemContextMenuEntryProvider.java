/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.springframework.stereotype.Service;

/**
 * Tree item context menu entry provider for view tree model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewTreeItemContextMenuEntryProvider(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return this.viewRepresentationDescriptionPredicate.test(treeDescription);
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        var optionalTreeDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, treeDescription.getId())
                .filter(org.eclipse.sirius.components.view.tree.TreeDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.tree.TreeDescription.class::cast);
        if (optionalTreeDescription.isPresent()) {
            var viewTreeDescription = optionalTreeDescription.get();

            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.TREE, tree);
            variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
            variableManager.put(TreeDescription.ID, treeItem.getId());
            var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(variableManager);
            variableManager.put(VariableManager.SELF, semanticTreeItemObject);

            return viewTreeDescription.getContextMenuEntries().stream()
                    .filter(viewAction -> this.isValidActionPrecondition(viewAction, variableManager, interpreter))
                    .map(treeItemContextMenuEntry -> this.convertContextAction(treeItemContextMenuEntry, variableManager, interpreter))
                    .toList();
        }
        return List.of();
    }

    private ITreeItemContextMenuEntry convertContextAction(TreeItemContextMenuEntry viewTreeItemContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        ITreeItemContextMenuEntry result = null;
        var id = this.idProvider.apply(viewTreeItemContextAction).toString();
        if (viewTreeItemContextAction instanceof SingleClickTreeItemContextMenuEntry singleClickTreeItemContextMenuEntry) {
            var label = this.evaluateString(variableManager, interpreter, singleClickTreeItemContextMenuEntry.getLabelExpression());
            var iconURL = this.evaluateStringList(variableManager, interpreter, singleClickTreeItemContextMenuEntry.getIconURLExpression());
            result = new org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry(id, label, iconURL, false);
        } else if (viewTreeItemContextAction instanceof FetchTreeItemContextMenuEntry fetchTreeItemContextMenuEntry) {
            var label = this.evaluateString(variableManager, interpreter, fetchTreeItemContextMenuEntry.getLabelExpression());
            var iconURL = this.evaluateStringList(variableManager, interpreter, fetchTreeItemContextMenuEntry.getIconURLExpression());
            result = new org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntry(id, label, iconURL);
        } else if (viewTreeItemContextAction instanceof CustomTreeItemContextMenuEntry customTreeItemContextMenuEntry) {
            // Use a SingleClickTreeItemContextMenuEntry instance with a dedicated ID to pass the information to the frontend.
            result = new org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry(customTreeItemContextMenuEntry.getContributionId(), "", List.of(), false);
        }
        return result;
    }

    private boolean isValidActionPrecondition(TreeItemContextMenuEntry viewContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var precondition = viewContextAction.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            return this.evaluateBoolean(variableManager, interpreter, precondition);
        }
        return false;
    }

    private String evaluateString(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

    private List<String> evaluateStringList(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        List<String> values = new ArrayList<>();
        if (expression != null && !expression.isBlank()) {
            Optional<List<Object>> optionalResult = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects();
            if (optionalResult.isPresent()) {
                values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
            }
        }
        return values;
    }

    private Boolean evaluateBoolean(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asBoolean()
                .orElse(true);
    }

}

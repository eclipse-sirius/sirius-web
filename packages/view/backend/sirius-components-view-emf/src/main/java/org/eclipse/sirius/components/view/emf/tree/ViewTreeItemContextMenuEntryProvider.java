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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Tree item context menu entry provider for view tree model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    private final IEditService editService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private AQLInterpreter interpreter;

    private VariableManager variableManager;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewTreeItemContextMenuEntryProvider(IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, ApplicationContext applicationContext, IEditService editService, List<IJavaServiceProvider> javaServiceProviders, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.editService =  Objects.requireNonNull(editService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.javaServiceProviders =  Objects.requireNonNull(javaServiceProviders);
        this.applicationContext =  Objects.requireNonNull(applicationContext);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, org.eclipse.sirius.components.trees.description.TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        var viewIdPrefix = ITreeIdProvider.TREE_DESCRIPTION_KIND + "&" + IRepresentationDescriptionIdProvider.SOURCE_KIND + "=" + IRepresentationDescriptionIdProvider.VIEW_SOURCE_KIND;
        return treeDescription.getId().startsWith(viewIdPrefix);
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, org.eclipse.sirius.components.trees.description.TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        var optionalTreeDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, treeDescription.getId())
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);
        if (optionalTreeDescription.isPresent()) {
            var viewTreeDescription = optionalTreeDescription.get();
            this.initializeAQLInterpreter(editingContext, treeDescription, tree, treeItem, viewTreeDescription);
            return viewTreeDescription.getContextMenuEntries().stream()
                .filter(viewAction -> this.isValidActionPrecondition(viewAction, editingContext, treeDescription, tree, treeItem))
                .map(this::convertContextAction)
//                .sorted(Comparator.comparing(ITreeItemContextAction::))
                .toList();
        }
        return List.of();
    }

    private ITreeItemContextMenuEntry convertContextAction(TreeItemContextMenuEntry viewTreeItemContextAction) {
        ITreeItemContextMenuEntry result = null;
        String id = idProvider.apply(viewTreeItemContextAction).toString();
        if (viewTreeItemContextAction instanceof SingleClickTreeItemContextMenuEntry singleClickMenuEntry) {
            result = org.eclipse.sirius.components.trees.SingleClickTreeItemContextMenuEntry.newSingleClickTreeItemContextMenuEntry(id)
                    .label(varManager -> this.evaluateString(varManager, singleClickMenuEntry.getLabelExpression()))
                    .iconURL(varManager -> this.evaluateStringList(varManager, singleClickMenuEntry.getIconURLExpression()))
                    .build();
        } else if (viewTreeItemContextAction instanceof FetchTreeItemContextMenuEntry fetchMenuEntry) {
            result = org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntry.newFetchTreeItemContextMenuEntry(id)
                    .label(varManager -> this.evaluateString(varManager, fetchMenuEntry.getLabelExpression()))
                    .iconURL(varManager -> this.evaluateStringList(varManager, fetchMenuEntry.getIconURLExpression()))
                    .build();
        }
        return result;
    }

    private boolean isValidActionPrecondition(TreeItemContextMenuEntry viewContextAction, IEditingContext editingContext, org.eclipse.sirius.components.trees.description.TreeDescription treeDescription, Tree tree,
            TreeItem treeItem) {
        var precondition = viewContextAction.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            return this.evaluateBoolean(this.variableManager, precondition);
        }
        return false;
    }

    private String evaluateString(VariableManager varManager, String expression) {
        return this.interpreter.evaluateExpression(varManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

    private List<String> evaluateStringList(VariableManager varManager, String expression) {
        List<String> values = new ArrayList<>();
        if (expression != null && !expression.isBlank()) {
            Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(varManager.getVariables(), expression).asObjects();
            if (optionalResult.isPresent()) {
                values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
            }
        }
        return values;
    }

    private Boolean evaluateBoolean(VariableManager varManager, String expression) {
        return this.interpreter.evaluateExpression(varManager.getVariables(), expression)
                .asBoolean()
                .orElse(true);
    }

    private void initializeAQLInterpreter(IEditingContext editingContext, org.eclipse.sirius.components.trees.description.TreeDescription treeDescription, Tree tree, TreeItem treeItem,
                                          TreeDescription viewTreeDescription) {
        this.interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());
        this.variableManager = new VariableManager();
        this.variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        this.variableManager.put(org.eclipse.sirius.components.trees.description.TreeDescription.TREE, tree);
        this.variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
        this.variableManager.put(org.eclipse.sirius.components.trees.description.TreeDescription.ID, treeItem.getId());
        var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(this.variableManager);
        this.variableManager.put(VariableManager.SELF, semanticTreeItemObject);
    }
}

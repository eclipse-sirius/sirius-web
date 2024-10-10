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

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.trees.api.IFetchTreeItemContextMenuEntryDataProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntryData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.springframework.stereotype.Service;

/**
 * Fetch tree item context menu entry data provider for view tree model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewFetchTreeItemContextMenuEntryDataProvider implements IFetchTreeItemContextMenuEntryDataProvider {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewFetchTreeItemContextMenuEntryDataProvider(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(TreeDescription treeDescription) {
        return this.viewRepresentationDescriptionPredicate.test(treeDescription);
    }

    @Override
    public FetchTreeItemContextMenuEntryData handle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, String treeItemMenuContextEntryId) {
        var optionalTreeDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, treeDescription.getId())
                .filter(org.eclipse.sirius.components.view.tree.TreeDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.tree.TreeDescription.class::cast);
        if (optionalTreeDescription.isPresent()) {
            var viewTreeDescription = optionalTreeDescription.get();

            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(org.eclipse.sirius.components.trees.description.TreeDescription.TREE, tree);
            variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
            variableManager.put(org.eclipse.sirius.components.trees.description.TreeDescription.ID, treeItem.getId());
            var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(variableManager);
            variableManager.put(VariableManager.SELF, semanticTreeItemObject);

            var fetchEntry = viewTreeDescription.getContextMenuEntries().stream()
                    .filter(FetchTreeItemContextMenuEntry.class::isInstance)
                    .map(FetchTreeItemContextMenuEntry.class::cast)
                    .filter(entry -> Objects.equals(treeItemMenuContextEntryId, this.idProvider.apply(entry).toString()))
                    .findFirst();
            if (fetchEntry.isPresent()) {
                var urlToFetch = this.evaluateString(variableManager, interpreter, fetchEntry.get().getUrlExression());
                return new FetchTreeItemContextMenuEntryData(urlToFetch, this.convertFetchKind(fetchEntry.get().getKind()));
            }
        }
        return null;
    }

    private FetchTreeItemContextMenuEntryKind convertFetchKind(org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind fetchActionKind) {
        return switch (fetchActionKind) {
            case DOWNLOAD -> FetchTreeItemContextMenuEntryKind.DOWNLOAD;
            case OPEN -> FetchTreeItemContextMenuEntryKind.OPEN;
        };
    }

    private String evaluateString(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

}

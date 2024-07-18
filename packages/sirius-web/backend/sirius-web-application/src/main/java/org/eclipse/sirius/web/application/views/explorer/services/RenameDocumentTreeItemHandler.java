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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.views.explorer.services.api.IRenameTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * Handles document renaming triggered via a tree item from the explorer.
 *
 * @author frouene
 */
@Service
public class RenameDocumentTreeItemHandler implements IRenameTreeItemHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public RenameDocumentTreeItemHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        return ExplorerDescriptionProvider.DOCUMENT_KIND.equals(treeItem.getKind());
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, String newLabel, Tree tree) {
        IStatus result = new Failure("Something went wrong while handling this rename action.");

        var optionalTreeDescription = this.representationDescriptionSearchService.findById(editingContext, tree.getDescriptionId())
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, treeItem.getId());

            var object = optionalTreeDescription.get().getTreeItemObjectProvider().apply(variableManager);
            if (object instanceof Resource resource) {
                resource.eAdapters().stream()
                        .filter(ResourceMetadataAdapter.class::isInstance)
                        .map(ResourceMetadataAdapter.class::cast)
                        .findFirst()
                        .ifPresent(adapter -> adapter.setName(newLabel));

                result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
            }
        }
        return result;
    }
}

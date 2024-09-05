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
package org.eclipse.sirius.components.collaborative.trees.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Services for the tree navigation.
 *
 * @author arichard
 */
@Service
public class TreeNavigationService implements ITreeNavigationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public TreeNavigationService(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public List<String> getAncestors(IEditingContext editingContext, Tree tree, String treeItemId) {
        List<String> ancestorsIds = new ArrayList<>();

        Optional<Object> optionalObject = this.getParentSemanticObject(editingContext, tree, treeItemId);
        while (optionalObject.isPresent()) {
            var optionalParentId = this.getItemId(editingContext, tree, optionalObject.get());
            if (optionalParentId.isPresent()) {
                ancestorsIds.add(optionalParentId.get());
                optionalObject = this.getParentSemanticObject(editingContext, tree, optionalParentId.get());
            } else {
                optionalObject = Optional.empty();
            }
        }
        return ancestorsIds;
    }

    private Optional<Object> getParentSemanticObject(IEditingContext editingContext, Tree tree, String elementId) {
        Optional<Object> result = Optional.empty();

        var variableManager = new VariableManager();
        var optionalSemanticObject = this.getTreeItemObject(editingContext, tree, elementId);
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());

        if (optionalSemanticObject.isPresent() && optionalTreeDescription.isPresent()) {
            variableManager.put(VariableManager.SELF, optionalSemanticObject.get());
            variableManager.put(TreeDescription.ID, elementId);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            result = Optional.ofNullable(optionalTreeDescription.get().getParentObjectProvider().apply(variableManager));
        }
        return result;
    }

    private Optional<String>  getItemId(IEditingContext editingContext, Tree tree, Object object) {
        Optional<String> result = Optional.empty();
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());
        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, object);
            result = Optional.of(optionalTreeDescription.get().getTreeItemIdProvider().apply(variableManager));
        }
        return result;
    }

    private Optional<Object> getTreeItemObject(IEditingContext editingContext, Tree tree, String id) {
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, id);
            return Optional.ofNullable(optionalTreeDescription.get().getTreeItemObjectProvider().apply(variableManager));
        }
        return Optional.empty();
    }

    private Optional<TreeDescription> getTreeDescription(IEditingContext editingContext, String descriptionId) {
        return this.representationDescriptionSearchService.findById(editingContext, descriptionId)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);
    }
}

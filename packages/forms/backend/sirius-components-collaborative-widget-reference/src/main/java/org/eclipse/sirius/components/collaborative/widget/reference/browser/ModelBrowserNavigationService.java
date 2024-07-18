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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.api.IModelBrowserNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Services for the navigation through the Widget Reference Tree.
 *
 * @author frouene
 */
@Service
public class ModelBrowserNavigationService implements IModelBrowserNavigationService {

    private final IIdentityService identityService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ModelBrowserNavigationService(IIdentityService identityService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public List<String> getAncestors(IEditingContext editingContext, String treeItemId, Tree tree) {
        List<String> ancestorsIds = new ArrayList<>();

        Optional<Object> optionalObject = this.getParentSemanticObject(treeItemId, ancestorsIds, editingContext, tree);
        while (optionalObject.isPresent()) {
            String parentId = this.getItemId(optionalObject.get());
            ancestorsIds.add(parentId);
            optionalObject = this.getParentSemanticObject(parentId, ancestorsIds, editingContext, tree);
        }
        return ancestorsIds;
    }

    private Optional<Object> getParentSemanticObject(String elementId, List<String> ancestorsIds, IEditingContext editingContext, Tree tree) {
        Optional<Object> result = Optional.empty();

        var variableManager = new VariableManager();
        var optionalSemanticObject = this.getTreeItemObject(editingContext, elementId, tree);
        var optionalTreeDescription = this.representationDescriptionSearchService.findById(editingContext, tree.getDescriptionId())
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        if (optionalSemanticObject.isPresent() && optionalTreeDescription.isPresent()) {
            variableManager.put(VariableManager.SELF, optionalSemanticObject.get());
            variableManager.put(TreeDescription.ID, elementId);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            result = Optional.ofNullable(optionalTreeDescription.get().getParentObjectProvider().apply(variableManager));
        }
        return result;
    }

    private String getItemId(Object object) {
        String result = null;
        if (object instanceof Resource resource) {
            result = resource.getURI().path().substring(1);
        } else if (object instanceof EObject) {
            result = this.identityService.getId(object);
        }
        return result;
    }

    private Optional<Object> getTreeItemObject(IEditingContext editingContext, String id, Tree tree) {
        var optionalTreeDescription = this.representationDescriptionSearchService.findById(editingContext, tree.getDescriptionId())
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, id);
            return Optional.ofNullable(optionalTreeDescription.get().getTreeItemObjectProvider().apply(variableManager));
        }
        return Optional.empty();
    }
}

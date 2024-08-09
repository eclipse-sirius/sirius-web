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
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeDescriptionBuilder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * TreeDescriptionBuilder service. It provider a method to create a TreeDescription to display a given selectable
 * elements set.
 *
 * @author fbarbin
 */
@Service
public class TreeDescriptionBuilder implements ITreeDescriptionBuilder {

    private final IObjectService objectService;

    public TreeDescriptionBuilder(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public TreeDescription createSelectableElementsTreeDescription(String descriptionId, String representationName, Predicate<VariableManager> canCreatePredicate, Function<VariableManager, Boolean> isSelectableProvider,
            Function<VariableManager, List<?>> elementsProvider) {
        return TreeDescription.newTreeDescription(descriptionId)
                .label(representationName)
                .idProvider(variableManager -> variableManager.get(TreeConfiguration.TREE_ID, String.class).orElse(null))
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .targetObjectIdProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .iconURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(isSelectableProvider)
                .elementsProvider(elementsProvider)
                .hasChildrenProvider(variableManager -> this.hasChildren(variableManager, isSelectableProvider))
                .childrenProvider(variableManager -> this.getChildren(variableManager, isSelectableProvider))
                // This predicate will NOT be used while creating the model browser, but we don't want to see the description of the
                // model browser in the list of representations that can be created. Thus, we will return false all the time.
                .canCreatePredicate(canCreatePredicate)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .build();
    }

    private boolean hasChildren(VariableManager variableManager, Function<VariableManager, Boolean> isSelectableProvider) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean hasChildren = false;
        if (self != null) {
            hasChildren = !this.objectService.getContents(self).isEmpty();
            // if (self instanceof Resource resource) {
            // hasChildren = !resource.getContents().isEmpty();
            // } else if (self instanceof EObject eObject) {
            // hasChildren = !eObject.eContents().isEmpty();
            hasChildren = hasChildren && this.hasCompatibleDescendants(variableManager, self, false, isSelectableProvider);
            // }
        }
        return hasChildren;
    }

    private boolean hasCompatibleDescendants(VariableManager variableManager, Object object, boolean isDescendant, Function<VariableManager, Boolean> isSelectableProvider) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, object);
        return isDescendant && isSelectableProvider.apply(childVariableManager)
                || this.objectService.getContents(object).stream().anyMatch(eContent -> this.hasCompatibleDescendants(childVariableManager, eContent, true, isSelectableProvider));
    }

    private List<Object> getChildren(VariableManager variableManager, Function<VariableManager, Boolean> isSelectableProvider) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);
                if (self != null) {
                    List<Object> contents = this.objectService.getContents(self);
                    result.addAll(contents);
                }
            }
        }
        result.removeIf(object -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, object);
            return !isSelectableProvider.apply(childVariableManager) && !this.hasChildren(childVariableManager, isSelectableProvider);
        });
        return result;
    }

    private String getTreeItemId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse("");
    }

    private List<String> getImageURL(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(List.of());
    }

    private String getKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse("");
    }

    private String getLabel(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse("");
    }

    private boolean isEditable(VariableManager variableManager) {
        return false;
    }

    private boolean isDeletable(VariableManager variableManager) {
        return false;
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        return new Failure("");
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        return new Failure("");
    }
}

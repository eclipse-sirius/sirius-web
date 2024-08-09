/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.selection.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.Selection;
import org.eclipse.sirius.components.selection.SelectionObject;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

/**
 * Renderer used to create the selection representation from its description and some variables.
 *
 * @author arichard
 */
public class SelectionRenderer {

    private final VariableManager variableManager;

    private final SelectionDescription selectionDescription;

    private final Map<String, SelectionNode> computedNodes = new HashMap<>();

    private final IObjectService objectService;

    public SelectionRenderer(VariableManager variableManager, SelectionDescription selectionDescription, IObjectService objectService) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.selectionDescription = Objects.requireNonNull(selectionDescription);
        this.objectService = Objects.requireNonNull(objectService);
    }

    public Selection render() {
        String id = this.selectionDescription.getIdProvider().apply(this.variableManager);
        String label = this.selectionDescription.getLabelProvider().apply(this.variableManager);
        String message = this.selectionDescription.getMessageProvider().apply(this.variableManager);
        String targetObjectId = this.selectionDescription.getTargetObjectIdProvider().apply(this.variableManager);

        List<?> selectionObjects = this.selectionDescription.getObjectsProvider().apply(this.variableManager);
        List<SelectionObject> objects;
        if (this.selectionDescription.isDisplayedAsTree()) {
            objects = this.computeTreeSelectionObjects(selectionObjects);
        } else {
            objects = this.computeFlatListSelectionObject(selectionObjects);
        }
        boolean displayedAsTree = this.selectionDescription.isDisplayedAsTree();
        return Selection.newSelection(id)
                .descriptionId(this.selectionDescription.getId())
                .label(label)
                .targetObjectId(targetObjectId)
                .message(message)
                .objects(objects)
                .displayedAsTree(displayedAsTree)
                .build();
    }

    private List<SelectionObject> computeFlatListSelectionObject(List<?> selectionObjects) {
        List<SelectionObject> objects = new ArrayList<>(selectionObjects.size());
        for (Object selectionObject : selectionObjects) {
            VariableManager selectionObjectVariableManager = this.variableManager.createChild();
            selectionObjectVariableManager.put(VariableManager.SELF, selectionObject);
            objects.add(this.renderSelectionObject(selectionObjectVariableManager, null, true));
        }
        return objects;
    }

    private List<SelectionObject> computeTreeSelectionObjects(List<?> selectionObjects) {
        selectionObjects.forEach(element -> {
            String elementId = this.objectService.getId(element);
            // We make sure the element has not been computed yet (by the computeAncestors of a previous element for
            // instance)
            if (!this.computedNodes.containsKey(elementId)) {
                SelectionNode elementSelectionNode = this.createSelectionNode(element, true);
                this.computedNodes.put(elementId, elementSelectionNode);
                this.computeAncestors(element, elementSelectionNode, selectionObjects);
            }
        });
        return this.computedNodes.keySet().stream()
                .map(this::convertToSelectionObject)
                .toList();
    }

    private void computeAncestors(Object element, SelectionNode elementSelectionNode, List<?> selectionObjects) {
        Object parent = this.objectService.getParent(element);
        // If the parent is null, it a root element.
        if (parent != null) {
            String parentId = this.objectService.getId(parent);
            // If the parentNode exists in the computedNodes, we add the element as child and we stop here (it has
            // already be computed by a previous pass)
            if (this.computedNodes.containsKey(parentId)) {
                elementSelectionNode.setParentId(parentId);
            } else {
                // The parentNode has not been computed yet
                boolean isSelectable = selectionObjects.contains(parent);
                SelectionNode parentNode = this.createSelectionNode(parent, isSelectable);
                this.computedNodes.put(parentId, parentNode);
                elementSelectionNode.setParentId(parentId);
                this.computeAncestors(parent, parentNode, selectionObjects);
            }
        }
    }

    private SelectionNode createSelectionNode(Object element, boolean isSelectable) {
        return new SelectionNode(element, isSelectable);
    }

    private SelectionObject convertToSelectionObject(String id) {
        SelectionNode selectionNode = this.computedNodes.get(id);
        VariableManager selectionObjectVariableManager = this.variableManager.createChild();
        selectionObjectVariableManager.put(VariableManager.SELF, selectionNode.getObject());
        return this.renderSelectionObject(selectionObjectVariableManager, selectionNode.getParentId(), selectionNode.isSelectable());
    }

    private SelectionObject renderSelectionObject(VariableManager selectionObjectVariableManager, String parentId, boolean selectable) {
        String id = this.selectionDescription.getSelectionObjectsIdProvider().apply(selectionObjectVariableManager);
        String label = this.selectionDescription.getLabelProvider().apply(selectionObjectVariableManager);
        List<String> iconURL = this.selectionDescription.getIconURLProvider().apply(selectionObjectVariableManager);

        var selectionObjectBuilder = SelectionObject.newSelectionObject(UUID.fromString(id))
                .label(label)
                .iconURL(iconURL)
                .isSelectable(selectable);
        if (parentId != null) {
            selectionObjectBuilder.parentId(parentId);
        }
        return selectionObjectBuilder.build();
    }
}

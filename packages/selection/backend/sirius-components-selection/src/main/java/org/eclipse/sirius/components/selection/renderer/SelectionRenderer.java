/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public SelectionRenderer(VariableManager variableManager, SelectionDescription selectionDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.selectionDescription = Objects.requireNonNull(selectionDescription);
    }

    public Selection render() {
        String id = this.selectionDescription.getIdProvider().apply(this.variableManager);
        String label = this.selectionDescription.getLabelProvider().apply(this.variableManager);
        String message = this.selectionDescription.getMessageProvider().apply(this.variableManager);
        String targetObjectId = this.selectionDescription.getTargetObjectIdProvider().apply(this.variableManager);

        List<?> selectionObjects = this.selectionDescription.getObjectsProvider().apply(this.variableManager);
        List<SelectionObject> objects = new ArrayList<>(selectionObjects.size());
        for (Object selectionObject : selectionObjects) {
            VariableManager selectionObjectVariableManager = this.variableManager.createChild();
            selectionObjectVariableManager.put(VariableManager.SELF, selectionObject);
            objects.add(this.renderSelectionObject(selectionObjectVariableManager));
        }

        // @formatter:off
        return Selection.newSelection(id)
                .descriptionId(this.selectionDescription.getId())
                .label(label)
                .targetObjectId(targetObjectId)
                .message(message)
                .objects(objects)
                .build();
        // @formatter:on
    }

    private SelectionObject renderSelectionObject(VariableManager selectionObjectVariableManager) {
        String id = this.selectionDescription.getSelectionObjectsIdProvider().apply(selectionObjectVariableManager);
        String label = this.selectionDescription.getLabelProvider().apply(selectionObjectVariableManager);
        List<String> iconURL = this.selectionDescription.getIconURLProvider().apply(selectionObjectVariableManager);

        return SelectionObject.newSelectionObject(UUID.fromString(id))
                .label(label)
                .iconURL(iconURL)
                .build();
    }
}

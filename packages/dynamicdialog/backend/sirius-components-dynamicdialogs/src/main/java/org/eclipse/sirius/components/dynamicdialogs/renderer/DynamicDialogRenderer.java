/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.dynamicdialogs.renderer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.dynamicdialogs.DSelectWidget;
import org.eclipse.sirius.components.dynamicdialogs.DTextFieldWidget;
import org.eclipse.sirius.components.dynamicdialogs.DWidget;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialog;
import org.eclipse.sirius.components.dynamicdialogs.description.AbstractDWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DSelectWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DTextFieldWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetOutputDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DynamicDialogDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Class used to render the dynamic dialog from its description.
 *
 * @author Laurent Fasani
 */
public class DynamicDialogRenderer {

    public DynamicDialog render(DynamicDialogDescription dynamicDialogDescription, Object semanticObject) {

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, semanticObject);

        String titleDescription = dynamicDialogDescription.getTitleProvider().apply(variableManager);
        String dialogDescription = dynamicDialogDescription.getDescriptionProvider().apply(variableManager);
        List<DWidget> widgets = new ArrayList<>();
        dynamicDialogDescription.getWidgetDescriptions().forEach(widgetDescription -> {
            widgets.add(this.renderWidget(dynamicDialogDescription, widgetDescription, semanticObject));
        });

        DynamicDialog dynamicDialog = DynamicDialog.newDynamicDialog(dynamicDialogDescription.getId())//
                .descriptionId(dynamicDialogDescription.getId())//
                .label(titleDescription)//
                .descriptionId(dialogDescription).widgets(widgets)//
                .build();
        return dynamicDialog;
    }

    private DWidget renderWidget(DynamicDialogDescription dynamicDialogDescription, AbstractDWidgetDescription widgetDescription, Object semanticObject) {

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, semanticObject);
        String label = widgetDescription.getLabelProvider().apply(variableManager);

        String initialValue = widgetDescription.getInitialValueProvider().apply(variableManager);

        List<String> inputVariables = new ArrayList<>();
        for (DWidgetOutputDescription inputWidget : widgetDescription.getInputs()) {
            inputVariables.add(inputWidget.name());
        }
        DWidget dWidget = null;
        if (widgetDescription instanceof DSelectWidgetDescription) {
            dWidget = DSelectWidget.newDSelectWidget(widgetDescription.getId())//
                    .descriptionId(widgetDescription.getId())//
                    .label(label)//
                    .initialValue(initialValue)//
                    .inputVariableNames(inputVariables)//
                    .outputVariableName(widgetDescription.getOutput().name())//
                    .parentId(dynamicDialogDescription.getId())//
                    .required(true).build();

        } else if (widgetDescription instanceof DTextFieldWidgetDescription) {
            dWidget = DTextFieldWidget.newDTextFieldWidget(widgetDescription.getId())//
                    .descriptionId(widgetDescription.getId())//
                    .label(label)//
                    .initialValue(initialValue)//
                    .inputVariableNames(inputVariables)//
                    .outputVariableName(widgetDescription.getOutput().name())//
                    .parentId(dynamicDialogDescription.getId())//
                    .required(true).build();
        }
        return dWidget;
    }
}

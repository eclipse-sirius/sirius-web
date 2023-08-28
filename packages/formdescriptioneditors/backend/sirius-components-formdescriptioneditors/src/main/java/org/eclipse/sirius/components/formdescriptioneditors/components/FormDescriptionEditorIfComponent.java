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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorIfDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorIfElementProps;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Component used to render a "virtual" widget to represent a If element when rendering in the context of a
 * FormDescriptionEditor.
 *
 * @author pcdavid
 */
public class FormDescriptionEditorIfComponent implements IComponent {

    private final FormDescriptionEditorIfComponentProps props;

    public FormDescriptionEditorIfComponent(FormDescriptionEditorIfComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        FormDescriptionEditorIfDescription ifDescription = this.props.formDescriptionEditorIfDescription();

        String label = ifDescription.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, ifDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, ifDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = ifDescription.getIdProvider().apply(idVariableManager);

        List<Element> childrenWidgets = new ArrayList<>();
        VariableManager childrenVariableManager = variableManager.createChild();
        childrenVariableManager.put(FormComponent.PARENT_ELEMENT_ID, id);
        ifDescription.getChildren().forEach(controlDescription -> {
            if (controlDescription instanceof AbstractWidgetDescription widgetDescription) {
                var widgetComponentProps = new FormDescriptionEditorWidgetComponentProps(childrenVariableManager, widgetDescription, this.props.widgetDescriptors());
                childrenWidgets.add(new Element(FormDescriptionEditorWidgetComponent.class, widgetComponentProps));
            }
        });

        childrenWidgets.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(ifDescription, variableManager)));

        var ifElementProps = FormDescriptionEditorIfElementProps.newFormDescriptionEditorIfElementProps(id)
                .label(label)
                .children(childrenWidgets)
                .build();

        return new Element(FormDescriptionEditorIfElementProps.TYPE, ifElementProps);
    }

}

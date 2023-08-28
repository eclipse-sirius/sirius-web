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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorForDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorForElementProps;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Component used to render a "virtual" widget to represent a For element when rendering in the context of a
 * FormDescriptionEditor.
 *
 * @author pcdavid
 */
public class FormDescriptionEditorForComponent implements IComponent {

    private final FormDescriptionEditorForComponentProps props;

    public FormDescriptionEditorForComponent(FormDescriptionEditorForComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        FormDescriptionEditorForDescription forDescription = this.props.formDescriptionEditorForDescription();

        String label = forDescription.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, forDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, forDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = forDescription.getIdProvider().apply(idVariableManager);

        List<Element> childrenWidgets = new ArrayList<>();
        VariableManager childrenVariableManager = variableManager.createChild();
        childrenVariableManager.put(FormComponent.PARENT_ELEMENT_ID, id);
        forDescription.getChildren().forEach(controlDescription -> {
            if (controlDescription instanceof AbstractWidgetDescription widgetDescription) {
                var widgetComponentProps = new FormDescriptionEditorWidgetComponentProps(childrenVariableManager, widgetDescription, this.props.widgetDescriptors());
                childrenWidgets.add(new Element(FormDescriptionEditorWidgetComponent.class, widgetComponentProps));
            }
        });

        childrenWidgets.add(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(forDescription, variableManager)));

        var forElementProps = FormDescriptionEditorForElementProps.newFormDescriptionEditorForElementProps(id)
                .label(label)
                .children(childrenWidgets)
                .build();

        return new Element(FormDescriptionEditorForElementProps.TYPE, forElementProps);
    }

}

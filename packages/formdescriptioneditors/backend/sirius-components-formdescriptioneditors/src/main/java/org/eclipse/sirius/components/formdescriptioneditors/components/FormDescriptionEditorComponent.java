/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponent;
import org.eclipse.sirius.components.forms.components.ToolbarActionComponentProps;
import org.eclipse.sirius.components.forms.components.WidgetComponent;
import org.eclipse.sirius.components.forms.components.WidgetComponentProps;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FormDescription;

/**
 * The component used to render the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponent implements IComponent {

    private final FormDescriptionEditorComponentProps props;

    private final ViewFormDescriptionEditorConverterSwitch converter;

    public FormDescriptionEditorComponent(FormDescriptionEditorComponentProps props) {
        this.props = props;
        this.converter = new ViewFormDescriptionEditorConverterSwitch(props.getFormDescriptionEditorDescription(), props.getVariableManager());
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        var formDescription = variableManager.get(VariableManager.SELF, FormDescription.class).get();

        FormDescriptionEditorDescription formDescriptionEditorDescription = this.props.getFormDescriptionEditorDescription();
        var optionalPreviousFormDescriptionEditor = this.props.getOptionalPreviousFormDescriptionEditor();

        String id = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getId).orElseGet(() -> UUID.randomUUID().toString());
        // @formatter:off
        String label = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getLabel)
                .orElseGet(() -> variableManager.get(FormDescriptionEditor.LABEL, String.class)
                .orElse("Form Description Editor")); //$NON-NLS-1$
        // @formatter:on
        Function<VariableManager, String> targetObjectIdProvider = formDescriptionEditorDescription.getTargetObjectIdProvider();
        String targetObjectId = targetObjectIdProvider.apply(variableManager);

        List<Element> childrenWidgets = new ArrayList<>();

        formDescription.getToolbarActions().forEach(viewToolbarActionDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, viewToolbarActionDescription);
            AbstractWidgetDescription toolbarActionDescription = this.converter.doSwitch(viewToolbarActionDescription);
            if (toolbarActionDescription instanceof ButtonDescription) {
                ToolbarActionComponentProps toolbarActionComponentProps = new ToolbarActionComponentProps(childVariableManager, (ButtonDescription) toolbarActionDescription);
                childrenWidgets.add(new Element(ToolbarActionComponent.class, toolbarActionComponentProps));
            }
        });

        formDescription.getWidgets().forEach(viewWidgetDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, viewWidgetDescription);
            AbstractWidgetDescription widgetDescription = this.converter.doSwitch(viewWidgetDescription);
            WidgetComponentProps widgetComponentProps = new WidgetComponentProps(childVariableManager, widgetDescription);
            childrenWidgets.add(new Element(WidgetComponent.class, widgetComponentProps));
        });

        // @formatter:off
        FormDescriptionEditorElementProps formDescriptionEditorElementProps = FormDescriptionEditorElementProps.newFormDescriptionEditorElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescriptionEditorDescription.getId())
                .children(childrenWidgets)
                .build();
        // @formatter:on

        return new Element(FormDescriptionEditorElementProps.TYPE, formDescriptionEditorElementProps);
    }
}

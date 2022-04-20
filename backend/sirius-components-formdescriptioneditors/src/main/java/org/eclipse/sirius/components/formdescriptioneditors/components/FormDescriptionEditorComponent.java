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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.description.AbstractFormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the form description editor.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponent implements IComponent {

    private final FormDescriptionEditorComponentProps props;

    public FormDescriptionEditorComponent(FormDescriptionEditorComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        FormDescriptionEditorDescription formDescriptionEditorDescription = this.props.getFormDescriptionEditorDescription();
        var optionalPreviousFormDescriptionEditor = this.props.getOptionalPreviousFormDescriptionEditor();

        String id = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getId).orElseGet(() -> UUID.randomUUID().toString());
        String label = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getLabel).orElseGet(() -> "Form Description Editor"); //$NON-NLS-1$
        String targetObjectId = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(variableManager);
        List<FormDescriptionEditorWidget> widgets = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getWidgets).orElseGet(() -> List.of());

        // @formatter:off
        List<Element> children = new ArrayList<>();
        widgets.stream().forEach(widget -> {
            FormDescriptionEditorWidgetComponentProps widgetComponentProps = new FormDescriptionEditorWidgetComponentProps(variableManager, this.getWidgetDescription(formDescriptionEditorDescription, widget.getKind()), Optional.of(widget));
            children.add(new Element(FormDescriptionEditorWidgetComponent.class, widgetComponentProps));
        });

        FormDescriptionEditorElementProps formDescriptionEditorElementProps = FormDescriptionEditorElementProps.newFormDescriptionEditorElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescriptionEditorDescription.getId())
                .children(children)
                .build();

        return new Element(FormDescriptionEditorElementProps.TYPE, formDescriptionEditorElementProps);
        // @formatter:on
    }

    private AbstractFormDescriptionEditorWidgetDescription getWidgetDescription(FormDescriptionEditorDescription formDescriptionEditorDescription, String widgetKind) {
        return formDescriptionEditorDescription.getWidgetDescriptions().stream().filter(wd -> widgetKind.equals(wd.getLabel())).findFirst().orElse(null);
    }

}

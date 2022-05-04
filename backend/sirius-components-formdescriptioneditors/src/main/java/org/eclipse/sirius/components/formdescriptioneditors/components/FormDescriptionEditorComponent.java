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
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.WidgetDescription;

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
        var formDescription = variableManager.get(VariableManager.SELF, FormDescription.class).get();

        FormDescriptionEditorDescription formDescriptionEditorDescription = this.props.getFormDescriptionEditorDescription();
        var optionalPreviousFormDescriptionEditor = this.props.getOptionalPreviousFormDescriptionEditor();

        String id = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getId).orElseGet(() -> UUID.randomUUID().toString());
        String label = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getLabel).orElseGet(() -> "Form Description Editor"); //$NON-NLS-1$
        String targetObjectId = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(variableManager);
        List<FormDescriptionEditorWidget> widgets = new ArrayList<>();
        widgets = formDescription.getWidgets().stream().map(widget -> {
            String name = widget.getName();
            if (name == null) {
                name = this.getKind(widget);
            }
            // @formatter:off
            return FormDescriptionEditorWidget.newFormDescriptionEditorWidget(UUID.nameUUIDFromBytes(EcoreUtil.getURI(widget).toString().getBytes()).toString())
                    .label(name)
                    .kind(this.getKind(widget))
                    .build();
            // @formatter:on
        }).collect(Collectors.toList());

        // @formatter:off
        FormDescriptionEditorElementProps formDescriptionEditorElementProps = FormDescriptionEditorElementProps.newFormDescriptionEditorElementProps(id)
                .label(label)
                .targetObjectId(targetObjectId)
                .descriptionId(formDescriptionEditorDescription.getId())
                .widgets(widgets)
                .build();

        return new Element(FormDescriptionEditorElementProps.TYPE, formDescriptionEditorElementProps);
        // @formatter:on
    }

    private String getKind(WidgetDescription widget) {
        return widget.eClass().getName().replace("Description", ""); //$NON-NLS-1$ //$NON-NLS-2$
    }
}

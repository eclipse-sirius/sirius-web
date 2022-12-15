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
        // @formatter:off
        String label = optionalPreviousFormDescriptionEditor.map(FormDescriptionEditor::getLabel)
                .orElseGet(() -> variableManager.get(FormDescriptionEditor.LABEL, String.class)
                .orElse("Form Description Editor"));
        // @formatter:on
        Function<VariableManager, String> targetObjectIdProvider = formDescriptionEditorDescription.getTargetObjectIdProvider();
        String targetObjectId = targetObjectIdProvider.apply(variableManager);

        List<Element> childrenWidgets = new ArrayList<>();

        formDescription.getGroups().forEach(viewGroupDescription -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, viewGroupDescription);
            FormDescriptionEditorGroupComponentProps fdeGroupComponentProps = new FormDescriptionEditorGroupComponentProps(childVariableManager, this.props.getFormDescriptionEditorDescription());
            childrenWidgets.add(new Element(FormDescriptionEditorGroupComponent.class, fdeGroupComponentProps));
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

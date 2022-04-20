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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorTextfieldElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * The component used to render the form description editor textfield.
 *
 * @author arichard
 */
public class FormDescriptionEditorTextfieldComponent implements IComponent {

    private FormDescriptionEditorTextfieldComponentProps props;

    public FormDescriptionEditorTextfieldComponent(FormDescriptionEditorTextfieldComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        var optionalPreviousTextfield = this.props.getOptionalPreviousTextfield();

        String id = optionalPreviousTextfield.map(FormDescriptionEditorWidget::getId).orElseGet(() -> UUID.randomUUID().toString());
        String label = optionalPreviousTextfield.map(FormDescriptionEditorWidget::getLabel).orElseGet(() -> "Textfield"); //$NON-NLS-1$

        // @formatter:off
        FormDescriptionEditorTextfieldElementProps textfieldElementProps = FormDescriptionEditorTextfieldElementProps.newFormDescriptionEditorTextfieldElementProps(id)
                .label(label)
                .build();
        return new Element(FormDescriptionEditorTextfieldElementProps.TYPE, textfieldElementProps);
        // @formatter:on
    }

}

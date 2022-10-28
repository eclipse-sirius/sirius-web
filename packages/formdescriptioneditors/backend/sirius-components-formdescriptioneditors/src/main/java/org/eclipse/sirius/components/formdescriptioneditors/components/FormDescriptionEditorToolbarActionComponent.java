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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorToolbarActionDescription;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorToolbarActionElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * The component used to render the form description editor toolbar action.
 *
 * @author arichard
 */
public class FormDescriptionEditorToolbarActionComponent implements IComponent {

    private final FormDescriptionEditorToolbarActionComponentProps props;

    public FormDescriptionEditorToolbarActionComponent(FormDescriptionEditorToolbarActionComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        FormDescriptionEditorToolbarActionDescription formDescriptionEditorToolbarActionDescription = this.props.getFormDescriptionEditorToolbarActionDescription();

        String id = formDescriptionEditorToolbarActionDescription.getId();
        String label = formDescriptionEditorToolbarActionDescription.getLabel();
        String kind = formDescriptionEditorToolbarActionDescription.getKind();

        // @formatter:off
        FormDescriptionEditorToolbarActionElementProps toolbarActionPropsBuilder = FormDescriptionEditorToolbarActionElementProps.newFormDescriptionEditorToolbarActionElementProps(id)
                .label(label)
                .kind(kind)
                .build();
        // @formatter:on

        return new Element(FormDescriptionEditorToolbarActionElementProps.TYPE, toolbarActionPropsBuilder);
    }
}

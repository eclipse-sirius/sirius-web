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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.StyleProperty;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorWidgetElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * The component used to render the form description editor widget.
 *
 * @author arichard
 */
public class FormDescriptionEditorWidgetComponent implements IComponent {

    private final FormDescriptionEditorWidgetComponentProps props;

    public FormDescriptionEditorWidgetComponent(FormDescriptionEditorWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        FormDescriptionEditorWidgetDescription formDescriptionEditorWidgetDescription = this.props.getFormDescriptionEditorWidgetDescription();

        String id = formDescriptionEditorWidgetDescription.getId();
        String label = formDescriptionEditorWidgetDescription.getLabel();
        String kind = formDescriptionEditorWidgetDescription.getKind();
        List<StyleProperty> styleProperties = formDescriptionEditorWidgetDescription.getStyleProperties();

        // @formatter:off
        FormDescriptionEditorWidgetElementProps widgetPropsBuilder = FormDescriptionEditorWidgetElementProps.newFormDescriptionEditorWidgetElementProps(id)
                .label(label)
                .kind(kind)
                .styleProperties(styleProperties)
                .build();
        // @formatter:on

        return new Element(FormDescriptionEditorWidgetElementProps.TYPE, widgetPropsBuilder);
    }
}

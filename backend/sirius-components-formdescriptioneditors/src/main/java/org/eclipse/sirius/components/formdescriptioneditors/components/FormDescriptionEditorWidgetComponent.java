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

import org.eclipse.sirius.components.formdescriptioneditors.description.AbstractFormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorTextfieldDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The component used to render the widget.
 *
 * @author arichard
 */
public class FormDescriptionEditorWidgetComponent implements IComponent {

    private final Logger logger = LoggerFactory.getLogger(FormDescriptionEditorWidgetComponent.class);

    private final FormDescriptionEditorWidgetComponentProps props;

    public FormDescriptionEditorWidgetComponent(FormDescriptionEditorWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        AbstractFormDescriptionEditorWidgetDescription widgetDescription = this.props.getWidgetDescription();
        var optionalPreviousWidget = this.props.getOptionalPreviousWidget();

        Element element = null;
        if (widgetDescription instanceof FormDescriptionEditorTextfieldDescription) {
            FormDescriptionEditorTextfieldComponentProps textfieldProps = new FormDescriptionEditorTextfieldComponentProps(variableManager,
                    (FormDescriptionEditorTextfieldDescription) widgetDescription, optionalPreviousWidget);
            element = new Element(FormDescriptionEditorTextfieldComponent.class, textfieldProps);
        } else {
            String pattern = "Unsupported widget description: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, widgetDescription.getClass().getSimpleName());
        }
        return element;
    }

}

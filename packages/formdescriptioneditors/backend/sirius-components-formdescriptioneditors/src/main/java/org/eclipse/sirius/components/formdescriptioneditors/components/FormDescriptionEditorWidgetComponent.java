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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorForDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorIfDescription;
import org.eclipse.sirius.components.forms.components.WidgetComponent;
import org.eclipse.sirius.components.forms.components.WidgetComponentProps;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Custom component to render both "normal" widgets and FDE-specific ones (For, If).
 *
 * @author pcdavid
 */
public class FormDescriptionEditorWidgetComponent implements IComponent {

    private final FormDescriptionEditorWidgetComponentProps props;

    public FormDescriptionEditorWidgetComponent(FormDescriptionEditorWidgetComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        AbstractWidgetDescription widgetDescription = this.props.abstractWidgetDescription();

        Element element = null;
        if (widgetDescription instanceof FormDescriptionEditorForDescription formDescriptionEditorForDescription) {
            var formDescriptionEditorForComponentProps = new FormDescriptionEditorForComponentProps(variableManager, formDescriptionEditorForDescription, this.props.widgetDescriptors());
            element = new Element(FormDescriptionEditorForComponent.class, formDescriptionEditorForComponentProps);
        } else if (widgetDescription instanceof FormDescriptionEditorIfDescription formDescriptionEditorIfDescription) {
            var formDescriptionEditorIfComponentProps = new FormDescriptionEditorIfComponentProps(variableManager, formDescriptionEditorIfDescription, this.props.widgetDescriptors());
            element = new Element(FormDescriptionEditorIfComponent.class, formDescriptionEditorIfComponentProps);
        } else if (widgetDescription instanceof FlexboxContainerDescription flexboxContainerDescription) {
            var formDescriptionEditorFlexboxContainerComponentProps = new FormDescriptionEditorFlexboxContainerComponentProps(variableManager, flexboxContainerDescription, this.props.widgetDescriptors());
            element = new Element(FormDescriptionEditorFlexboxContainerComponent.class, formDescriptionEditorFlexboxContainerComponentProps);
        }

        if (element != null) {
            return element;
        } else {
            var widgetComponentProps = new WidgetComponentProps(this.props.variableManager(), this.props.abstractWidgetDescription(), this.props.widgetDescriptors());
            var widgetComponent = new WidgetComponent(widgetComponentProps);
            return widgetComponent.render();
        }
    }

}

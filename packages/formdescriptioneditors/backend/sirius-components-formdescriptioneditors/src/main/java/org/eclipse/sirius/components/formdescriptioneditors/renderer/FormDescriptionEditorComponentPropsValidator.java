/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.renderer;

import java.util.List;

import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorFlexboxContainerComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorFlexboxContainerComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorForComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorForComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorGroupComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorGroupComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorIfComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorIfComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorPageComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorPageComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorWidgetComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorWidgetComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormComponentPropsValidator;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponentPropsValidator implements IComponentPropsValidator {

    private final FormComponentPropsValidator formComponentPropsValidator;

    public FormDescriptionEditorComponentPropsValidator(List<IWidgetDescriptor> widgetDescriptors) {
        this.formComponentPropsValidator = new FormComponentPropsValidator(widgetDescriptors);
    }

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (FormDescriptionEditorComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorComponentProps;
        } else if (FormDescriptionEditorGroupComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorGroupComponentProps;
        } else if (FormDescriptionEditorPageComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorPageComponentProps;
        } else if (FormDescriptionEditorFlexboxContainerComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorFlexboxContainerComponentProps;
        } else if (FormDescriptionEditorForComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorForComponentProps;
        } else if (FormDescriptionEditorIfComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorIfComponentProps;
        } else if (FormDescriptionEditorWidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorWidgetComponentProps;
        } else {
            checkValidProps = this.formComponentPropsValidator.validateComponentProps(componentType, props);
        }
        return checkValidProps;
    }
}

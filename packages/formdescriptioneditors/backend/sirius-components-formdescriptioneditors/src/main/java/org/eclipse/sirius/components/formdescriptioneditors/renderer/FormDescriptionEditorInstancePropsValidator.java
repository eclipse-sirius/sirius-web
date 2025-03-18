/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorForElementProps;
import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorIfElementProps;
import org.eclipse.sirius.components.forms.renderer.FormInstancePropsValidator;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;

/**
 * Used to validate the instance props.
 *
 * @author arichard
 */
public class FormDescriptionEditorInstancePropsValidator implements IInstancePropsValidator {

    private final FormInstancePropsValidator formInstancePropsValidator;

    public FormDescriptionEditorInstancePropsValidator(List<IWidgetDescriptor> widgetDescriptors, List<ICustomCellDescriptor> customCellDescriptors) {
        this.formInstancePropsValidator = new FormInstancePropsValidator(widgetDescriptors, customCellDescriptors);
    }

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (FormDescriptionEditorElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FormDescriptionEditorElementProps;
        } else if (FormDescriptionEditorForElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FormDescriptionEditorForElementProps;
        } else if (FormDescriptionEditorIfElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FormDescriptionEditorIfElementProps;
        } else {
            checkValidProps = this.formInstancePropsValidator.validateInstanceProps(type, props);
        }
        return checkValidProps;
    }

}

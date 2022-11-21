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
package org.eclipse.sirius.components.formdescriptioneditors.renderer;

import org.eclipse.sirius.components.formdescriptioneditors.elements.FormDescriptionEditorElementProps;
import org.eclipse.sirius.components.forms.renderer.FormInstancePropsValidator;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the instance props.
 *
 * @author arichard
 */
public class FormDescriptionEditorInstancePropsValidator implements IInstancePropsValidator {

    private final FormInstancePropsValidator formInstancePropsValidator;

    public FormDescriptionEditorInstancePropsValidator() {
        this.formInstancePropsValidator = new FormInstancePropsValidator();
    }

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (FormDescriptionEditorElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof FormDescriptionEditorElementProps;
        } else {
            checkValidProps = this.formInstancePropsValidator.validateInstanceProps(type, props);
        }
        return checkValidProps;
    }

}

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

import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorTextfieldComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorTextfieldComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorWidgetComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorWidgetComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component.
 *
 * @author arichard
 */
public class FormDescriptionEditorComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (FormDescriptionEditorComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorComponentProps;
        } else if (FormDescriptionEditorWidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorWidgetComponentProps;
        } else if (FormDescriptionEditorTextfieldComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormDescriptionEditorTextfieldComponentProps;
        }
        return checkValidProps;
    }
}

/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.forms.renderer;

import org.eclipse.sirius.components.forms.components.CheckboxComponent;
import org.eclipse.sirius.components.forms.components.CheckboxComponentProps;
import org.eclipse.sirius.components.forms.components.ForComponent;
import org.eclipse.sirius.components.forms.components.ForComponentProps;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.components.GroupComponent;
import org.eclipse.sirius.components.forms.components.GroupComponentProps;
import org.eclipse.sirius.components.forms.components.IfComponent;
import org.eclipse.sirius.components.forms.components.IfComponentProps;
import org.eclipse.sirius.components.forms.components.LinkComponent;
import org.eclipse.sirius.components.forms.components.LinkComponentProps;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.components.ListComponentProps;
import org.eclipse.sirius.components.forms.components.MultiSelectComponent;
import org.eclipse.sirius.components.forms.components.MultiSelectComponentProps;
import org.eclipse.sirius.components.forms.components.PageComponent;
import org.eclipse.sirius.components.forms.components.PageComponentProps;
import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.forms.components.RadioComponentProps;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.components.SelectComponentProps;
import org.eclipse.sirius.components.forms.components.TextareaComponent;
import org.eclipse.sirius.components.forms.components.TextareaComponentProps;
import org.eclipse.sirius.components.forms.components.TextfieldComponent;
import org.eclipse.sirius.components.forms.components.TextfieldComponentProps;
import org.eclipse.sirius.components.forms.components.WidgetComponent;
import org.eclipse.sirius.components.forms.components.WidgetComponentProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component.
 *
 * @author sbegaudeau
 */
public class FormComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (FormComponent.class.equals(componentType)) {
            checkValidProps = props instanceof FormComponentProps;
        } else if (PageComponent.class.equals(componentType)) {
            checkValidProps = props instanceof PageComponentProps;
        } else if (GroupComponent.class.equals(componentType)) {
            checkValidProps = props instanceof GroupComponentProps;
        } else if (ForComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ForComponentProps;
        } else if (IfComponent.class.equals(componentType)) {
            checkValidProps = props instanceof IfComponentProps;
        } else if (WidgetComponent.class.equals(componentType)) {
            checkValidProps = props instanceof WidgetComponentProps;
        } else if (CheckboxComponent.class.equals(componentType)) {
            checkValidProps = props instanceof CheckboxComponentProps;
        } else if (ListComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ListComponentProps;
        } else if (RadioComponent.class.equals(componentType)) {
            checkValidProps = props instanceof RadioComponentProps;
        } else if (SelectComponent.class.equals(componentType)) {
            checkValidProps = props instanceof SelectComponentProps;
        } else if (MultiSelectComponent.class.equals(componentType)) {
            checkValidProps = props instanceof MultiSelectComponentProps;
        } else if (TextareaComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextareaComponentProps;
        } else if (TextfieldComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextfieldComponentProps;
        } else if (DiagnosticComponent.class.equals(componentType)) {
            checkValidProps = props instanceof DiagnosticComponentProps;
        } else if (LinkComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LinkComponentProps;
        }

        return checkValidProps;
    }

}

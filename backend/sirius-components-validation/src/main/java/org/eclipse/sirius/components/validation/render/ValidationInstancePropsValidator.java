/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.validation.render;

import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.validation.elements.DiagnosticElementProps;
import org.eclipse.sirius.components.validation.elements.ValidationElementProps;

/**
 * Used to validate the instance props.
 *
 * @author gcoutable
 */
public class ValidationInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (ValidationElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ValidationElementProps;
        } else if (DiagnosticElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof DiagnosticElementProps;
        }

        return checkValidProps;
    }

}

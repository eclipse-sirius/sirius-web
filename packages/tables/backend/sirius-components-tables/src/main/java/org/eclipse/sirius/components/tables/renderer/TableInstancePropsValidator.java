/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables.renderer;

import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;
import org.eclipse.sirius.components.tables.elements.LineElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;

import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of the instance type.
 *
 * @author arichard
 */
public class TableInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (TableElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TableElementProps;
        } else if (LineElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof LineElementProps;
        } else if (ColumnElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof ColumnElementProps;
        } else if (TextfieldCellElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof TextfieldCellElementProps;
        } else if (CheckboxCellElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof CheckboxCellElementProps;
        } else if (SelectCellElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof SelectCellElementProps;
        } else if (MultiSelectCellElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof MultiSelectCellElementProps;
        }

        return checkValidProps;
    }

}

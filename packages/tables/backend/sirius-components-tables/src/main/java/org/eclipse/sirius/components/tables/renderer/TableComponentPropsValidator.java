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

import org.eclipse.sirius.components.tables.components.CheckboxCellComponent;
import org.eclipse.sirius.components.tables.components.CheckboxCellComponentProps;
import org.eclipse.sirius.components.tables.components.ColumnComponent;
import org.eclipse.sirius.components.tables.components.ColumnComponentProps;
import org.eclipse.sirius.components.tables.components.LineComponent;
import org.eclipse.sirius.components.tables.components.LineComponentProps;
import org.eclipse.sirius.components.tables.components.MultiSelectCellComponent;
import org.eclipse.sirius.components.tables.components.MultiSelectCellComponentProps;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;
import org.eclipse.sirius.components.tables.components.SelectCellComponentProps;
import org.eclipse.sirius.components.tables.components.TableComponent;
import org.eclipse.sirius.components.tables.components.TableComponentProps;
import org.eclipse.sirius.components.tables.components.TextfieldCellComponent;
import org.eclipse.sirius.components.tables.components.TextfieldCellComponentProps;

import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component type.
 *
 * @author arichard
 */
public class TableComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;

        if (TableComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TableComponentProps;
        } else if (LineComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LineComponentProps;
        } else if (ColumnComponent.class.equals(componentType)) {
            checkValidProps = props instanceof ColumnComponentProps;
        } else if (TextfieldCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextfieldCellComponentProps;
        } else if (CheckboxCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof CheckboxCellComponentProps;
        } else if (SelectCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof SelectCellComponentProps;
        } else if (MultiSelectCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof MultiSelectCellComponentProps;
        }

        return checkValidProps;
    }

}

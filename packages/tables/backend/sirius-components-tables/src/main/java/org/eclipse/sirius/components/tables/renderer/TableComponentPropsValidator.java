/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.components.ColumnComponent;
import org.eclipse.sirius.components.tables.components.ColumnComponentProps;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;
import org.eclipse.sirius.components.tables.components.IconLabelCellComponent;
import org.eclipse.sirius.components.tables.components.IconLabelCellComponentProps;
import org.eclipse.sirius.components.tables.components.LineComponent;
import org.eclipse.sirius.components.tables.components.LineComponentProps;
import org.eclipse.sirius.components.tables.components.MultiSelectCellComponent;
import org.eclipse.sirius.components.tables.components.MultiSelectCellComponentProps;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;
import org.eclipse.sirius.components.tables.components.SelectCellComponentProps;
import org.eclipse.sirius.components.tables.components.TableComponent;
import org.eclipse.sirius.components.tables.components.TableComponentProps;
import org.eclipse.sirius.components.tables.components.TextareaCellComponent;
import org.eclipse.sirius.components.tables.components.TextareaCellComponentProps;
import org.eclipse.sirius.components.tables.components.TextfieldCellComponent;
import org.eclipse.sirius.components.tables.components.TextfieldCellComponentProps;

/**
 * Used to validate the properties of a component type.
 *
 * @author arichard
 */
public class TableComponentPropsValidator implements IComponentPropsValidator {

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public TableComponentPropsValidator(List<ICustomCellDescriptor> customCellDescriptors) {
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

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
        } else if (TextareaCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof TextareaCellComponentProps;
        } else if (SelectCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof SelectCellComponentProps;
        } else if (MultiSelectCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof MultiSelectCellComponentProps;
        } else if (IconLabelCellComponent.class.equals(componentType)) {
            checkValidProps = props instanceof IconLabelCellComponentProps;
        } else {
            checkValidProps = this.customCellDescriptors.stream()
                    .map(customCellDescriptor -> customCellDescriptor.validateComponentProps(componentType, props))
                    .filter(Optional::isPresent)
                    .findFirst()
                    .map(Optional::get)
                    .orElse(false);
        }

        return checkValidProps;
    }

}

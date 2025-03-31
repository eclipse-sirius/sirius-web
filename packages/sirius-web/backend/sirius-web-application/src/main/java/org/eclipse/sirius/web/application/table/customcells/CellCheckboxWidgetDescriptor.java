/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.web.application.table.customcells;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the cell checkbox widget.
 *
 * @author Jerome Gout
 */
@Component
public class CellCheckboxWidgetDescriptor implements ICustomCellDescriptor {

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        if (CheckboxCellComponent.class.equals(componentType)) {
            return Optional.of(props instanceof CheckboxCellComponentProps);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        if (CheckboxCellElementProps.TYPE.equals(type)) {
            return Optional.of(props instanceof CheckboxCellElementProps);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> instantiate(String type, IProps elementProps, List<Object> children) {
        if (Objects.equals(type, CheckboxCellElementProps.TYPE) && elementProps instanceof CheckboxCellElementProps checkboxCellElementProps) {
            var cell = CheckboxCell.newCheckboxCell(checkboxCellElementProps.id())
                    .descriptionId(checkboxCellElementProps.descriptionId())
                    .columnId(checkboxCellElementProps.columnId())
                    .targetObjectId(checkboxCellElementProps.targetObjectId())
                    .targetObjectKind(checkboxCellElementProps.targetObjectKind())
                    .value(checkboxCellElementProps.value())
                    .build();
            return Optional.of(cell);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, ICellDescription cellDescription, UUID cellId, UUID columnId, Object columnTargetObject) {
        if (cellDescription instanceof CheckboxCellDescription checkboxCellDescription) {
            var cellComponentProps = new CheckboxCellComponentProps(variableManager, checkboxCellDescription, cellId, columnId, columnTargetObject);
            return Optional.of(new Element(CheckboxCellComponent.class, cellComponentProps));
        }
        return Optional.empty();
    }
}

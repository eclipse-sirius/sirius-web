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

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the cell checkbox widget in a table widget form.
 *
 * @author frouene
 */
@Component
public class CellCheckboxFormWidgetDescriptor implements IWidgetDescriptor {

    private static final String TYPE = "CellCheckboxFormWidget";

    @Override
    public List<String> getWidgetTypes() {
        return List.of(TYPE);
    }

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
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
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
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        return Optional.empty();
    }

}

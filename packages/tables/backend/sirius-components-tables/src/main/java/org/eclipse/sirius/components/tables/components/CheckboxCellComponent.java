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
package org.eclipse.sirius.components.tables.components;

import java.util.Objects;
import java.util.function.BiFunction;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;

/**
 * The component used to render Checkbox-based cells.
 *
 * @author arichard
 */
public class CheckboxCellComponent implements IComponent {

    private final CheckboxCellComponentProps props;

    public CheckboxCellComponent(CheckboxCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        BiFunction<VariableManager, String, Object> cellValueProvider = this.props.getCellValueProvider();
        String featureName = this.props.getFeatureName();
        Object value = cellValueProvider.apply(variableManager, featureName);
        String stringValue = ""; //$NON-NLS-1$
        if (value instanceof String) {
            stringValue = (String) value;
        }
        boolean booleanValue = Boolean.parseBoolean(stringValue);
        CheckboxCellElementProps cellElementProps = CheckboxCellElementProps.newCheckboxCellElementProps(this.props.getCellId())
                .parentLineId(this.props.getParentLineId())
                .columnId(this.props.getColumnId())
                .value(booleanValue)
                .build();
        return new Element(CheckboxCellElementProps.TYPE, cellElementProps);
    }
}

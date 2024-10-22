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

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.CheckboxCellDescription;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;

/**
 * The component used to render Checkbox-based cells.
 *
 * @author lfasani
 */
public class CheckboxCellComponent implements IComponent {

    private final CheckboxCellComponentProps props;

    public CheckboxCellComponent(CheckboxCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        CheckboxCellDescription cellDescription = this.props.checkboxCellDescription();

        String targetObjectId = cellDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = cellDescription.getTargetObjectKindProvider().apply(variableManager);

        Boolean value = cellDescription.getCellValueProvider().apply(variableManager, this.props.columnTargetObject());
        CheckboxCellElementProps cellElementProps = new CheckboxCellElementProps(this.props.cellId(), targetObjectId, targetObjectKind, this.props.columnId(), value);
        return new Element(CheckboxCellElementProps.TYPE, cellElementProps);
    }
}

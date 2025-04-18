/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.IconLabelCellDescription;
import org.eclipse.sirius.components.tables.elements.IconLabelCellElementProps;

/**
 * The component used to render IconLabel-based cells.
 *
 * @author frouene
 */
public class IconLabelCellComponent implements IComponent {

    private final IconLabelCellComponentProps props;

    public IconLabelCellComponent(IconLabelCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        IconLabelCellDescription cellDescription = this.props.iconLabelCellDescription();

        String targetObjectId = cellDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = cellDescription.getTargetObjectKindProvider().apply(variableManager);

        String value = cellDescription.getCellValueProvider().apply(variableManager, this.props.columnTargetObject());
        List<String> iconURLs = cellDescription.getCellIconURLsProvider().apply(variableManager, this.props.columnTargetObject());

        String tooltipValue = cellDescription.getCellTooltipValueProvider().apply(variableManager, this.props.columnTargetObject());

        IconLabelCellElementProps cellElementProps = new IconLabelCellElementProps(this.props.cellId(), this.props.iconLabelCellDescription().getId(), targetObjectId, targetObjectKind,
                this.props.columnId(), value, iconURLs, tooltipValue);
        return new Element(IconLabelCellElementProps.TYPE, cellElementProps);
    }
}

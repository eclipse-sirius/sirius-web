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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.AbstractCell;
import org.eclipse.sirius.components.tables.CheckboxCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.TextfieldCell;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;
import org.eclipse.sirius.components.tables.elements.LineElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;

/**
 * Used to instantiate the elements of the table representation.
 *
 * @author arichard
 */
public class TableElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (TableElementProps.TYPE.equals(type) && props instanceof TableElementProps) {
            object = this.instantiateTable((TableElementProps) props, children);
        } else if (LineElementProps.TYPE.equals(type) && props instanceof LineElementProps) {
            object = this.instantiateLine((LineElementProps) props, children);
        } else if (ColumnElementProps.TYPE.equals(type) && props instanceof ColumnElementProps) {
            object = this.instantiateColumn((ColumnElementProps) props);
        } else if (TextfieldCellElementProps.TYPE.equals(type) && props instanceof TextfieldCellElementProps) {
            object = this.instantiateCell((TextfieldCellElementProps) props);
        } else if (CheckboxCellElementProps.TYPE.equals(type) && props instanceof CheckboxCellElementProps) {
            object = this.instantiateCell((CheckboxCellElementProps) props);
        } else if (SelectCellElementProps.TYPE.equals(type) && props instanceof SelectCellElementProps) {
            object = this.instantiateCell((SelectCellElementProps) props);
        } else if (MultiSelectCellElementProps.TYPE.equals(type) && props instanceof MultiSelectCellElementProps) {
            object = this.instantiateCell((MultiSelectCellElementProps) props);
        }
        return object;
    }

    private Table instantiateTable(TableElementProps props, List<Object> children) {
        List<Line> lines = children.stream()
                .filter(Line.class::isInstance)
                .map(Line.class::cast)
                .collect(Collectors.toList());

        List<Column> columns = children.stream()
                .filter(Column.class::isInstance)
                .map(Column.class::cast)
                .collect(Collectors.toList());

        return Table.newTable(props.id())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .descriptionId(props.descriptionId())
                .label(props.label())
                .lines(lines)
                .columns(columns)
                .build();
    }

    private Line instantiateLine(LineElementProps props, List<Object> children) {
        List<AbstractCell> cells = children.stream()
                .filter(AbstractCell.class::isInstance)
                .map(AbstractCell.class::cast)
                .collect(Collectors.toList());

        return Line.newLine(props.id())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .descriptionId(props.descriptionId())
                .cells(cells)
                .build();
    }

    private Column instantiateColumn(ColumnElementProps props) {
        return Column.newColumn(props.id())
                .descriptionId(props.descriptionId())
                .label(props.label())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .build();
    }

    private TextfieldCell instantiateCell(TextfieldCellElementProps props) {
        return TextfieldCell.newCell(props.id())
                .columnId(props.columnId())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .value(props.value())
                .build();
    }

    private CheckboxCell instantiateCell(CheckboxCellElementProps props) {
        return CheckboxCell.newCell(props.id())
                .columnId(props.columnId())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .value(props.value())
                .build();
    }

    private SelectCell instantiateCell(SelectCellElementProps props) {
        return SelectCell.newCell(props.id())
                .columnId(props.columnId())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .options(props.options())
                .value(props.value())
                .build();
    }

    private MultiSelectCell instantiateCell(MultiSelectCellElementProps props) {
        return MultiSelectCell.newCell(props.id())
                .columnId(props.columnId())
                .targetObjectId(props.targetObjectId())
                .targetObjectKind(props.targetObjectKind())
                .options(props.options())
                .values(props.values())
                .build();
    }
}

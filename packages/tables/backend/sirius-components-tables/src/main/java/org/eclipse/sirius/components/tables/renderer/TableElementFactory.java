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
import org.eclipse.sirius.components.tables.CheckboxCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
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
    @SuppressWarnings("checkstyle:JavaNCSS")
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        return switch (type) {
            case TableElementProps.TYPE -> this.instantiateTable(props, children);
            case LineElementProps.TYPE -> this.instantiateLine(props, children);
            case ColumnElementProps.TYPE -> this.instantiateColumn(props);
            case TextfieldCellElementProps.TYPE, CheckboxCellElementProps.TYPE, SelectCellElementProps.TYPE, MultiSelectCellElementProps.TYPE -> this.instantiateCell(props);
            default -> null;
        };
    }

    private Table instantiateTable(IProps props, List<Object> children) {
        if (props instanceof TableElementProps tableElementProps) {
            List<Line> lines = children.stream()
                    .filter(Line.class::isInstance)
                    .map(Line.class::cast)
                    .collect(Collectors.toList());

            List<Column> columns = children.stream()
                    .filter(Column.class::isInstance)
                    .map(Column.class::cast)
                    .collect(Collectors.toList());

            return Table.newTable(tableElementProps.id())
                    .targetObjectId(tableElementProps.targetObjectId())
                    .targetObjectKind(tableElementProps.targetObjectKind())
                    .descriptionId(tableElementProps.descriptionId())
                    .lines(lines)
                    .columns(columns)
                    .build();
        }
        return null;
    }

    private Line instantiateLine(IProps props, List<Object> children) {
        if (props instanceof LineElementProps lineElementProps) {
            List<ICell> cells = children.stream()
                    .filter(TextfieldCell.class::isInstance)
                    .map(TextfieldCell.class::cast)
                    .collect(Collectors.toList());

            cells.addAll(children.stream()
                    .filter(CheckboxCell.class::isInstance)
                    .map(CheckboxCell.class::cast)
                    .toList());

            cells.addAll(children.stream()
                    .filter(SelectCell.class::isInstance)
                    .map(SelectCell.class::cast)
                    .toList());

            cells.addAll(children.stream()
                    .filter(MultiSelectCell.class::isInstance)
                    .map(MultiSelectCell.class::cast)
                    .toList());

            return Line.newLine(lineElementProps.id())
                    .targetObjectId(lineElementProps.targetObjectId())
                    .targetObjectKind(lineElementProps.targetObjectKind())
                    .descriptionId(lineElementProps.descriptionId())
                    .headerLabel(lineElementProps.headerLabel())
                    .headerIconURLs(lineElementProps.headerIconURLs())
                    .headerIndexLabel(lineElementProps.headerIndexLabel())
                    .cells(cells)
                    .build();
        }
        return null;
    }

    private Column instantiateColumn(IProps props) {
        if (props instanceof ColumnElementProps columnElementProps) {

            return Column.newColumn(columnElementProps.id())
                    .descriptionId(columnElementProps.descriptionId())
                    .label(columnElementProps.label())
                    .iconURLs(columnElementProps.iconURLs())
                    .targetObjectId(columnElementProps.targetObjectId())
                    .targetObjectKind(columnElementProps.targetObjectKind())
                    .build();
        }
        return null;
    }

    private ICell instantiateCell(IProps props) {
        ICell cell = null;
        if (props instanceof TextfieldCellElementProps textfieldCellElementProps) {
            cell = TextfieldCell.newTextfieldCell(textfieldCellElementProps.id())
                    .columnId(textfieldCellElementProps.columnId())
                    .targetObjectId(textfieldCellElementProps.targetObjectId())
                    .targetObjectKind(textfieldCellElementProps.targetObjectKind())
                    .value(textfieldCellElementProps.value())
                    .build();
        } else if (props instanceof CheckboxCellElementProps checkboxCellElementProps) {
            cell = CheckboxCell.newCheckboxCell(checkboxCellElementProps.id())
                    .columnId(checkboxCellElementProps.columnId())
                    .targetObjectId(checkboxCellElementProps.targetObjectId())
                    .targetObjectKind(checkboxCellElementProps.targetObjectKind())
                    .value(checkboxCellElementProps.value())
                    .build();
        } else if (props instanceof SelectCellElementProps selectCellElementProps) {
            cell = SelectCell.newSelectCell(selectCellElementProps.id())
                    .columnId(selectCellElementProps.columnId())
                    .targetObjectId(selectCellElementProps.targetObjectId())
                    .targetObjectKind(selectCellElementProps.targetObjectKind())
                    .options(selectCellElementProps.options())
                    .value(selectCellElementProps.value())
                    .build();
        } else if (props instanceof MultiSelectCellElementProps multiSelectCellElementProps) {
            cell = MultiSelectCell.newMultiSelectCell(multiSelectCellElementProps.id())
                    .columnId(multiSelectCellElementProps.columnId())
                    .targetObjectId(multiSelectCellElementProps.targetObjectId())
                    .targetObjectKind(multiSelectCellElementProps.targetObjectKind())
                    .options(multiSelectCellElementProps.options())
                    .values(multiSelectCellElementProps.values())
                    .build();
        }
        return cell;

    }

}

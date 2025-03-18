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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.CheckboxCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.IconLabelCell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.TextareaCell;
import org.eclipse.sirius.components.tables.TextfieldCell;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;
import org.eclipse.sirius.components.tables.elements.IconLabelCellElementProps;
import org.eclipse.sirius.components.tables.elements.LineElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.elements.TextareaCellElementProps;
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
            case TextfieldCellElementProps.TYPE, TextareaCellElementProps.TYPE, CheckboxCellElementProps.TYPE, SelectCellElementProps.TYPE, MultiSelectCellElementProps.TYPE,
                 IconLabelCellElementProps.TYPE -> this.instantiateCell(props);
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
                    .sorted(Comparator.comparing(Column::getIndex))
                    .collect(Collectors.toList());

            return Table.newTable(tableElementProps.id())
                    .targetObjectId(tableElementProps.targetObjectId())
                    .targetObjectKind(tableElementProps.targetObjectKind())
                    .descriptionId(tableElementProps.descriptionId())
                    .stripeRow(tableElementProps.stripeRow())
                    .lines(lines)
                    .columns(columns)
                    .paginationData(tableElementProps.paginationData())
                    .globalFilter(tableElementProps.globalFilter())
                    .columnFilters(tableElementProps.columnFilters())
                    .enableSubRows(tableElementProps.enableSubRows())
                    .columnSort(tableElementProps.columnSort())
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
                    .filter(TextareaCell.class::isInstance)
                    .map(TextareaCell.class::cast)
                    .toList());

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

            cells.addAll(children.stream()
                    .filter(IconLabelCell.class::isInstance)
                    .map(IconLabelCell.class::cast)
                    .toList());

            return Line.newLine(lineElementProps.id())
                    .targetObjectId(lineElementProps.targetObjectId())
                    .targetObjectKind(lineElementProps.targetObjectKind())
                    .descriptionId(lineElementProps.descriptionId())
                    .headerLabel(lineElementProps.headerLabel())
                    .headerIconURLs(lineElementProps.headerIconURLs())
                    .headerIndexLabel(lineElementProps.headerIndexLabel())
                    .cells(cells)
                    .resizable(lineElementProps.resizable())
                    .height(lineElementProps.height())
                    .depthLevel(lineElementProps.depthLevel())
                    .build();
        }
        return null;
    }

    private Column instantiateColumn(IProps props) {
        if (props instanceof ColumnElementProps columnElementProps) {

            return Column.newColumn(columnElementProps.id())
                    .descriptionId(columnElementProps.descriptionId())
                    .headerLabel(columnElementProps.headerLabel())
                    .headerIconURLs(columnElementProps.headerIconURLs())
                    .headerIndexLabel(columnElementProps.headerIndexLabel())
                    .targetObjectId(columnElementProps.targetObjectId())
                    .targetObjectKind(columnElementProps.targetObjectKind())
                    .width(columnElementProps.width())
                    .resizable(columnElementProps.resizable())
                    .hidden(columnElementProps.hidden())
                    .filterVariant(columnElementProps.filterVariant())
                    .sortable(columnElementProps.sortable())
                    .index(columnElementProps.index())
                    .build();
        }
        return null;
    }

    private ICell instantiateCell(IProps props) {
        ICell cell = null;
        if (props instanceof TextfieldCellElementProps textfieldCellElementProps) {
            cell = TextfieldCell.newTextfieldCell(textfieldCellElementProps.id())
                    .descriptionId(textfieldCellElementProps.descriptionId())
                    .columnId(textfieldCellElementProps.columnId())
                    .targetObjectId(textfieldCellElementProps.targetObjectId())
                    .targetObjectKind(textfieldCellElementProps.targetObjectKind())
                    .value(textfieldCellElementProps.value())
                    .build();
        } else if (props instanceof TextareaCellElementProps textareaCellElementProps) {
            cell = TextareaCell.newTextareaCell(textareaCellElementProps.id())
                    .descriptionId(textareaCellElementProps.descriptionId())
                    .columnId(textareaCellElementProps.columnId())
                    .targetObjectId(textareaCellElementProps.targetObjectId())
                    .targetObjectKind(textareaCellElementProps.targetObjectKind())
                    .value(textareaCellElementProps.value())
                    .build();
        } else if (props instanceof CheckboxCellElementProps checkboxCellElementProps) {
            cell = CheckboxCell.newCheckboxCell(checkboxCellElementProps.id())
                    .descriptionId(checkboxCellElementProps.descriptionId())
                    .columnId(checkboxCellElementProps.columnId())
                    .targetObjectId(checkboxCellElementProps.targetObjectId())
                    .targetObjectKind(checkboxCellElementProps.targetObjectKind())
                    .value(checkboxCellElementProps.value())
                    .build();
        } else if (props instanceof SelectCellElementProps selectCellElementProps) {
            cell = SelectCell.newSelectCell(selectCellElementProps.id())
                    .descriptionId(selectCellElementProps.descriptionId())
                    .columnId(selectCellElementProps.columnId())
                    .targetObjectId(selectCellElementProps.targetObjectId())
                    .targetObjectKind(selectCellElementProps.targetObjectKind())
                    .options(selectCellElementProps.options())
                    .value(selectCellElementProps.value())
                    .build();
        } else if (props instanceof MultiSelectCellElementProps multiSelectCellElementProps) {
            cell = MultiSelectCell.newMultiSelectCell(multiSelectCellElementProps.id())
                    .descriptionId(multiSelectCellElementProps.descriptionId())
                    .columnId(multiSelectCellElementProps.columnId())
                    .targetObjectId(multiSelectCellElementProps.targetObjectId())
                    .targetObjectKind(multiSelectCellElementProps.targetObjectKind())
                    .options(multiSelectCellElementProps.options())
                    .values(multiSelectCellElementProps.values())
                    .build();
        } else if (props instanceof IconLabelCellElementProps iconLabelCellElementProps) {
            cell = IconLabelCell.newIconLabelCell(iconLabelCellElementProps.id())
                    .descriptionId(iconLabelCellElementProps.descriptionId())
                    .columnId(iconLabelCellElementProps.columnId())
                    .targetObjectId(iconLabelCellElementProps.targetObjectId())
                    .targetObjectKind(iconLabelCellElementProps.targetObjectKind())
                    .value(iconLabelCellElementProps.value())
                    .iconURLs(iconLabelCellElementProps.iconURLs())
                    .build();
        }
        return cell;

    }

}

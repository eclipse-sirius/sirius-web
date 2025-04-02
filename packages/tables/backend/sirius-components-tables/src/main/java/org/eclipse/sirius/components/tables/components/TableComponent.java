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
package org.eclipse.sirius.components.tables.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.PaginationData;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The component used to render the table representation.
 *
 * @author arichard
 */
public class TableComponent implements IComponent {

    private final TableComponentProps props;

    public TableComponent(TableComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        TableDescription tableDescription = this.props.tableDescription();
        Optional<Table> optionalPreviousTable = this.props.previousTable();

        String id = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = tableDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = tableDescription.getTargetObjectKindProvider().apply(variableManager);
        boolean stripeRow = tableDescription.getIsStripeRowPredicate().test(variableManager);
        List<Integer> pageSizeOptions = tableDescription.getPageSizeOptionsProvider().apply(variableManager);
        int pageSizeIndex = tableDescription.getDefaultPageSizeIndexProvider().apply(variableManager);
        int pageSize = 0;
        if (pageSizeIndex < pageSizeOptions.size()) {
            pageSize = pageSizeOptions.get(pageSizeIndex);
        } else if (!pageSizeOptions.isEmpty()) {
            pageSize = pageSizeOptions.get(0);
        }

        TableRenderingCache cache = new TableRenderingCache();
        ITableElementRequestor tableElementRequestor = new TableElementRequestor();

        List<ColumnFilter> columnsFilters = this.props.columnFilters();
        List<ColumnSort> columnSort = this.props.columnSort();

        var childrenColumns = tableDescription.getColumnDescriptions().stream()
                .map(columnDescription -> {
                    var previousColumns = optionalPreviousTable.map(previousTable -> tableElementRequestor.getColumns(previousTable, columnDescription)).orElse(List.of());
                    var columnComponentProps = new ColumnComponentProps(variableManager, columnDescription, previousColumns, cache, this.props.tableEvents());
                    return new Element(ColumnComponent.class, columnComponentProps);
                })
                .toList();
        var columnFiltersMapped = optionalPreviousTable
                .map(previousTable -> columnsFilters.stream()
                        .map(columnFilter ->
                                tableElementRequestor.getColumn(previousTable, columnFilter.id())
                                        .map(column -> new ColumnFilter(column.getTargetObjectId(), columnFilter.value()))
                        )
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
                )
                .orElse(List.of());

        var columnSortMapped = optionalPreviousTable
                .map(previousTable -> columnSort.stream()
                        .map(sort ->
                                tableElementRequestor.getColumn(previousTable, sort.id())
                                        .map(column -> new ColumnSort(column.getTargetObjectId(), sort.desc()))
                        )
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
                )
                .orElse(List.of());

        variableManager.put(TableRenderer.COLUMN_FILTERS, columnFiltersMapped);
        variableManager.put(TableRenderer.COLUMN_SORT, columnSortMapped);

        PaginatedData paginatedData = tableDescription.getLineDescription().getSemanticElementsProvider().apply(variableManager);

        var previousLines = optionalPreviousTable.map(previousTable -> tableElementRequestor.getRootLines(previousTable, tableDescription.getLineDescription())).orElse(List.of());
        ILinesRequestor linesRequestor = new LinesRequestor(previousLines);
        var lineComponentProps = new LineComponentProps(variableManager, tableDescription.getLineDescription(), tableDescription.getCellDescriptions(), linesRequestor, cache, id, paginatedData.rows(), this.props.tableEvents());
        var childrenLine = new Element(LineComponent.class, lineComponentProps);

        List<Element> children = new ArrayList<>(childrenColumns);
        children.add(childrenLine);

        TableElementProps tableElementProps = TableElementProps.newTableElementProps(id)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .descriptionId(tableDescription.getId())
                .stripeRow(stripeRow)
                .children(children)
                .paginationData(new PaginationData(paginatedData.hasPreviousPage(), paginatedData.hasNextPage(), paginatedData.totalRowCount()))
                .globalFilter(this.props.globalFilter())
                .columnFilters(columnsFilters)
                .enableSubRows(tableDescription.isEnableSubRows())
                .columnSort(columnSort)
                .pageSizeOptions(pageSizeOptions)
                .defaultPageSize(pageSize)
                .build();

        return new Element(TableElementProps.TYPE, tableElementProps);
    }

}

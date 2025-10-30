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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the table event processors.
 *
 * @author frouene
 */
@Service
public class TableEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private static final String URL_PARAM = "?";
    private static final String CURSOR = "cursor";
    private static final String DIRECTION = "direction";
    private static final String SIZE = "size";
    private static final String GLOBAL_FILTER = "globalFilter";
    private static final String COLUMN_FILTERS = "columnFilters";
    private static final String EXPANDED_IDS = "expandedIds";
    private static final String ACTIVE_ROW_FILTER_IDS = "activeRowFilterIds";
    private static final String COLUMN_SORT = "columnSort";
    private static final String EXPAND_ALL = "expandAll";

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectSearchService objectSearchService;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public TableEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IRepresentationPersistenceService representationPersistenceService,
            IObjectSearchService objectSearchService, List<ITableEventHandler> tableEventHandlers, IURLParser urlParser, List<ICustomCellDescriptor> customCellDescriptors) {
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.tableEventHandlers = Objects.requireNonNull(tableEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.urlParser = Objects.requireNonNull(urlParser);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(this.getTableIdFromRepresentationId(representationId), List.of(Table.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Optional<Table> optionalTable = this.representationSearchService.findById(editingContext, this.getTableIdFromRepresentationId(representationId), Table.class);
        if (optionalTable.isPresent()) {
            Table table = optionalTable.get();
            Optional<TableDescription> optionalTableDescription = this.representationDescriptionSearchService.findById(editingContext, table.getDescriptionId())
                    .filter(TableDescription.class::isInstance)
                    .map(TableDescription.class::cast);
            Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, table.getTargetObjectId());
            if (optionalTableDescription.isPresent() && optionalObject.isPresent()) {
                TableDescription tableDescription = optionalTableDescription.get();
                Object object = optionalObject.get();

                var tableCreationParameters = TableCreationParameters.newTableCreationParameters(this.getTableIdFromRepresentationId(representationId))
                        .tableDescription(tableDescription)
                        .editingContext(editingContext)
                        .targetObject(object)
                        .cursorBasedPaginationData(this.getCursorBasedPaginationData(editingContext, representationId))
                        .targetObject(object)
                        .globalFilter(this.getGlobalFilter(representationId, table))
                        .columnFilters(this.getColumnFilters(representationId, table))
                        .expanded(this.getExpandedIdsFromRepresentationId(representationId, table))
                        .activeRowFilterIds(this.getActiveRowFilterIds(representationId))
                        .columnSort(this.getColumnSort(representationId, table))
                        .customCellDescriptors(this.customCellDescriptors)
                        .expandAll(this.isExpandAll(representationId))
                        .build();

                IRepresentationEventProcessor tableEventProcessor = new TableEventProcessor(tableCreationParameters, this.tableEventHandlers, new TableContext(table),
                        this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry, this.representationPersistenceService);
                return Optional.of(tableEventProcessor);
            }
        }
        return Optional.empty();
    }

    private List<String> getActiveRowFilterIds(String representationId) {
        var param = this.urlParser.getParameterValues(representationId);
        return Optional.ofNullable(param.get(ACTIVE_ROW_FILTER_IDS))
                .map(activeFilterIdsParams -> activeFilterIdsParams.get(0))
                .map(this.urlParser::getParameterEntries)
                .orElse(List.of());
    }

    private String getTableIdFromRepresentationId(String representationId) {
        if (representationId.indexOf(URL_PARAM) > 0) {
            return representationId.substring(0, representationId.indexOf(URL_PARAM));
        }
        return representationId;
    }

    private CursorBasedPaginationData getCursorBasedPaginationData(IEditingContext editingContext, String representationId) {
        Object cursor = null;
        String direction = "NEXT";
        int size = 10;
        if (representationId.indexOf(CURSOR) > 0) {
            var param = this.urlParser.getParameterValues(representationId);
            if (param.containsKey(CURSOR)) {
                cursor = param.get(CURSOR).stream()
                        .filter(cursorValue -> !cursorValue.equals("null"))
                        .findFirst()
                        .flatMap(cursorId -> this.objectSearchService.getObject(editingContext, cursorId))
                        .orElse(null);
            }
            if (param.containsKey(SIZE)) {
                size = param.get(SIZE).stream().mapToInt(Integer::parseInt).findFirst().orElse(0);
            }
            if (param.containsKey(DIRECTION)) {
                direction = param.get(DIRECTION).stream().filter(dir -> !dir.equals("null")).findFirst().orElse(null);
            }
        }
        return new CursorBasedPaginationData(cursor, direction, size);
    }

    private String getGlobalFilter(String representationId, Table table) {
        String globalFilter = table.getGlobalFilter();
        if (representationId.indexOf(GLOBAL_FILTER) > 0) {
            var param = this.urlParser.getParameterValues(representationId);
            if (param.containsKey(GLOBAL_FILTER)) {
                globalFilter = param.get(GLOBAL_FILTER).stream().findFirst().orElse("");
            } else {
                globalFilter = "";
            }
        }
        return globalFilter;
    }

    private List<ColumnFilter> getColumnFilters(String representationId, Table table) {
        if (representationId.indexOf(COLUMN_FILTERS) > 0) {
            var param = this.urlParser.getParameterValues(representationId);
            if (param.containsKey(COLUMN_FILTERS)) {
                return this.urlParser.getParameterEntries(param.get(COLUMN_FILTERS).get(0)).stream().map(s -> {
                    String[] parts = s.split(":");
                    return new ColumnFilter(parts[0], parts[1]);
                }).toList();
            }
        }
        return table.getColumnFilters();
    }

    private List<String> getExpandedIdsFromRepresentationId(String representationId, Table table) {
        var param = this.urlParser.getParameterValues(representationId);
        return Optional.ofNullable(param.get(EXPANDED_IDS))
                .map(expandedIds -> expandedIds.get(0))
                .map(this.urlParser::getParameterEntries)
                .orElse(List.of());
    }

    private List<ColumnSort> getColumnSort(String representationId, Table table) {
        if (representationId.indexOf(COLUMN_SORT) > 0) {
            var param = this.urlParser.getParameterValues(representationId);
            if (param.containsKey(COLUMN_SORT)) {
                return this.urlParser.getParameterEntries(param.get(COLUMN_SORT).get(0)).stream().map(s -> {
                    String[] parts = s.split(":");
                    return new ColumnSort(parts[0], Boolean.parseBoolean(parts[1]));
                }).toList();
            }
        }
        return table.getColumnSort();
    }

    private boolean isExpandAll(String representationId) {
        return representationId.indexOf(EXPAND_ALL) > 0;
    }
}

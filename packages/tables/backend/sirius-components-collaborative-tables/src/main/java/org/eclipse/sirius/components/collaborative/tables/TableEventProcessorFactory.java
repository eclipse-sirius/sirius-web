/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.tables.Table;
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

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    public TableEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IRepresentationPersistenceService representationPersistenceService,
            IObjectService objectService, List<ITableEventHandler> tableEventHandlers, IURLParser urlParser) {
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        this.tableEventHandlers = Objects.requireNonNull(tableEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.urlParser = Objects.requireNonNull(urlParser);
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
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, table.getTargetObjectId());
            if (optionalTableDescription.isPresent() && optionalObject.isPresent()) {
                TableDescription tableDescription = optionalTableDescription.get();
                Object object = optionalObject.get();

                var tableCreationParametersBuilder = TableCreationParameters.newTableCreationParameters(this.getTableIdFromRepresentationId(representationId))
                        .tableDescription(tableDescription)
                        .editingContext(editingContext)
                        .targetObject(object)
                        .cursorBasedPaginationData(this.getCursorBasedPaginationData(editingContext, representationId))
                        .targetObject(object);
                var globalFilter = this.getGlobalFilter(representationId);
                if (globalFilter != null) {
                    tableCreationParametersBuilder.globalFilter(globalFilter);
                }

                IRepresentationEventProcessor tableEventProcessor = new TableEventProcessor(tableCreationParametersBuilder.build(), this.tableEventHandlers, new TableContext(table),
                        this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry, this.representationPersistenceService);
                return Optional.of(tableEventProcessor);
            }
        }
        return Optional.empty();
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
                        .flatMap(cursorId -> this.objectService.getObject(editingContext, cursorId))
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

    private String getGlobalFilter(String representationId) {
        String globalFilter = null;
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

}

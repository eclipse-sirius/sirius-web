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
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
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


    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public TableEventProcessorFactory(IRepresentationSearchService representationSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IObjectService objectService, List<ITableEventHandler> tableEventHandlers, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.tableEventHandlers = Objects.requireNonNull(tableEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(representationId, List.of(Table.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Optional<Table> optionalTable = this.representationSearchService.findById(editingContext, representationId, Table.class);
        if (optionalTable.isPresent()) {
            Table table = optionalTable.get();
            Optional<TableDescription> optionalTableDescription = this.representationDescriptionSearchService.findById(editingContext, table.getDescriptionId())
                    .filter(TableDescription.class::isInstance)
                    .map(TableDescription.class::cast);
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, table.getTargetObjectId());
            if (optionalTableDescription.isPresent() && optionalObject.isPresent()) {
                TableDescription tableDescription = optionalTableDescription.get();
                Object object = optionalObject.get();

                TableCreationParameters tableCreationParameters = TableCreationParameters.newTableCreationParameters(representationId)
                        .tableDescription(tableDescription)
                        .editingContext(editingContext)
                        .targetObject(object)
                        .build();

                IRepresentationEventProcessor tableEventProcessor = new TableEventProcessor(editingContext, tableCreationParameters, this.tableEventHandlers,
                        this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);
                return Optional.of(tableEventProcessor);
            }
        }
        return Optional.empty();
    }


}

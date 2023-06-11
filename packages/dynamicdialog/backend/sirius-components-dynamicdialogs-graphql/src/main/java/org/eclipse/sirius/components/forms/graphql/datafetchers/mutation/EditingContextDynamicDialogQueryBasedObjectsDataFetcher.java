/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.forms.graphql.datafetchers.mutation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an Object[] result in an editing context.
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "dynamicDialogQueryObjects")
public class EditingContextDynamicDialogQueryBasedObjectsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<Object>>> {

    private static final String DIALOG_DESCRIPTION_ARGUMENT = "dialogDescriptionId"; //$NON-NLS-1$
    private static final String WIDGET_DESCRIPTION_ARGUMENT = "widgetDescriptionId"; //$NON-NLS-1$
    private static final String VARIABLES_ARGUMENT = "variables"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextDynamicDialogQueryBasedObjectsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<Object>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String dialogDescriptionId = environment.getArgument(DIALOG_DESCRIPTION_ARGUMENT);
        String widgetDescriptionId = environment.getArgument(WIDGET_DESCRIPTION_ARGUMENT);
        Object variables = environment.getArgument(VARIABLES_ARGUMENT);
        Map<Object, Object> variablesMap = new LinkedHashMap<>();
        if (variables instanceof List<?> variablesTmp) {
            for (Object listElement : variablesTmp) {
                if (listElement instanceof Map<?, ?> map) {
                    variablesMap.put(map.get("name"), map.get("value"));
                }
            }
        }
        DynamicDialogQueryBasedObjectsInput input = new DynamicDialogQueryBasedObjectsInput(UUID.randomUUID(), dialogDescriptionId, widgetDescriptionId, variablesMap);


        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(DynamicDialogQueryBasedObjectsSuccessPayload.class::isInstance)
                .map(DynamicDialogQueryBasedObjectsSuccessPayload.class::cast)
                .map(DynamicDialogQueryBasedObjectsSuccessPayload::result)
                .toFuture();
        // @formatter:on
    }
}

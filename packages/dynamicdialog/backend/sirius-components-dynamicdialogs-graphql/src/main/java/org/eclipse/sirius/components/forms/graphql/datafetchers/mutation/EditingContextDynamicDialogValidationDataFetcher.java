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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogVariable;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.GetDynamicDialogValidationMessagesInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.GetDynamicDialogValidationMessagesSuccessPayload;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialogValidationMessage;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an array of validation messages.
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "dynamicDialogValidationMessages")
public class EditingContextDynamicDialogValidationDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<DynamicDialogValidationMessage>>> {

    private static final String OBJECT_ID_ARGUMENT = "objectId"; //$NON-NLS-1$

    private static final String DIALOG_DESCRIPTION_ARGUMENT = "dialogDescriptionId"; //$NON-NLS-1$

    private static final String VARIABLES_ARGUMENT = "variables"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextDynamicDialogValidationDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, ObjectMapper objectMapper) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<List<DynamicDialogValidationMessage>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String dialogDescriptionId = environment.getArgument(DIALOG_DESCRIPTION_ARGUMENT);
        String objectId = environment.getArgument(OBJECT_ID_ARGUMENT);
        Object variables = environment.getArgument(VARIABLES_ARGUMENT);
        List<DynamicDialogVariable> widgetVariables = new ArrayList<>();
        if (variables instanceof List<?> variablesTmp) {
            for (Object listElement : variablesTmp) {
                if (listElement instanceof Map<?, ?> map) {
                    if (map.get("name") instanceof String name && map.get("value") instanceof String value) {
                        widgetVariables.add(new DynamicDialogVariable(name, value));
                    }
                }
            }
        }
        GetDynamicDialogValidationMessagesInput getDynamicDialogValidationMessagesInput = new GetDynamicDialogValidationMessagesInput(UUID.randomUUID(), editingContextId, dialogDescriptionId, objectId, widgetVariables);

        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, getDynamicDialogValidationMessagesInput)
                .filter(GetDynamicDialogValidationMessagesSuccessPayload.class::isInstance)
                .map(GetDynamicDialogValidationMessagesSuccessPayload.class::cast)
                .map(GetDynamicDialogValidationMessagesSuccessPayload::result)
                .toFuture();
        // @formatter:on
    }
}

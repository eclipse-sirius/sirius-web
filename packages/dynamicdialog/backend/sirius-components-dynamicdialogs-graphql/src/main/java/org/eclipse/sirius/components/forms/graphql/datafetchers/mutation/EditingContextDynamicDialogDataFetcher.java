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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogSuccessPayload;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialog;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an Object[] result in an editing context.
 *
 * @author lfasani
 */
@QueryDataFetcher(type = "EditingContext", field = "dynamicDialog")
public class EditingContextDynamicDialogDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<DynamicDialog>> {

    private static final String OBJECT_ID_ARGUMENT = "objectId"; //$NON-NLS-1$
    private static final String DIALOG_DESCRIPTION_ARGUMENT = "dialogDescriptionId"; //$NON-NLS-1$

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextDynamicDialogDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<DynamicDialog> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String dialogDescriptionId = environment.getArgument(DIALOG_DESCRIPTION_ARGUMENT);
        String objectId = environment.getArgument(OBJECT_ID_ARGUMENT);

        DynamicDialogInput input = new DynamicDialogInput(UUID.randomUUID(), objectId, dialogDescriptionId);

        // @formatter:off
        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(DynamicDialogSuccessPayload.class::isInstance)
                .map(DynamicDialogSuccessPayload.class::cast)
                .map(DynamicDialogSuccessPayload::dynamicDialog)
                .toFuture();
        // @formatter:on
    }
}

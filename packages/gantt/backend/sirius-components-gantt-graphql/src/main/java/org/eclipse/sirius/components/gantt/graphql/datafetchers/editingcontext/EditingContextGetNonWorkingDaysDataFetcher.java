/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.gantt.graphql.datafetchers.editingcontext;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.gantt.dto.GetNonWorkingDaysEventPayload;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.GetNonWorkingDaysInput;
import org.eclipse.sirius.components.gantt.NonWorkingDays;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the non-working days in a gantt.
 *
 * @author ncouvert
 */
@QueryDataFetcher(type = "EditingContext", field = "getNonWorkingDays")
public class EditingContextGetNonWorkingDaysDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<NonWorkingDays>> {

    private final IEditingContextDispatcher editingContextDispatcher;

    public EditingContextGetNonWorkingDaysDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<NonWorkingDays> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId =
                Optional.ofNullable(environment.getVariables().get("editingContextId"))
                        .map(Object::toString)
                        .orElse(null);
        String representationId =
                Optional.ofNullable(environment.getVariables().get("representationId"))
                        .map(Object::toString)
                        .orElse(null);

        GetNonWorkingDaysInput input = new GetNonWorkingDaysInput(UUID.randomUUID(), editingContextId, representationId);
        return this.editingContextDispatcher.dispatchQuery(editingContextId, input)
                .filter(GetNonWorkingDaysEventPayload.class::isInstance)
                .map(GetNonWorkingDaysEventPayload.class::cast)
                .map(GetNonWorkingDaysEventPayload::nonWorkingDays)
                .toFuture();
    }
}

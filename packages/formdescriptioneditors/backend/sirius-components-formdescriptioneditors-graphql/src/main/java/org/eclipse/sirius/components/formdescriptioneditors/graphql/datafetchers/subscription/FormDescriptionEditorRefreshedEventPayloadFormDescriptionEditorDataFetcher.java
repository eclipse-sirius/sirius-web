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
package org.eclipse.sirius.components.formdescriptioneditors.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the form from its payload.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "FormDescriptionEditorRefreshedEventPayload", field = "formDescriptionEditor")
public class FormDescriptionEditorRefreshedEventPayloadFormDescriptionEditorDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<FormDescriptionEditor>> {
    @Override
    public DataFetcherResult<FormDescriptionEditor> get(DataFetchingEnvironment environment) throws Exception {
        FormDescriptionEditorRefreshedEventPayload payload = environment.getSource();
        return DataFetcherResult.<FormDescriptionEditor>newResult()
                .data(payload.formDescriptionEditor())
                .localContext(environment.getLocalContext())
                .build();
    }
}

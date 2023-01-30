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
package org.eclipse.sirius.components.forms.graphql.datafetchers.subscription;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the form from its payload.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "FormRefreshedEventPayload", field = "form")
public class FormRefreshedEventPayloadFormDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<Form>> {

    @Override
    public DataFetcherResult<Form> get(DataFetchingEnvironment environment) throws Exception {
        FormRefreshedEventPayload payload = environment.getSource();
        // @formatter:off
        return DataFetcherResult.<Form>newResult()
                .data(payload.getForm())
                .localContext(environment.getLocalContext())
                .build();
        // @formatter:on
    }

}

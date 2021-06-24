/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetchingEnvironment;

/**
 * Implementation of the data fetching environment service.
 *
 * @author pcdavid
 */
@Service
public class DataFetchingEnvironmentService implements IDataFetchingEnvironmentService {

    private final ObjectMapper objectMapper;

    public DataFetchingEnvironmentService(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public <T> T getInput(DataFetchingEnvironment environment, Class<T> inputType) {
        Object argument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        return this.objectMapper.convertValue(argument, inputType);
    }

}

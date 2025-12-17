/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.library.api.IPublishLibraryInput;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#publishLibraries.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Mutation", field = "publishLibraries")
public class MutationPublishLibrariesDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final ILibraryApplicationService libraryApplicationService;

    public MutationPublishLibrariesDataFetcher(ObjectMapper objectMapper, ILibraryApplicationService libraryApplicationService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.libraryApplicationService = Objects.requireNonNull(libraryApplicationService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, IPublishLibraryInput.class);

        return this.libraryApplicationService.publishLibraries(input);
    }
}

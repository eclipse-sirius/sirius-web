/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.images.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.images.dto.DeleteImageInput;
import org.eclipse.sirius.web.application.images.services.api.IProjectImageApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#deleteImage.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "deleteImage")
public class MutationDeleteImageDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IProjectImageApplicationService imageApplicationService;

    public MutationDeleteImageDataFetcher(ObjectMapper objectMapper, IProjectImageApplicationService imageApplicationService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.imageApplicationService = Objects.requireNonNull(imageApplicationService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DeleteImageInput.class);
        return this.imageApplicationService.deleteImage(input);
    }
}

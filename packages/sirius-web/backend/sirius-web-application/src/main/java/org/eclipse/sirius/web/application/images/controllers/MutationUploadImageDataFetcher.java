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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.images.dto.UploadImageInput;
import org.eclipse.sirius.web.application.images.services.api.IProjectImageApplicationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#uploadImage.
 *
 * @author sbegaudeau
 */
@MutationDataFetcher(type = "Mutation", field = "uploadImage")
public class MutationUploadImageDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private static final String ID = "id";

    private static final String PROJECT_ID = "projectId";

    private static final String LABEL = "label";

    private static final String FILE = "file";

    private final IProjectImageApplicationService imageApplicationService;

    private final IMessageService messageService;

    public MutationUploadImageDataFetcher(IProjectImageApplicationService imageApplicationService, IMessageService messageService) {
        this.imageApplicationService = Objects.requireNonNull(imageApplicationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> inputArgument = environment.getArgument(INPUT_ARGUMENT);

        var optionalId = Optional.ofNullable(inputArgument.get(ID))
                .map(Object::toString)
                .flatMap(new UUIDParser()::parse);

        var optionalProjectId = Optional.ofNullable(inputArgument.get(PROJECT_ID))
                .map(Object::toString)
                .flatMap(new UUIDParser()::parse);

        var optionalLabel = Optional.ofNullable(inputArgument.get(LABEL))
                .filter(String.class::isInstance)
                .map(String.class::cast);

        var optionalFile = Optional.ofNullable(inputArgument.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast);

        if (optionalId.isPresent() && optionalProjectId.isPresent() && optionalLabel.isPresent() && optionalFile.isPresent()) {
            var id = optionalId.get();
            var projectId = optionalProjectId.get();
            var label = optionalLabel.get();
            var file = optionalFile.get();

            var input = new UploadImageInput(id, projectId, label, file);
            return this.imageApplicationService.uploadImage(input);
        }
        return new ErrorPayload(optionalId.orElse(UUID.randomUUID()), this.messageService.unexpectedError());
    }
}

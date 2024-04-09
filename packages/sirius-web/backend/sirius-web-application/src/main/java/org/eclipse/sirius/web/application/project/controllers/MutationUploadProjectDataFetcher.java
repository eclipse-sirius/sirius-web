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
package org.eclipse.sirius.web.application.project.controllers;

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
import org.eclipse.sirius.web.application.project.services.api.IProjectImportService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#uploadProject.
 *
 * @author gcoutable
 */
@MutationDataFetcher(type = "Mutation", field = "uploadProject")
public class MutationUploadProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private static final String ID = "id";

    private static final String FILE = "file";

    private final IProjectImportService projectImportService;

    private final IMessageService messageService;

    public MutationUploadProjectDataFetcher(IProjectImportService projectImportService, IMessageService messageService) {
        this.projectImportService = Objects.requireNonNull(projectImportService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> input = environment.getArgument(INPUT_ARGUMENT);

        var optionalId = Optional.ofNullable(input.get(ID))
                .map(Object::toString)
                .flatMap(new UUIDParser()::parse);

        var optionalFile = Optional.ofNullable(input.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast);

        if (optionalId.isPresent() && optionalFile.isPresent()) {
            var id = optionalId.get();
            var uploadFile = optionalFile.get();
            return this.projectImportService.importProject(id, uploadFile);
        }

        return new ErrorPayload(optionalId.orElse(UUID.randomUUID()), this.messageService.unexpectedError());

    }

}

/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectImportService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.UploadProjectInput;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create a new {@link Project} thanks to an {@link UploadProjectInput}.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   uploadProject(input: UploadProjectInput!): UploadProjectPayload!
 * }
 * </pre>
 *
 * @author gcoutable
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationUploadProjectDataFetcher.UPLOAD_PROJECT_FIELD)
public class MutationUploadProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    public static final String UPLOAD_PROJECT_FIELD = "uploadProject"; //$NON-NLS-1$

    private static final String ID = "id"; //$NON-NLS-1$

    private static final String FILE = "file"; //$NON-NLS-1$

    private final IGraphQLMessageService messageService;

    private final IProjectImportService projectImportService;

    public MutationUploadProjectDataFetcher(IGraphQLMessageService messageService, IProjectImportService projectImportService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.projectImportService = Objects.requireNonNull(projectImportService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> input = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        // @formatter:off
        UUID id = Optional.of(input.get(ID))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .flatMap(new IDParser()::parse)
                .orElse(null);

        return Optional.of(input.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast)
                .map(uploadFile -> this.projectImportService.importProject(id, uploadFile))
                .orElse(new ErrorPayload(id, this.messageService.unexpectedError()));
        // @formatter:on
    }

}

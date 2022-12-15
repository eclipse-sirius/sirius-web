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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.datafetchers.IViewerProvider;
import org.eclipse.sirius.web.graphql.messages.IGraphQLMessageService;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.projects.DeleteProjectInput;
import org.eclipse.sirius.web.services.api.projects.DeleteProjectSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.viewer.IViewer;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to delete a project.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   deleteProject(input: DeleteProjectInput!): DeleteProjectPayload!
 * }
 * </pre>
 *
 * @author fbarbin
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = MutationDeleteProjectDataFetcher.DELETE_PROJECT_FIELD)
public class MutationDeleteProjectDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    public static final String DELETE_PROJECT_FIELD = "deleteProject";

    private final ObjectMapper objectMapper;

    private final IViewerProvider viewerProvider;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IProjectService projectService;

    private final IGraphQLMessageService messageService;

    public MutationDeleteProjectDataFetcher(ObjectMapper objectMapper, IViewerProvider viewerProvider, IProjectService projectService,
            IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IGraphQLMessageService messageService) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.viewerProvider = Objects.requireNonNull(viewerProvider);
        this.projectService = Objects.requireNonNull(projectService);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DeleteProjectInput.class);

        var optionalViewer = this.viewerProvider.getViewer(environment);
        IPayload payload = new ErrorPayload(input.getId(), this.messageService.unexpectedError());
        if (optionalViewer.isPresent()) {
            IViewer viewer = optionalViewer.get();
            this.editingContextEventProcessorRegistry.disposeEditingContextEventProcessor(input.getProjectId().toString());
            this.projectService.delete(input.getProjectId());
            payload = new DeleteProjectSuccessPayload(input.getId(), viewer);
        }

        return payload;
    }
}

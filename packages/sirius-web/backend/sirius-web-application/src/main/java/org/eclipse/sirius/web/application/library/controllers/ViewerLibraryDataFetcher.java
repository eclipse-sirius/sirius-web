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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import graphql.execution.DataFetcherResult;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#library.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "Viewer", field = "library")
public class ViewerLibraryDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<LibraryDTO>> {

    private static final String INPUT_ARGUMENT_NAMESPACE = "namespace";

    private static final String INPUT_ARGUMENT_NAME = "name";

    private static final String INPUT_ARGUMENT_VERSION = "version";

    private final ILibraryApplicationService libraryApplicationService;

    public ViewerLibraryDataFetcher(ILibraryApplicationService libraryApplicationService) {
        this.libraryApplicationService = Objects.requireNonNull(libraryApplicationService);
    }

    @Override
    public DataFetcherResult<LibraryDTO> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = new HashMap<>();
        LibraryDTO libraryDTO = null;

        var variables = environment.getVariables();
        var namespaceArgument = variables.get(INPUT_ARGUMENT_NAMESPACE);
        var nameArgument = variables.get(INPUT_ARGUMENT_NAME);
        var versionArgument = variables.get(INPUT_ARGUMENT_VERSION);

        if (nameArgument instanceof String name && namespaceArgument instanceof String namespace && versionArgument instanceof String version) {
            var optionalLibrary = this.libraryApplicationService.findByNamespaceAndNameAndVersion(namespace, name, version);
            if (optionalLibrary.isPresent()) {
                var library = optionalLibrary.get();
                localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, library.getSemanticData().getId());
                libraryDTO = new LibraryDTO(UUID.randomUUID(), library.getNamespace(), library.getName(), library.getVersion(), library.getDescription(), library.getCreatedOn());
            }
        }

        return DataFetcherResult.<LibraryDTO>newResult()
                .data(libraryDTO)
                .localContext(localContext)
                .build();
    }
}

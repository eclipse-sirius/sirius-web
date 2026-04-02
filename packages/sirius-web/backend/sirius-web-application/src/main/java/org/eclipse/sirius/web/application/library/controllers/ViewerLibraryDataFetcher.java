/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#library.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "Viewer", field = "library")
public class ViewerLibraryDataFetcher implements IDataFetcherWithFieldCoordinates<LibraryDTO> {

    private static final String INPUT_ARGUMENT_NAMESPACE = "namespace";

    private static final String INPUT_ARGUMENT_NAME = "name";

    private static final String INPUT_ARGUMENT_VERSION = "version";

    private final ICapabilityEvaluator capabilityEvaluator;

    private final ILibraryApplicationService libraryApplicationService;

    private final Logger logger = LoggerFactory.getLogger(ViewerLibraryDataFetcher.class);

    public ViewerLibraryDataFetcher(ICapabilityEvaluator capabilityEvaluator, ILibraryApplicationService libraryApplicationService) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.libraryApplicationService = Objects.requireNonNull(libraryApplicationService);
    }

    @Override
    public LibraryDTO get(DataFetchingEnvironment environment) throws Exception {
        String namespace = environment.getArgument(INPUT_ARGUMENT_NAMESPACE);
        String name = environment.getArgument(INPUT_ARGUMENT_NAME);
        String version = environment.getArgument(INPUT_ARGUMENT_VERSION);

        MDC.put("namespace", namespace);
        MDC.put("name", name);
        MDC.put("version", version);

        String libraryIdentifier = String.format("%s:%s:%s", namespace, name, version);
        var hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.LIBRARY, libraryIdentifier, SiriusWebCapabilities.Library.VIEW);
        if (!hasCapability) {
            this.logger.atWarn()
                    .setMessage("Access denied to library {}:{}:{}")
                    .addArgument(namespace)
                    .addArgument(name)
                    .addArgument(version)
                    .addKeyValue("capabilityType", SiriusWebCapabilities.LIBRARY)
                    .addKeyValue("capability", SiriusWebCapabilities.Library.VIEW)
                    .log();

            return null;
        }

        var optionalLibrary = this.libraryApplicationService.findByNamespaceAndNameAndVersion(namespace, name, version);
        if (optionalLibrary.isPresent()) {
            this.logger.atInfo()
                    .setMessage("Library {}:{}:{} retrieved")
                    .addArgument(namespace)
                    .addArgument(name)
                    .addArgument(version)
                    .log();
        } else {
            this.logger.atWarn()
                    .setMessage("Library {}:{}:{} not found")
                    .addArgument(namespace)
                    .addArgument(name)
                    .addArgument(version)
                    .log();
        }

        MDC.clear();

        return optionalLibrary.orElse(null);
    }
}

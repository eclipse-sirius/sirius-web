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
package org.eclipse.sirius.web.test.diagrams.only.services;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.datafetchers.IViewerProvider;
import org.eclipse.sirius.web.services.api.viewer.IViewer;
import org.eclipse.sirius.web.services.api.viewer.User;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetchingEnvironment;

/**
 * Service used to retrieve the current viewer.
 *
 * @author sbegaudeau
 */
@Service
public class ViewerProvider implements IViewerProvider {

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    public ViewerProvider(IDataFetchingEnvironmentService dataFetchingEnvironmentService) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
    }

    @Override
    public Optional<IViewer> getViewer(DataFetchingEnvironment environment) {
        // @formatter:off
        return this.dataFetchingEnvironmentService.getPrincipal(environment)
                .map(Principal::getName)
                .map(username -> new User(UUID.randomUUID(), username));
        // @formatter:on
    }

}

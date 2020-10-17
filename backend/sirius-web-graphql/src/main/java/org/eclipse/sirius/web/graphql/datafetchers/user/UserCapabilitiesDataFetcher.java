/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.user;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.api.configuration.IFrontendContribution;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.services.api.accounts.Capabilities;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the optional capabilities available to a given viewer.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = ViewerTypeProvider.USER_TYPE, field = ViewerTypeProvider.CAPABILITIES_FIELD)
public class UserCapabilitiesDataFetcher implements IDataFetcherWithFieldCoordinates<Capabilities> {

    private final List<IFrontendContribution> frontendExtensions;

    public UserCapabilitiesDataFetcher(List<IFrontendContribution> frontendExtensions) {
        this.frontendExtensions = Objects.requireNonNull(frontendExtensions);
    }

    @Override
    public Capabilities get(DataFetchingEnvironment environment) throws Exception {
        // @formatter:off
        List<String> pathOverrides = this.frontendExtensions.stream()
                                         .flatMap(f -> f.getPathPatterns().stream())
                                         .map(this::globToRegex)
                                         .collect(Collectors.toList());
        // @formatter:on
        return new Capabilities(pathOverrides);
    }

    private String globToRegex(String globPattern) {
        // // @formatter:off
        return "^" + globPattern.replace("/", "\\/")          //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                .replace("**", ".*")          //$NON-NLS-1$ //$NON-NLS-2$
                                .replace("*", "[^/]*") + "$"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        // @formatter:on
    }
}

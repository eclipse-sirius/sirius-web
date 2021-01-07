/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.project;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ProjectTypeProvider;
import org.eclipse.sirius.web.services.api.modelers.IModelerService;
import org.eclipse.sirius.web.services.api.modelers.Modeler;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve all the modelers of a project.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Project {
 *   modelers: [Modeler!]!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = ProjectTypeProvider.TYPE, field = ProjectTypeProvider.MODELERS_FIELD)
public class ProjectModelersDataFetcher implements IDataFetcherWithFieldCoordinates<List<Modeler>> {
    private final IModelerService modelerService;

    public ProjectModelersDataFetcher(IModelerService modelerService) {
        this.modelerService = Objects.requireNonNull(modelerService);
    }

    @Override
    public List<Modeler> get(DataFetchingEnvironment environment) throws Exception {
        Project parentProject = environment.getSource();
        return this.modelerService.getModelers(parentProject);
    }

}

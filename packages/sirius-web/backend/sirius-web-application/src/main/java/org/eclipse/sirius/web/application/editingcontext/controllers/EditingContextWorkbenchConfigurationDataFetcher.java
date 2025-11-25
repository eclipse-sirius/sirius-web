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
package org.eclipse.sirius.web.application.editingcontext.controllers;

import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.editingcontext.dto.DefaultViewConfiguration;
import org.eclipse.sirius.web.application.editingcontext.dto.DetailsViewConfiguration;
import org.eclipse.sirius.web.application.editingcontext.dto.ExplorerViewConfiguration;
import org.eclipse.sirius.web.application.editingcontext.dto.WorkbenchConfiguration;
import org.eclipse.sirius.web.application.editingcontext.dto.WorkbenchMainPanelConfiguration;
import org.eclipse.sirius.web.application.editingcontext.dto.WorkbenchSidePanelConfiguration;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#workbenchConfiguration.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "EditingContext", field = "workbenchConfiguration")
public class EditingContextWorkbenchConfigurationDataFetcher implements IDataFetcherWithFieldCoordinates<WorkbenchConfiguration> {

    @Override
    public WorkbenchConfiguration get(DataFetchingEnvironment environment) throws Exception {
        // TODO move this in the default implementation of a configuration service
        return new WorkbenchConfiguration(new WorkbenchMainPanelConfiguration("main", List.of()), List.of(
                new WorkbenchSidePanelConfiguration("left", true, List.of(
                        new ExplorerViewConfiguration(true, List.of(), "explorer_tree_description"),
                        new DefaultViewConfiguration("validation", false),
                        new DefaultViewConfiguration("search", false)
                )),
                new WorkbenchSidePanelConfiguration("right", true, List.of(
                        // TODO the selected page ID is hard to retrieve, we can maybe have a first version without it and include it later.
                        new DetailsViewConfiguration(true, ""),
                        new DefaultViewConfiguration("query", false),
                        new DefaultViewConfiguration("representations", false),
                        new DefaultViewConfiguration("related-elements", false)
                ))
        ));
    }
}

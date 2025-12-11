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
package org.eclipse.sirius.components.core.graphql.datafetchers;

import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.DefaultViewConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchMainPanelConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchSidePanelConfiguration;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

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
        return new WorkbenchConfiguration(new WorkbenchMainPanelConfiguration("main", List.of()), List.of(
                new WorkbenchSidePanelConfiguration("left", true, List.of(
                        new DefaultViewConfiguration("explorer", true),
                        new DefaultViewConfiguration("validation", false),
                        new DefaultViewConfiguration("search", false)
                )),
                new WorkbenchSidePanelConfiguration("right", true, List.of(
                        new DefaultViewConfiguration("details", true),
                        new DefaultViewConfiguration("query", false),
                        new DefaultViewConfiguration("representations", false),
                        new DefaultViewConfiguration("related-elements", false)
                ))
        ));
    }
}

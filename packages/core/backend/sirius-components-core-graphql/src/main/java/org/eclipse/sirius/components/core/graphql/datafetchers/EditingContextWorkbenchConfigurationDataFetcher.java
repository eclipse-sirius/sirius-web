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
package org.eclipse.sirius.components.core.graphql.datafetchers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationCustomizer;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationProvider;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field EditingContext#workbenchConfiguration.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "EditingContext", field = "workbenchConfiguration")
public class EditingContextWorkbenchConfigurationDataFetcher implements IDataFetcherWithFieldCoordinates<WorkbenchConfiguration> {

    private final IWorkbenchConfigurationProvider workbenchConfigurationProvider;

    private final List<IWorkbenchConfigurationCustomizer> workbenchConfigurationCustomizers;

    public EditingContextWorkbenchConfigurationDataFetcher(IWorkbenchConfigurationProvider workbenchConfigurationProvider, List<IWorkbenchConfigurationCustomizer> workbenchConfigurationCustomizers) {
        this.workbenchConfigurationProvider = Objects.requireNonNull(workbenchConfigurationProvider);
        this.workbenchConfigurationCustomizers = Objects.requireNonNull(workbenchConfigurationCustomizers);
    }

    @Override
    public WorkbenchConfiguration get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID))
                .map(Object::toString)
                .orElse("");
        var workbenchConfiguration = this.workbenchConfigurationProvider.getWorkbenchConfiguration(editingContextId);
        for (var customizer: this.workbenchConfigurationCustomizers) {
            workbenchConfiguration = customizer.customize(editingContextId, workbenchConfiguration);
        }
        return workbenchConfiguration;
    }
}

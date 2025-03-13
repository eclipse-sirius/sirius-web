/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.components.tables.graphql.datafetchers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.tables.api.IRowFilterProvider;
import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the table description context menu.
 *
 * @author Jerome Gout
 */
@QueryDataFetcher(type = "TableDescription", field = "rowFilters")
public class TableDescriptionRowFiltersDataFetcher implements IDataFetcherWithFieldCoordinates<List<RowFilter>> {

    private final List<IRowFilterProvider> tableRowFiltersProviders;

    public TableDescriptionRowFiltersDataFetcher(List<IRowFilterProvider> tableRowFiltersProviders) {
        this.tableRowFiltersProviders = tableRowFiltersProviders;
    }

    @Override
    public List<RowFilter> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        TableDescription tableDescription = environment.getSource();

        if (editingContextId != null && representationId != null) {
            return this.tableRowFiltersProviders.stream()
                    .filter(rowFiltersProvider -> rowFiltersProvider.canHandle(editingContextId, tableDescription, representationId))
                    .map(rowFiltersProvider -> rowFiltersProvider.get(editingContextId, tableDescription, representationId))
                    .flatMap(Collection::stream)
                    .toList();
        }
        return List.of();
    }
}

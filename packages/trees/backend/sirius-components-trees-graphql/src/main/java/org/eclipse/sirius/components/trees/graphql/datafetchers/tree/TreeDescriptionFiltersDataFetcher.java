/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeFilterProvider;
import org.eclipse.sirius.components.collaborative.trees.api.TreeFilter;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.components.trees.description.TreeDescription;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the tree description "filters" field.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "TreeDescription", field = "filters")
public class TreeDescriptionFiltersDataFetcher implements IDataFetcherWithFieldCoordinates<List<TreeFilter>> {

    private final List<ITreeFilterProvider> treeFiltersProviders;

    public TreeDescriptionFiltersDataFetcher(List<ITreeFilterProvider> treeFiltersProviders) {
        this.treeFiltersProviders = Objects.requireNonNull(treeFiltersProviders);
    }

    @Override
    public List<TreeFilter> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);

        TreeDescription treeDescription = environment.getSource();

        if (editingContextId != null && representationId != null) {
            return this.treeFiltersProviders.stream()
                    .map(treeFilterProvider -> treeFilterProvider.get(editingContextId, treeDescription, representationId))
                    .flatMap(Collection::stream)
                    .toList();
        }
        return List.of();
    }
}

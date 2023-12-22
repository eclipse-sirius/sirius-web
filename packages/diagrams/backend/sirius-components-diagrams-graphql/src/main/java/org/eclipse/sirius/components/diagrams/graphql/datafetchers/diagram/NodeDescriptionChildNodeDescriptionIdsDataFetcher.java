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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher to retrieve the children node description IDs of a node.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "NodeDescription", field = "childNodeDescriptionIds")
public class NodeDescriptionChildNodeDescriptionIdsDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) {
        NodeDescription nodeDescription = environment.getSource();
        var childNodeDescriptionIds = new ArrayList<String>();

        childNodeDescriptionIds.addAll(nodeDescription.getChildNodeDescriptions().stream().map(NodeDescription::getId).toList());
        childNodeDescriptionIds.addAll(nodeDescription.getReusedChildNodeDescriptionIds());

        return childNodeDescriptionIds;
    }
}

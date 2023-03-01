/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Node", field = "size")
public class NodeSizeDataFetcher implements IDataFetcherWithFieldCoordinates<Size> {
    @Override
    public Size get(DataFetchingEnvironment environment) throws Exception {
        Node node = environment.getSource();
        Map<String, Object> localContext = environment.getLocalContext();

        return Optional.ofNullable(localContext.get("diagram"))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .flatMap(diagram -> this.size(diagram, node))
                .orElse(new Size(0, 0));
    }

    private Optional<Size> size(Diagram diagram, Node node) {
        Optional<Size> optionalSize = Optional.empty();

        if (diagram.getLabel().endsWith("__EXPERIMENTAL")) {
            optionalSize = Optional.of(diagram.getLayoutData())
                    .map(DiagramLayoutData::nodeLayoutData)
                    .map(layoutData -> layoutData.get(node.getId()))
                    .map(NodeLayoutData::size);
        } else {
            optionalSize = Optional.of(new Size(node.getSize().getWidth(), node.getSize().getHeight()));
        }

        return optionalSize;
    }
}

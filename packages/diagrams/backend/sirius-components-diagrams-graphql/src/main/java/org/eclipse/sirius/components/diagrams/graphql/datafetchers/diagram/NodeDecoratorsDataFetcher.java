/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeDecorator;
import org.eclipse.sirius.components.diagrams.NodeDecoratorPosition;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.beans.factory.annotation.Value;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher returning decorators for a node (hardcoded for now).
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Node", field = "decorators")
public class NodeDecoratorsDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<List<NodeDecorator>>> {

    private static final String PRIVATE = "/icons/papaya/full/ovr16/Visibility_PRIVATE.svg";

    private static final String PROTECTED = "/icons/papaya/full/ovr16/Visibility_PROTECTED.svg";

    private static final String PUBLIC = "/icons/papaya/full/ovr16/Visibility_PUBLIC.svg";

    private static final String PACKAGE = "/icons/papaya/full/ovr16/Visibility_PACKAGE.svg";

    private final boolean enabledDecorators;

    public NodeDecoratorsDataFetcher(@Value("${sirius.components.diagram.enableDecorators:false}") boolean enableDecorators) {
        this.enabledDecorators = enableDecorators;
    }

    @Override
    public DataFetcherResult<List<NodeDecorator>> get(DataFetchingEnvironment environment) throws Exception {
        List<NodeDecorator> decorators = new ArrayList<>();
        Node node = environment.getSource();

        Map<String, Object> parentLocalContext = environment.getLocalContext();
        Map<String, Object> localContext = new HashMap<>();
        localContext.put("diagram", parentLocalContext.get("diagram"));

        if (this.enabledDecorators) {
            if (node != null && node.getTargetObjectLabel() != null) {
                if (node.getTargetObjectLabel().contains("Decorator")) {
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("North Decorator")
                            .position(NodeDecoratorPosition.NORTH)
                            .iconURL(PUBLIC)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("North Decorator 2")
                            .position(NodeDecoratorPosition.NORTH)
                            .iconURL(PRIVATE)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("North East Decorator")
                            .position(NodeDecoratorPosition.NORTH_EAST)
                            .iconURL(PRIVATE)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("East Decorator")
                            .position(NodeDecoratorPosition.EAST)
                            .iconURL(PROTECTED)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("South East Decorator")
                            .position(NodeDecoratorPosition.SOUTH_EAST)
                            .iconURL(PACKAGE)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("South Decorator")
                            .position(NodeDecoratorPosition.SOUTH)
                            .iconURL(PUBLIC)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("South West Decorator")
                            .position(NodeDecoratorPosition.SOUTH_WEST)
                            .iconURL(PUBLIC)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("West Decorator")
                            .position(NodeDecoratorPosition.WEST)
                            .iconURL(PROTECTED)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("North West Decorator")
                            .position(NodeDecoratorPosition.NORTH_WEST)
                            .iconURL(PRIVATE)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("Center Decorator")
                            .position(NodeDecoratorPosition.CENTER)
                            .iconURL(PACKAGE)
                            .build());
                } else if (node.getTargetObjectLabel().contains("Papaya")) {
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("Papaya")
                            .position(NodeDecoratorPosition.NORTH)
                            .iconURL("/project-templates/Cosmic-Papaya.jpeg")
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("Papaya2")
                            .position(NodeDecoratorPosition.EAST)
                            .iconURL("/project-templates/Retro-Papaya.jpeg")
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("Papaya3")
                            .position(NodeDecoratorPosition.WEST)
                            .iconURL(PUBLIC)
                            .build());
                    decorators.add(NodeDecorator.newNodeDecorator()
                            .label("Papaya4")
                            .position(NodeDecoratorPosition.SOUTH_WEST)
                            .iconURL(PRIVATE)
                            .build());
                }
            }
        }
        return DataFetcherResult.<List<NodeDecorator>>newResult()
                .data(decorators)
                .localContext(localContext)
                .build();
    }
}

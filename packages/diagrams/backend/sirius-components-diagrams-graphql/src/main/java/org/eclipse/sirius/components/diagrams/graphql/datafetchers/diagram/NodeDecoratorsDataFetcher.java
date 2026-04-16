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

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
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
                    decorators.add(new NodeDecorator("North Decorator", "North Decorator", NodeDecoratorPosition.NORTH, PUBLIC));
                    decorators.add(new NodeDecorator("North Decorator 2", "North Decorator 2", NodeDecoratorPosition.NORTH, PRIVATE));
                    decorators.add(new NodeDecorator("North East Decorator", "North East Decorator", NodeDecoratorPosition.NORTH_EAST, PRIVATE));
                    decorators.add(new NodeDecorator("East Decorator", "East Decorator", NodeDecoratorPosition.EAST, PROTECTED));
                    decorators.add(new NodeDecorator("South East Decorator", "South East Decorator", NodeDecoratorPosition.SOUTH_EAST, PACKAGE));
                    decorators.add(new NodeDecorator("South Decorator", "South Decorator", NodeDecoratorPosition.SOUTH, PUBLIC));
                    decorators.add(new NodeDecorator("South West Decorator", "South West Decorator", NodeDecoratorPosition.SOUTH_WEST, PUBLIC));
                    decorators.add(new NodeDecorator("West Decorator", "West Decorator", NodeDecoratorPosition.WEST, PROTECTED));
                    decorators.add(new NodeDecorator("North West Decorator", "North West Decorator", NodeDecoratorPosition.NORTH_WEST, PRIVATE));
                    decorators.add(new NodeDecorator("Center Decorator", "Center Decorator", NodeDecoratorPosition.CENTER, PACKAGE));
                } else if (node.getTargetObjectLabel().contains("Papaya")) {
                    decorators.add(new NodeDecorator("Papaya", "Papaya", NodeDecoratorPosition.NORTH, "/project-templates/Cosmic-Papaya.jpeg"));
                    decorators.add(new NodeDecorator("Papaya2", "Papaya2", NodeDecoratorPosition.EAST, "/project-templates/Retro-Papaya.jpeg"));
                    decorators.add(new NodeDecorator("Papaya3", "Papaya3", NodeDecoratorPosition.WEST, PUBLIC));
                    decorators.add(new NodeDecorator("Papaya4", "Papaya4", NodeDecoratorPosition.SOUTH_WEST, PRIVATE));
                }
            }
        }
        return DataFetcherResult.<List<NodeDecorator>> newResult()
                .data(decorators)
                .localContext(localContext)
                .build();
    }
}

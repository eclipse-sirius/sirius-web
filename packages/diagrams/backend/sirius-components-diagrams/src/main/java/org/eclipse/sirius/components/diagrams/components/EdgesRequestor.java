/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.Edge;

/**
 * Used to perform requests on some cached edges from the previous diagram.
 *
 * @author sbegaudeau
 */
public class EdgesRequestor implements IEdgesRequestor {

    private final Map<String, Edge> edgeId2Edges;

    public EdgesRequestor(List<Edge> previousEdges) {
        this.edgeId2Edges = previousEdges.stream()
                .collect(Collectors.toMap(Edge::getId, Function.identity(), (oldValue, newValue) -> newValue));
    }

    @Override
    public Optional<Edge> getById(String edgeId) {
        return Optional.ofNullable(this.edgeId2Edges.get(edgeId));
    }

}

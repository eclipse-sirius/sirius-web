/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.diagrams.Edge;

/**
 * Used to perform requests on some cached edges from the previous diagram.
 *
 * @author sbegaudeau
 */
public class EdgesRequestor implements IEdgesRequestor {

    private final Map<UUID, Edge> edgeId2Edges;

    public EdgesRequestor(List<Edge> previousEdges) {
        // @formatter:off
        this.edgeId2Edges = previousEdges.stream()
                .collect(Collectors.toMap(Edge::getId, Function.identity()));
        // @formatter:on
    }

    @Override
    public Optional<Edge> getById(UUID edgeId) {
        return Optional.ofNullable(this.edgeId2Edges.get(edgeId));
    }

}

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

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to perform requests on some cached nodes from the previous diagram.
 *
 * @author sbegaudeau
 */
public class NodesRequestor implements INodesRequestor {

    private final Map<String, Node> targetObjectId2Nodes;

    public NodesRequestor(List<Node> previousNodes) {
        this.targetObjectId2Nodes = previousNodes.stream()
                .collect(Collectors.toMap(Node::getTargetObjectId, Function.identity(), (oldValue, newValue) -> newValue));
    }

    @Override
    public Optional<Node> getByTargetObjectId(String targetObjectId) {
        return Optional.ofNullable(this.targetObjectId2Nodes.get(targetObjectId));
    }

}

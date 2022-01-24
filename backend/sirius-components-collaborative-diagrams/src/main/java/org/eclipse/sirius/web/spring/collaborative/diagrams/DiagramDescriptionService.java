/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramDescriptionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IDiagramDescriptionService}.
 *
 * @author pcdavid
 */
@Service
public class DiagramDescriptionService implements IDiagramDescriptionService {

    @Override
    public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, UUID nodeDescriptionId) {
        return this.findNodeDescription(nodeDesc -> Objects.equals(nodeDesc.getId(), nodeDescriptionId), diagramDescription.getNodeDescriptions());
    }

    private Optional<NodeDescription> findNodeDescription(Predicate<NodeDescription> condition, List<NodeDescription> candidates) {
        Optional<NodeDescription> result = Optional.empty();
        for (NodeDescription node : candidates) {
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                result = this.findNodeDescription(condition, node.getBorderNodeDescriptions()).or(() -> this.findNodeDescription(condition, node.getChildNodeDescriptions()));
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

    @Override
    public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, UUID edgeDescriptionId) {
        return this.findEdgeDescription(edgeDesc -> Objects.equals(edgeDesc.getId(), edgeDescriptionId), diagramDescription.getEdgeDescriptions());
    }

    private Optional<EdgeDescription> findEdgeDescription(Predicate<EdgeDescription> condition, List<EdgeDescription> candidates) {
        return candidates.stream().filter(condition).findFirst();
    }
}

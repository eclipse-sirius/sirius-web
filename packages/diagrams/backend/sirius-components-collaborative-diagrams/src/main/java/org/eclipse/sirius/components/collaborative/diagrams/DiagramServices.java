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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramServices;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.springframework.stereotype.Service;

/**
 * Service used to perform operations on a diagram.
 *
 * @author gdaniel
 */
@Service
public class DiagramServices implements IDiagramServices {

    @Override
    public Object collapse(IDiagramService diagramService, List<Node> nodes) {
        nodes.stream().map(Node::getId).forEach(nodeId -> {
            diagramService.getDiagramContext().getDiagramEvents().add(new UpdateCollapsingStateEvent(nodeId, CollapsingState.COLLAPSED));
        });
        return nodes;
    }

    @Override
    public Object expand(IDiagramService diagramService, List<Node> nodes) {
        nodes.stream().map(Node::getId).forEach(nodeId -> {
            diagramService.getDiagramContext().getDiagramEvents().add(new UpdateCollapsingStateEvent(nodeId, CollapsingState.EXPANDED));
        });
        return nodes;
    }

}

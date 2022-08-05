/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IDiagramOperationsService}.
 *
 * @author pcdavid
 */
@Service
public class DiagramOperationsService implements IDiagramOperationsService {
    private final IObjectService objectService;

    public DiagramOperationsService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void createView(IDiagramContext diagramContext, EObject semanticElement, Optional<Node> optionalParentNode, NodeDescription nodeDescription) {
        String targetObjectId = this.objectService.getId(semanticElement);
        String parentElementId = optionalParentNode.map(Node::getId).orElseGet(() -> diagramContext.getDiagram().getId());
        // @formatter:off
        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .parentElementId(parentElementId)
                .targetObjectId(targetObjectId)
                .descriptionId(nodeDescription.getId())
                .build();
        // @formatter:on
        diagramContext.getViewCreationRequests().add(viewCreationRequest);
    }

    @Override
    public void deleteView(IDiagramContext diagramContext, Node node) {
        ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest().elementId(node.getId()).build();
        diagramContext.getViewDeletionRequests().add(viewDeletionRequest);
    }

}

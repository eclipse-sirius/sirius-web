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
package org.eclipse.sirius.components.flow.starter.services;

import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Java Service for the Flow view.
 *
 * @author frouene
 */
public class FlowService {

    private final IIdentityService identityService;

    public FlowService(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    public EObject drop(EObject self, Node selectedNode, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        var parentElementId = Optional.ofNullable(selectedNode)
                .map(Node::getId)
                .orElse(diagramContext.getDiagram().getId());

        var targetObjectId = this.identityService.getId(self);

        String nodeDescriptionName;
        if (self.eClass() == FlowPackage.Literals.COMPOSITE_PROCESSOR) {
            nodeDescriptionName = "System Node";
        } else {
            nodeDescriptionName = self.eClass().getName() + " Node";
        }

        var descriptionId = convertedNodes.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(nodeDescriptionName))
                .findFirst()
                .map(entry -> entry.getValue().getId())
                .orElse(null);

        if (parentElementId != null && targetObjectId != null && descriptionId != null) {
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .parentElementId(parentElementId)
                    .targetObjectId(targetObjectId)
                    .descriptionId(descriptionId)
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .build();
            diagramContext.getViewCreationRequests().add(viewCreationRequest);
        }

        return self;
    }

    public int computeDataSourceHeight(DataSource self) {
        if (self.getName() != null && (self.getName().equals("Camera") || self.getName().equals("Radar") || self.getName().equals("Sensor"))) {
            return self.getVolume() * 15;
        }
        return self.getVolume() * 11;
    }

}

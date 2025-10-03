/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeViewRequestChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo/redo for the ViewCreationRequest and ViewDeletionRequest changes.
 *
 * @author mcharfadi
 */
@Service
public class ViewRequestChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    public ViewRequestChangeRecorder(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            List<ViewCreationRequest> undoViewCreationRequests = new ArrayList<>();
            List<ViewDeletionRequest> undoViewDeletionRequests = new ArrayList<>();
            List<ViewCreationRequest> redoViewCreationRequests = new ArrayList<>(viewCreationRequests);
            List<ViewDeletionRequest> redoViewDeletionRequests = new ArrayList<>(viewDeletionRequests);

            viewDeletionRequests.forEach(viewDeletionRequest -> {
                var nodeId = viewDeletionRequest.getElementId();
                var node = this.diagramQueryService.findNodeById(previousDiagram, nodeId);
                if (node.isPresent()) {
                    var containmentKind = NodeContainmentKind.CHILD_NODE;
                    if (node.get().isBorderNode()) {
                        containmentKind = NodeContainmentKind.BORDER_NODE;
                    }

                    Optional<String> optionalParentId = Optional.empty();
                    var parentId = previousDiagram.getId();

                    for (Node currentNode : previousDiagram.getNodes()) {
                        optionalParentId = findParentId(currentNode, node.get().getId());
                    }
                    if (optionalParentId.isPresent()) {
                        parentId = optionalParentId.get();
                    }

                    var creationRequest = ViewCreationRequest.newViewCreationRequest()
                            .containmentKind(containmentKind)
                            .parentElementId(parentId)
                            .descriptionId(node.get().getDescriptionId())
                            .targetObjectId(node.get().getTargetObjectId())
                            .build();
                    undoViewCreationRequests.add(creationRequest);
                }
            });

            viewCreationRequests.forEach(viewCreationRequest -> {
                var nodeId = new NodeIdProvider().getNodeId(viewCreationRequest.getParentElementId(), viewCreationRequest.getDescriptionId(), viewCreationRequest.getContainmentKind(), viewCreationRequest.getTargetObjectId());
                var deletionRequest = ViewDeletionRequest.newViewDeletionRequest()
                        .elementId(nodeId)
                        .build();
                undoViewDeletionRequests.add(deletionRequest);
            });

            if (!undoViewCreationRequests.isEmpty() || !undoViewDeletionRequests.isEmpty()) {
                var nodeChange = new DiagramNodeViewRequestChange(diagramInput.id(), diagramInput.representationId(), undoViewCreationRequests, undoViewDeletionRequests, redoViewCreationRequests, redoViewDeletionRequests);
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id())
                        || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), List.of(nodeChange));
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).add(nodeChange);
                }

            }
        }
    }

    private Optional<String> findParentId(Node currentNode, String childId) {
        var parentNode = Stream.concat(currentNode.getChildNodes().stream(), currentNode.getBorderNodes().stream())
                .map(Node::getId)
                .filter(id -> id.equals(childId))
                .findFirst();

        if (parentNode.isEmpty()) {
            var childNodes = Stream.concat(currentNode.getBorderNodes().stream(), currentNode.getChildNodes().stream()).toList();
            for (Node node : childNodes) {
                return findParentId(node, childId);
            }
        }

        return parentNode;
    }
}

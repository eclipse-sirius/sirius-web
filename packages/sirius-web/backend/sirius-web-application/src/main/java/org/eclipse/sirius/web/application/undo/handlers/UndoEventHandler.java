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
package org.eclipse.sirius.web.application.undo.handlers;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramEventChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.LayoutDiagramRepresentionChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewCreationRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewDeleteRequestChange;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

import java.util.Map;
import java.util.Objects;

/**
 * Handler used to undo mutations.
 *
 * @author mcharfadi
 */
@Service
public class UndoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDiagramCreationService diagramCreationService;

    private IPayload payload;

    public UndoEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService, IDiagramCreationService diagramCreationService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = representationPersistenceService;
        this.diagramCreationService = diagramCreationService;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UndoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.payload = new ErrorPayload(input.id(), "Error ");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof UndoInput undoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(undoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                emfChangeDescription.applyAndReverse();
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                this.payload = new SuccessPayload(input.id());
                changeDescriptionSink.tryEmitNext(changeDescription);
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    if (change instanceof LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
                        changeDescription = handleLayoutPortalChange(payloadSink, editingContext, undoInput, layoutPortalRepresentionChange);
                    }
                    if (change instanceof AddPortalRepresentionChange addPortalRepresentionChange) {
                        changeDescription = handleAddPortalChange(payloadSink, editingContext, undoInput, addPortalRepresentionChange);
                    }
                    if (change instanceof RemovePortalRepresentionChange removePortalRepresentionChange) {
                        changeDescription = handleRemovePortalChange(payloadSink, editingContext, undoInput, removePortalRepresentionChange);
                    }
                    if (change instanceof LayoutDiagramRepresentionChange layoutDiagramRepresentionChange) {
                        changeDescription = handleLayoutDiagramChange(payloadSink, editingContext, undoInput, layoutDiagramRepresentionChange);
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        changeDescription = handleDiagramEventChange(payloadSink, editingContext, undoInput, diagramEventChange);
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        changeDescription = handleViewCreationRequestChange(payloadSink, editingContext, undoInput, viewCreationRequestChange);
                    }
                    if (change instanceof ViewDeleteRequestChange viewDeleteRequestChange) {
                        changeDescription = handleViewDeleteRequestChange(payloadSink, editingContext, undoInput, viewDeleteRequestChange);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private ChangeDescription handleViewDeleteRequestChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, ViewDeleteRequestChange viewDeleteRequestChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = viewDeleteRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            var nodeToRestore = viewDeleteRequestChange.deletedNode();
            var nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
            if (nodeToRestore.isBorderNode()) {
                nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
            }
            var parentId = diagramContext.getDiagram().getId();
            if (viewDeleteRequestChange.parentNode().isPresent()) {
                parentId = viewDeleteRequestChange.parentNode().get().getId();
            }
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .containmentKind(nodeContainmentKind)
                .descriptionId(nodeToRestore.getDescriptionId())
                .targetObjectId(nodeToRestore.getTargetObjectId())
                .parentElementId(parentId)
                .build();

            diagramContext.getViewCreationRequests().add(viewCreationRequest);
            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram.get());
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
            this.payload = new SuccessPayload(undoInput.id());
        }
        return changeDescription;
    }

    private ChangeDescription handleViewCreationRequestChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, ViewCreationRequestChange viewCreationRequestChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = viewCreationRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            viewCreationRequestChange.addedNodes().forEach(node -> {
                diagramContext.getViewDeletionRequests().add(ViewDeletionRequest.newViewDeletionRequest().elementId(node.getId()).build());
            });
            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram.get());
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
            this.payload = new SuccessPayload(undoInput.id());
        }
        return changeDescription;
    }

    private ChangeDescription handleDiagramEventChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, DiagramEventChange diagramEventChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = diagramEventChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);
        if (currentDiagram.isPresent()) {
            if (diagramEventChange.diagramEvent() instanceof HideDiagramElementEvent hideDiagramElementEvent) {
                var diagramContext = new DiagramContext(currentDiagram.get());
                diagramContext.getDiagramEvents().add(new HideDiagramElementEvent(hideDiagramElementEvent.getElementIds(), !hideDiagramElementEvent.hideElement()));
                var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
                this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram.get());
                changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
                this.payload = new SuccessPayload(undoInput.id());
            }
        }
        return changeDescription;
    }

    private ChangeDescription handleLayoutDiagramChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, LayoutDiagramRepresentionChange layoutDiagramRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = layoutDiagramRepresentionChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);

        //Should check if the new position is valid
        if (currentDiagram.isPresent()) {
            var previousLayout = layoutDiagramRepresentionChange.oldValue();
            var layoutData = new DiagramLayoutData(previousLayout, Map.of(), Map.of());
            var laidOutDiagram = Diagram.newDiagram(currentDiagram.get())
                    .layoutData(layoutData)
                    .build();
            this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram);
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
            this.payload = new SuccessPayload(undoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleLayoutPortalChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = layoutPortalRepresentionChange.representationId();
        var oldLayoutData = layoutPortalRepresentionChange.oldValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.layout(currentPortal.get(), oldLayoutData);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            this.payload = new SuccessPayload(undoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleAddPortalChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, AddPortalRepresentionChange addPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.removeView(currentPortal.get(), addPortalRepresentionChange.portalViewLayoutData().getPortalViewId());
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_REMOVAL.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            this.payload = new SuccessPayload(undoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleRemovePortalChange(One<IPayload> payloadSink, IEditingContext editingContext, UndoInput undoInput, RemovePortalRepresentionChange removePortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        var oldLayoutData = removePortalRepresentionChange.oldLayout();
        var oldPortalView = removePortalRepresentionChange.oldPortalView();

        if (currentPortal.isPresent()) {
            if (portalServices.referencesRepresentation(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getRepresentationId())) {
                this.payload = new ErrorPayload(undoInput.id(), "The representation is already included in the portal");
            } else {
                var optionalNewPortal = portalServices.addView(currentPortal.get(), oldPortalView.getRepresentationId(), oldLayoutData.getX(), oldLayoutData.getY(), oldLayoutData.getWidth(), oldLayoutData.getHeight());
                if (optionalNewPortal.isPresent() && currentPortal.get().getViews().stream().noneMatch(view -> view.getId().equals(oldPortalView.getId()))) {
                    changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), undoInput);
                    changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    this.payload = new SuccessPayload(undoInput.id());
                }
            }
        }

        return changeDescription;
    }

}

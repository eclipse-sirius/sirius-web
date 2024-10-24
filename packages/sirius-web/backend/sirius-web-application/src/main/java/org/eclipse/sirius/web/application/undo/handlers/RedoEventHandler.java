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
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.components.core.api.RedoInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

import java.util.Map;


/**
 * Handler used to redo mutations.
 *
 * @author mcharfadi
 */
@Service
public class RedoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDiagramCreationService diagramCreationService;

    private IPayload payload;

    public RedoEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService, IDiagramCreationService diagramCreationService) {
        this.representationSearchService = representationSearchService;
        this.representationPersistenceService = representationPersistenceService;
        this.diagramCreationService = diagramCreationService;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RedoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.payload = new ErrorPayload(input.id(), "Error ");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof RedoInput redoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(redoInput.mutationId());
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                this.payload = new SuccessPayload(input.id());
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    if (change instanceof LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
                        changeDescription = handleLayoutPortalChange(editingContext, redoInput, layoutPortalRepresentionChange);
                    }
                    if (change instanceof AddPortalRepresentionChange addPortalRepresentionChange) {
                        changeDescription = handleAddPortalChange(editingContext, redoInput, addPortalRepresentionChange);
                    }
                    if (change instanceof RemovePortalRepresentionChange removePortalRepresentionChange) {
                        changeDescription = handleRemovePortalChange(editingContext, redoInput, removePortalRepresentionChange);
                    }
                    if (change instanceof LayoutDiagramRepresentionChange layoutDiagramRepresentionChange) {
                        changeDescription = handleLayoutDiagramChange(editingContext, redoInput, layoutDiagramRepresentionChange);
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        changeDescription = handleDiagramEventChange(editingContext, redoInput, diagramEventChange);
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        changeDescription = handleViewCreationRequestChange(editingContext, redoInput, viewCreationRequestChange);
                    }
                    if (change instanceof ViewDeleteRequestChange viewDeleteRequestChange) {
                        changeDescription = handleViewDeleteRequestChange(editingContext, redoInput, viewDeleteRequestChange);
                    }
                }
            }

        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private ChangeDescription handleViewDeleteRequestChange(IEditingContext editingContext, RedoInput redoInput, ViewDeleteRequestChange viewDeleteRequestChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = viewDeleteRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getViewDeletionRequests().add(viewDeleteRequestChange.viewDeletionRequest());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
            this.payload = new SuccessPayload(redoInput.id());
        }
        return changeDescription;
    }

    private ChangeDescription handleViewCreationRequestChange(IEditingContext editingContext, RedoInput redoInput, ViewCreationRequestChange viewCreationRequestChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = viewCreationRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);

        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getViewCreationRequests().add(viewCreationRequestChange.viewCreationRequest());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
            this.payload = new SuccessPayload(redoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleDiagramEventChange(IEditingContext editingContext, RedoInput redoInput, DiagramEventChange diagramEventChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = diagramEventChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);

        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getDiagramEvents().add(diagramEventChange.diagramEvent());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
            this.payload = new SuccessPayload(redoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleLayoutDiagramChange(IEditingContext editingContext, RedoInput redoInput, LayoutDiagramRepresentionChange layoutDiagramRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = layoutDiagramRepresentionChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId.toString(), Diagram.class);

        //Should check if the new position is valid
        if (currentDiagram.isPresent()) {
            var newLayout = layoutDiagramRepresentionChange.newValue();
            var layoutData = new DiagramLayoutData(newLayout, Map.of(), Map.of());
            var laidOutDiagram = Diagram.newDiagram(currentDiagram.get())
                    .layoutData(layoutData)
                    .build();
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram);
            changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
            this.payload = new SuccessPayload(redoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleLayoutPortalChange(IEditingContext editingContext, RedoInput redoInput, LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = layoutPortalRepresentionChange.representationId();
        var newLayoutData = layoutPortalRepresentionChange.newValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.layout(currentPortal.get(), newLayoutData);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), redoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            this.payload = new SuccessPayload(redoInput.id());
        }

        return changeDescription;
    }

    private ChangeDescription handleAddPortalChange(IEditingContext editingContext, RedoInput redoInput, AddPortalRepresentionChange addPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);
        var addPortalViewInput = addPortalRepresentionChange.previousInput();

        if (currentPortal.isPresent()) {
            if (portalServices.referencesRepresentation(currentPortal.get(), addPortalRepresentionChange.previousInput().representationId())) {
                this.payload = new ErrorPayload(redoInput.id(), "The representation is already included in the portal");
            } else {
                var optionalNewPortal = portalServices.addView(currentPortal.get(), addPortalViewInput.viewRepresentationId(), addPortalViewInput.x(), addPortalViewInput.y(), addPortalViewInput.width(), addPortalViewInput.height());
                if (optionalNewPortal.isPresent()) {
                    changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), redoInput);
                    changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    this.payload = new SuccessPayload(redoInput.id());
                }
            }
        }

        return changeDescription;
    }

    private ChangeDescription handleRemovePortalChange(IEditingContext editingContext, RedoInput undoInput, RemovePortalRepresentionChange removePortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortalToSerialize = portalServices.removeView(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getId());
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_REMOVAL.name(), newPortalToSerialize.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortalToSerialize);
            this.payload = new SuccessPayload(undoInput.id());
        }
        return changeDescription;
    }

}

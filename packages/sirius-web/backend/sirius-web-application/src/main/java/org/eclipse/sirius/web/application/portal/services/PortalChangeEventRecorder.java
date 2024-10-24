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
package org.eclipse.sirius.web.application.portal.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalViewLayoutDataInput;
import org.eclipse.sirius.components.collaborative.portals.dto.RemovePortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEventRecorder;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Portal change event recorder.
 *
 * @author mcharfadi
 */
// TODO: to remove
public class PortalChangeEventRecorder implements IRepresentationChangeEventRecorder {

    private final IRepresentationSearchService representationSearchService;

    public PortalChangeEventRecorder(IRepresentationSearchService representationSearchService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof IPortalInput;
    }

    @Override
    public void recordChanges(IEditingContext editingContext, IInput input, IRepresentation previousRepresentation, IRepresentation newRepresentation) {
//        var listChanges = new ArrayList<IRepresentationChangeEvent>();
//        if (editingContext instanceof EditingContext siriusEditingContext && previousRepresentation instanceof Portal previousPortal && newRepresentation instanceof Portal newPortal) {
//            if (input instanceof LayoutPortalInput layoutPortalInput) {
//                listChanges.addAll(this.handleLayoutPortalInput(previousPortal, layoutPortalInput));
//            }
//            if (input instanceof AddPortalViewInput addPortalViewInput) {
//                listChanges.addAll(this.handleAddPortalViewInput(editingContext, previousPortal, addPortalViewInput));
//            }
//            if (input instanceof RemovePortalViewInput removePortalViewInput) {
//                listChanges.addAll(this.handleRemovePortalViewInput(previousPortal, removePortalViewInput));
//            }
//            siriusEditingContext.getRepresentationChangesDescription().computeIfAbsent(input.id().toString(), key -> new ArrayList<>()).addAll(listChanges);
//        }
    }

    private List<IRepresentationChangeEvent> handleLayoutPortalInput(Portal currentPortal, LayoutPortalInput layoutPortalInput) {
        var currentPortalPortalLayoutData = currentPortal.getLayoutData();
        var newPortalPortalLayoutData = layoutPortalInput.layoutData().stream().map(this::convert).toList();
        var portalLayoutChange = new LayoutPortalRepresentationChange(layoutPortalInput.representationId(), currentPortalPortalLayoutData, newPortalPortalLayoutData);
        return List.of(portalLayoutChange);
    }

    private List<IRepresentationChangeEvent> handleAddPortalViewInput(IEditingContext editingContext, Portal currentPortal, AddPortalViewInput addPortalViewInput) {
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var currentPortalPortalLayoutData = currentPortal.getLayoutData();
        var newPortalPortalLayoutData = PortalViewLayoutData.newPortalViewLayoutData(portalServices.getPortalViewId(currentPortal, addPortalViewInput.viewRepresentationId()))
                .x(addPortalViewInput.x())
                .y(addPortalViewInput.y())
                .width(addPortalViewInput.width())
                .height(addPortalViewInput.height())
                .build();
        var portalLayoutChange = new AddPortalRepresentationChange(addPortalViewInput.representationId(), currentPortalPortalLayoutData, addPortalViewInput.viewRepresentationId(), newPortalPortalLayoutData);
        return List.of(portalLayoutChange);
    }

    private List<IRepresentationChangeEvent> handleRemovePortalViewInput(Portal currentPortal, RemovePortalViewInput removePortalViewInput) {
        var currentLayoutData = currentPortal.getLayoutData().stream().filter(layoutData -> layoutData.getPortalViewId().equals(removePortalViewInput.portalViewId())).findAny();
        var currentPortalView = currentPortal.getViews().stream().filter(view -> view.getId().equals(removePortalViewInput.portalViewId())).findAny();
        if (currentLayoutData.isPresent() && currentPortalView.isPresent()) {
            var portalLayoutChange = new RemovePortalRepresentationChange(removePortalViewInput.representationId(), currentLayoutData.get(), currentPortalView.get());
            return List.of(portalLayoutChange);
        }
        return List.of();
    }

    private PortalViewLayoutData convert(PortalViewLayoutDataInput input) {
        return PortalViewLayoutData.newPortalViewLayoutData(input.portalViewId())
                .x(input.x())
                .y(input.y())
                .width(input.width())
                .height(input.height())
                .build();
    }

}

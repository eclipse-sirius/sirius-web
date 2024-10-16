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
package org.eclipse.sirius.components.collaborative.portals.changes;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalViewLayoutDataInput;
import org.eclipse.sirius.components.collaborative.portals.dto.RemovePortalViewInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEventRecorder;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Portal change event recorder.
 *
 * @author mcharfadi
 */
@Service
public class PortalChangeEventRecorder implements IRepresentationChangeEventRecorder {

    private final IRepresentationSearchService representationSearchService;

    public PortalChangeEventRecorder(IRepresentationSearchService representationSearchService) {
        this.representationSearchService = representationSearchService;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof IPortalInput;
    }

    public List<IRepresentationChangeEvent> getChanges(IEditingContext editingContext, IInput input) {
        var listChanges = new ArrayList<IRepresentationChangeEvent>();
        if (input instanceof IPortalInput portalInput) {
            var currentPortal = this.representationSearchService.findById(editingContext, portalInput.representationId(), Portal.class);
            if (currentPortal.isPresent()) {
                if (portalInput instanceof LayoutPortalInput layoutPortalInput) {
                    listChanges.addAll(handleLayoutPortalInput(currentPortal.get(), layoutPortalInput));
                }
                if (portalInput instanceof AddPortalViewInput addPortalViewInput) {
                    listChanges.addAll(handleAddPortalViewInput(currentPortal.get(), addPortalViewInput));
                }
                if (portalInput instanceof RemovePortalViewInput removePortalViewInput) {
                    listChanges.addAll(handleRemovePortalViewInput(currentPortal.get(), removePortalViewInput));
                }
            }
        }
        return listChanges;
    }

    private List<IRepresentationChangeEvent> handleLayoutPortalInput(Portal currentPortal, LayoutPortalInput layoutPortalInput) {
        var currentPortalPortalLayoutData = currentPortal.getLayoutData();
        var newPortalPortalLayoutData = layoutPortalInput.layoutData().stream().map(this::convert).toList();
        var portalLayoutChange = new LayoutPortalRepresentionChange(UUID.fromString(layoutPortalInput.representationId()), currentPortalPortalLayoutData, newPortalPortalLayoutData);
        return List.of(portalLayoutChange);
    }

    private List<IRepresentationChangeEvent> handleAddPortalViewInput(Portal currentPortal, AddPortalViewInput addPortalViewInput) {
        var currentPortalPortalLayoutData = currentPortal.getLayoutData();
        var newPortalPortalLayoutData = PortalViewLayoutData.newPortalViewLayoutData(this.getPortalViewId(currentPortal, addPortalViewInput.viewRepresentationId()))
                .x(addPortalViewInput.x())
                .y(addPortalViewInput.y())
                .width(addPortalViewInput.width())
                .height(addPortalViewInput.height())
                .build();
        var portalLayoutChange = new AddPortalRepresentionChange(UUID.fromString(addPortalViewInput.representationId()), currentPortalPortalLayoutData, newPortalPortalLayoutData);
        return List.of(portalLayoutChange);
    }

    private List<IRepresentationChangeEvent> handleRemovePortalViewInput(Portal currentPortal, RemovePortalViewInput removePortalViewInput) {
        var currentLayoutData = currentPortal.getLayoutData().stream().filter(layoutData -> layoutData.getPortalViewId().equals(removePortalViewInput.portalViewId())).findAny();
        var currentPortalView = currentPortal.getViews().stream().filter(view -> view.getId().equals(removePortalViewInput.portalViewId())).findAny();
        if (currentLayoutData.isPresent() && currentPortalView.isPresent()) {
            var portalLayoutChange = new RemovePortalRepresentionChange(UUID.fromString(removePortalViewInput.representationId()), currentLayoutData.get(), currentPortalView.get());
            return List.of(portalLayoutChange);
        }
        return List.of();
    }

    //TODO use method from PortalServices
    private String getPortalViewId(Portal parentPortal, String viewRepresentationId) {
        String rawId = parentPortal.getId() + viewRepresentationId;
        return UUID.nameUUIDFromBytes(rawId.getBytes()).toString();
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

/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;

/**
 * Common services to manipulate portals.
 *
 * @author pcdavid
 */
public class PortalServices {
    private final IRepresentationSearchService representationSearchService;

    private final IEditingContext editingContext;

    public PortalServices(IRepresentationSearchService representationSearchService, IEditingContext editingContext) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
    }

    public boolean referencesRepresentation(Portal portal, String representationId) {
        if (portal.getViews().stream().anyMatch(portalView -> Objects.equals(portalView.getRepresentationId(), representationId))) {
            return true;
        } else {
            return portal.getViews().stream().flatMap(view -> this.representationSearchService.findById(this.editingContext, view.getRepresentationId(), Portal.class).stream())
                    .anyMatch(subPortal -> subPortal.getId().equals(representationId) || this.referencesRepresentation(subPortal, representationId));
        }
    }

    /**
     * Removes all the references to a given representation from a portal. The layout of the views which are left is not
     * affected, which can lead to "holes". Note that only <em>direct</em> references are considered, i.e. sub-portals
     * which may reference the representation must be updated too.
     *
     * @param portal
     *            the original portal.
     * @param representationId
     *            the identifier of the representation which should be removed.
     * @return a new portal which no longer references the specified representation.
     */
    public Portal removeRepresentation(Portal portal, String representationId) {
        // Keep only the views which do *not* refer to the representation to remove.
        List<PortalView> newViews = portal.getViews().stream().filter(portalView -> !Objects.equals(portalView.getRepresentationId(), representationId)).toList();

        // Keep only the layout info for the new views.
        List<String> newViewsIds = newViews.stream().map(PortalView::getId).toList();
        List<PortalViewLayoutData> newLayouts = portal.getLayoutData().stream().filter(viewLayoutData -> newViewsIds.contains(viewLayoutData.getPortalViewId())).toList();

        return Portal.newPortal(portal).views(newViews).layoutData(newLayouts).build();
    }

    public Optional<Portal> addView(Portal portal, String viewRepresentationId, int x, int y, int width, int height) {

        var newViews = new ArrayList<>(portal.getViews());
        newViews.add(PortalView.newPortalView(this.getPortalViewId(portal, viewRepresentationId)).representationId(viewRepresentationId).build());

        var newLayoutData = new ArrayList<>(portal.getLayoutData());
        newLayoutData.add(PortalViewLayoutData.newPortalViewLayoutData(this.getPortalViewId(portal, viewRepresentationId)).x(x).y(y).width(width).height(height).build());

        var newPortal = Portal.newPortal(portal).views(newViews).layoutData(newLayoutData).build();
        if (this.referencesRepresentation(newPortal, newPortal.getId())) {
            return Optional.empty();
        } else {
            return Optional.of(newPortal);
        }
    }

    public Portal removeView(Portal portal, String viewId) {
        // Keep the other views
        List<PortalView> newViews = portal.getViews().stream().filter(portalView -> !Objects.equals(portalView.getId(), viewId)).toList();

        // Keep only the layout info for the new views.
        List<String> newViewsIds = newViews.stream().map(PortalView::getId).toList();
        List<PortalViewLayoutData> newLayouts = portal.getLayoutData().stream().filter(viewLayoutData -> newViewsIds.contains(viewLayoutData.getPortalViewId())).toList();

        return Portal.newPortal(portal).views(newViews).layoutData(newLayouts).build();
    }

    public Portal layout(Portal portal, List<PortalViewLayoutData> layoutData) {
        List<String> valueViewIds = portal.getViews().stream().map(PortalView::getId).toList();
        Map<String, PortalViewLayoutData> updatedLayouts = layoutData.stream().collect(Collectors.toMap(PortalViewLayoutData::getPortalViewId, Function.identity()));
        List<PortalViewLayoutData> newLayoutData = portal.getLayoutData().stream()
                .map(portalViewLayoutData -> updatedLayouts.getOrDefault(portalViewLayoutData.getPortalViewId(), portalViewLayoutData))
                .filter(portalViewLayoutData -> valueViewIds.contains(portalViewLayoutData.getPortalViewId())).collect(Collectors.toCollection(ArrayList::new));
        return Portal.newPortal(portal).layoutData(newLayoutData).build();
    }

    private String getPortalViewId(Portal parentPortal, String viewRepresentationId) {
        String rawId = parentPortal.getId() + viewRepresentationId;
        return UUID.nameUUIDFromBytes(rawId.getBytes()).toString();
    }
}

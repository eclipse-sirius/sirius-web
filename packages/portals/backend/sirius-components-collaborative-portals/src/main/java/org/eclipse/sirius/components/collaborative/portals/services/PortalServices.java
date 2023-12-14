/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;

/**
 * Common services to manipulate portals.
 *
 * @author pcdavid
 */
public class PortalServices {

    public boolean referencesRepresentation(Portal portal, String representationId) {
        return portal.getViews().stream().anyMatch(portalView -> Objects.equals(portalView.getRepresentationId(), representationId));
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
        List<PortalView> newViews = portal.getViews().stream()
                .filter(portalView -> !Objects.equals(portalView.getRepresentationId(), representationId))
                .toList();

        // Keep only the layout info for the new views.
        List<String> newViewsIds = newViews.stream().map(PortalView::getId).toList();
        List<PortalViewLayoutData> newLayouts = portal.getLayoutData().stream()
                .filter(viewLayoutData -> newViewsIds.contains(viewLayoutData.getPortalViewId()))
                .toList();

        return Portal.newPortal(portal).views(newViews).layoutData(newLayouts).build();
    }

    public Portal addView(Portal portal, String viewRepresentationId) {
        return this.addView(portal, viewRepresentationId, 0, 0, 3, 3);
    }

    public Portal addView(Portal portal, String viewRepresentationId, int x, int y, int width, int height) {
        var newPortalViewId = this.getPortalViewId(portal, viewRepresentationId);

        var newViews = new ArrayList<>(portal.getViews());
        newViews.add(PortalView.newPortalView(newPortalViewId).representationId(viewRepresentationId).build());

        var newLayoutData = new ArrayList<>(portal.getLayoutData());
        newLayoutData.add(PortalViewLayoutData.newPortalViewLayoutData(newPortalViewId).x(x).y(y).width(width).height(height).build());

        return Portal.newPortal(portal)
                     .views(newViews)
                     .layoutData(newLayoutData)
                     .build();
    }

    public Portal removeView(Portal portal, String viewId) {
        // Keep the other views
        List<PortalView> newViews = portal.getViews().stream()
                .filter(portalView -> !Objects.equals(portalView.getId(), viewId))
                .toList();

        // Keep only the layout info for the new views.
        List<String> newViewsIds = newViews.stream().map(PortalView::getId).toList();
        List<PortalViewLayoutData> newLayouts = portal.getLayoutData().stream()
                .filter(viewLayoutData -> newViewsIds.contains(viewLayoutData.getPortalViewId()))
                .toList();

        return Portal.newPortal(portal).views(newViews).layoutData(newLayouts).build();
    }

    public Portal layout(Portal portal, List<PortalViewLayoutData> layoutData) {
        List<String> valueViewIds = portal.getViews().stream().map(PortalView::getId).toList();
        Map<String, PortalViewLayoutData> updatedLayouts = layoutData.stream()
                .collect(Collectors.toMap(PortalViewLayoutData::getPortalViewId, Function.identity()));
        List<PortalViewLayoutData> newLayoutData = portal.getLayoutData().stream()
                .map(portalViewLayoutData -> updatedLayouts.getOrDefault(portalViewLayoutData.getPortalViewId(), portalViewLayoutData))
                .filter(portalViewLayoutData -> valueViewIds.contains(portalViewLayoutData.getPortalViewId()))
                .toList();
        return Portal.newPortal(portal).layoutData(newLayoutData).build();
    }

    private String getPortalViewId(Portal parentPortal, String viewRepresentationId) {
        String rawId = parentPortal.getId() + viewRepresentationId;
        return UUID.nameUUIDFromBytes(rawId.getBytes()).toString();
    }
}

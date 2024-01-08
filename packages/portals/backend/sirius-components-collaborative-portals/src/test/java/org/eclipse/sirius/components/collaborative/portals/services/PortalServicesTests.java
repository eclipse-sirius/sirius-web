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
package org.eclipse.sirius.components.collaborative.portals.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PortalServices}.
 *
 * @author pcdavid
 */
public class PortalServicesTests {

    private static final IEditingContext NOOP_EDITING_CONTEXT = new IEditingContext.NoOp();

    private static final IRepresentationSearchService NOOP_SEARCH_SERVICE = new IRepresentationSearchService() {
        @Override
        public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
            return Optional.empty();
        }
    };

    private static final String PORTAL_ID = "portal";
    private static final String PORTAL_DESCRIPTION_ID = "portalDescription";
    private static final String TARGET_OBJECT_ID = "targetObject";

    private PortalServices services;

    @BeforeEach
    public void setup() {
        this.services = new PortalServices(NOOP_SEARCH_SERVICE, NOOP_EDITING_CONTEXT);
    }

    @Test
    public void testReferencesRepresentationOnEmptyPortal() {
        Portal emptyPortal = Portal.newPortal(PORTAL_ID)
                .descriptionId(PORTAL_DESCRIPTION_ID)
                .label("Empty Portal")
                .targetObjectId(TARGET_OBJECT_ID)
                .build();
        assertThat(this.services.referencesRepresentation(emptyPortal, null)).isFalse();
        assertThat(this.services.referencesRepresentation(emptyPortal, "")).isFalse();
        assertThat(this.services.referencesRepresentation(emptyPortal, UUID.randomUUID().toString())).isFalse();
    }

    @Test
    public void testReferencesRepresentationOnSimplePortal() {
        var representationId = "someRepresentation";
        Portal simplePortal = Portal.newPortal(PORTAL_ID)
                .descriptionId(PORTAL_DESCRIPTION_ID)
                .label("Simple Portal")
                .targetObjectId(TARGET_OBJECT_ID)
                .views(List.of(PortalView.newPortalView("aView").representationId(representationId).build()))
                .build();
        assertThat(this.services.referencesRepresentation(simplePortal, null)).isFalse();
        assertThat(this.services.referencesRepresentation(simplePortal, "")).isFalse();
        assertThat(this.services.referencesRepresentation(simplePortal, UUID.randomUUID().toString())).isFalse();
        assertThat(this.services.referencesRepresentation(simplePortal, representationId)).isTrue();
    }

    @Test
    public void testRemoveUnknownRepresentationHasNoEffect() {
        Portal portal = this.createSamplePortal(3);
        Portal newPortal = this.services.removeRepresentation(portal, "representation-unknown");
        assertThat(newPortal.getViews()).hasSameSizeAs(portal.getViews());
        assertThat(newPortal.getLayoutData()).hasSameSizeAs(portal.getLayoutData());
    }

    @Test
    public void testRemoveReferencedRepresentationRemovesFromBothViewsAndLayoutData() {
        Portal portal = this.createSamplePortal(3);
        Portal newPortal = this.services.removeRepresentation(portal, "representation-1");
        assertThat(newPortal.getViews()).hasSize(portal.getViews().size() - 1);
        assertThat(newPortal.getLayoutData()).hasSize(portal.getLayoutData().size() - 1);
    }

    @Test
    public void testRemovePortalView() {
        Portal portal = this.createSamplePortal(3);
        assertThat(portal.getViews()).hasSize(3);
        Portal newPortal = this.services.removeView(portal, "view-0");
        assertThat(newPortal.getViews()).hasSize(2);
        assertThat(newPortal.getLayoutData()).hasSize(2);
    }

    @Test
    public void testChangeLayoutPortal() {
        String viewId = "view-0";
        Portal portal = this.createSamplePortal(1);
        Portal newPortal = this.services.layout(portal, List.of(PortalViewLayoutData.newPortalViewLayoutData(viewId).x(2).y(2).width(4).height(4).build()));
        var optionalLayout = newPortal.getLayoutData().stream().filter(layoutdata -> layoutdata.getPortalViewId().equals(viewId)).findFirst();
        assertThat(optionalLayout).isPresent();
        assertThat(optionalLayout.get()).extracting(PortalViewLayoutData::getX).isEqualTo(2);
        assertThat(optionalLayout.get()).extracting(PortalViewLayoutData::getY).isEqualTo(2);
        assertThat(optionalLayout.get()).extracting(PortalViewLayoutData::getWidth).isEqualTo(4);
        assertThat(optionalLayout.get()).extracting(PortalViewLayoutData::getHeight).isEqualTo(4);
    }

    @Test
    public void testPreventDirectLoop() {
        Portal portal = Portal.newPortal(PORTAL_ID).descriptionId(PORTAL_DESCRIPTION_ID).label(PORTAL_ID).targetObjectId(TARGET_OBJECT_ID).build();
        IRepresentationSearchService mockSearchService = new IRepresentationSearchService() {
            @Override
            public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
                return Optional.of(representationClass.cast(portal));
            }
        };
        Optional<Portal> newPortal = new PortalServices(mockSearchService, NOOP_EDITING_CONTEXT).addView(portal, PORTAL_ID, 0, 0, 0, 0);
        assertThat(newPortal).isEmpty();
    }

    @Test
    public void testPreventIndirectLoop() {
        Portal portal1 = Portal.newPortal(PORTAL_ID + "_1").descriptionId(PORTAL_DESCRIPTION_ID).label(PORTAL_ID + "_1").targetObjectId(TARGET_OBJECT_ID).build();
        Portal portal2 = Portal.newPortal(PORTAL_ID + "_2").descriptionId(PORTAL_DESCRIPTION_ID).label(PORTAL_ID + "_2").targetObjectId(TARGET_OBJECT_ID).build();

        Collection<Portal> portalsRepository = new ArrayList<>();
        portalsRepository.add(portal1);
        portalsRepository.add(portal2);

        IRepresentationSearchService mockSearchService = new IRepresentationSearchService() {
            @Override
            public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
                return portalsRepository.stream().filter(portal -> portal.getId().equals(representationId)).findFirst().map(representationClass::cast);
            }
        };

        // Add P2 inside P1: should work
        Optional<Portal> portal1WithPortal2 = new PortalServices(mockSearchService, NOOP_EDITING_CONTEXT).addView(portal1, portal2.getId(), 0, 0, 0, 0);
        assertThat(portal1WithPortal2).isNotEmpty();
        assertThat(portal1WithPortal2.get().getViews()).hasSize(1);

        // Update the mock repo with the updated P1
        portalsRepository.remove(portal1);
        portalsRepository.add(portal1WithPortal2.get());

        // Try to add (the new) P1 inside P2: should be forbidden
        Optional<Portal> portal2WithPortal1 = new PortalServices(mockSearchService, NOOP_EDITING_CONTEXT).addView(portal2, portal1WithPortal2.get().getId(), 0, 0, 0, 0);
        assertThat(portal2WithPortal1).isEmpty();
    }

    private Portal createSamplePortal(int nbViews) {
        var views = IntStream.range(0, nbViews).mapToObj(index -> PortalView.newPortalView("view-" + index).representationId("representation-" + index).build()).toList();
        var layoutData = IntStream.range(0, nbViews).mapToObj(index -> PortalViewLayoutData.newPortalViewLayoutData("view-" + index).x(index).build()).toList();
        Portal portal = Portal.newPortal(PORTAL_ID)
                .descriptionId(PORTAL_DESCRIPTION_ID)
                .label("Portal")
                .targetObjectId(TARGET_OBJECT_ID)
                .views(views)
                .layoutData(layoutData)
                .build();
        return portal;
    }

}

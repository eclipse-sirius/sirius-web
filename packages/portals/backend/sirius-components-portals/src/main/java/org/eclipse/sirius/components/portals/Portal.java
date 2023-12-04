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
package org.eclipse.sirius.components.portals;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * The root concept of a Portal representation.
 *
 * @author pcdavid
 */
@Immutable
public final class Portal implements ISemanticRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Portal";

    private String id;

    private String kind;

    private String descriptionId;

    private String label;

    private String targetObjectId;

    private List<PortalView> views;

    private List<PortalViewLayoutData> layoutData;

    private Portal() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public List<PortalView> getViews() {
        return this.views;
    }

    public List<PortalViewLayoutData> getLayoutData() {
        return this.layoutData;
    }

    public static Builder newPortal(String id) {
        return new Builder(id);
    }

    public static Builder newPortal(Portal portal) {
        return Portal.newPortal(portal.getId())
                .descriptionId(portal.getDescriptionId())
                .label(portal.getLabel())
                .targetObjectId(portal.getTargetObjectId())
                .views(portal.getViews())
                .layoutData(portal.getLayoutData());
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label);
    }

    /**
     * The builder used to create the portal.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String descriptionId;

        private String label;

        private String targetObjectId;

        private List<PortalView> views = List.of();

        private List<PortalViewLayoutData> layoutData = List.of();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder views(List<PortalView> views) {
            this.views = Objects.requireNonNull(views);
            return this;
        }

        public Builder layoutData(List<PortalViewLayoutData> layoutData) {
            this.layoutData = Objects.requireNonNull(layoutData);
            return this;
        }

        public Portal build() {
            Portal portal = new Portal();
            portal.id = Objects.requireNonNull(this.id);
            portal.kind = Objects.requireNonNull(this.kind);
            portal.descriptionId = Objects.requireNonNull(this.descriptionId);
            portal.label = Objects.requireNonNull(this.label);
            portal.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            portal.views = Objects.requireNonNull(this.views);
            portal.layoutData = Objects.requireNonNull(this.layoutData);
            return portal;
        }
    }
}

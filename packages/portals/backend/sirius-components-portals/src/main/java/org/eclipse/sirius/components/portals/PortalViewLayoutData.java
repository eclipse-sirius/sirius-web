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
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Represents the layout data for a single {@link PortalView}.
 *
 * @author pcdavid
 */
@Immutable
public final class PortalViewLayoutData {

    private String portalViewId;

    private int x;

    private int y;

    private int width;

    private int height;

    private PortalViewLayoutData() {
        // Prevent instantiation
    }

    public String getPortalViewId() {
        return this.portalViewId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'portalViewId: {1}, x: {2}, y: {3}, width: {4}, height: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.portalViewId, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public static Builder newPortalViewLayoutData(String portalViewId) {
        return new Builder(Objects.requireNonNull(portalViewId));
    }

    /**
     * Builder used to create the PortalViewLayout.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String portalViewId;

        private int x;

        private int y;

        private int width;

        private int height;

        private Builder(String portalViewId) {
            this.portalViewId = Objects.requireNonNull(portalViewId);
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public PortalViewLayoutData build() {
            PortalViewLayoutData portalViewLayoutData = new PortalViewLayoutData();
            portalViewLayoutData.portalViewId = this.portalViewId;
            portalViewLayoutData.x = this.x;
            portalViewLayoutData.y = this.y;
            portalViewLayoutData.width = this.width;
            portalViewLayoutData.height = this.height;
            return portalViewLayoutData;
        }
    }
}

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
package org.eclipse.sirius.components.portals;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Represents a view for a representation embedded inside a Portal.
 *
 * @author pcdavid
 */
@Immutable
public final class PortalView {
    private String id;

    private String representationId;

    private PortalView() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getRepresentationId() {
        return this.representationId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, representationId: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.getRepresentationId());
    }

    public static Builder newPortalView(PortalView portalView) {
        return new Builder(portalView.id).representationId(portalView.getRepresentationId());
    }

    public static Builder newPortalView(String id) {
        return new Builder(id);
    }

    /**
     * Builder used to create the PortalView.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String representationId;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder id(String id) {
            this.id = Objects.requireNonNull(id);
            return this;
        }

        public Builder representationId(String representationId) {
            this.representationId = Objects.requireNonNull(representationId);
            return this;
        }

        public PortalView build() {
            PortalView portalView = new PortalView();
            portalView.id = Objects.requireNonNull(this.id);
            portalView.representationId = Objects.requireNonNull(this.representationId);
            return portalView;
        }
    }

}

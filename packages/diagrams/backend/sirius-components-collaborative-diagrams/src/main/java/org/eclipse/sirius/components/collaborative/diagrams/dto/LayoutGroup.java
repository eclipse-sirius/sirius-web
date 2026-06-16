/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.Objects;

/**
 * A layout group, contains the identifier of the group and the list of its node IDs.
 *
 * @author ocailleau
 */
public record LayoutGroup(String id, List<String> nodeIds, LayoutConfiguration layoutConfiguration) {

    public LayoutGroup {
        Objects.requireNonNull(id);
        Objects.requireNonNull(nodeIds);
        Objects.requireNonNull(layoutConfiguration);
    }

    public static Builder newLayoutGroup(String id) {
        return new Builder(id);
    }
    /**
     * The builder used to create a layoutGroup.
     *
     * @author ocailleau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private final String id;

        private List<String> nodeIds;

        private LayoutConfiguration layoutConfiguration;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder nodeIds(List<String> nodeIds) {
            this.nodeIds = Objects.requireNonNull(nodeIds);
            return this;
        }

        public Builder layoutConfiguration(LayoutConfiguration layoutConfiguration) {
            this.layoutConfiguration = Objects.requireNonNull(layoutConfiguration);
            return this;
        }

        public LayoutGroup build() {
            return new LayoutGroup(this.id, this.nodeIds, this.layoutConfiguration);
        }
    }
}
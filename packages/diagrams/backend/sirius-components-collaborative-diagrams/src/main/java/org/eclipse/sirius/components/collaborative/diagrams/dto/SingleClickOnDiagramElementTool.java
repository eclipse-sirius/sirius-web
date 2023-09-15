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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;

/**
 * A tool triggered by a single click in the palette on a diagram element.
 *
 * @author mcharfadi
 */
public record SingleClickOnDiagramElementTool(String id, String label, List<String> iconURL, List<IDiagramElementDescription> targetDescriptions, String selectionDescriptionId,
                                              boolean appliesToDiagramRoot) implements ITool {

    public SingleClickOnDiagramElementTool {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURL);
        Objects.requireNonNull(targetDescriptions);
    }

    public static Builder newSingleClickOnDiagramElementTool(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a tool.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private List<IDiagramElementDescription> targetDescriptions;

        private String selectionDescriptionId;

        private boolean appliesToDiagramRoot;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetDescriptions(List<IDiagramElementDescription> targetDescriptions) {
            this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
            return this;
        }

        public Builder appliesToDiagramRoot(boolean appliesToDiagramRoot) {
            this.appliesToDiagramRoot = appliesToDiagramRoot;
            return this;
        }

        public Builder selectionDescriptionId(String selectionDescriptionId) {
            this.selectionDescriptionId = selectionDescriptionId;
            return this;
        }

        public SingleClickOnDiagramElementTool build() {
            return new SingleClickOnDiagramElementTool(this.id, this.label, this.iconURL, this.targetDescriptions, this.selectionDescriptionId, this.appliesToDiagramRoot);
        }
    }

}

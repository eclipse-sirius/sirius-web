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

/**
 * A tool triggered by a single click on two diagram elements thanks to the palette.
 *
 * @author mcharfadi
 */
public record SingleClickOnTwoDiagramElementsTool(String id, String label, List<String> iconURL, List<SingleClickOnTwoDiagramElementsCandidate> candidates) implements ITool {

    public SingleClickOnTwoDiagramElementsTool {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURL);
        Objects.requireNonNull(candidates);
    }

    public static Builder newSingleClickOnTwoDiagramElementsTool(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a tool.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private List<SingleClickOnTwoDiagramElementsCandidate> candidates;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public SingleClickOnTwoDiagramElementsTool.Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public SingleClickOnTwoDiagramElementsTool.Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public SingleClickOnTwoDiagramElementsTool.Builder candidates(List<SingleClickOnTwoDiagramElementsCandidate> candidates) {
            this.candidates = Objects.requireNonNull(candidates);
            return this;
        }

        public SingleClickOnTwoDiagramElementsTool build() {
            return new SingleClickOnTwoDiagramElementsTool(this.id, this.label, this.iconURL, this.candidates);
        }
    }

}

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

import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * An candidate containing a valid couple of sources/targets.
 *
 * @author mcharfadi
 */
public record SingleClickOnTwoDiagramElementsCandidate(List<NodeDescription> sources, List<NodeDescription> targets) {

    public SingleClickOnTwoDiagramElementsCandidate {
        Objects.requireNonNull(sources);
        Objects.requireNonNull(targets);
    }

    public static Builder newSingleClickOnTwoDiagramElementsCandidate() {
        return new Builder();
    }

    /**
     * The builder used to create a candidate.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {
        private List<NodeDescription> sources;

        private List<NodeDescription> targets;

        private Builder() {
        }

        public SingleClickOnTwoDiagramElementsCandidate.Builder sources(List<NodeDescription> sources) {
            this.sources = sources;
            return this;
        }

        public SingleClickOnTwoDiagramElementsCandidate.Builder targets(List<NodeDescription> targets) {
            this.targets = targets;
            return this;
        }

        public SingleClickOnTwoDiagramElementsCandidate build() {
            return new SingleClickOnTwoDiagramElementsCandidate(this.sources, this.targets);
        }
    }

}

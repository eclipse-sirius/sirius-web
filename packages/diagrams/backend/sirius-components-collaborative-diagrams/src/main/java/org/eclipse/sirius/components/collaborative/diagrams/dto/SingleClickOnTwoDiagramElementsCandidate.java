/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * An candidate containing a valid couple of sources/targets.
 *
 * @author mcharfadi
 */
public record SingleClickOnTwoDiagramElementsCandidate(List<IDiagramElementDescription> sources, List<IDiagramElementDescription> targets) {

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
        private List<IDiagramElementDescription> sources;

        private List<IDiagramElementDescription> targets;

        private Builder() {
        }

        public SingleClickOnTwoDiagramElementsCandidate.Builder sources(List<IDiagramElementDescription> sources) {
            this.sources = sources;
            return this;
        }

        public SingleClickOnTwoDiagramElementsCandidate.Builder targets(List<IDiagramElementDescription> targets) {
            this.targets = targets;
            return this;
        }

        public SingleClickOnTwoDiagramElementsCandidate build() {
            return new SingleClickOnTwoDiagramElementsCandidate(this.sources, this.targets);
        }
    }

}

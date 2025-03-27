/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tools;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;

/**
 * An candidate containing a valid couple of sources/targets.
 *
 * @author hmarchadour
 */
@Immutable
public final class SingleClickOnTwoDiagramElementsCandidate {

    private List<IDiagramElementDescription> sources;

    private List<IDiagramElementDescription> targets;

    private SingleClickOnTwoDiagramElementsCandidate() {
        // Prevent instantiation
    }

    public List<IDiagramElementDescription> getSources() {
        return this.sources;
    }

    public List<IDiagramElementDescription> getTargets() {
        return this.targets;
    }

    @Override
    public String toString() {
        String pattern = "{0} ";
        return MessageFormat.format(pattern, this.getClass().getSimpleName());
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
    public static final class Builder {
        private List<IDiagramElementDescription> sources;

        private List<IDiagramElementDescription> targets;

        private Builder() {
        }

        public Builder sources(List<IDiagramElementDescription> sources) {
            this.sources = sources;
            return this;
        }

        public Builder targets(List<IDiagramElementDescription> targets) {
            this.targets = targets;
            return this;
        }

        public SingleClickOnTwoDiagramElementsCandidate build() {
            SingleClickOnTwoDiagramElementsCandidate candidate = new SingleClickOnTwoDiagramElementsCandidate();
            candidate.sources = Objects.requireNonNull(this.sources);
            candidate.targets = Objects.requireNonNull(this.targets);
            return candidate;
        }
    }
}

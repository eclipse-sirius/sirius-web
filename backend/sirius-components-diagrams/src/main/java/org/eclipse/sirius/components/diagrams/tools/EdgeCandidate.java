/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * An edge candidate contains a valid couple of sources/targets.
 *
 * @author hmarchadour
 */
@Immutable
public final class EdgeCandidate {

    private List<NodeDescription> sources;

    private List<NodeDescription> targets;

    private EdgeCandidate() {
        // Prevent instantiation
    }

    public List<NodeDescription> getSources() {
        return this.sources;
    }

    public List<NodeDescription> getTargets() {
        return this.targets;
    }

    @Override
    public String toString() {
        String pattern = "{0} "; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName());
    }

    public static Builder newEdgeCandidate() {
        return new Builder();
    }

    /**
     * The builder used to create a sourceTargetsDescription.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private List<NodeDescription> sources;

        private List<NodeDescription> targets;

        private Builder() {
        }

        public Builder sources(List<NodeDescription> sources) {
            this.sources = sources;
            return this;
        }

        public Builder targets(List<NodeDescription> targets) {
            this.targets = targets;
            return this;
        }

        public EdgeCandidate build() {
            EdgeCandidate tool = new EdgeCandidate();
            tool.sources = Objects.requireNonNull(this.sources);
            tool.targets = Objects.requireNonNull(this.targets);
            return tool;
        }
    }
}

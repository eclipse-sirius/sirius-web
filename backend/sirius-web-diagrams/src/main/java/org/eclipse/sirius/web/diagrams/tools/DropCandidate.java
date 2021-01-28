/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.tools;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;

/**
 * A drop candidate contains a valid drop couple of sourceKinds/targets.
 *
 * @author hmarchadour
 */
@Immutable
@GraphQLObjectType
public final class DropCandidate {

    private List<String> sourceKinds;

    private List<NodeDescription> targets;

    private boolean appliesToDiagramRoot;

    private DropCandidate() {
        // Prevent instantiation
    }

    @GraphQLField
    @GraphQLNonNull
    public List<String> getSourceKinds() {
        return this.sourceKinds;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull NodeDescription> getTargets() {
        return this.targets;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isAppliesToDiagramRoot() {
        return this.appliesToDiagramRoot;
    }

    @Override
    public String toString() {
        String pattern = "{0} "; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName());
    }

    public static Builder newDropCandidate() {
        return new Builder();
    }

    /**
     * The builder used to create a dropCandidate.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private List<String> sourceKinds;

        private List<NodeDescription> targets;

        private boolean appliesToDiagramRoot;

        private Builder() {
        }

        public Builder sourceKinds(List<String> sourceKinds) {
            this.sourceKinds = sourceKinds;
            return this;
        }

        public Builder targets(List<NodeDescription> targets) {
            this.targets = targets;
            return this;
        }

        public Builder appliesToDiagramRoot(boolean appliesToDiagramRoot) {
            this.appliesToDiagramRoot = appliesToDiagramRoot;
            return this;
        }

        public DropCandidate build() {
            DropCandidate tool = new DropCandidate();
            tool.sourceKinds = Objects.requireNonNull(this.sourceKinds);
            tool.targets = Objects.requireNonNull(this.targets);
            tool.appliesToDiagramRoot = this.appliesToDiagramRoot;
            return tool;
        }
    }
}

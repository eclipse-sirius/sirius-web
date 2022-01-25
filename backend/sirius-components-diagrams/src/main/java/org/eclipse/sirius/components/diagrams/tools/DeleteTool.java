/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * A delete element Tool implementation.
 *
 * @author arichard
 */
@Immutable
@GraphQLObjectType
public final class DeleteTool implements ITool {

    private String id;

    private String imageURL;

    private String label;

    private Function<VariableManager, IStatus> handler;

    private List<NodeDescription> targetDescriptions;

    private DeleteTool() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLNonNull
    @Override
    public String getId() {
        return this.id;
    }

    @GraphQLNonNull
    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Function<VariableManager, IStatus> getHandler() {
        return this.handler;
    }

    @GraphQLNonNull
    @Override
    public String getImageURL() {
        return this.imageURL;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull NodeDescription> getTargetDescriptions() {
        return this.targetDescriptions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, imageURL: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.imageURL);
    }

    public static Builder newDeleteTool(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a tool.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String imageURL;

        private String label;

        private Function<VariableManager, IStatus> handler;

        private List<NodeDescription> targetDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder handler(Function<VariableManager, IStatus> handler) {
            this.handler = Objects.requireNonNull(handler);
            return this;
        }

        public Builder targetDescriptions(List<NodeDescription> targetDescriptions) {
            this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
            return this;
        }

        public DeleteTool build() {
            DeleteTool tool = new DeleteTool();
            tool.id = Objects.requireNonNull(this.id);
            tool.imageURL = Objects.requireNonNull(this.imageURL);
            tool.label = Objects.requireNonNull(this.label);
            tool.handler = Objects.requireNonNull(this.handler);
            tool.targetDescriptions = Objects.requireNonNull(this.targetDescriptions);
            return tool;
        }
    }
}

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
package org.eclipse.sirius.components.collaborative.trees.api;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 * This class is used because creating tree requires sending at once multiple parameters.
 *
 * @author sbegaudeau
 */
@Immutable
public final class TreeCreationParameters {
    private String id;

    private TreeDescription treeDescription;

    private List<String> expanded;

    private IEditingContext editingContext;

    private TreeCreationParameters() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public TreeDescription getTreeDescription() {
        return this.treeDescription;
    }

    public List<String> getExpanded() {
        return this.expanded;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public static Builder newTreeCreationParameters(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, treeDescriptionId: {2}, expanded: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.treeDescription.getId(), this.expanded);
    }

    /**
     * The builder of the tree creation parameters.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private TreeDescription treeDescription;

        private List<String> expanded;

        private IEditingContext editingContext;

        private Builder(String id) {
            this.id = id;
        }

        public Builder treeDescription(TreeDescription treeDescription) {
            this.treeDescription = Objects.requireNonNull(treeDescription);
            return this;
        }

        public Builder expanded(List<String> expanded) {
            this.expanded = Objects.requireNonNull(expanded);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public TreeCreationParameters build() {
            TreeCreationParameters treeCreationParameters = new TreeCreationParameters();
            treeCreationParameters.id = Objects.requireNonNull(this.id);
            treeCreationParameters.treeDescription = Objects.requireNonNull(this.treeDescription);
            treeCreationParameters.expanded = Objects.requireNonNull(this.expanded);
            treeCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            return treeCreationParameters;
        }
    }
}

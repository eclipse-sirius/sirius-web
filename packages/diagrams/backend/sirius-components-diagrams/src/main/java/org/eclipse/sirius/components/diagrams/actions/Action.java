/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.actions;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * An Action triggered by a single click on a diagram element.
 *
 * @author arichard
 */
@Immutable
public final class Action {

    private String id;

    private List<String> iconURL;

    private String label;

    private Function<VariableManager, IStatus> handler;

    private List<IDiagramElementDescription> targetDescriptions;

    private Action() {
        // Prevent instantiation
    }

    public static Builder newAction(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public String getLabel() {
        return this.label;
    }

    public Function<VariableManager, IStatus> getHandler() {
        return this.handler;
    }

    public List<IDiagramElementDescription> getTargetDescriptions() {
        return this.targetDescriptions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder used to create a tool.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private List<String> iconURL;

        private String label;

        private Function<VariableManager, IStatus> handler;

        private List<IDiagramElementDescription> targetDescriptions;

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

        public Builder handler(Function<VariableManager, IStatus> handler) {
            this.handler = Objects.requireNonNull(handler);
            return this;
        }

        public Builder targetDescriptions(List<IDiagramElementDescription> targetDescriptions) {
            this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
            return this;
        }

        public Action build() {
            Action tool = new Action();
            tool.id = Objects.requireNonNull(this.id);
            tool.iconURL = Objects.requireNonNull(this.iconURL);
            tool.label = Objects.requireNonNull(this.label);
            tool.handler = Objects.requireNonNull(this.handler);
            tool.targetDescriptions = Objects.requireNonNull(this.targetDescriptions);
            return tool;
        }
    }
}

/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The edge reconnection tool.
 *
 * @author gcoutable
 */
@Immutable
public final class EdgeReconnectionTool implements ITool {

    private String id;

    private String label;

    private ReconnectEdgeKind kind;

    private Function<VariableManager, IStatus> handler;

    private EdgeReconnectionTool() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public ReconnectEdgeKind getKind() {
        return this.kind;
    }

    @Override
    public Function<VariableManager, IStatus> getHandler() {
        return this.handler;
    }

    @Override
    public String getImageURL() {
        return "";
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    public static Builder newEdgeReconnectionTool(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create an edge reconnection tool.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private ReconnectEdgeKind kind;

        private Function<VariableManager, IStatus> handler;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(ReconnectEdgeKind kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder handler(Function<VariableManager, IStatus> handler) {
            this.handler = Objects.requireNonNull(handler);
            return this;
        }

        public EdgeReconnectionTool build() {
            EdgeReconnectionTool tool = new EdgeReconnectionTool();
            tool.id = Objects.requireNonNull(this.id);
            tool.label = Objects.requireNonNull(this.label);
            tool.kind = Objects.requireNonNull(this.kind);
            tool.handler = Objects.requireNonNull(this.handler);
            return tool;
        }
    }
}

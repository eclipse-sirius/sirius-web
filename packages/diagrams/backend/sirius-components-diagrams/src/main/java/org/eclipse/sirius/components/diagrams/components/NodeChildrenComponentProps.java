/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the node children component.
 *
 * @author frouene
 */
@Immutable
public final class NodeChildrenComponentProps implements IProps {

    private VariableManager variableManager;

    private ViewModifier state;

    private ViewModifier parentState;

    private NodeComponentProps nodeComponentProps;

    private NodeChildrenComponentProps() {
        // Prevent initialisation
    }

    public static Builder newNodeChildrenComponentProps() {
        return new Builder();
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ViewModifier getState() {
        return this.state;
    }

    public ViewModifier getParentState() {
        return this.parentState;
    }

    public NodeComponentProps getNodeComponentProps() {
        return this.nodeComponentProps;
    }

    /**
     * The builder of the node children component props.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private VariableManager variableManager;

        private ViewModifier state;

        private ViewModifier parentState;

        private NodeComponentProps nodeComponentProps;

        public Builder variableManager(VariableManager variableManager) {
            this.variableManager = Objects.requireNonNull(variableManager);
            return this;
        }

        public Builder state(ViewModifier state) {
            this.state = Objects.requireNonNull(state);
            return this;
        }

        public Builder parentState(ViewModifier parentState) {
            this.parentState = Objects.requireNonNull(parentState);
            return this;
        }

        public Builder nodeComponentProps(NodeComponentProps nodeComponentProps) {
            this.nodeComponentProps = Objects.requireNonNull(nodeComponentProps);
            return this;
        }

        public NodeChildrenComponentProps build() {
            NodeChildrenComponentProps nodeChildrenComponentProps = new NodeChildrenComponentProps();
            nodeChildrenComponentProps.variableManager = Objects.requireNonNull(this.variableManager);
            nodeChildrenComponentProps.state = Objects.requireNonNull(this.state);
            nodeChildrenComponentProps.parentState = Objects.requireNonNull(this.parentState);
            nodeChildrenComponentProps.nodeComponentProps = Objects.requireNonNull(this.nodeComponentProps);
            return nodeChildrenComponentProps;
        }

    }
}

/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;

/**
 * Custom assertion class used to perform some tests on a node.
 *
 * @author gdaniel
 */
public class NodeAssert extends DiagramElementAssert<NodeAssert, Node> {

    public NodeAssert(Node node) {
        super(node, NodeAssert.class);
    }

    public NodeAssert hasType(String type) {
        assertThat(this.actual.getType()).isEqualTo(type);
        return this;
    }

    public NodeAssert hasTargetObjectId(String targetObjectId) {
        assertThat(this.actual.getTargetObjectId()).isEqualTo(targetObjectId);
        return this;
    }

    /**
     * Used to assert that the current node has the given target object kind.
     *
     * @param targetObjectKind The target object kind
     * @return This current node assert
     * @deprecated See the <a href="https://github.com/eclipse-sirius/sirius-web/issues/5114">Github issue</a>
     */
    @Deprecated(forRemoval = true)
    public NodeAssert hasTargetObjectKind(String targetObjectKind) {
        assertThat(this.actual.getTargetObjectKind()).isEqualTo(targetObjectKind);
        return this;
    }

    /**
     * Used to assert that the current node has the given target object label.
     *
     * @param targetObjectLabel The target object label
     * @return This current node assert
     * @deprecated See the <a href="https://github.com/eclipse-sirius/sirius-web/issues/5114">Github issue</a>
     */
    @Deprecated(forRemoval = true)
    public NodeAssert hasTargetObjectLabel(String targetObjectLabel) {
        assertThat(this.actual.getTargetObjectLabel()).isEqualTo(targetObjectLabel);
        return this;
    }

    public NodeAssert isBorderNode() {
        assertThat(this.actual.isBorderNode()).isTrue();
        return this;
    }

    public NodeAssert isNotBorderNode() {
        assertThat(this.actual.isBorderNode()).isFalse();
        return this;
    }

    public NodeAssert hasModifiers(Set<ViewModifier> modifiers) {
        assertThat(this.actual.getModifiers()).hasSameElementsAs(modifiers);
        return this;
    }

    public NodeAssert hasState(ViewModifier state) {
        assertThat(this.actual.getState()).isEqualTo(state);
        return this;
    }

    public NodeAssert hasCollapsingState(CollapsingState collapsingState) {
        assertThat(this.actual.getCollapsingState()).isEqualTo(collapsingState);
        return this;
    }

    public NodeAssert hasDefaultWidth(int defaultWidth) {
        assertThat(this.actual.getDefaultWidth()).isEqualTo(defaultWidth);
        return this;
    }

    public NodeAssert hasDefaultHeight(int defaultHeight) {
        assertThat(this.actual.getDefaultHeight()).isEqualTo(defaultHeight);
        return this;
    }

    public NodeAssert isLabelEditable() {
        assertThat(this.actual.isLabelEditable()).isTrue();
        return this;
    }

    public NodeAssert isNotLabelEditable() {
        assertThat(this.actual.isLabelEditable()).isFalse();
        return this;
    }

    public NodeAssert isPinned() {
        assertThat(this.actual.isPinned()).isTrue();
        return this;
    }

    public NodeAssert isNotPinned() {
        assertThat(this.actual.isPinned()).isFalse();
        return this;
    }
}

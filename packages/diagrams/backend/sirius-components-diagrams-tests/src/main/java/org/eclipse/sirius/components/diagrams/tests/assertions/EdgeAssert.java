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
package org.eclipse.sirius.components.diagrams.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.ViewModifier;

/**
 * Custom assertion class used to perform some tests on an edge.
 *
 * @author gdaniel
 */
public class EdgeAssert extends DiagramElementAssert<EdgeAssert, Edge> {

    public EdgeAssert(Edge edge) {
        super(edge, EdgeAssert.class);
    }

    public EdgeAssert hasType(String type) {
        assertThat(this.actual.getType()).isEqualTo(type);
        return this;
    }

    public EdgeAssert hasTargetObjectId(String targetObjectId) {
        assertThat(this.actual.getTargetObjectId()).isEqualTo(targetObjectId);
        return this;
    }

    public EdgeAssert hasTargetObjectKind(String targetObjectKind) {
        assertThat(this.actual.getTargetObjectKind()).isEqualTo(targetObjectKind);
        return this;
    }

    public EdgeAssert hasTargetObjectLabel(String targetObjectLabel) {
        assertThat(this.actual.getTargetObjectLabel()).isEqualTo(targetObjectLabel);
        return this;
    }

    public EdgeAssert hasSourceId(String sourceId) {
        assertThat(this.actual.getSourceId()).isEqualTo(sourceId);
        return this;
    }

    public EdgeAssert hasTargetId(String targetId) {
        assertThat(this.actual.getTargetId()).isEqualTo(targetId);
        return this;
    }

    public EdgeAssert hasModifiers(Set<ViewModifier> modifiers) {
        assertThat(this.actual.getModifiers()).hasSameElementsAs(modifiers);
        return this;
    }

    public EdgeAssert hasState(ViewModifier state) {
        assertThat(this.actual.getState()).isEqualTo(state);
        return this;
    }

    public EdgeAssert hasSourceAnchorRelativePosition(Ratio sourceAnchorRelativePosition) {
        assertThat(this.actual.getSourceAnchorRelativePosition()).isEqualTo(sourceAnchorRelativePosition);
        return this;
    }

    public EdgeAssert hasTargetAnchorRelativePosition(Ratio targetAnchorRelativePosition) {
        assertThat(this.actual.getTargetAnchorRelativePosition()).isEqualTo(targetAnchorRelativePosition);
        return this;
    }

    public EdgeAssert isCenterLabelEditable() {
        assertThat(this.actual.isCenterLabelEditable()).isTrue();
        return this;
    }

    public EdgeAssert isNotCenterLabelEditable() {
        assertThat(this.actual.isCenterLabelEditable()).isFalse();
        return this;
    }
}

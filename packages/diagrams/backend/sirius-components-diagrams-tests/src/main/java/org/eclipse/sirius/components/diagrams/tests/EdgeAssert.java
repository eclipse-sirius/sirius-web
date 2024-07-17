/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;

/**
 * Custom assertion class used to perform some tests on an edge.
 *
 * @author sbegaudeau
 */
public class EdgeAssert extends AbstractAssert<EdgeAssert, Edge> {

    public EdgeAssert(Edge edge) {
        super(edge, EdgeAssert.class);
    }

    public EdgeAssert matches(Edge edge, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(edge.getId());
            assertThat(this.actual.getSourceId()).isEqualTo(edge.getSourceId());
            assertThat(this.actual.getTargetId()).isEqualTo(edge.getTargetId());
        }

        assertThat(this.actual.getType()).isEqualTo(edge.getType());
        assertThat(this.actual.getTargetObjectId()).isEqualTo(edge.getTargetObjectId());
        assertThat(this.actual.getDescriptionId()).isEqualTo(edge.getDescriptionId());

        EdgeStyle actualStyle = this.actual.getStyle();
        EdgeStyle style = edge.getStyle();

        assertThat(actualStyle.getSize()).isEqualTo(style.getSize());
        assertThat(actualStyle.getLineStyle()).isEqualTo(style.getLineStyle());
        assertThat(actualStyle.getSourceArrow()).isEqualTo(style.getSourceArrow());
        assertThat(actualStyle.getTargetArrow()).isEqualTo(style.getTargetArrow());
        assertThat(actualStyle.getColor()).isEqualTo(style.getColor());

        return this;
    }

    public EdgeAssert hasId(String expectedId) {
        assertThat(this.actual.getId()).isEqualTo(expectedId);
        return this;
    }

}

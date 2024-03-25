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

import org.assertj.core.api.AbstractObjectAssert;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;

/**
 * Custom assertion class used to perform some tests on an edge style.

 * @author gdaniel
 */
public class EdgeStyleAssert extends AbstractObjectAssert<EdgeStyleAssert, EdgeStyle> {

    public EdgeStyleAssert(EdgeStyle edgeStyle) {
        super(edgeStyle, EdgeStyleAssert.class);
    }

    public EdgeStyleAssert hasSize(int size) {
        assertThat(this.actual.getSize()).isEqualTo(size);
        return this;
    }

    public EdgeStyleAssert hasSourceArrow(ArrowStyle sourceArrow) {
        assertThat(this.actual.getSourceArrow()).isEqualTo(sourceArrow);
        return this;
    }

    public EdgeStyleAssert hasTargetArrow(ArrowStyle targetArrow) {
        assertThat(this.actual.getTargetArrow()).isEqualTo(targetArrow);
        return this;
    }

    public EdgeStyleAssert hasColor(String color) {
        assertThat(this.actual.getColor()).isEqualTo(color);
        return this;
    }
}

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

import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * Custom assertion class used to perform some tests on a rectangular node style.
 *
 * @author gdaniel
 */
public class RectangularNodeStyleAssert extends NodeStyleAssert<RectangularNodeStyleAssert, RectangularNodeStyle> {

    public RectangularNodeStyleAssert(RectangularNodeStyle rectangularNodeStyle) {
        super(rectangularNodeStyle, RectangularNodeStyleAssert.class);
    }

    public RectangularNodeStyleAssert hasBackground(String color) {
        assertThat(this.actual.getBackground()).isEqualTo(color);
        return this;
    }

    public RectangularNodeStyleAssert hasBorderColor(String borderColor) {
        assertThat(this.actual.getBorderColor()).isEqualTo(borderColor);
        return this;
    }

    public RectangularNodeStyleAssert hasBorderSize(int borderSize) {
        assertThat(this.actual.getBorderSize()).isEqualTo(borderSize);
        return this;
    }

    public RectangularNodeStyleAssert hasBorderRadius(int borderRadius) {
        assertThat(this.actual.getBorderRadius()).isEqualTo(borderRadius);
        return this;
    }

    public RectangularNodeStyleAssert hasBorderStyle(LineStyle borderStyle) {
        assertThat(this.actual.getBorderStyle()).isEqualTo(borderStyle);
        return this;
    }
}

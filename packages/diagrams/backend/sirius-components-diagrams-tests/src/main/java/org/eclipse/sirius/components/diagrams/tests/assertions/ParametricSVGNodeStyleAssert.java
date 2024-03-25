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
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;

/**
 * Custom assertion class used to perform some tests on a parametric SVG node style.
 *
 * @author gdaniel
 */
public class ParametricSVGNodeStyleAssert extends NodeStyleAssert<ParametricSVGNodeStyleAssert, ParametricSVGNodeStyle> {

    public ParametricSVGNodeStyleAssert(ParametricSVGNodeStyle parametricSvgNodeStyle) {
        super(parametricSvgNodeStyle, ParametricSVGNodeStyleAssert.class);
    }

    public ParametricSVGNodeStyleAssert hasSvgURL(String svgURL) {
        assertThat(this.actual.getSvgURL()).isEqualTo(svgURL);
        return this;
    }

    public ParametricSVGNodeStyleAssert hasBackgroundColor(String backgroundColor) {
        assertThat(this.actual.getBackgroundColor()).isEqualTo(backgroundColor);
        return this;
    }

    public ParametricSVGNodeStyleAssert hasBorderColor(String borderColor) {
        assertThat(this.actual.getBorderColor()).isEqualTo(borderColor);
        return this;
    }

    public ParametricSVGNodeStyleAssert hasBorderSize(int borderSize) {
        assertThat(this.actual.getBorderSize()).isEqualTo(borderSize);
        return this;
    }

    public ParametricSVGNodeStyleAssert hasBorderRadius(int borderRadius) {
        assertThat(this.actual.getBorderRadius()).isEqualTo(borderRadius);
        return this;
    }

    public ParametricSVGNodeStyleAssert hasBorderStyle(LineStyle borderStyle) {
        assertThat(this.actual.getBorderStyle()).isEqualTo(borderStyle);
        return this;
    }
}

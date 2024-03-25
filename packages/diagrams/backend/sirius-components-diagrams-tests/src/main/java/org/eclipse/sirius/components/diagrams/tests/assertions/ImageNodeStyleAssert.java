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

import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Custom assertion class used to perform some tests on an image node style.
 *
 * @author gdaniel
 */
public class ImageNodeStyleAssert extends NodeStyleAssert<ImageNodeStyleAssert, ImageNodeStyle> {

    public ImageNodeStyleAssert(ImageNodeStyle imageNodeStyle) {
        super(imageNodeStyle, ImageNodeStyleAssert.class);
    }

    public ImageNodeStyleAssert hasImageURL(String imageURL) {
        assertThat(this.actual.getImageURL()).isEqualTo(imageURL);
        return this;
    }

    public ImageNodeStyleAssert hasScalingFactor(int scalingFactor) {
        assertThat(this.actual.getScalingFactor()).isEqualTo(scalingFactor);
        return this;
    }

    public ImageNodeStyleAssert hasBorderColor(String borderColor) {
        assertThat(this.actual.getBorderColor()).isEqualTo(borderColor);
        return this;
    }

    public ImageNodeStyleAssert hasBorderSize(int borderSize) {
        assertThat(this.actual.getBorderSize()).isEqualTo(borderSize);
        return this;
    }

    public ImageNodeStyleAssert hasBorderRadius(int borderRadius) {
        assertThat(this.actual.getBorderRadius()).isEqualTo(borderRadius);
        return this;
    }

    public ImageNodeStyleAssert hasBorderStyle(LineStyle lineStyle) {
        assertThat(this.actual.getBorderStyle()).isEqualTo(lineStyle);
        return this;
    }

    public ImageNodeStyleAssert isPositionDependentRotation() {
        assertThat(this.actual.isPositionDependentRotation()).isTrue();
        return this;
    }

    public ImageNodeStyleAssert isNotPositionDependentRotation() {
        assertThat(this.actual.isPositionDependentRotation()).isFalse();
        return this;
    }
}

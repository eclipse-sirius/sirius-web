/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;

/**
 * Custom assertion class used to perform some tests on an image node style.
 *
 * @author sbegaudeau
 */
public class ImageNodeStyleAssert extends AbstractAssert<ImageNodeStyleAssert, ImageNodeStyle> {
    public ImageNodeStyleAssert(ImageNodeStyle imageNodeStyle) {
        super(imageNodeStyle, ImageNodeStyleAssert.class);
    }

    public ImageNodeStyleAssert matches(ImageNodeStyle imageNodeStyle) {
        this.isNotNull();

        assertThat(this.actual.getImageURL()).isEqualTo(imageNodeStyle.getImageURL());
        assertThat(this.actual.getScalingFactor()).isEqualTo(imageNodeStyle.getScalingFactor());

        return this;
    }
}

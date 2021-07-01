/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.data.Offset;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the image node style size provider.
 *
 * @author sbegaudeau
 */
public class ImageNodeStyleSizeProviderTests {

    private static final String IMAGE_PNG = "/image.png"; //$NON-NLS-1$

    private static final Size SIZE = Size.of(42, 42);

    private ImageSizeProvider imageSizeProvider = new ImageSizeProvider() {
        @Override
        public Optional<Size> getSize(String imagePath) {
            return Optional.of(SIZE);
        }
    };

    @Test
    public void testImageNodeStyleNativeSize() {
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(this.imageSizeProvider);

        // @formatter:off
        ImageNodeStyle imageNodeStyle = ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(-1)
                .build();
        // @formatter:on

        Size size = imageNodeStyleSizeProvider.getSize(imageNodeStyle);
        assertThat(size.getWidth()).isCloseTo(42.0, Offset.offset(0.0001));
    }

    @Test
    public void testImageNodeStyleScaledSize() {
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(this.imageSizeProvider);

        // @formatter:off
        ImageNodeStyle imageNodeStyle = ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(2)
                .build();
        // @formatter:on

        Size size = imageNodeStyleSizeProvider.getSize(imageNodeStyle);
        assertThat(size.getWidth()).isCloseTo(20.0, Offset.offset(0.0001));
    }
}

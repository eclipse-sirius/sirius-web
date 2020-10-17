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
package org.eclipse.sirius.web.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.data.Offset;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.ImageNodeStyleSizeService;
import org.eclipse.sirius.web.diagrams.layout.ImageSizeService;
import org.junit.Test;

/**
 * Unit tests of the image node style size service.
 *
 * @author sbegaudeau
 */
public class ImageNodeStyleSizeServiceTestCases {

    private static final String IMAGE_PNG = "/image.png"; //$NON-NLS-1$

    // @formatter:off
    private static final Size SIZE = Size.newSize()
            .width(42)
            .height(42)
            .build();
    // @formatter:on

    private ImageSizeService imageSizeService = new ImageSizeService() {
        @Override
        public Optional<Size> getSize(String imagePath) {
            return Optional.of(SIZE);
        }
    };

    @Test
    public void testImageNodeStyleNativeSize() {
        ImageNodeStyleSizeService imageNodeStyleSizeService = new ImageNodeStyleSizeService(this.imageSizeService);

        // @formatter:off
        ImageNodeStyle imageNodeStyle = ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(-1)
                .build();
        // @formatter:on

        Size size = imageNodeStyleSizeService.getSize(imageNodeStyle);
        assertThat(size.getWidth()).isCloseTo(42.0, Offset.offset(0.0001));
    }

    @Test
    public void testImageNodeStyleScaledSize() {
        ImageNodeStyleSizeService imageNodeStyleSizeService = new ImageNodeStyleSizeService(this.imageSizeService);

        // @formatter:off
        ImageNodeStyle imageNodeStyle = ImageNodeStyle.newImageNodeStyle()
                .imageURL(IMAGE_PNG)
                .scalingFactor(2)
                .build();
        // @formatter:on

        Size size = imageNodeStyleSizeService.getSize(imageNodeStyle);
        assertThat(size.getWidth()).isCloseTo(20.0, Offset.offset(0.0001));
    }
}

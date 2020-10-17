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
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.assertj.core.data.Offset;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.ImageSizeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests of the image size service.
 *
 * @author hmarchadour
 */
@RunWith(Parameterized.class)
public class ImageSizeServiceTestCases {

    private static final List<String> FOLDERS = List.of("svg", "png", "jpg"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    private final ImageSizeService imageSizeService = new ImageSizeService();

    private final String imagePath;

    private final double width;

    private final double height;

    public ImageSizeServiceTestCases(String imagePath, double width, double height) {
        this.imagePath = Objects.requireNonNull(imagePath);
        this.width = width;
        this.height = height;
    }

    @Parameters(name = "{index} - imagePath: {0}")
    public static List<Object[]> getImagePaths() {
        List<Object[]> result = new ArrayList<>();
        ClassLoader classLoader = ImageSizeServiceTestCases.class.getClassLoader();

        FOLDERS.forEach(folder -> {
            URL url = classLoader.getResource(folder);
            String path = url.getPath();

            try {
                path = url.toURI().getPath();
            } catch (URISyntaxException exception) {
                fail(exception.getMessage());
            }

            File[] listFiles = new File(path).listFiles();

            for (File file : listFiles) {
                String fileName = file.getName();
                String baseName = FilenameUtils.getBaseName(fileName);

                String[] splitBaseName = baseName.split("-"); //$NON-NLS-1$
                String[] expectedSize = splitBaseName[splitBaseName.length - 1].split("_"); //$NON-NLS-1$
                String imagePath = folder + File.separator + fileName;
                Double width = Double.parseDouble(expectedSize[0]);
                Double height = Double.parseDouble(expectedSize[1]);

                result.add(new Object[] { imagePath, width, height });
            }
        });
        return result;
    }

    /**
     * In src/test/resources, an index.html file can be consulted to watch all test images.<br/>
     * <br/>
     * Note that each test image file name respects the following convention :
     * ${FILE_IDENT}-${EXPECTED_WIDTH}_${EXPECTED_HEIGHT}.${FILE_EXTENSION}
     *
     * @throws IOException
     */
    @Test
    public void testGetSize() throws IOException {
        Optional<Size> optionalSize = this.imageSizeService.getSize(this.imagePath);
        assertThat(optionalSize).isPresent();
        Size size = optionalSize.get();
        assertThat(size.getWidth()).isCloseTo(this.width, Offset.offset(0.0001));
        assertThat(size.getHeight()).isCloseTo(this.height, Offset.offset(0.0001));
    }
}

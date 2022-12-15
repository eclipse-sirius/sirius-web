/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.assertj.core.data.Offset;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests of the image size provider.
 *
 * @author hmarchadour
 */
public class ImageSizeProviderTests {

    private static final List<String> FOLDERS = List.of("svg", "png", "jpg");

    private final ImageSizeProvider imageSizeProvider = new ImageSizeProvider();

    public static List<Arguments> getImagePaths() {
        List<Arguments> result = new ArrayList<>();
        ClassLoader classLoader = ImageSizeProviderTests.class.getClassLoader();

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

                String[] splitBaseName = baseName.split("-");
                String[] expectedSize = splitBaseName[splitBaseName.length - 1].split("_");
                String imagePath = folder + File.separator + fileName;
                Double width = Double.parseDouble(expectedSize[0]);
                Double height = Double.parseDouble(expectedSize[1]);

                result.add(arguments(imagePath, width, height));
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
    @ParameterizedTest
    @MethodSource("getImagePaths")
    public void testGetSize(String imagePath, double width, double height) throws IOException {
        Optional<Size> optionalSize = this.imageSizeProvider.getSize(imagePath);
        assertThat(optionalSize).isPresent();
        Size size = optionalSize.get();
        assertThat(size.getWidth()).isCloseTo(width, Offset.offset(0.0001));
        assertThat(size.getHeight()).isCloseTo(height, Offset.offset(0.0001));
    }
}

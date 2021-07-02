/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.eclipse.sirius.web.persistence.entities.CustomImageEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Loads custom images into the database on startup.
 *
 * @author pcdavid
 */
@Component
public class CustomImagesLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CustomImagesLoader.class);

    private final ICustomImageRepository customImageRepository;

    public CustomImagesLoader(ICustomImageRepository customImageRepository) {
        this.customImageRepository = Objects.requireNonNull(customImageRepository);
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.customImageRepository.count() == 0) {
            this.loadImages(Paths.get(System.getProperty("org.eclipse.sirius.web.customImages.path"))); //$NON-NLS-1$
        }
    }

    private void loadImages(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(imgPath -> {
                try {
                    CustomImageEntity customImageEntity = new CustomImageEntity();
                    customImageEntity.setLabel(this.getImageLabel(imgPath));
                    customImageEntity.setFileName(imgPath.getFileName().toString());
                    customImageEntity.setContent(Files.readAllBytes(imgPath));
                    customImageEntity = this.customImageRepository.save(customImageEntity);
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
            });
        }
    }

    private String getImageLabel(Path imgPath) {
        String label = imgPath.getFileName().toString();
        label = label.replace("shape_", ""); //$NON-NLS-1$ //$NON-NLS-2$
        label = label.replace(".svg", ""); //$NON-NLS-1$ //$NON-NLS-2$
        label = Character.toUpperCase(label.charAt(0)) + label.substring(1);
        return label;
    }

}

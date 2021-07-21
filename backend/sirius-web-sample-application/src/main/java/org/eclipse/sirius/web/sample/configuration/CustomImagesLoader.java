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
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.CustomImageEntity;
import org.eclipse.sirius.web.persistence.repositories.ICustomImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Import custom images into the database on startup.
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
        String rootPath = System.getProperty("org.eclipse.sirius.web.customImages.path"); //$NON-NLS-1$
        if (rootPath != null) {
            this.importAllImages(Paths.get(rootPath));
        }
    }

    public void importAllImages(Path rootPath) throws IOException {
        try (var images = Files.walk(rootPath).filter(this::isImageFile)) {
            int prefixLength = rootPath.toString().length() + 1;
            images.forEach(imgPath -> {
                try {
                    CustomImageEntity customImageEntity = this.loadImageFile(imgPath);
                    String fullLabel = imgPath.toString().substring(prefixLength).replace("/", " / "); //$NON-NLS-1$ //$NON-NLS-2$
                    customImageEntity.setLabel(this.trimFileExtension(fullLabel));
                    customImageEntity = this.customImageRepository.save(customImageEntity);
                } catch (IOException e) {
                    this.logger.warn("Error loading image {}: {}", imgPath, e.getMessage()); //$NON-NLS-1$
                }
            });
        }
    }

    private boolean isImageFile(Path path) {
        try {
            String probedType = Files.probeContentType(path);
            return probedType != null && probedType.startsWith("image/"); //$NON-NLS-1$
        } catch (IOException ioe) {
            return false;
        }
    }

    private CustomImageEntity loadImageFile(Path imgPath) throws IOException {
        CustomImageEntity customImageEntity = new CustomImageEntity();
        customImageEntity.setLabel(this.trimFileExtension(imgPath.getFileName().toString()));
        customImageEntity.setContentType(Files.probeContentType(imgPath));
        customImageEntity.setContent(Files.readAllBytes(imgPath));
        customImageEntity.setId(UUID.nameUUIDFromBytes(customImageEntity.getContent()));
        return customImageEntity;
    }

    private String trimFileExtension(String fileName) {
        int extensionStart = fileName.lastIndexOf('.');
        if (extensionStart != -1) {
            return fileName.substring(0, extensionStart);
        } else {
            return fileName;
        }
    }
}

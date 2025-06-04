/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.sirius.web.papaya.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.sirius.web.application.images.services.api.IImageResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Used to compute an image dynamically in Papaya.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaImageResourceProvider implements IImageResourceProvider {

    private final Logger logger = LoggerFactory.getLogger(PapayaImageResourceProvider.class);

    @Override
    public boolean canHandle(String imagePath) {
        return imagePath.contains("papaya/full/obj16/");
    }

    @Override
    public Resource getResource(String imagePath) {
        var endIndexOfImage = imagePath.lastIndexOf("+");
        var startIndexOfExtension = imagePath.lastIndexOf(".svg");

        if (endIndexOfImage != -1 && startIndexOfExtension != -1 && endIndexOfImage < startIndexOfExtension) {
            var rawImagePath = imagePath.substring(0, endIndexOfImage) + ".svg";
            var color = imagePath.substring(endIndexOfImage + "+".length(), startIndexOfExtension);

            try (var inputStream = new ClassPathResource(rawImagePath).getInputStream()) {
                var content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                var newContent = content.replace("currentColor", color);
                return new ByteArrayResource(newContent.getBytes(StandardCharsets.UTF_8));
            } catch (IOException exception) {
                this.logger.warn(exception.getMessage());
            }
        }

        return new ClassPathResource(imagePath);
    }
}

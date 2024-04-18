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
package org.eclipse.sirius.components.core.services;

import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.springframework.stereotype.Service;

/**
 * Used to cleanup image url in a coherent manner.
 *
 * @author sbegaudeau
 */
@Service
public class ImageURLSanitizer implements IImageURLSanitizer {
    @Override
    public String sanitize(String basePath, String url) {
        String imageURL = "";
        if (url != null && !url.isBlank()) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                imageURL = url;
            } else {
                if (url.startsWith("/")) {
                    imageURL = basePath + url;
                } else {
                    imageURL = basePath + "/" + url;
                }
            }
        }
        return imageURL;
    }
}

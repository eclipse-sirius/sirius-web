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
package org.eclipse.sirius.web.application.images.services.api;

import org.eclipse.sirius.web.application.images.dto.ImageMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.image.Image;

/**
 * Used to convert an image to a DTO.
 *
 * @author sbegaudeau
 */
public interface IImageMapper {
    ImageMetadata toDTO(Image image);
}

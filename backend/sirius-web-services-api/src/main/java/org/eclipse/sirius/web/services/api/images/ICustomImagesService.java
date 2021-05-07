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
package org.eclipse.sirius.web.services.api.images;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for custom images.
 *
 * @author pcdavid
 */
public interface ICustomImagesService {
    Optional<CustomImage> findById(UUID id);

    Optional<byte[]> getImageContentsByFileName(String fileName);

    List<CustomImage> getAvailableImages();
}

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
package org.eclipse.sirius.web.emf.view;

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

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICustomImagesService {

        @Override
        public Optional<CustomImage> findById(UUID id) {
            return Optional.empty();
        }

        @Override
        public Optional<byte[]> getImageContentsByFileName(String fileName) {
            return Optional.empty();
        }

        @Override
        public List<CustomImage> getAvailableImages() {
            return List.of();
        }

    }
}

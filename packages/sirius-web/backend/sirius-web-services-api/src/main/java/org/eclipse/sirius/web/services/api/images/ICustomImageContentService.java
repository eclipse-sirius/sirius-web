/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import java.util.Optional;
import java.util.UUID;

/**
 * Service to retrieve the actual content of a custom image.
 *
 * @author pcdavid
 */
public interface ICustomImageContentService {

    Optional<byte[]> getImageContentById(String editingContextId, UUID imageId);

    Optional<String> getImageContentTypeById(String editingContextId, UUID imageId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ICustomImageContentService {

        @Override
        public Optional<byte[]> getImageContentById(String editingContextId, UUID imageId) {
            return Optional.empty();
        }

        @Override
        public Optional<String> getImageContentTypeById(String editingContextId, UUID imageId) {
            return Optional.empty();
        }

    }

}

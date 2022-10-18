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

import java.util.List;

/**
 * Service used to find existing custom images.
 *
 * @author pcdavid
 */
public interface IProjectCustomImageMetadataSearchService {
    List<CustomImageMetadata> getProjectImages(String editingContextId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IProjectCustomImageMetadataSearchService {
        @Override
        public List<CustomImageMetadata> getProjectImages(String editingContextId) {
            return List.of();
        }
    }
}

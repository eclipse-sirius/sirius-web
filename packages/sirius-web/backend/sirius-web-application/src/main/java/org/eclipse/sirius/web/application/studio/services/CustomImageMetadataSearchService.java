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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;

import org.eclipse.sirius.components.view.emf.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to return the custom images available for studios.
 *
 * @author sbegaudeau
 */
@Service
public class CustomImageMetadataSearchService implements ICustomImageMetadataSearchService {
    @Override
    public List<CustomImageMetadata> getAvailableImages(String editingContextId) {
        return List.of();
    }
}

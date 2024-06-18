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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.trees.Tree;
import org.springframework.stereotype.Service;

/**
 * Provides the metadata for the model browser tree representations.
 *
 * @author pcdavid
 */
@Service
public class ModelBrowserMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public Optional<RepresentationMetadata> getMetadata(String representationId) {
        RepresentationMetadata representationMetadata = null;
        if (representationId.startsWith(ModelBrowsersDescriptionProvider.MODEL_BROWSER_CONTAINER_PREFIX)) {
            representationMetadata = new RepresentationMetadata(representationId, Tree.KIND, ModelBrowsersDescriptionProvider.REPRESENTATION_NAME, ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID);
        } else if (representationId.startsWith(ModelBrowsersDescriptionProvider.MODEL_BROWSER_REFERENCE_PREFIX)) {
            representationMetadata = new RepresentationMetadata(representationId, Tree.KIND, ModelBrowsersDescriptionProvider.REPRESENTATION_NAME, ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID);
        }
        return Optional.ofNullable(representationMetadata);
    }

}

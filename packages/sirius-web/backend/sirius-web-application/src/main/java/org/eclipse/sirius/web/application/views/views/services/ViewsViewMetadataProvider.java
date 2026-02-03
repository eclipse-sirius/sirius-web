/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.views.services;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.trees.Tree;
import org.springframework.stereotype.Service;

/**
 * Provides the metadata for the views view representation.
 *
 * @author tgiraudet
 */
@Service
public class ViewsViewMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public Optional<RepresentationMetadata> getMetadata(String editingContextId, String representationId) {
        if (representationId.startsWith(ViewsTreeDescriptionProvider.PREFIX)) {
            var representationMetadata = RepresentationMetadata.newRepresentationMetadata(representationId)
                    .kind(Tree.KIND)
                    .label(ViewsTreeDescriptionProvider.REPRESENTATION_NAME)
                    .descriptionId(ViewsTreeDescriptionProvider.DESCRIPTION_ID)
                    .iconURLs(List.of("/views/views.svg"))
                    .build();

            return Optional.of(representationMetadata);
        }
        return Optional.empty();
    }

}

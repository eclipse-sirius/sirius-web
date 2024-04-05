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
package org.eclipse.sirius.web.services.explorer;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.trees.Tree;
import org.springframework.stereotype.Service;

/**
 * Provides the metadata for the explorer representation.
 *
 * @author pcdavid
 */
@Service
public class ExplorerMetadataProvider implements IRepresentationMetadataProvider {

    @Override
    public boolean canHandle(String representationId) {
        return representationId != null && representationId.startsWith(ExplorerDescriptionProvider.PREFIX);
    }

    @Override
    public RepresentationMetadata handle(String representationId) {
        return new RepresentationMetadata(representationId, Tree.KIND, ExplorerDescriptionProvider.REPRESENTATION_NAME, ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

}

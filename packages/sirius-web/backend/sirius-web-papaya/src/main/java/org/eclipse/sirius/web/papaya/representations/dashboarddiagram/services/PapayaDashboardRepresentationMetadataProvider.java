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
package org.eclipse.sirius.web.papaya.representations.dashboarddiagram.services;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.papaya.representations.dashboarddiagram.PapayaDashboardDiagramDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the Metadata for the transient Papaya dashboard diagram.
 *
 * @author fbarbin
 */
@Service
public class PapayaDashboardRepresentationMetadataProvider implements IRepresentationMetadataProvider {
    @Override
    public Optional<RepresentationMetadata> getMetadata(String editingContextId, String representationId) {
        if (PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID.equals(representationId)) {
            var representationMetadata = RepresentationMetadata.newRepresentationMetadata(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID)
                    .descriptionId(PapayaDashboardDiagramDescriptionProvider.DASHBOARD_DESCRIPTION_ID)
                    .kind(Diagram.KIND)
                    .label(PapayaDashboardDiagramDescriptionProvider.NAME)
                    .iconURLs(List.of())
                    .build();
            return Optional.of(representationMetadata);
        }
        return Optional.empty();
    }
}

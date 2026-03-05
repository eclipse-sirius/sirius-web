/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.services;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationDescriptionsProvider;
import org.eclipse.sirius.components.collaborative.dto.RepresentationDescriptionMetadataDTO;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Used to make the portals work.
 *
 * @author pcdavid
 */
@Service
public class PortalRepresentationDescriptionsProvider implements IRepresentationDescriptionsProvider {

    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        return representationDescription.getId().equals(UUID.nameUUIDFromBytes("portals_description".getBytes()).toString());
    }

    @Override
    public List<RepresentationDescriptionMetadataDTO> handle(IEditingContext editingContext, Object object, IRepresentationDescription representationDescription) {
        return List.of(new RepresentationDescriptionMetadataDTO(representationDescription.getId(), representationDescription.getLabel(), representationDescription.getLabel(), ""));
    }

}

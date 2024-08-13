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

package org.eclipse.sirius.web.application.representation.services;

import org.eclipse.sirius.web.application.representation.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationMetadataMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Used to convert a representation metadata to a DTO.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataMapper implements IRepresentationMetadataMapper {

    @Override
    public RepresentationMetadataDTO toDTO(RepresentationMetadata representationMetadata) {
        return new RepresentationMetadataDTO(representationMetadata.getId(), representationMetadata.getLabel(), representationMetadata.getKind(), representationMetadata.getTargetObjectId(), representationMetadata.getDescriptionId());
    }
}

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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Used to create new representation metadata.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationMetadataCreationService implements IRepresentationMetadataCreationService {

    private final IRepresentationMetadataRepository representationMetadataRepository;

    public RepresentationMetadataCreationService(IRepresentationMetadataRepository representationMetadataRepository) {
        this.representationMetadataRepository = Objects.requireNonNull(representationMetadataRepository);
    }

    @Override
    public IResult<RepresentationMetadata> create(RepresentationMetadata representationMetadata) {
        this.representationMetadataRepository.save(representationMetadata);
        return new Success<>(representationMetadata);
    }
}

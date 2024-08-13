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
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete representation metadata.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationMetadataDeletionService implements IRepresentationMetadataDeletionService {

    private final IRepresentationMetadataRepository representationMetadataRepository;

    private final IMessageService messageService;

    public RepresentationMetadataDeletionService(IRepresentationMetadataRepository representationMetadataRepository, IMessageService messageService) {
        this.representationMetadataRepository = Objects.requireNonNull(representationMetadataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> delete(ICause cause, UUID representationMetadataId) {
        IResult<Void> result = null;

        var optionalRepresentationMetadata = this.representationMetadataRepository.findById(representationMetadataId);
        if (optionalRepresentationMetadata.isPresent()) {
            var representationMetadata = optionalRepresentationMetadata.get();
            representationMetadata.dispose(cause);

            this.representationMetadataRepository.delete(representationMetadata);
            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }
        return result;
    }
}

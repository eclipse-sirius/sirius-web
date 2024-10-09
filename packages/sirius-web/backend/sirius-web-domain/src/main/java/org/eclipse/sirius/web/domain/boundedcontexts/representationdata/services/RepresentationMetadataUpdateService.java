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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update representation metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationMetadataUpdateService implements IRepresentationMetadataUpdateService {

    private final IRepresentationMetadataRepository representationMetadataRepository;

    private final IMessageService messageService;

    public RepresentationMetadataUpdateService(IRepresentationMetadataRepository representationMetadataRepository, IMessageService messageService) {
        this.representationMetadataRepository = Objects.requireNonNull(representationMetadataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> updateRepresentationMetadataLabelById(ICause cause, UUID representationMetadataId, String label) {
        IResult<Void> result = new Failure<>(this.messageService.notFound());

        var optionalRepresentationMetadata = this.representationMetadataRepository.findMetadataById(representationMetadataId);
        if (optionalRepresentationMetadata.isPresent()) {
            var representationMetadata = optionalRepresentationMetadata.get();
            representationMetadata.updateLabel(cause, label);
            this.representationMetadataRepository.save(representationMetadata);

            result = new Success<>(null);
        }

        return result;
    }
}

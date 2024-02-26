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

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataDeletionService implements IRepresentationDataDeletionService {

    private final IRepresentationDataRepository representationDataRepository;

    private final IMessageService messageService;

    public RepresentationDataDeletionService(IRepresentationDataRepository representationDataRepository, IMessageService messageService) {
        this.representationDataRepository = Objects.requireNonNull(representationDataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> delete(UUID representationDataId) {
        IResult<Void> result = null;

        var optionalRepresentationData = this.representationDataRepository.findById(representationDataId);
        if (optionalRepresentationData.isPresent()) {
            var representationData = optionalRepresentationData.get();
            representationData.dispose();

            this.representationDataRepository.delete(representationData);
            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }
        return result;
    }
}

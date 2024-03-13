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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataUpdateService implements IRepresentationDataUpdateService {

    private final IRepresentationDataRepository representationDataRepository;

    private final IMessageService messageService;

    public RepresentationDataUpdateService(IRepresentationDataRepository representationDataRepository, IMessageService messageService) {
        this.representationDataRepository = Objects.requireNonNull(representationDataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> updateContent(ICause cause, UUID id, String content) {
        IResult<Void> result = null;

        var optionalRepresentationData = this.representationDataRepository.findById(id);
        if (optionalRepresentationData.isPresent()) {
            var representationData = optionalRepresentationData.get();
            representationData.updateContent(cause, content);
            this.representationDataRepository.save(representationData);

            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }
}

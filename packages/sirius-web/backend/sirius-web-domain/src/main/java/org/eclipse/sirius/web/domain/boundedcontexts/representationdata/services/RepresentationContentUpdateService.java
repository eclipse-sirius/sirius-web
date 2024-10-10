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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update representation content.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationContentUpdateService implements IRepresentationContentUpdateService {

    private final IRepresentationContentRepository representationContentRepository;

    private final IMessageService messageService;

    public RepresentationContentUpdateService(IRepresentationContentRepository representationContentRepository, IMessageService messageService) {
        this.representationContentRepository = Objects.requireNonNull(representationContentRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> updateContentByRepresentationId(ICause cause, UUID representationId, String content) {
        IResult<Void> result = null;

        var optionalRepresentationContent = this.representationContentRepository.findContentByRepresentationMetadataId(representationId);
        if (optionalRepresentationContent.isPresent()) {
            var representationContent = optionalRepresentationContent.get();
            representationContent.updateContent(cause, content);
            this.representationContentRepository.save(representationContent);

            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }

    @Override
    public IResult<Void> updateContentByRepresentationIdWithMigrationData(ICause cause, UUID representationId, String content, String lastMigrationPerformed, String migrationVersion) {
        IResult<Void> result = null;

        var optionalRepresentationContent = this.representationContentRepository.findContentByRepresentationMetadataId(representationId);
        if (optionalRepresentationContent.isPresent()) {
            var representationContent = optionalRepresentationContent.get();
            representationContent.updateContent(cause, content);
            representationContent.updateMigrationData(lastMigrationPerformed, migrationVersion);
            this.representationContentRepository.save(representationContent);

            result = new Success<>(null);
        } else {
            result = new Failure<>(this.messageService.notFound());
        }

        return result;
    }
}

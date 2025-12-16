/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataDeletionService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete semantic data.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataDeletionService implements ISemanticDataDeletionService {

    private final ISemanticDataRepository semanticDataRepository;

    private final IMessageService messageService;

    public SemanticDataDeletionService(ISemanticDataRepository semanticDataRepository, IMessageService messageService) {
        this.semanticDataRepository = Objects.requireNonNull(semanticDataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteAllById(List<UUID> semanticDataIds) {
        this.semanticDataRepository.deleteAllById(semanticDataIds);
        return new Success<>(null);
    }

    @Override
    public IResult<Void> deleteSemanticData(ICause cause, UUID semanticDataId) {
        IResult<Void> result = new Failure<>(this.messageService.notFound());

        Optional<SemanticData> optionalSemanticData = this.semanticDataRepository.findById(semanticDataId);
        if (optionalSemanticData.isPresent()) {
            SemanticData semanticData = optionalSemanticData.get();
            semanticData.dispose(cause);
            this.semanticDataRepository.delete(semanticData);
            result = new Success<>(null);
        }

        return result;
    }
}

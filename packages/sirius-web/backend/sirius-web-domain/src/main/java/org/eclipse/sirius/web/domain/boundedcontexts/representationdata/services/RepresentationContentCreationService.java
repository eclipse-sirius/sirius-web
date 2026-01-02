/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationContent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationContentRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to create new representation content.
 *
 * @author gcoutable
 */
@Service
public class RepresentationContentCreationService implements IRepresentationContentCreationService {

    private final IRepresentationContentRepository representationContentRepository;

    public RepresentationContentCreationService(IRepresentationContentRepository representationContentRepository) {
        this.representationContentRepository = Objects.requireNonNull(representationContentRepository);
    }

    @Override
    public IResult<RepresentationContent> create(ICause cause, UUID representationId, UUID semanticDataId, String content, String lastMigrationPerformed, String migrationVersion) {
        var representationContent = RepresentationContent.newRepresentationContent(representationId)
                .representationMetadata(AggregateReference.to(representationId))
                .semanticData(AggregateReference.to(semanticDataId))
                .content(content)
                .lastMigrationPerformed(lastMigrationPerformed)
                .migrationVersion(migrationVersion)
                .build(cause);

        this.representationContentRepository.save(representationContent);
        return new Success<>(representationContent);
    }
}

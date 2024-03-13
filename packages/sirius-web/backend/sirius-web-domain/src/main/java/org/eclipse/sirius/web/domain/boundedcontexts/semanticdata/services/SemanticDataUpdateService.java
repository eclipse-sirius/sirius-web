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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to update the semantic data.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataUpdateService implements ISemanticDataUpdateService {

    private final ISemanticDataRepository semanticDataRepository;

    public SemanticDataUpdateService(ISemanticDataRepository semanticDataRepository) {
        this.semanticDataRepository = Objects.requireNonNull(semanticDataRepository);
    }

    @Override
    public void updateDocuments(ICause cause, AggregateReference<Project, UUID> project, Set<Document> documents) {
        this.semanticDataRepository.findByProjectId(project.getId()).ifPresent(semanticData -> {
            semanticData.updateDocuments(cause, documents);
            this.semanticDataRepository.save(semanticData);
        });
    }
}

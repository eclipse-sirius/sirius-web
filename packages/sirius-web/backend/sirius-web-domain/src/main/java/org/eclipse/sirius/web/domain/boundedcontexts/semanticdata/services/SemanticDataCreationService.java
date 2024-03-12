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
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to create semantic data.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataCreationService implements ISemanticDataCreationService {

    private final ISemanticDataRepository semanticDataRepository;

    public SemanticDataCreationService(ISemanticDataRepository semanticDataRepository) {
        this.semanticDataRepository = Objects.requireNonNull(semanticDataRepository);
    }

    @Override
    public void initialize(ICause cause, AggregateReference<Project, UUID> project) {
        var semanticData = SemanticData.newSemanticData()
                .project(project)
                .documents(Set.of())
                .build(cause);
        this.semanticDataRepository.save(semanticData);
    }
}

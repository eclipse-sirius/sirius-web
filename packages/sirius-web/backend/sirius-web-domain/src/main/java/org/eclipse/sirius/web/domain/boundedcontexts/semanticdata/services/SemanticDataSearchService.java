/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve semantic data.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataSearchService implements ISemanticDataSearchService {

    private final ISemanticDataRepository semanticDataRepository;

    public SemanticDataSearchService(ISemanticDataRepository semanticDataRepository) {
        this.semanticDataRepository = Objects.requireNonNull(semanticDataRepository);
    }

    @Override
    public List<SemanticData> findAllByDomains(List<String> domainUris) {
        return this.semanticDataRepository.findAllByDomains(domainUris);
    }

    @Override
    public boolean isUsingDomains(UUID id, List<String> domainUris) {
        return this.semanticDataRepository.isUsingDomains(id, domainUris);
    }

    @Override
    public Optional<SemanticData> findById(UUID id) {
        return this.semanticDataRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.semanticDataRepository.existsById(id);
    }

    @Override
    public List<AggregateReference<SemanticData, UUID>> findAllDependenciesIdById(UUID id) {
        return this.semanticDataRepository.findAllDependenciesById(id).stream()
                .map(AggregateReference::<SemanticData, UUID>to)
                .toList();
    }

    @Override
    public List<SemanticData> findAllDependenciesRecursivelyById(UUID id) {
        var semanticDataIds = this.semanticDataRepository.findAllDependenciesRecursivelyById(id);
        return this.semanticDataRepository.findAllById(semanticDataIds);
    }

}

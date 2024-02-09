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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to find representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataSearchService implements IRepresentationDataSearchService {
    @Override
    public Optional<RepresentationData> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<RepresentationData> findAllByTargetObjectId(String targetObjectId) {
        return List.of();
    }
}

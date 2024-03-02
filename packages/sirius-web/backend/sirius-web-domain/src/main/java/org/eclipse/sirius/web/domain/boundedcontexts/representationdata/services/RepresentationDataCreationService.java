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

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.stereotype.Service;

/**
 * Used to create new representation data.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDataCreationService implements IRepresentationDataCreationService {

    private final IRepresentationDataRepository representationDataRepository;

    public RepresentationDataCreationService(IRepresentationDataRepository representationDataRepository) {
        this.representationDataRepository = Objects.requireNonNull(representationDataRepository);
    }

    @Override
    public IResult<RepresentationData> create(RepresentationData representationData) {
        this.representationDataRepository.save(representationData);
        return new Success<>(representationData);
    }
}

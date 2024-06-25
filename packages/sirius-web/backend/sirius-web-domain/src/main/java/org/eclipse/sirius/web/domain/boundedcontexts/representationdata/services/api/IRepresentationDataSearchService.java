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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to find representation data.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDataSearchService {

    boolean existsById(UUID id);

    Optional<RepresentationData> findById(UUID id);

    List<RepresentationData> findAllByProject(AggregateReference<Project, UUID> project);

    List<RepresentationDataMetadataOnly> findAllMetadataByProject(AggregateReference<Project, UUID> project);

    boolean existAnyRepresentationForTargetObjectId(String targetObjectId);

    List<RepresentationData> findAllByTargetObjectId(String targetObjectId);

    Optional<AggregateReference<Project, UUID>> findProjectByRepresentationId(UUID representationId);
}

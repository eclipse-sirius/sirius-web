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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to find representation metadata.
 *
 * @author sbegaudeau
 */
public interface IRepresentationMetadataSearchService {

    boolean existsById(UUID id);

    Optional<RepresentationMetadata> findMetadataById(UUID id);

    boolean existsByIdAndKind(UUID id, List<String> kinds);

    List<RepresentationMetadata> findAllMetadataByProject(AggregateReference<Project, UUID> project);

    List<RepresentationMetadata> findAllMetadataByProjectAndTargetObjectId(AggregateReference<Project, UUID> project, String targetObjectId);

    boolean existAnyRepresentationForProjectAndTargetObjectId(AggregateReference<Project, UUID> project, String targetObjectId);

    Optional<AggregateReference<Project, UUID>> findProjectByRepresentationId(UUID representationId);
}

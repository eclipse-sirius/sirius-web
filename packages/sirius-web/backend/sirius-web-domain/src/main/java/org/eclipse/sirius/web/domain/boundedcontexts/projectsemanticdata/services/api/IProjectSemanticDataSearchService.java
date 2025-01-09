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
package org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to retrieve the project semantic data.
 *
 * @author mcharfadi
 */
public interface IProjectSemanticDataSearchService {

    Optional<ProjectSemanticData> findByProjectId(AggregateReference<Project, String> project);

    List<ProjectSemanticData> findAllByProjectId(AggregateReference<Project, String> project);

    Optional<ProjectSemanticData> findByProjectIdAndName(AggregateReference<Project, String> project, String name);

    Optional<ProjectSemanticData> findBySemanticDataId(AggregateReference<SemanticData, UUID> semanticData);
}

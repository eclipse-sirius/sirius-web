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
package org.eclipse.sirius.web.domain.boundedcontexts.project.repositories;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository used to persist the project aggregate.
 *
 * @author sbegaudeau
 */
@Repository
public interface IProjectRepository extends ListCrudRepository<Project, String>, ProjectSearchRepository<Project, String> {
}

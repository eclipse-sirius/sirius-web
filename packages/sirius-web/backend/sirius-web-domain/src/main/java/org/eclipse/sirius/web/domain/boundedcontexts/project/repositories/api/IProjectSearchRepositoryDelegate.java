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
package org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api;

import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.ProjectSearchRepository;

/**
 * Used to delegate the behavior of the project search repository.
 *
 * @author sbegaudeau
 */
public interface IProjectSearchRepositoryDelegate extends ProjectSearchRepository<Project, UUID> {
}

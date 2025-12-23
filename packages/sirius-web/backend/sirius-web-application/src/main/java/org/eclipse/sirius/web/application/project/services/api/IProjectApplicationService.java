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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;

/**
 * Application services used to manipulate projects.
 *
 * @author sbegaudeau
 */
public interface IProjectApplicationService {
    Optional<ProjectDTO> findById(String id);

    Window<ProjectDTO> findAll(KeysetScrollPosition position, int limit, Map<String, Object> filter);

    IPayload createProject(ICreateProjectInput input);

    IPayload renameProject(RenameProjectInput input);

    IPayload deleteProject(DeleteProjectInput input);
}

/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;

/**
 * Application service used to search projects.
 *
 * @author sbegaudeau
 * @since v2026.1.0
 */
public interface IProjectSearchApplicationService {
    Optional<ProjectDTO> findById(String id);

    Window<ProjectDTO> findAll(KeysetScrollPosition position, int limit, Map<String, Object> filter);
}

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

import java.util.List;

import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Used to interact with project templates.
 *
 * @author sbegaudeau
 */
public interface IProjectTemplateApplicationService {
    Page<ProjectTemplateDTO> findAll(Pageable pageable, String context);
    List<ProjectTemplateDTO> findAll();
}

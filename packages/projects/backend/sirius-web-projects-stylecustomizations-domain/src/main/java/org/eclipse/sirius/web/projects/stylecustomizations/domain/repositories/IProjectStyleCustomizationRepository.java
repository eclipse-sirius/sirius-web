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
package org.eclipse.sirius.web.projects.stylecustomizations.domain.repositories;

import java.util.List;

import org.eclipse.sirius.web.projects.stylecustomizations.domain.ProjectStyleCustomization;

/**
 * Repository used to persist the project style customization aggregate.
 *
 * @author gcoutable
 * @since 2026.11.0
 */
public interface IProjectStyleCustomizationRepository {
    boolean existsByProjectIdAndStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId);

    List<ProjectStyleCustomization> findAllByProjectId(String projectId);

    ProjectStyleCustomization save(ProjectStyleCustomization projectStyleCustomization);

    void deleteAll();
}

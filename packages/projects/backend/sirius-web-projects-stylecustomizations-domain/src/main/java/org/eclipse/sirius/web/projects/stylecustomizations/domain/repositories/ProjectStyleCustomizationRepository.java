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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.sirius.web.projects.stylecustomizations.domain.ProjectStyleCustomization;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the project style customization aggregate.
 *
 * @author gcoutable
 */
@Repository
public class ProjectStyleCustomizationRepository implements IProjectStyleCustomizationRepository {

    private final Map<String, ProjectStyleCustomization> store = new ConcurrentHashMap<>();

    @Override
    public boolean existsByProjectIdAndStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId) {
        return true;
    }

    @Override
    public List<ProjectStyleCustomization> findAllByProjectId(String projectId) {
        return this.store.values().stream()
                .filter(projectStyleCustomization -> projectStyleCustomization.getProject().getId().equals(projectId))
                .toList();
    }

    @Override
    public ProjectStyleCustomization save(ProjectStyleCustomization projectStyleCustomization) {
        this.store.put(this.getStyleCustomizationDescriptionId(projectStyleCustomization.getProject().getId(), projectStyleCustomization.getStyleCustomizationDescriptionId()), projectStyleCustomization);
        return projectStyleCustomization;
    }

    @Override
    public void deleteAll() {
        this.store.clear();
    }

    private String getStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId) {
        return String.join("#", projectId, styleCustomizationDescriptionId);
    }

}

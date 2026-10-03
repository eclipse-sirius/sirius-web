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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the project style customization aggregate.
 *
 * @author gcoutable
 */
@Repository
public class ProjectStyleCustomizationStore {

    private final Set<String> store = new HashSet<>();

    public boolean existsByProjectIdAndStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId) {
        return this.store.contains(this.getStyleCustomizationDescriptionId(projectId, styleCustomizationDescriptionId));
    }

    public void createProjectStyleCustomization(String projectId, String styleCustomizationDescriptionId) {
        this.store.add(this.getStyleCustomizationDescriptionId(projectId, styleCustomizationDescriptionId));
    }

    public void deleteByProjectIdAndStyleDescriptionId(String projectId, String styleCustomizationDescriptionId) {
        this.store.remove(this.getStyleCustomizationDescriptionId(projectId, styleCustomizationDescriptionId));
    }

    public List<String> findAllByProjectId(String projectId) {
        return this.store.stream()
                .filter(styleCustomizationDescriptionId -> styleCustomizationDescriptionId.contains(projectId + "#"))
                .toList();
    }

    public void clear() {
        this.store.clear();
    }

    private String getStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId) {
        return String.join("#", projectId, styleCustomizationDescriptionId);
    }

}

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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.projects.stylecustomizations.domain.ProjectStyleCustomization;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository used to persist the project style customization aggregate.
 *
 * @author gcoutable
 */
@Repository
public interface IProjectStyleCustomizationRepository extends ListPagingAndSortingRepository<ProjectStyleCustomization, UUID>, ListCrudRepository<ProjectStyleCustomization, UUID> {

    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
        FROM project_style_customization projectStyleCustomization
        WHERE projectStyleCustomization.project_id = :projectId AND projectStyleCustomization.style_customization_description_id = :styleCustomizationDescriptionId
        """)
    boolean existsByProjectIdAndStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId);

    @Query("""
        SELECT * FROM project_style_customization projectStyleCustomization
        WHERE projectStyleCustomization.project_id = :projectId
        """)
    List<ProjectStyleCustomization> findAllByProjectId(String projectId);

    @Query("""
        SELECT * FROM project_style_customization projectStyleCustomization
        WHERE projectStyleCustomization.project_id = :projectId AND projectStyleCustomization.style_customization_description_id = :styleCustomizationDescriptionId
        """)
    Optional<ProjectStyleCustomization> findByProjectIdAndStyleCustomizationDescriptionId(String projectId, String styleCustomizationDescriptionId);
}

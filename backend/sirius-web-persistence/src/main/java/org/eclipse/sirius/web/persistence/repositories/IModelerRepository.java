/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ModelerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Persistence layer use to manipulate modelers.
 * <p>
 * The actual definitions for the named queries referenced here must be available at runtime in a file at
 * <code>META-INF/jpa-named-queries.properties</code>. For the Sirius Web example application it is in Typically it is
 * defined in <code>sirius-web-sample-application/src/main/resources/META-INF/jpa-named-queries.properties</code>.
 *
 * @author pcdavid
 */
public interface IModelerRepository extends PagingAndSortingRepository<ModelerEntity, UUID> {
    @Audited
    List<ModelerEntity> findAllByProjectId(UUID projectId);

    @Audited
    @Query("SELECT modeler FROM ModelerEntity modeler WHERE modeler.project.id=?1 AND modeler.id=?2")
    Optional<DocumentEntity> findByProjectIdAndId(UUID projectId, UUID modelerId);

    @Audited
    @Override
    Optional<ModelerEntity> findById(UUID id);

    @Audited
    @Override
    <S extends ModelerEntity> S save(S modelerEntity);

    @Audited
    @Override
    void deleteById(UUID id);

}

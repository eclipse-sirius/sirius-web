/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Audited;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer used to manipulate documents.
 *
 * @author sbegaudeau
 */
@Repository
public interface IDocumentRepository extends PagingAndSortingRepository<DocumentEntity, UUID>, ListCrudRepository<DocumentEntity, UUID> {

    @Audited
    @Override
    Optional<DocumentEntity> findById(UUID id);

    @Audited
    @Override
    List<DocumentEntity> findAll();

    @Audited
    default List<DocumentEntity> findAllByType(String name, String uri) {
        List<DocumentEntity> documents = this.findAll();
        return documents.stream().filter(doc -> {
            String jsonDocumentContent = doc.getContent();
            if (jsonDocumentContent != null && !jsonDocumentContent.isBlank()) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode jsonNode = objectMapper.readTree(jsonDocumentContent);
                    JsonNode nsProperty = jsonNode.get("ns");
                    if (nsProperty != null) {
                        JsonNode nsUri = nsProperty.get(name);
                        if (nsUri != null) {
                            return Objects.equals(nsUri.asText(), uri);
                        }
                    }
                } catch (JsonMappingException e) {
                } catch (JsonProcessingException e) {
                }
            }
            return false;
        }).toList();
    }

    @Audited
    List<DocumentEntity> findAllByProjectId(UUID projectId);

    @Audited
    @Query(value = """
            SELECT document FROM DocumentEntity document
            WHERE document.project.id=?1 AND document.id=?2
            """)
    Optional<DocumentEntity> findByProjectIdAndId(UUID projectId, UUID documentId);

    @Audited
    @Override
    void deleteById(UUID id);

    @Audited
    @Override
    <S extends DocumentEntity> S save(S entity);
}

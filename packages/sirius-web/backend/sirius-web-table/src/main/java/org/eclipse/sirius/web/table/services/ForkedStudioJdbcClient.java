/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

package org.eclipse.sirius.web.table.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation IForkedStudioJdbcServices used to update a forked studio.
 *
 * @author mcharfadi
 */
@Service
@Transactional
public class ForkedStudioJdbcClient implements IForkedStudioJdbcServices {

    public static final String STUDIO_NATURE = "siriusComponents://nature?kind=studio";

    private final JdbcClient jdbcClient;

    private final IRepresentationContentUpdateService representationContentUpdateService;

    private final IRepresentationContentSearchService representationContentSearchService;

    private final IProjectCreationService projectCreationService;

    public ForkedStudioJdbcClient(JdbcClient jdbcClient, IRepresentationContentUpdateService representationContentUpdateService, IRepresentationContentSearchService representationContentSearchService, IProjectCreationService projectCreationService) {
        this.jdbcClient = Objects.requireNonNull(jdbcClient);
        this.representationContentUpdateService = Objects.requireNonNull(representationContentUpdateService);
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
    }

    @Override
    public void insertViewDocument(UUID documentId, UUID semanticProjectId, String content, String name) {
        String sql = "INSERT INTO document (id, semantic_data_id, name, content, created_on, last_modified_on) VALUES (:id, :semantic_data_id, :name, :content, :created_on, :last_modified_on)";

        var now = Instant.now();
        Timestamp current = Timestamp.from(now);

        jdbcClient.sql(sql).param("id", documentId)
                .param("name", name)
                .param("semantic_data_id", semanticProjectId)
                .param("content", content)
                .param("created_on", current)
                .param("last_modified_on", current)
                .update();
    }

    @Override
    public void updateRepresentationMetataDataDescriptionId(UUID id, String newRepresentationDescriptionId) {
        String sql = "UPDATE representation_metadata SET description_id = :description_id WHERE id = :id";
        jdbcClient.sql(sql).param("description_id", newRepresentationDescriptionId)
                .param("id", id)
                .update();
    }

    @Override
    public void updateSemanticDataDomainProjectId(UUID id) {
        String sql = "INSERT INTO semantic_data_domain (semantic_data_id, uri) VALUES (:semantic_data_id, :uri)";

        jdbcClient.sql(sql).param("semantic_data_id", id)
                .param("uri", "http://www.eclipse.org/sirius-web/view")
                .update();
    }

    @Override
    public void updateRepresentationContentDescriptionId(IRepresentationInput representationInput, String oldDescriptionId, String newDescriptionId, String oldSourceId, String newSourceId) {
        var optionalRepresentationId = getRepresentationId(representationInput.representationId());
        if (optionalRepresentationId.isPresent()) {
            var representationContent = this.representationContentSearchService.findContentById(UUID.fromString(optionalRepresentationId.get()));
            if (representationContent.isPresent()) {
                //  Update the descriptionId of the current representation
                var newContent = representationContent.get().getContent()
                        .replace(oldDescriptionId, newDescriptionId)
                        .replace(oldSourceId, newSourceId);

                this.representationContentUpdateService.updateContentByRepresentationId(representationInput, UUID.fromString(optionalRepresentationId.get()), newContent);
            }
        }
    }

    @Override
    public Optional<Project> createStudioProject(IRepresentationInput representationInput, String newName) {
        var studioNatures = List.of(STUDIO_NATURE);
        var result = this.projectCreationService.createProject(representationInput, newName, studioNatures);
        if (result instanceof Success<Project> success && success.data() != null) {
            return Optional.of(success.data());
        } else {
            return  Optional.empty();
        }
    }

    private Optional<String> getRepresentationId(String descriptionId) {
        var id = descriptionId.split("\\?cursor");
        return Optional.ofNullable(id[0]);
    }

}
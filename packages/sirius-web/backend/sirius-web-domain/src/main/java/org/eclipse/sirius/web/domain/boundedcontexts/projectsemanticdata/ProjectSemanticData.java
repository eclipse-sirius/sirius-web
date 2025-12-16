/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * The aggregate root of the project semantic data bounded context.
 *
 * @author mcharfadi
 */
@Table("project_semantic_data")
public class ProjectSemanticData extends AbstractValidatingAggregateRoot<ProjectSemanticData> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, String> project;

    @Column("semantic_data_id")
    private AggregateReference<SemanticData, UUID> semanticData;

    @Column("name")
    private String name;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, String> getProject() {
        return this.project;
    }

    public AggregateReference<SemanticData, UUID> getSemanticData() {
        return this.semanticData;
    }

    public String getName() {
        return this.name;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Instant getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public static ProjectSemanticData.Builder newProjectSemanticData() {
        return new ProjectSemanticData.Builder();
    }

    public void dispose(ICause cause) {
        this.registerEvent(new ProjectSemanticDataDeletedEvent(UUID.randomUUID(), Instant.now(), cause, this));
    }

    /**
     * Used to create new project semantic data.
     *
     * @author mcharfadi
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private AggregateReference<Project, String> project;

        private AggregateReference<SemanticData, UUID> semanticData;

        private String name;

        public ProjectSemanticData.Builder project(AggregateReference<Project, String> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public ProjectSemanticData.Builder semanticData(AggregateReference<SemanticData, UUID> semanticData) {
            this.semanticData = Objects.requireNonNull(semanticData);
            return this;
        }

        public ProjectSemanticData.Builder name(String name) {
            this.name = name;
            return this;
        }

        public ProjectSemanticData build(ICause cause) {
            var projectSemanticData = new ProjectSemanticData();

            projectSemanticData.isNew = true;
            projectSemanticData.id = UUID.randomUUID();
            projectSemanticData.project = Objects.requireNonNull(this.project);
            projectSemanticData.semanticData = Objects.requireNonNull(this.semanticData);
            projectSemanticData.name = Objects.requireNonNull(this.name);

            var now = Instant.now();
            projectSemanticData.createdOn = now;
            projectSemanticData.lastModifiedOn = now;

            projectSemanticData.registerEvent(new ProjectSemanticDataCreatedEvent(UUID.randomUUID(), now, cause, projectSemanticData));
            return projectSemanticData;
        }
    }
}
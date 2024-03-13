/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the semantic data bounded context.
 *
 * @author sbegaudeau
 */
@Table("semantic_data")
public class SemanticData extends AbstractValidatingAggregateRoot<SemanticData> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, UUID> project;

    @MappedCollection(idColumn = "semantic_data_id")
    private Set<Document> documents = new LinkedHashSet<>();

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, UUID> getProject() {
        return this.project;
    }

    public Set<Document> getDocuments() {
        return Collections.unmodifiableSet(this.documents);
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

    public void updateDocuments(ICause cause, Set<Document> newDocuments) {
        this.documents = newDocuments;

        this.lastModifiedOn = Instant.now();
        this.registerEvent(new SemanticDataUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
    }

    public static Builder newSemanticData() {
        return new Builder();
    }

    /**
     * Used to create new semantic data.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private AggregateReference<Project, UUID> project;

        private Set<Document> documents = new LinkedHashSet<>();

        public Builder project(AggregateReference<Project, UUID> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder documents(Set<Document> documents) {
            this.documents = Objects.requireNonNull(documents);
            return this;
        }

        public SemanticData build(ICause cause) {
            var semanticData = new SemanticData();

            semanticData.isNew = true;
            semanticData.id = UUID.randomUUID();
            semanticData.project = Objects.requireNonNull(project);
            semanticData.documents = Objects.requireNonNull(this.documents);

            var now = Instant.now();
            semanticData.createdOn = now;
            semanticData.lastModifiedOn = now;

            semanticData.registerEvent(new SemanticDataCreatedEvent(UUID.randomUUID(), now, cause, semanticData));
            return semanticData;
        }
    }
}

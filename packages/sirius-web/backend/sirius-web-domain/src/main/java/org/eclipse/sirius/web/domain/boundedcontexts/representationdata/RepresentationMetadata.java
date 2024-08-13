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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataDeletedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the representation metadata bounded context.
 *
 * @author sbegaudeau
 */
@Table("representation_metadata")
public class RepresentationMetadata extends AbstractValidatingAggregateRoot<RepresentationMetadata> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, UUID> project;

    private String targetObjectId;

    private String descriptionId;

    private String label;

    private String kind;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, UUID> getProject() {
        return this.project;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Instant getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    public void dispose(ICause cause) {
        this.registerEvent(new RepresentationMetadataDeletedEvent(UUID.randomUUID(), Instant.now(), cause, this));
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public static Builder newRepresentationMetadata(UUID id) {
        return new Builder(id);
    }

    /**
     * Used to create new representation metadata.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private AggregateReference<Project, UUID> project;

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private String kind;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder project(AggregateReference<Project, UUID> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public RepresentationMetadata build(ICause cause) {
            var representationMetadata = new RepresentationMetadata();
            representationMetadata.isNew = true;
            representationMetadata.id = Objects.requireNonNull(this.id);
            representationMetadata.project = Objects.requireNonNull(this.project);
            representationMetadata.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            representationMetadata.descriptionId = Objects.requireNonNull(this.descriptionId);
            representationMetadata.label = Objects.requireNonNull(this.label);
            representationMetadata.kind = Objects.requireNonNull(this.kind);

            var now = Instant.now();
            representationMetadata.createdOn = now;
            representationMetadata.lastModifiedOn = now;

            representationMetadata.registerEvent(new RepresentationMetadataCreatedEvent(UUID.randomUUID(), now, cause, representationMetadata));
            return representationMetadata;
        }
    }
}

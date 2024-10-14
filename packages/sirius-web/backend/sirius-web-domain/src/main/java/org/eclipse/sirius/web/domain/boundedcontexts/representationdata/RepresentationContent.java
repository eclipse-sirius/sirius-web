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
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationContentCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationContentUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the representation content bounded context.
 *
 * @author gcoutable
 */
@Table("representation_content")
public class RepresentationContent extends AbstractValidatingAggregateRoot<RepresentationContent> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("representation_metadata_id")
    private AggregateReference<RepresentationMetadata, UUID> representationMetadata;

    private String content;

    private String lastMigrationPerformed;

    private String migrationVersion;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public String getLastMigrationPerformed() {
        return this.lastMigrationPerformed;
    }

    public String getMigrationVersion() {
        return this.migrationVersion;
    }

    public Instant getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    public void updateContent(ICause cause, String newContent) {
        if (!Objects.equals(this.content, newContent)) {
            this.content = newContent;

            var now = Instant.now();
            this.lastModifiedOn = now;

            this.registerEvent(new RepresentationContentUpdatedEvent(UUID.randomUUID(), now, cause, this));
        }
    }

    public void updateMigrationData(String newLastMigrationPerformed, String newMigrationVersion) {
        if (!Objects.equals(this.migrationVersion, newMigrationVersion)) {
            this.lastMigrationPerformed = Objects.requireNonNull(newLastMigrationPerformed);
            this.migrationVersion = Objects.requireNonNull(newMigrationVersion);
            this.lastModifiedOn = Instant.now();
        }
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public static Builder newRepresentationContent(UUID id) {
        return new Builder(id);
    }

    /**
     * Used to create new representation content.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private AggregateReference<RepresentationMetadata, UUID> representationMetadata;

        private String content;

        private String lastMigrationPerformed;

        private String migrationVersion;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder representationMetadata(AggregateReference<RepresentationMetadata, UUID> representationMetadata) {
            this.representationMetadata = Objects.requireNonNull(representationMetadata);
            return this;
        }

        public Builder content(String content) {
            this.content = Objects.requireNonNull(content);
            return this;
        }

        public Builder lastMigrationPerformed(String lastMigrationPerformed) {
            this.lastMigrationPerformed = Objects.requireNonNull(lastMigrationPerformed);
            return this;
        }

        public Builder migrationVersion(String migrationVersion) {
            this.migrationVersion = Objects.requireNonNull(migrationVersion);
            return this;
        }

        public RepresentationContent build(ICause cause) {
            var representationContent = new RepresentationContent();
            representationContent.isNew = true;
            representationContent.id = Objects.requireNonNull(this.id);
            representationContent.representationMetadata = Objects.requireNonNull(this.representationMetadata);
            representationContent.content = Objects.requireNonNull(this.content);
            representationContent.lastMigrationPerformed = Objects.requireNonNull(this.lastMigrationPerformed);
            representationContent.migrationVersion = Objects.requireNonNull(this.migrationVersion);

            var now = Instant.now();
            representationContent.createdOn = now;
            representationContent.lastModifiedOn = now;

            representationContent.registerEvent(new RepresentationContentCreatedEvent(UUID.randomUUID(), now, cause, representationContent));
            return representationContent;
        }

    }
}

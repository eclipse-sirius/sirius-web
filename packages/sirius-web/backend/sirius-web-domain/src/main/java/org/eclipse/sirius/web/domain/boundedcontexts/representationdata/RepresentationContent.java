/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
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
public class RepresentationContent extends AbstractValidatingAggregateRoot<RepresentationContent> implements Persistable<String> {

    @Transient
    private boolean isNew;

    @Id
    private String id;

    private UUID representationMetadataId;

    @Column("semantic_data_id")
    private AggregateReference<SemanticData, UUID> semanticData;

    private String content;

    private String lastMigrationPerformed;

    private String migrationVersion;

    private Instant createdOn;

    private Instant lastModifiedOn;

    /**
     * Returns the identifier of the representation content. This identifier is unique for the entire server.
     *
     * @return The identifier for the representation content
     *
     * @technical-debt This identifier has been switched from a UUID to a String which encapsulates two pieces of data
     * the semantic data id and the representation metadata id in order to satisfy a limitation that we have with Spring
     * Boot 3.x. Part of this limitation will not exist in Spring Boot 4.x and this identifier will then be modified again.
     * This previous value of this identifier can now be found in {@code getRepresentationMetadata}. This identifier
     * should not be used at all unless you know exactly what you are doing.
     */
    @Deprecated(forRemoval = true)
    @Override
    public String getId() {
        return this.id;
    }

    public UUID getRepresentationMetadataId() {
        return this.representationMetadataId;
    }

    public AggregateReference<SemanticData, UUID> getSemanticData() {
        return this.semanticData;
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

    public static Builder newRepresentationContent(String id) {
        return new Builder(id);
    }

    /**
     * Used to create new representation content.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private UUID representationMetadataId;

        private AggregateReference<SemanticData, UUID> semanticData;

        private String content;

        private String lastMigrationPerformed;

        private String migrationVersion;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder representationMetadataId(UUID representationMetadataId) {
            this.representationMetadataId = Objects.requireNonNull(representationMetadataId);
            return this;
        }

        public Builder semanticData(AggregateReference<SemanticData, UUID> semanticData) {
            this.semanticData = Objects.requireNonNull(semanticData);
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
            representationContent.representationMetadataId = Objects.requireNonNull(this.representationMetadataId);
            representationContent.semanticData = Objects.requireNonNull(this.semanticData);
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

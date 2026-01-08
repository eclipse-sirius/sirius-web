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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the representation metadata bounded context.
 *
 * @author sbegaudeau
 */
@Table("representation_metadata")
public class RepresentationMetadata extends AbstractValidatingAggregateRoot<RepresentationMetadata> implements Persistable<String> {

    @Transient
    private boolean isNew;

    @Id
    private String id;

    private UUID representationMetadataId;

    @Column("semantic_data_id")
    private AggregateReference<SemanticData, UUID> semanticData;

    private String targetObjectId;

    private String descriptionId;

    private String label;

    private String kind;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @MappedCollection(idColumn = "representation_metadata_id", keyColumn = "index")
    private List<RepresentationIconURL> iconURLs = new ArrayList<>();

    private String documentation;

    /**
     * Returns the identifier of the representation metadata. This identifier is unique for the entire server.
     *
     * @return The identifier for the representation metadata
     *
     * @technical-debt This identifier has been switched from a UUID to a String which encapsulates two pieces of data
     * the semantic data id and the representation metadata id in order to satisfy a limitation that we have with Spring
     * Boot 3.x. Part of this limitation will not exist in Spring Boot 4.x and this identifier will then be modified again.
     * This previous value of this identifier can now be found in {@code getRepresentationMetadataId}. This identifier
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

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    public void updateLabel(ICause cause, String newLabel) {
        if (!this.label.equals(newLabel)) {
            this.label = newLabel;

            var now = Instant.now();
            this.lastModifiedOn = now;

            this.registerEvent(new RepresentationMetadataUpdatedEvent(UUID.randomUUID(), now, cause, this));
        }
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

    public List<RepresentationIconURL> getIconURLs() {
        return Collections.unmodifiableList(this.iconURLs);
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public void updateDocumentation(ICause cause, String newDocumentation) {
        if (this.documentation.isEmpty() || !this.documentation.equals(newDocumentation)) {
            this.documentation = newDocumentation;

            var now = Instant.now();
            this.lastModifiedOn = now;

            this.registerEvent(new RepresentationMetadataUpdatedEvent(UUID.randomUUID(), now, cause, this));
        }
    }

    public void updateDescriptionId(ICause cause, String newDescriptionId) {
        if (!newDescriptionId.isEmpty() || !this.descriptionId.equals(newDescriptionId)) {
            this.descriptionId = newDescriptionId;

            var now = Instant.now();
            this.lastModifiedOn = now;

            this.registerEvent(new RepresentationMetadataUpdatedEvent(UUID.randomUUID(), now, cause, this));
        }
    }

    public void updateTargetObjectId(ICause cause, String newTargetObjectId) {
        if (!newTargetObjectId.isEmpty() || !this.targetObjectId.equals(newTargetObjectId)) {
            this.targetObjectId = newTargetObjectId;

            var now = Instant.now();
            this.lastModifiedOn = now;

            this.registerEvent(new RepresentationMetadataUpdatedEvent(UUID.randomUUID(), now, cause, this));
        }
    }

    public void dispose(ICause cause) {
        this.registerEvent(new RepresentationMetadataDeletedEvent(UUID.randomUUID(), Instant.now(), cause, this));
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public static Builder newRepresentationMetadata(String id) {
        return new Builder(id);
    }

    /**
     * Used to create new representation metadata.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private UUID representationMetadataId;

        private AggregateReference<SemanticData, UUID> semanticData;

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private String kind;

        private List<RepresentationIconURL> iconURLs;

        private String documentation;

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

        public Builder iconURLs(List<RepresentationIconURL> iconURLs) {
            this.iconURLs = Objects.requireNonNull(iconURLs);
            return this;
        }

        public Builder documentation(String documentation) {
            this.documentation = Objects.requireNonNull(documentation);
            return this;
        }

        public RepresentationMetadata build(ICause cause) {
            var representationMetadata = new RepresentationMetadata();
            representationMetadata.isNew = true;
            representationMetadata.id = Objects.requireNonNull(this.id);
            representationMetadata.representationMetadataId = Objects.requireNonNull(this.representationMetadataId);
            representationMetadata.semanticData = Objects.requireNonNull(this.semanticData);
            representationMetadata.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            representationMetadata.descriptionId = Objects.requireNonNull(this.descriptionId);
            representationMetadata.label = Objects.requireNonNull(this.label);
            representationMetadata.kind = Objects.requireNonNull(this.kind);
            representationMetadata.iconURLs = Objects.requireNonNull(this.iconURLs);
            representationMetadata.documentation = Objects.requireNonNull(this.documentation);

            var now = Instant.now();
            representationMetadata.createdOn = now;
            representationMetadata.lastModifiedOn = now;

            representationMetadata.registerEvent(new RepresentationMetadataCreatedEvent(UUID.randomUUID(), now, cause, representationMetadata));
            return representationMetadata;
        }
    }
}

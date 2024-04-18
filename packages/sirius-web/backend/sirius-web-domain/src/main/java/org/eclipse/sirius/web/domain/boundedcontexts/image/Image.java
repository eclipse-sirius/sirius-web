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
package org.eclipse.sirius.web.domain.boundedcontexts.image;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.image.events.ImageCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.image.events.ImageDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.image.events.ImageLabelUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the image bounded context.
 *
 * @author sbegaudeau
 */
@Table("image")
public class Image extends AbstractValidatingAggregateRoot<Image> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, UUID> project;

    private String label;

    private String contentType;

    private byte[] content;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public void updateLabel(String newLabel) {
        if (!Objects.equals(this.label, newLabel)) {
            this.label = newLabel;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new ImageLabelUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, this));
        }
    }

    public String getContentType() {
        return this.contentType;
    }

    public byte[] getContent() {
        return this.content;
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

    public void dispose() {
        this.registerEvent(new ImageDeletedEvent(UUID.randomUUID(), Instant.now(), this));
    }

    public static Builder newImage() {
        return new Builder();
    }

    /**
     * Used to create new images.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private AggregateReference<Project, UUID> project;

        private String label;

        private String contentType;

        private byte[] content;

        public Builder project(AggregateReference<Project, UUID> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = Objects.requireNonNull(contentType);
            return this;
        }

        public Builder content(byte[] content) {
            this.content = Objects.requireNonNull(content);
            return this;
        }

        public Image build() {
            var image = new Image();

            image.isNew = true;
            image.id = UUID.randomUUID();
            image.project = Objects.requireNonNull(this.project);
            image.label = Objects.requireNonNull(this.label);
            image.contentType = Objects.requireNonNull(this.contentType);
            image.content = Objects.requireNonNull(this.content);

            var now = Instant.now();
            image.createdOn = now;
            image.lastModifiedOn = now;

            image.registerEvent(new ImageCreatedEvent(UUID.randomUUID(), now, image));
            return image;
        }
    }
}

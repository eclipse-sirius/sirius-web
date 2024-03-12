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

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.image.events.ImageCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.image.events.ImageDeletedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
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

    public void dispose(ICause cause) {
        this.registerEvent(new ImageDeletedEvent(UUID.randomUUID(), Instant.now(), cause, this));
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

        private String label;

        private String contentType;

        private byte[] content;

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

        public Image build(ICause cause) {
            var image = new Image();

            image.isNew = true;
            image.id = UUID.nameUUIDFromBytes(this.content);
            image.label = Objects.requireNonNull(this.label);
            image.contentType = Objects.requireNonNull(this.contentType);
            image.content = Objects.requireNonNull(this.content);

            var now = Instant.now();
            image.createdOn = now;
            image.lastModifiedOn = now;

            image.registerEvent(new ImageCreatedEvent(UUID.randomUUID(), now, cause, image));
            return image;
        }
    }
}

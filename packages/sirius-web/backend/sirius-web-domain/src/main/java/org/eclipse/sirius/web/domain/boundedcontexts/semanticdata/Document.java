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
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.relational.core.mapping.Table;

/**
 * The document entity of the semantic data aggregate.
 *
 * @author sbegaudeau
 */
@Table("document")
public class Document {

    private UUID id;

    private String name;

    private String content;

    private Instant createdOn;

    private Instant lastModifiedOn;

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Instant getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    public static Builder newDocument(UUID id) {
        return new Builder(id);
    }

    /**
     * Used to create new documents.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private UUID id;

        private String name;

        private String content;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder content(String content) {
            this.content = Objects.requireNonNull(content);
            return this;
        }

        public Document build() {
            var document = new Document();
            document.id = Objects.requireNonNull(this.id);
            document.name = Objects.requireNonNull(this.name);
            document.content = Objects.requireNonNull(this.content);

            var now = Instant.now();
            document.createdOn = now;
            document.lastModifiedOn = now;

            return document;
        }
    }
}

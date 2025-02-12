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
package org.eclipse.sirius.web.domain.boundedcontexts.library;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the library bounded context.
 *
 * @author gdaniel
 */
@Table("library")
public class Library extends AbstractValidatingAggregateRoot<Library> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    private String namespace;

    private String name;

    private String version;

    @Column("semantic_data_id")
    private AggregateReference<SemanticData, UUID> semanticData;

    @MappedCollection(idColumn = "library_id", keyColumn = "index")
    private Set<LibraryDependency> dependencies = new LinkedHashSet<>();

    private String description;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public AggregateReference<SemanticData, UUID> getSemanticData() {
        return this.semanticData;
    }

    public Set<LibraryDependency> getDependencies() {
        return Collections.unmodifiableSet(this.dependencies);
    }

    public String getDescription() {
        return this.description;
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

    public static Builder newLibrary() {
        return new Builder();
    }

    /**
     * Used to create new libraries.
     *
     * @author gdaniel
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String namespace;

        private String name;

        private String version;

        private AggregateReference<SemanticData, UUID> semanticData;

        private Set<LibraryDependency> dependencies = new LinkedHashSet<>();

        private String description;

        public Builder namespace(String namespace) {
            this.namespace = Objects.requireNonNull(namespace);
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder version(String version) {
            this.version = Objects.requireNonNull(version);
            return this;
        }

        public Builder semanticData(AggregateReference<SemanticData, UUID> semanticData) {
            this.semanticData = Objects.requireNonNull(semanticData);
            return this;
        }

        public Builder dependencies(List<AggregateReference<Library, UUID>> dependencies) {
            this.dependencies = dependencies.stream()
                .map(LibraryDependency::new)
                .collect(Collectors.toSet());
            return this;
        }

        public Builder description(String description) {
            this.description = Objects.requireNonNull(description);
            return this;
        }

        public Library build(ICause cause) {
            var library = new Library();
            library.isNew = true;
            library.id = UUID.randomUUID();
            library.namespace = Objects.requireNonNull(this.namespace);
            library.name = Objects.requireNonNull(this.name);
            library.version = Objects.requireNonNull(this.version);
            library.semanticData = Objects.requireNonNull(this.semanticData);
            library.dependencies = Objects.requireNonNull(this.dependencies);
            library.description = Objects.requireNonNull(this.description);

            var now = Instant.now();
            library.createdOn = now;
            library.lastModifiedOn = now;

            library.registerEvent(new LibraryCreatedEvent(UUID.randomUUID(), now, cause, library));
            return library;
        }
    }

}

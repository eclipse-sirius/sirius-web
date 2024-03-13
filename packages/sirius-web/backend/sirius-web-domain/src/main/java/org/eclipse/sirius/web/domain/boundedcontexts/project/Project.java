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
package org.eclipse.sirius.web.domain.boundedcontexts.project;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNatureAddedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNatureRemovedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the project bounded context.
 *
 * @author sbegaudeau
 */
@Table("project")
public class Project extends AbstractValidatingAggregateRoot<Project> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    private String name;

    @MappedCollection(idColumn = "project_id")
    private Set<Nature> natures = new LinkedHashSet<>();

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void updateName(String newName) {
        this.name = newName;
        this.lastModifiedOn = Instant.now();

        this.registerEvent(new ProjectNameUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, this));
    }

    public Set<Nature> getNatures() {
        return Collections.unmodifiableSet(this.natures);
    }

    public void addNature(String natureName) {
        var newNature = new Nature(natureName);

        this.natures.add(newNature);
        this.lastModifiedOn = Instant.now();

        this.registerEvent(new ProjectNatureAddedEvent(UUID.randomUUID(), this.lastModifiedOn, this, newNature));
    }

    public void removeNature(String natureName) {
        this.natures.stream()
                .filter(nature -> nature.name().equals(natureName))
                .findFirst()
                .ifPresent(nature -> {
                    this.natures.remove(nature);
                    this.lastModifiedOn = Instant.now();

                    this.registerEvent(new ProjectNatureRemovedEvent(UUID.randomUUID(), this.lastModifiedOn, this, nature));
                });
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
        this.registerEvent(new ProjectDeletedEvent(UUID.randomUUID(), Instant.now(), this));
    }

    public static Builder newProject() {
        return new Builder();
    }

    /**
     * Used to create new projects.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String name;

        private Set<Nature> natures = new LinkedHashSet<>();

        private Builder() {
            // Prevent instantiation
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder natures(Collection<String> natures) {
            this.natures = natures.stream()
                    .map(Nature::new)
                    .collect(Collectors.toSet());
            return this;
        }

        public Project build(ICause cause) {
            var project = new Project();
            project.isNew = true;
            project.id = UUID.randomUUID();
            project.name = Objects.requireNonNull(this.name);
            project.natures = Objects.requireNonNull(this.natures);

            var now = Instant.now();
            project.createdOn = now;
            project.lastModifiedOn = now;

            project.registerEvent(new ProjectCreatedEvent(UUID.randomUUID(), now, cause, project));
            return project;
        }
    }
}

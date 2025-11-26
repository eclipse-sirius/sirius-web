/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNaturesAddedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNaturesRemovedEvent;
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
public class Project extends AbstractValidatingAggregateRoot<Project> implements Persistable<String> {

    @Transient
    private boolean isNew;

    @Id
    private String id;

    private String name;

    @MappedCollection(idColumn = "project_id")
    private Set<Nature> natures = new LinkedHashSet<>();

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void updateName(ICause cause, String newName) {
        if (!Objects.equals(this.name, newName)) {
            this.name = newName;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new ProjectNameUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
        }
    }

    public Set<Nature> getNatures() {
        return Collections.unmodifiableSet(this.natures);
    }

    public void addNatures(ICause cause, List<String> natureNames) {
        var newNatures = natureNames.stream()
                .map(Nature::new)
                .toList();

        this.natures.addAll(newNatures);
        this.lastModifiedOn = Instant.now();

        this.registerEvent(new ProjectNaturesAddedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this, newNatures));
    }

    public void removeNatures(ICause cause, List<String> natureNames) {
        var naturesToRemove = this.natures.stream()
                .filter(nature -> natureNames.contains(nature.name()))
                .toList();
        naturesToRemove.forEach(this.natures::remove);
        this.lastModifiedOn = Instant.now();

        this.registerEvent(new ProjectNaturesRemovedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this, naturesToRemove));
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
        this.registerEvent(new ProjectDeletedEvent(UUID.randomUUID(), Instant.now(), cause, this));
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
            project.id = UUID.randomUUID().toString();
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

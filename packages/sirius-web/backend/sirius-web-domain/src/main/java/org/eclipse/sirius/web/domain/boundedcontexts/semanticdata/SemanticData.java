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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata;

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
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the semantic data bounded context.
 *
 * @author sbegaudeau
 */
@Table("semantic_data")
public class SemanticData extends AbstractValidatingAggregateRoot<SemanticData> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, String> project;

    @MappedCollection(idColumn = "semantic_data_id")
    private Set<Document> documents = new LinkedHashSet<>();

    @MappedCollection(idColumn = "semantic_data_id")
    private Set<SemanticDataDomain> domains = new LinkedHashSet<>();

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, String> getProject() {
        return this.project;
    }

    public Set<Document> getDocuments() {
        return Collections.unmodifiableSet(this.documents);
    }

    public Set<SemanticDataDomain> getDomains() {
        return Collections.unmodifiableSet(this.domains);
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

    public void updateDocuments(ICause cause, Set<Document> newDocuments, Set<String> domainUris) {
        boolean shouldBeUpdated = false;

        Set<Document> documentsToSet = new LinkedHashSet<>();
        for (var document : newDocuments) {
            var optionalExistingDocument = this.documents.stream()
                    .filter(existingDocument -> existingDocument.getId().equals(document.getId()))
                    .findFirst();

            if (optionalExistingDocument.isPresent()) {
                var existingDocument = optionalExistingDocument.get();
                if (this.sameContent(existingDocument, document)) {
                    // Reuse the existing instance, timestamps included
                    documentsToSet.add(existingDocument);
                } else {
                    var newDocument = Document.newDocument(existingDocument.getId())
                            .name(document.getName())
                            .content(document.getContent())
                            .build();
                    documentsToSet.add(newDocument);
                    shouldBeUpdated = true;
                }
            } else {
                // New document which did not exist before
                documentsToSet.add(document);
                shouldBeUpdated = true;
            }
        }
        // The previous code will not detect the removal of an existing document as cause for update, so also check if the set of document ids has changed
        shouldBeUpdated = shouldBeUpdated || !this.documents.stream()
                .map(Document::getId)
                .collect(Collectors.toSet())
                .equals(documentsToSet.stream()
                        .map(Document::getId)
                        .collect(Collectors.toSet()));

        if (shouldBeUpdated) {
            this.doUpdateDocuments(cause, documentsToSet, domainUris);
        }
    }

    private boolean sameContent(Document currentDocument, Document newDocument) {
        return currentDocument.getId().equals(newDocument.getId())
                && currentDocument.getName().equals(newDocument.getName())
                && currentDocument.getContent().equals(newDocument.getContent());
    }

    private void doUpdateDocuments(ICause cause, Set<Document> newDocuments, Set<String> domainUris) {
        this.documents = newDocuments;
        this.domains = domainUris.stream()
                .map(SemanticDataDomain::new)
                .collect(Collectors.toSet());

        this.lastModifiedOn = Instant.now();
        this.registerEvent(new SemanticDataUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
    }

    public static Builder newSemanticData() {
        return new Builder();
    }

    /**
     * Used to create new semantic data.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private AggregateReference<Project, String> project;

        private Set<Document> documents = new LinkedHashSet<>();

        private Set<SemanticDataDomain> domains = new LinkedHashSet<>();

        public Builder project(AggregateReference<Project, String> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder documents(Set<Document> documents) {
            this.documents = Objects.requireNonNull(documents);
            return this;
        }

        public Builder domains(Collection<String> domains) {
            this.domains = domains.stream()
                    .map(SemanticDataDomain::new)
                    .collect(Collectors.toSet());
            return this;
        }

        public SemanticData build(ICause cause) {
            var semanticData = new SemanticData();

            semanticData.isNew = true;
            semanticData.id = UUID.randomUUID();
            semanticData.project = Objects.requireNonNull(this.project);
            semanticData.documents = Objects.requireNonNull(this.documents);
            semanticData.domains = Objects.requireNonNull(this.domains);

            var now = Instant.now();
            semanticData.createdOn = now;
            semanticData.lastModifiedOn = now;

            semanticData.registerEvent(new SemanticDataCreatedEvent(UUID.randomUUID(), now, cause, semanticData));
            return semanticData;
        }
    }
}

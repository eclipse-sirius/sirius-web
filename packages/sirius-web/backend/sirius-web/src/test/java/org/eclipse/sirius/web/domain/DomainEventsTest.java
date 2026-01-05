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
package org.eclipse.sirius.web.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationContentUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationMetadataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDomain;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for domain events publication.
 *
 * @author pcdavid
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DomainEventsTest extends AbstractIntegrationTests {

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectUpdateService projectUpdateService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private ISemanticDataUpdateService semanticDataUpdateService;

    @Autowired
    private IRepresentationContentSearchService representationContentSearchService;

    @Autowired
    private IRepresentationContentUpdateService representationContentUpdateService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private IRepresentationMetadataUpdateService representationMetadataUpdateService;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, changing its name produces a domain event")
    public void givenProjectWhenNameModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();

        var newProjectName = "renamed project";
        this.projectUpdateService.renameProject(null, TestIdentifiers.ECORE_SAMPLE_PROJECT, newProjectName);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof ProjectNameUpdatedEvent projectNameUpdateEvent && projectNameUpdateEvent.project().getName().equals(newProjectName)).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, updating its name with the same value does not produce a domain event")
    public void givenProjectWhenNameNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();

        var sampleProject = this.projectSearchService.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT).get();
        this.projectUpdateService.renameProject(null, TestIdentifiers.ECORE_SAMPLE_PROJECT, sampleProject.getName());
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a document, changing its name produces a domain event")
    public void givenDocumentWhenNameModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, String> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(projectId);
        assertThat(optionalProjectSemanticData).isPresent();

        var projectSemanticData = optionalProjectSemanticData.get();

        var optionalSemanticData = this.semanticDataSearchService.findById(projectSemanticData.getSemanticData().getId());
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var newDocumentName = "renamed document";
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(newDocumentName).content(originalDocument.getContent()).build();
        this.semanticDataUpdateService.updateDocuments(null, projectSemanticData.getSemanticData(), Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof SemanticDataUpdatedEvent semanticDataUpdateEvent && semanticDataUpdateEvent.semanticData().getDocuments().iterator().next().getName().equals(newDocumentName)).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a document, updating its name and content with the same value does not produce a domain event")
    public void givenDocumentWhenNameAndContentNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, String> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(projectId);
        assertThat(optionalProjectSemanticData).isPresent();

        var projectSemanticData = optionalProjectSemanticData.get();

        var optionalSemanticData = this.semanticDataSearchService.findById(projectSemanticData.getSemanticData().getId());
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var newDocumentName = originalDocument.getName();
        // Identical to the original except for the timestamps
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(newDocumentName).content(originalDocument.getContent()).build();
        this.semanticDataUpdateService.updateDocuments(null, projectSemanticData.getSemanticData(), Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a document, when its content is updated with a different value then a domain event is published")
    public void givenDocumentWhenContentModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, String> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(projectId);
        assertThat(optionalProjectSemanticData).isPresent();

        var projectSemanticData = optionalProjectSemanticData.get();

        var optionalSemanticData = this.semanticDataSearchService.findById(projectSemanticData.getSemanticData().getId());
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var originalContent = originalDocument.getContent();
        var newContent = originalContent + "modified";
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(originalDocument.getName()).content(newContent).build();
        this.semanticDataUpdateService.updateDocuments(null, projectSemanticData.getSemanticData(), Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof SemanticDataUpdatedEvent semanticDataUpdateEvent && semanticDataUpdateEvent.semanticData().getDocuments().iterator().next().getContent().equals(newContent)).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a representations, updating its content with a different value produces a domain event")
    public void givenRepresentationWhenContentModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        var optionalRepresentationContent = this.representationContentSearchService.findContentById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertThat(optionalRepresentationContent).isPresent();

        var representationContent = optionalRepresentationContent.get();

        var originalContent = representationContent.getContent();
        var newContent = originalContent + "modified";

        this.representationContentUpdateService.updateContentByRepresentationId(null, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, newContent);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof RepresentationContentUpdatedEvent representationDataContentUpdateEvent && representationDataContentUpdateEvent.representationContent().getContent().equals(newContent)).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a representations, updating its content with the same value does not produce a domain event")
    public void givenRepresentationWhenContentNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        var optionalRepresentationContent = this.representationContentSearchService.findContentById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertThat(optionalRepresentationContent).isPresent();

        var representationContent = optionalRepresentationContent.get();

        var newContent = representationContent.getContent();
        this.representationContentUpdateService.updateContentByRepresentationId(null, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, newContent);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a representation metadata, when its targetObjectId is updated, then a domain event is published")
    public void givenARepresentationMetadataWhenItsTargetObjectIdIsUpdatedThenADomainEventIsPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();

        var semanticData = AggregateReference.<SemanticData, UUID> to(UUID.fromString(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID));

        var optionalMetadata = this.representationMetadataSearchService.findMetadataById(semanticData, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertTrue(optionalMetadata.isPresent());
        assertEquals(TestIdentifiers.EPACKAGE_OBJECT.toString(), optionalMetadata.get().getTargetObjectId());

        var newTargetObjectId = TestIdentifiers.ECLASS_OBJECT.toString();
        this.representationMetadataUpdateService.updateTargetObjectId(null, semanticData, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, newTargetObjectId);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof RepresentationMetadataUpdatedEvent representationMetadataUpdatedEvent && newTargetObjectId.equals(representationMetadataUpdatedEvent.representationMetadata().getTargetObjectId())).isTrue();

        optionalMetadata = this.representationMetadataSearchService.findMetadataById(semanticData, TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertTrue(optionalMetadata.isPresent());
        assertEquals(TestIdentifiers.ECLASS_OBJECT.toString(), optionalMetadata.get().getTargetObjectId());
    }
}

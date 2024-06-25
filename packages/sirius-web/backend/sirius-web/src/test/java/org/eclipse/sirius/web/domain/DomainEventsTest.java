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
package org.eclipse.sirius.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events.RepresentationDataContentUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDomain;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for domain events publication.
 *
 * @author pcdavid
 */
@Transactional
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
    private ISemanticDataUpdateService semanticDataUpdateService;

    @Autowired
    private IRepresentationDataSearchService representationDataSearchService;

    @Autowired
    private IRepresentationDataUpdateService representationDataUpdateService;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
    }

    @Test
    @DisplayName("Given a project, changing its name produces a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenNameModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();

        var newProjectName = "renamed project";
        this.projectUpdateService.renameProject(TestIdentifiers.ECORE_SAMPLE_PROJECT, newProjectName);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof ProjectNameUpdatedEvent projectNameUpdateEvent && projectNameUpdateEvent.project().getName().equals(newProjectName)).isTrue();
    }

    @Test
    @DisplayName("Given a project, updating its name with the same value does not produce a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenNameNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();

        var sampleProject = this.projectSearchService.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT).get();
        this.projectUpdateService.renameProject(TestIdentifiers.ECORE_SAMPLE_PROJECT, sampleProject.getName());
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }


    @Test
    @DisplayName("Given a document, changing its name produces a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDocumentWhenNameModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, UUID> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalSemanticData = this.semanticDataSearchService.findByProject(projectId);
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var newDocumentName = "renamed document";
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(newDocumentName).content(originalDocument.getContent()).build();
        this.semanticDataUpdateService.updateDocuments(projectId, Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof SemanticDataUpdatedEvent semanticDataUpdateEvent && semanticDataUpdateEvent.semanticData().getDocuments().iterator().next().getName().equals(newDocumentName)).isTrue();
    }

    @Test
    @DisplayName("Given a document, updating its name and content with the same value does not produce a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDocumentWhenNameAndContentNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, UUID> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalSemanticData = this.semanticDataSearchService.findByProject(projectId);
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var newDocumentName = originalDocument.getName();
        // Identical to the original except for the timestamps
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(newDocumentName).content(originalDocument.getContent()).build();
        this.semanticDataUpdateService.updateDocuments(projectId, Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

    @Test
    @DisplayName("Given a document, updating its content with a diffrent value produces a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDocumentWhenContentModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        AggregateReference<Project, UUID> projectId = AggregateReference.to(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        var optionalSemanticData = this.semanticDataSearchService.findByProject(projectId);
        assertThat(optionalSemanticData).isPresent();

        var semanticData = optionalSemanticData.get();

        var optionalDocument = semanticData.getDocuments().stream().filter(doc -> doc.getId().equals(TestIdentifiers.ECORE_SAMPLE_DOCUMENT)).findFirst();
        assertThat(optionalDocument).isPresent();
        var originalDocument = optionalDocument.get();

        var originalContent = originalDocument.getContent();
        var newContent = originalContent + "modified";
        var updatedDocument = Document.newDocument(originalDocument.getId()).name(originalDocument.getName()).content(newContent).build();
        this.semanticDataUpdateService.updateDocuments(projectId, Set.of(updatedDocument), semanticData.getDomains().stream().map(SemanticDataDomain::uri).collect(Collectors.toSet()));
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof SemanticDataUpdatedEvent semanticDataUpdateEvent && semanticDataUpdateEvent.semanticData().getDocuments().iterator().next().getContent().equals(newContent)).isTrue();
    }

    @Test
    @DisplayName("Given a representations, updating its content with a different value produces a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationWhenContentModifiedDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        var optionalRepresentationData = this.representationDataSearchService.findContentById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertThat(optionalRepresentationData).isPresent();

        var representationData = optionalRepresentationData.get();

        var originalContent = representationData.content();
        var newContent = originalContent + "modified";

        this.representationDataUpdateService.updateContent(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, newContent);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).hasSize(1);
        var event = this.domainEventCollector.getDomainEvents().get(0);
        assertThat(event instanceof RepresentationDataContentUpdatedEvent represnetationDataContentUpdateEvent && represnetationDataContentUpdateEvent.representationData().getContent().equals(newContent)).isTrue();
    }

    @Test
    @DisplayName("Given a representations, updating its content with the same value does not produce a domain event")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRepresentationWhenContentNotModifiedNoDomainEventPublished() {
        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
        var optionalRepresentationData = this.representationDataSearchService.findContentById(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION);
        assertThat(optionalRepresentationData).isPresent();

        var representationData = optionalRepresentationData.get();

        var newContent = representationData.content();
        this.representationDataUpdateService.updateContent(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, newContent);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertThat(this.domainEventCollector.getDomainEvents()).isEmpty();
    }

}

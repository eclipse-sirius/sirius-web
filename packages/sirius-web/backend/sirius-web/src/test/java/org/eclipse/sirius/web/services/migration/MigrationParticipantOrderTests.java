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

package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests to check the execution order of migration participant and representation migration participant.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MigrationParticipantOrderTestsConfiguration.class)
public class MigrationParticipantOrderTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private IRepresentationContentSearchService representationContentSearchService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    @Autowired
    private IRepresentationPersistenceService representationPersistenceService;

    @Test
    @DisplayName("Given a project with an old hierarchy representation, when the representation is loaded, then the most recent representation migration participant is the last applied.")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testRepresentationMigrationParticipantExecutionOrder() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY.toString(), Hierarchy.class);
        assertThat(optionalRepresentation).isPresent();

        Hierarchy hierarchy = optionalRepresentation.get();
        assertThat(hierarchy.getChildNodes()).hasSize(1);

        this.representationPersistenceService.save(null, optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY));
        assertThat(optionalUpdatedRepresentationContent).isPresent();

        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();

        assertThat(updatedRepresentationContent.getMigrationVersion()).isEqualTo("9999.12.99-300012310900");
        // The name is empty because we registered an anonymous representation migration participant.
        assertThat(updatedRepresentationContent.getLastMigrationPerformed()).isEqualTo("");
    }

    @Test
    @DisplayName("Given a project, when a new Hierarchy representation is created, then the initial representation migration participant is the most recent representation migration participant.")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testRepresentationMigrationParticipantOnCreatedRepresentation() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY.toString(), Hierarchy.class);
        assertThat(optionalRepresentation).isPresent();
        var representation = optionalRepresentation.get();
        var newHierarchy = new Hierarchy(UUID.randomUUID().toString(), representation.getDescriptionId(), representation.getTargetObjectId(), representation.getKind(), representation.getChildNodes());

        var representationMetadata = new RepresentationMetadata(newHierarchy.getId(), newHierarchy.getKind(), "new Hierarchy", newHierarchy.getDescriptionId());
        representationMetadataPersistenceService.save(null, optionalEditingContext.get(), representationMetadata, newHierarchy.getTargetObjectId());
        this.representationPersistenceService.save(null, optionalEditingContext.get(), newHierarchy);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(UUID.fromString(newHierarchy.getId())));
        assertThat(optionalUpdatedRepresentationContent).isPresent();
        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();

        assertThat(updatedRepresentationContent.getMigrationVersion()).isEqualTo("9999.12.99-300012310900");
        // The name is not empty because the initial representation migration participant is initialized with "none"
        assertThat(updatedRepresentationContent.getLastMigrationPerformed()).isEqualTo("none");
    }

    @Test
    @DisplayName("Given an old model, when the model is retrieved, then the last migration participant applied is the most recent one.")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testMigrationParticipantExecutionOrder() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        if (optionalEditingContext.get() instanceof EditingContext siriusWebEditingContext) {
            var resource = siriusWebEditingContext.getDomain().getResourceSet().getResource(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM_URI, true);
            var optionalResourceMetadataAdapter = resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast)
                    .findFirst();
            assertThat(optionalResourceMetadataAdapter).isPresent();
            var resourceMetadataAdapter = optionalResourceMetadataAdapter.get();
            var migrationData = resourceMetadataAdapter.getMigrationData();
            assertThat(migrationData.migrationVersion()).isEqualTo("9999.12.99-300012310901");
            // The name is empty because we registered an anonymous migration participant.
            assertThat(migrationData.lastMigrationPerformed()).isEqualTo("");
        }
    }
}

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

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of DiagramUserResizableAttributeMigrationParticipant.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramUserResizableAttributeMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IRepresentationDataSearchService representationDataSearchService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private IRepresentationPersistenceService representationPersistenceService;

    @Test
    @DisplayName("Given a project with an old diagram representation, when the representation is loaded, then userResizable objects are migrated correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWithAnOldDiagramRepresentationWhenTheRepresentationIsLoadedThenUserResizableObjectsAreMigratedCorrectly() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM.toString(), Diagram.class);
        assertThat(optionalRepresentation).isPresent();

        this.representationPersistenceService.save(optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationData = this.representationDataSearchService.findContentById(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM);
        assertThat(optionalUpdatedRepresentationData).isPresent();

        var updatedRepresentationData = optionalUpdatedRepresentationData.get();

        assertThat(updatedRepresentationData.migrationVersion()).isEqualTo("2024.5.4-202407040900");
        assertThat(updatedRepresentationData.lastMigrationPerformed()).isEqualTo("DiagramUserResizableAttributeMigrationParticipant");

    }
}

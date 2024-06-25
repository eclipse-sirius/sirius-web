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

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
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
 * Integration tests of NodeDescriptionLabelExpressionMigrationParticipant.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IRepresentationDataSearchService representationDataSearchService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private IRepresentationPersistenceService representationPersistenceService;

    @Test
    @DisplayName("Given a project with an old hierarchy representation, when the representation is loaded, then its child nodes are migrated correctly")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWithAnOldHierarchyRepresentationWhenTheRepresentationIsLoadedThenItsChildNodesAreMigratedCorrectly() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY.toString(), Hierarchy.class);
        assertThat(optionalRepresentation).isPresent();

        Hierarchy hierarchy = optionalRepresentation.get();
        assertThat(hierarchy.getChildNodes()).hasSize(1);

        this.representationPersistenceService.save(optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationData = this.representationDataSearchService.findContentById(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY);
        assertThat(optionalUpdatedRepresentationData).isPresent();

        var updatedRepresentationData = optionalUpdatedRepresentationData.get();

        assertThat(updatedRepresentationData.migrationVersion()).isEqualTo("2024.4.3-202405130907");
        assertThat(updatedRepresentationData.lastMigrationPerformed()).isEqualTo("HierarchyChildrenAttributeMigration");

    }
}

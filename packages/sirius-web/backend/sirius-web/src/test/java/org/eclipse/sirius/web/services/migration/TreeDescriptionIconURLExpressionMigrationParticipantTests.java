/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests of TreeDescriptionIconURLExpressionMigrationParticipant.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TreeDescriptionIconURLExpressionMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @DisplayName("Given a project with an old model, TreeDescriptionIconURLExpressionMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_TREE_DESCRIPTION_ICON_URL_STUDIO);
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalTreeDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_TREE_DESCRIPTION_ICON_URL_STUDIO_DOCUMENT_NAME))
                    .findFirst();
            assertThat(optionalTreeDescription).isPresent();
            assertThat(optionalTreeDescription.get()).isInstanceOf(TreeDescription.class);
            optionalTreeDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof TreeDescription treeDescription) {
                    assertThat(treeDescription.getTreeItemIconExpression()).isEqualTo("iconUrl");

                }
            });
        }
    }
}

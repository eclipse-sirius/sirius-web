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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the lifecycle of the studios.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudioLifecycleIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @DisplayName("Given a regular project, when it is loaded, then the domains from all studios are available")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRegularProjectWhenItIsLoadedThenTheDomainsFromAllStudiosAreAvailable() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var ePackageRegistry = siriusWebEditingContext.getDomain().getResourceSet().getPackageRegistry();
            var ePackage = ePackageRegistry.get("domain://buck");
            assertThat(ePackage).isNotNull();
        } else {
            fail("Invalid editing context");
        }
    }

    @Test
    @DisplayName("Given a regularProject, when it is loaded, then the views from all studios are available")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRegularProjectWhenItIsLoadedThenTheViewsFromAllStudiosAreAvailable() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var views = siriusWebEditingContext.getViews();
            assertThat(views).isNotEmpty();

            var hasHumanFormDescription = views.stream()
                    .anyMatch(view -> view.getDescriptions().stream()
                            .filter(FormDescription.class::isInstance)
                            .anyMatch(representationDescription -> representationDescription.getName().equals("Human Form")));
            assertThat(hasHumanFormDescription).isTrue();
        } else {
            fail("Invalid editing context");
        }
    }

    @Test
    @DisplayName("Given a studio, when it is loaded, then the palette is available")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenItIsLoadedThenThenPaletteIsAvailable() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.EMPTY_STUDIO_PROJECT.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            assertThat(resourceSet.getResources()).hasSize(1);

            var resource = resourceSet.getResources().get(0);
            assertThat(resource.getURI().toString()).isEqualTo(ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI);
        } else {
            fail("Invalid editing context");
        }
    }
}

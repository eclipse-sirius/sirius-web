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
package org.eclipse.sirius.web.sample.tests.integration.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.task.starter.configuration.view.ViewsStereotypeDescriptionRegistryConfigurer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests of the deck controllers.
 *
 * @author lfasani
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeckControllerIntegrationTests extends AbstractTaskControllerIntegrationTests {

    @Autowired
    private IRepresentationDescriptionSearchService representationDescriptionSearchService;

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        this.createStudio(ViewsStereotypeDescriptionRegistryConfigurer.DECK_VIEW_ID);

        this.createTaskProject();
    }

    @Test
    @DisplayName("Given a task model, deck representation creation succeeds")
    public void givenGanttThenTasksMutationsSucceed() {
        Optional<IRepresentationDescription> repDescOpt = this.representationDescriptionSearchService.findAll(this.editingDomain).values().stream()
                .filter(desc -> desc.getLabel().equals("Deck Daily Representation"))
                .findFirst();

        assertThat(repDescOpt).isPresent();

        String taskProjectId = this.getTaskProjectId("Daily Project Dev");

        String representationId = this.createRepresentation(repDescOpt.get().getId(), taskProjectId, "Deck");
        assertThat(representationId).isNotBlank();
    }
}

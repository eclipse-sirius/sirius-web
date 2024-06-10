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
package org.eclipse.sirius.web.application.controllers.formdescriptioneditors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormDescriptionEditorSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the form description editors.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormDescriptionEditorControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormDescriptionEditorSubscription givenCreatedFormDescriptionEditorSubscription;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<FormDescriptionEditorRefreshedEventPayload> givenSubscriptionToFormDescriptionEditor() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                UUID.nameUUIDFromBytes("FormDescriptionEditor".getBytes()).toString(),
                StudioIdentifiers.FORM_DESCRIPTION_OBJECT.toString(),
                "FormDescriptionEditor"
        );
        return this.givenCreatedFormDescriptionEditorSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a form description editor, when we subscribe to its events, then the representation data are received")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeSubscribeToItsEventsThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToFormDescriptionEditor();

        Consumer<FormDescriptionEditorRefreshedEventPayload> initialFormDescriptionEditorContentConsumer = payload -> Optional.of(payload)
                .map(FormDescriptionEditorRefreshedEventPayload::formDescriptionEditor)
                .ifPresentOrElse(formDescriptionEditor -> {
                    assertThat(formDescriptionEditor).isNotNull();
                }, () -> fail("Missing form description editor"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormDescriptionEditorContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

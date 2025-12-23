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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.tests.graphql.EditTextfieldMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithTextareaDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the textarea widget.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class TextareaControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithTextareaDescriptionProvider formWithTextareaDescriptionProvider;

    @Autowired
    private EditTextfieldMutationRunner editTextfieldMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTextareaForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithTextareaDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithTextarea"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textarea widget, when it is displayed, then it is properly initialized")
    public void givenTextareaWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToTextareaForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textarea = groupNavigator.findWidget("Name", Textarea.class);

            assertThat(textarea)
                    .hasLabel("Name")
                    .hasValue("buck")
                    .hasHelp("The name of the object")
                    .isNotReadOnly()
                    .isBold()
                    .isNotItalic();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textarea widget, when it is edited, then its value is updated")
    public void givenTextareaWidgetWhenItIsEditedThenItsValueIsUpdated() {
        var flux = this.givenSubscriptionToTextareaForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var textareaId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textarea = groupNavigator.findWidget("Name", Textarea.class);

            textareaId.set(textarea.getId());
        });

        Runnable editTextarea = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), textareaId.get(), "A new and very long value");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result.data(), "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textarea = groupNavigator.findWidget("Name", Textarea.class);

            assertThat(textarea)
                    .hasValue("A new and very long value")
                    .isNotBold()
                    .isItalic()
                    .isReadOnly();
        });

        Runnable tryEditReadOnlyTextarea = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), textareaId.get(), "buck");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result.data(), "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editTextarea)
                .consumeNextWith(updatedFormContentConsumer)
                .then(tryEditReadOnlyTextarea)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

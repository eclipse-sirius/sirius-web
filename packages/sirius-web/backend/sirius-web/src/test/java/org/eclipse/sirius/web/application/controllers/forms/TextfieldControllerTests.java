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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.graphql.EditTextfieldMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithReadOnlyTextfieldDescriptionProvider;
import org.eclipse.sirius.web.services.forms.FormWithTextfieldDescriptionProvider;
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
 * Integration tests of the textfield widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class TextfieldControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithTextfieldDescriptionProvider formWithTextfieldDescriptionProvider;

    @Autowired
    private FormWithReadOnlyTextfieldDescriptionProvider formWithReadOnlyTextfieldDescriptionProvider;

    @Autowired
    private EditTextfieldMutationRunner editTextfieldMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTextfieldForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                this.formWithTextfieldDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithTextfield"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield widget, when it is displayed, then it is properly initialized")
    public void givenTextfieldWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToTextfieldForm();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var textfield = groupNavigator.findWidget("Name", Textfield.class);

                    assertThat(textfield)
                            .hasLabel("Name")
                            .hasValue("buck")
                            .hasHelp("The name of the object")
                            .hasDiagnostic("Warning", "name should start with upper case")
                            .isNotReadOnly()
                            .isBold()
                            .isNotItalic();
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield widget, when it is edited, then its value is updated")
    public void givenTextfieldWidgetWhenItIsEditedThenItsValueIsUpdated() {
        var flux = this.givenSubscriptionToTextfieldForm();

        var formId = new AtomicReference<String>();
        var textfieldId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var textfield = groupNavigator.findWidget("Name", Textfield.class);

                    textfieldId.set(textfield.getId());
                }, () -> fail("Missing form"));

        Runnable editTextfield = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), formId.get(), textfieldId.get(), "A new and very long value");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var textfield = groupNavigator.findWidget("Name", Textfield.class);

                    assertThat(textfield)
                            .hasValue("A new and very long value")
                            .isNotBold()
                            .isItalic()
                            .isReadOnly();
                }, () -> fail("Missing form"));

        Runnable tryEditReadOnlyTextField = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), formId.get(), textfieldId.get(), "buck");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editTextfield)
                .consumeNextWith(updatedFormContentConsumer)
                .then(tryEditReadOnlyTextField)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Flux<Object> givenSubscriptionToReadOnlyTextfieldForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                this.formWithReadOnlyTextfieldDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithReadOnlyTextfield"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a readonly textfield widget, when it is edited, then an error is returned")
    public void givenReadOnlyTextfieldWidgetWhenItIsEditedThenAnErrorIsReturned() {
        var flux = this.givenSubscriptionToReadOnlyTextfieldForm();

        var formId = new AtomicReference<String>();
        var textfieldId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var textfield = groupNavigator.findWidget("Name", Textfield.class);

                    assertThat(textfield).isReadOnly();

                    textfieldId.set(textfield.getId());
                }, () -> fail("Missing form"));

        Runnable editTextfield = () -> {
            var input = new EditTextfieldInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), formId.get(), textfieldId.get(), "buck");
            var result = this.editTextfieldMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editTextfield)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

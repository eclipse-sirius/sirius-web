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
import org.eclipse.sirius.components.collaborative.forms.dto.EditRadioInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioOption;
import org.eclipse.sirius.components.forms.tests.graphql.EditRadioMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithRadioDescriptionProvider;
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
 * Integration tests of the checkbox widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class RadioControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithRadioDescriptionProvider formWithRadioDescriptionProvider;

    @Autowired
    private EditRadioMutationRunner editRadioMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToRadioForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithRadioDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithCheckbox"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a radio widget, when it is displayed, then it is properly initialized")
    public void givenRadioWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToRadioForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var radio = groupNavigator.findWidget("SuperType", Radio.class);

            assertThat(radio)
                    .hasLabel("SuperType")
                    .hasHelp("Pick a super type")
                    .hasValueWithLabel("NamedElement")
                    .isNotReadOnly();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a radio widget, when it is edited, then its value is updated")
    public void givenRadioWidgetWhenItIsEditedThenItsValueIsUpdated() {
        var flux = this.givenSubscriptionToRadioForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var radioId = new AtomicReference<String>();
        var optionId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var radio = groupNavigator.findWidget("SuperType", Radio.class);

            radioId.set(radio.getId());
            assertThat(radio)
                    .hasLabel("SuperType")
                    .hasHelp("Pick a super type")
                    .hasValueWithLabel("NamedElement")
                    .isNotReadOnly();

            assertThat(radio.getStyle().isBold()).isFalse();
            assertThat(radio.getStyle().isItalic()).isFalse();
            assertThat(radio.getStyle().isStrikeThrough()).isFalse();
            assertThat(radio.getStyle().isUnderline()).isFalse();
            assertThat(radio.getStyle().getFontSize()).isEqualTo(16);

            var newValue = radio.getOptions().stream()
                    .filter(radioOption -> radioOption.getLabel().equals("Human"))
                    .findFirst()
                    .map(RadioOption::getId)
                    .orElseThrow(IllegalStateException::new);
            optionId.set(newValue);
        });

        Runnable editCheckbox = () -> {
            var input = new EditRadioInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), radioId.get(), optionId.get());
            var result = this.editRadioMutationRunner.run(input);

            String typename = JsonPath.read(result.data(), "$.data.editRadio.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var radio = groupNavigator.findWidget("SuperType", Radio.class);

            assertThat(radio.getStyle().isBold()).isTrue();
            assertThat(radio.getStyle().isItalic()).isTrue();
            assertThat(radio.getStyle().isStrikeThrough()).isTrue();
            assertThat(radio.getStyle().isUnderline()).isTrue();
            assertThat(radio.getStyle().getFontSize()).isEqualTo(24);

            assertThat(radio)
                    .hasLabel("SuperType")
                    .hasHelp("Pick a super type")
                    .hasValueWithLabel("Human")
                    .isNotReadOnly();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editCheckbox)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

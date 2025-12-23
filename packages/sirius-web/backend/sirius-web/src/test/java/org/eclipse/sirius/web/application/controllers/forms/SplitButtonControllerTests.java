/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.PushButtonInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.SplitButton;
import org.eclipse.sirius.components.forms.tests.graphql.PushButtonMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithSplitButtonDescriptionProvider;
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
 * Integration tests of the split button widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class SplitButtonControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithSplitButtonDescriptionProvider formWithSplitButtonDescriptionProvider;

    @Autowired
    private PushButtonMutationRunner pushButtonMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToSplitButtonForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithSplitButtonDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithCheckbox"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a split button widget, when it is displayed, then it is properly initialized")
    public void givenSplitButtonWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToSplitButtonForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var splitButton = groupNavigator.findWidget("Button", SplitButton.class);

            assertThat(splitButton.getActions()).hasSize(2);

            var firstButton = splitButton.getActions().get(0);
            assertThat(firstButton.getButtonLabel()).isEqualTo("First");
            assertThat(firstButton.getImageURL()).isEqualTo("https://www.example.org/images/test.png");

            var secondButton = splitButton.getActions().get(1);
            assertThat(secondButton.getButtonLabel()).isEqualTo("Second");
            assertThat(secondButton.getImageURL()).isEqualTo("https://www.example.org/images/test.png");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a split button widget, when it is edited, then its value is updated")
    public void givenSplitButtonWidgetWhenItIsEditedThenItsValueIsUpdated() {
        var flux = this.givenSubscriptionToSplitButtonForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var buttonId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var label = groupNavigator.findWidget("Name", LabelWidget.class);
            assertThat(label.getValue()).isEqualTo("buck");

            var splitButton = groupNavigator.findWidget("Button", SplitButton.class);

            assertThat(splitButton.getActions()).hasSize(2);

            buttonId.set(splitButton.getActions().get(1).getId());
        });

        Runnable pushButton = () -> {
            var input = new PushButtonInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), buttonId.get());
            var result = this.pushButtonMutationRunner.run(input);

            String typename = JsonPath.read(result.data(), "$.data.pushButton.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var label = groupNavigator.findWidget("Name", LabelWidget.class);
            assertThat(label.getValue()).isEqualTo("");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(pushButton)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

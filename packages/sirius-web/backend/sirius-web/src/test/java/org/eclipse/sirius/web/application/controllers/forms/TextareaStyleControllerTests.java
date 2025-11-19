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

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithStyledTextareaDescriptionProvider;
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
 * Integration tests of the textarea widget style.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class TextareaStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithStyledTextareaDescriptionProvider formWithStyledTextareaDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTextareaForm(String objectId) {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithStyledTextareaDescriptionProvider.getRepresentationDescriptionId(),
                objectId,
                "FormWithStyledTextarea"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textarea widget with a style, when it is displayed, then the style is properly apply")
    public void givenTextareaWidgetWithStyleWhenItIsDisplayedThenStyleIsApplied() {
        var flux = this.givenSubscriptionToTextareaForm(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString())
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textarea = groupNavigator.findWidget("Name", Textarea.class);

            assertThat(textarea.getStyle().getBackgroundColor()).isEqualTo("#7FFFD4");
            assertThat(textarea.getStyle().getForegroundColor()).isEqualTo("#7FFFD4");
            assertThat(textarea.getStyle().isItalic()).isFalse();
            assertThat(textarea.getStyle().isBold()).isFalse();
            assertThat(textarea.getStyle().isStrikeThrough()).isFalse();
            assertThat(textarea.getStyle().isUnderline()).isFalse();
            assertThat(textarea.getStyle().getFontSize()).isEqualTo(8);

            assertThat(textarea.getStyle().getWidgetGridLayout())
                    .hasGridTemplateColumns("none")
                    .hasGridTemplateRows("none")
                    .hasGap("normal")
                    .hasLabelGridRow("auto")
                    .hasLabelGridColumn("auto")
                    .hasWidgetGridColumn("auto")
                    .hasWidgetGridRow("auto");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textarea widget with a conditional style, when the condition is validated, then the conditional style is applied")
    public void givenTextareaWidgetWithConditionalStyleWhenTheConditionIsValidatedThenConditionalStyleIsApplied() {
        var flux = this.givenSubscriptionToTextareaForm(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString())
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textarea = groupNavigator.findWidget("Name", Textarea.class);

            assertThat(textarea.getStyle().getBackgroundColor()).isEqualTo("#A52A2A");
            assertThat(textarea.getStyle().getForegroundColor()).isEqualTo("#A52A2A");
            assertThat(textarea.getStyle().isItalic()).isTrue();
            assertThat(textarea.getStyle().isBold()).isTrue();
            assertThat(textarea.getStyle().isUnderline()).isTrue();
            assertThat(textarea.getStyle().isStrikeThrough()).isTrue();
            assertThat(textarea.getStyle().getFontSize()).isEqualTo(10);

            assertThat(textarea.getStyle().getWidgetGridLayout())
                    .hasGridTemplateColumns("max-content")
                    .hasGridTemplateRows("max-content")
                    .hasGap("1px")
                    .hasLabelGridRow("1")
                    .hasLabelGridColumn("1")
                    .hasWidgetGridColumn("2")
                    .hasWidgetGridRow("2");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

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

import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.DateTime;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithStyledDateTimeDescriptionProvider;
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
 * Integration tests of the dateTime widget style.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class DateTimeStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithStyledDateTimeDescriptionProvider formWithStyledDateTimeDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToDateTimeForm(String objectId) {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.formWithStyledDateTimeDescriptionProvider.getRepresentationDescriptionId(),
                objectId,
                "FormWithStyledDateTime"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a date time widget with a style, when it is displayed, then the style is properly apply")
    public void givenDateTimeWidgetWithStyleWhenItIsDisplayedThenStyleIsApplied() {
        var flux = this.givenSubscriptionToDateTimeForm(PapayaIdentifiers.FIRST_ITERATION_OBJECT.toString());

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var dateTime = groupNavigator.findWidget("Start Date", DateTime.class);

                    Assertions.assertThat(dateTime.getStyle().getBackgroundColor()).isEqualTo("#7FFFD4");
                    Assertions.assertThat(dateTime.getStyle().getForegroundColor()).isEqualTo("#7FFFD4");
                    Assertions.assertThat(dateTime.getStyle().isItalic()).isFalse();
                    Assertions.assertThat(dateTime.getStyle().isBold()).isFalse();
                    assertThat(dateTime.getStyle().getWidgetGridLayout())
                            .hasGridTemplateColumns("none")
                            .hasGridTemplateRows("none")
                            .hasGap("normal")
                            .hasLabelGridRow("auto")
                            .hasLabelGridColumn("auto")
                            .hasWidgetGridColumn("auto")
                            .hasWidgetGridRow("auto");
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a date time widget with a conditional style, when the condition is validated, then the conditional style is applied")
    public void givenDateTimeWidgetWithConditionalStyleWhenTheConditionIsValidatedThenConditionalStyleIsApplied() {
        var flux = this.givenSubscriptionToDateTimeForm(PapayaIdentifiers.SECOND_ITERATION_OBJECT.toString());

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var dateTime = groupNavigator.findWidget("Start Date", DateTime.class);

                    Assertions.assertThat(dateTime.getStyle().getBackgroundColor()).isEqualTo("#A52A2A");
                    Assertions.assertThat(dateTime.getStyle().getForegroundColor()).isEqualTo("#A52A2A");
                    Assertions.assertThat(dateTime.getStyle().isItalic()).isTrue();
                    Assertions.assertThat(dateTime.getStyle().isBold()).isTrue();
                    assertThat(dateTime.getStyle().getWidgetGridLayout())
                            .hasGridTemplateColumns("max-content")
                            .hasGridTemplateRows("max-content")
                            .hasGap("1px")
                            .hasLabelGridRow("1")
                            .hasLabelGridColumn("1")
                            .hasWidgetGridColumn("2")
                            .hasWidgetGridRow("2");
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


}

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
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithStyledSelectDescriptionProvider;
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
 * Integration tests of the select widget style.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class SelectStyleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithStyledSelectDescriptionProvider formWithStyledSelectDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToSelectForm(String objectId) {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                this.formWithStyledSelectDescriptionProvider.getRepresentationDescriptionId(),
                objectId,
                "FormWithStyledSelect"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a select widget with a style, when it is displayed, then the style is properly apply")
    public void givenSelectWidgetWithStyleWhenItIsDisplayedThenStyleIsApplied() {
        var flux = this.givenSubscriptionToSelectForm(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString());

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var select = groupNavigator.findWidget("Super types", Select.class);

                    Assertions.assertThat(select.getStyle().getBackgroundColor()).isEqualTo("#7FFFD4");
                    Assertions.assertThat(select.getStyle().getForegroundColor()).isEqualTo("#7FFFD4");
                    Assertions.assertThat(select.getStyle().isShowIcon()).isFalse();
                    Assertions.assertThat(select.getStyle().isItalic()).isFalse();
                    Assertions.assertThat(select.getStyle().isBold()).isFalse();
                    Assertions.assertThat(select.getStyle().getFontSize()).isEqualTo(8);
                    assertThat(select.getStyle().getWidgetGridLayout())
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
    @DisplayName("Given a select widget with a conditional style, when the condition is validated, then the conditional style is applied")
    public void givenSelectWidgetWithConditionalStyleWhenTheConditionIsValidatedThenConditionalStyleIsApplied() {
        var flux = this.givenSubscriptionToSelectForm(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var select = groupNavigator.findWidget("Super types", Select.class);

                    Assertions.assertThat(select.getStyle().getBackgroundColor()).isEqualTo("#A52A2A");
                    Assertions.assertThat(select.getStyle().getForegroundColor()).isEqualTo("#A52A2A");
                    Assertions.assertThat(select.getStyle().isShowIcon()).isTrue();
                    Assertions.assertThat(select.getStyle().isItalic()).isTrue();
                    Assertions.assertThat(select.getStyle().isBold()).isTrue();
                    Assertions.assertThat(select.getStyle().getFontSize()).isEqualTo(10);
                    assertThat(select.getStyle().getWidgetGridLayout())
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

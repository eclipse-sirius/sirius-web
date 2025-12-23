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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.ChartWidget;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithBarChartDescriptionProvider;
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
 * Integration tests of the bar chart widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class BarChartControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithBarChartDescriptionProvider formWithBarChartDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToBarChartForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithBarChartDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithBarChart"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a bar chart widget, when it is displayed, then it is properly initialized")
    public void givenBarChartWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToBarChartForm()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var chartWidget = groupNavigator.findWidget("Count", ChartWidget.class);

            assertThat(chartWidget.getChart()).isInstanceOf(BarChart.class);
            BarChart barChart = (BarChart) chartWidget.getChart();

            assertThat(barChart.getStyle().getFontSize()).isEqualTo(24);
            assertThat(barChart.getStyle().isBold()).isTrue();
            assertThat(barChart.getStyle().isUnderline()).isTrue();
            assertThat(barChart.getStyle().isItalic()).isTrue();
            assertThat(barChart.getStyle().isStrikeThrough()).isTrue();
            assertThat(barChart.getStyle().getBarsColor()).isNotEmpty().contains("red");

            assertThat(barChart.getEntries())
                    .isNotEmpty()
                    .anySatisfy(entry -> {
                        assertThat(entry.getKey()).isEqualTo("Attribute");
                        assertThat(entry.getValue().intValue()).isPositive();
                    });
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

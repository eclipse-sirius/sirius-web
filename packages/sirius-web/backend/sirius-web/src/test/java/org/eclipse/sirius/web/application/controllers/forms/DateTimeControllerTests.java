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
import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditDateTimeInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.DateTime;
import org.eclipse.sirius.components.forms.tests.graphql.EditDateTimeMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithDateTimeDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the dateTime widget.
 *
 * @author lfasani
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class DateTimeControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithDateTimeDescriptionProvider formWithDateTimeDescriptionProvider;

    @Autowired
    private EditDateTimeMutationRunner editDateTimeMutationRunner;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToDateTimeForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.formWithDateTimeDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.FIRST_ITERATION_OBJECT.toString(),
                "FormWithDateTime"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a datetime widget, when it is displayed, then it is properly initialized")
    public void givenDateTimeWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToDateTimeForm();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var dateTime = groupNavigator.findWidget("Start Date", DateTime.class);

            assertThat(dateTime)
                    .hasLabel("Start Date")
                    .hasValue("2023-12-11T09:00:00Z")
                    .hasHelp("The start Date of the Iteration")
                    .isNotReadOnly();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a dateTime widget, when it is edited, then its value is updated")
    public void givenDateTimeWidgetWhenItIsEditedThenTheValueIsUpdated() {
        var flux = this.givenSubscriptionToDateTimeForm();

        var formId = new AtomicReference<String>();
        var dateTimeId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var dateTime = new FormNavigator(form).page("Page").group("Group").findWidget("Start Date", DateTime.class);
            dateTimeId.set(dateTime.getId());

            assertThat(dateTime).hasValue("2023-12-11T09:00:00Z");
        });

        Runnable editDateTime = () -> {
            var input = new EditDateTimeInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), dateTimeId.get(), "2024-02-02T18:00:00.00Z");
            var result = this.editDateTimeMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editDateTime.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var dateTime = new FormNavigator(form).page("Page").group("Group").findWidget("Start Date", DateTime.class);
            assertThat(dateTime).hasValue("2024-02-02T18:00:00Z");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editDateTime)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a dateTime widget from default Details, when it is displayed/edited, then its value is properly initialized/updated")
    public void givenDateTimeWidgetFromDefaultDetailsWhenItIsEditedThenTheValueIsUpdated() {
        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(PapayaIdentifiers.FIRST_ITERATION_OBJECT.toString()));
        var detailsEventInput = new DetailsEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(detailsEventInput);

        var formId = new AtomicReference<String>();
        var startDate = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var startDateTime = new FormNavigator(form).page("2024.3.0").group("Core Properties").findWidget("Start Date", DateTime.class);
            startDate.set(startDateTime.getId());

            assertThat(startDateTime).hasValue("2023-12-11T09:00:00Z");
        });

        Runnable editDateTime = () -> {
            var input = new EditDateTimeInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), startDate.get(), "2024-02-11T09:00:00Z");
            var result = this.editDateTimeMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editDateTime.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var dateTime = new FormNavigator(form).page("2024.3.0").group("Core Properties").findWidget("Start Date", DateTime.class);
            assertThat(dateTime).hasValue("2024-02-11T09:00:00Z");
        });

        Runnable editDateTime2 = () -> {
            var input = new EditDateTimeInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), startDate.get(), "");
            var result = this.editDateTimeMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editDateTime.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer2 = assertRefreshedFormThat(form -> {
            var dateTime = new FormNavigator(form).page("2024.3.0").group("Core Properties").findWidget("Start Date", DateTime.class);
            assertThat(dateTime).hasValue("");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editDateTime)
                .consumeNextWith(updatedFormContentConsumer)
                .then(editDateTime2)
                .consumeNextWith(updatedFormContentConsumer2)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


}

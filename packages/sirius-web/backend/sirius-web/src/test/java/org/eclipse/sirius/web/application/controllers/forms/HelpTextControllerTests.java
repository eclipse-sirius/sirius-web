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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.graphql.HelpTextQueryRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithTextfieldDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the help text controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class HelpTextControllerTests extends AbstractIntegrationTests {

    private static final String HELP_TEXT_FORM_EVENT_SUBSCRIPTION = """
            subscription formEvent($input: FormEventInput!) {
              formEvent(input: $input) {
                __typename
                ... on FormRefreshedEventPayload {
                 form {
                   id
                   pages {
                     id
                     groups {
                       id
                       widgets {
                         ... on Textfield {
                           hasHelpText
                         }
                       }
                     }
                   }
                 }
               }
              }
            }
            """;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithTextfieldDescriptionProvider formWithTextfieldDescriptionProvider;

    @Autowired
    private HelpTextQueryRunner helpTextQueryRunner;

    @Autowired
    private IGivenCreatedRepresentation givenCreatedRepresentation;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield widget, when its help text is requested, then it is properly received")
    public void givenTextfieldWidgetWhenItsHelpTextIsRequestedThenItIsProperlyReceived() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithTextfieldDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithTextfield"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var textfieldId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var textfield = groupNavigator.findWidget("Name", Textfield.class);

            textfieldId.set(textfield.getId());
        });

        Runnable requestHelpText = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", formId.get(),
                    "widgetId", textfieldId.get()
            );
            var result = this.helpTextQueryRunner.run(variables);

            String helpText = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.helpText");
            assertThat(helpText).isEqualTo("The name of the object");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(requestHelpText)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a textfield widget, when we ask if it support help text, then its support is received")
    public void givenTextfieldWidgetWhenWeAskIfItSupportHelpTextThenItsSupportIsReceived() {
        var createRepresentationInput = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithTextfieldDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithTextfield"
        );
        String representationId = this.givenCreatedRepresentation.createRepresentation(createRepresentationInput);

        var formEventInput = new FormEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        var flux = this.graphQLRequestor.subscribeToSpecification(HELP_TEXT_FORM_EVENT_SUBSCRIPTION, formEventInput)
                .flux()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(json -> json.contains("FormRefreshedEventPayload"));


        Consumer<String> initialFormContentConsumer = payload -> {
            boolean hasHelpText = JsonPath.read(payload, "$.data.formEvent.form.pages[0].groups[0].widgets[0].hasHelpText");
            assertThat(hasHelpText).isTrue();
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

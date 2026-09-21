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
import static org.eclipse.sirius.components.widget.reference.tests.assertions.ReferenceWidgetAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ClearReferenceInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.RemoveReferenceValueInput;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceClearMutationRunner;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceRemoveMutationRunner;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceValueOptionsQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithReferenceWidgetDescriptionProvider;
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

import reactor.test.StepVerifier;

/**
 * Integration tests of the reference widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ReferenceWidgetControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithReferenceWidgetDescriptionProvider formWithReferenceWidgetDescriptionProvider;

    @Autowired
    private ReferenceValueOptionsQueryRunner referenceValueOptionsQueryRunner;

    @Autowired
    private ReferenceClearMutationRunner referenceClearMutationRunner;

    @Autowired
    private ReferenceRemoveMutationRunner referenceRemoveMutationRunner;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget, when it is displayed, then it is properly initialized")
    public void givenReferenceWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithReferenceWidget"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var nameTextfield = groupNavigator.findWidget("Name", Textfield.class);
            assertThat(nameTextfield.getValue()).isEqualTo("Human");

            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);

            assertThat(referenceWidget)
                    .hasLabel("Super types")
                    .hasHelpText("Specify the super-types of Human")
                    .hasValueWithLabel("NamedElement")
                    .isBold()
                    .isItalic()
                    .isStrikeThrough()
                    .isUnderline();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget, when its options are requested, then some values are returned")
    public void givenReferenceWidgetWhenItsOptionsAreRequestedThenSomeValuesAreReturned() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithReferenceWidget"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);

            referenceWidgetId.set(referenceWidget.getId());
        });

        Runnable requestReferenceValueOptions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    "representationId", formId.get(),
                    "referenceWidgetId", referenceWidgetId.get()
            );
            var result = this.referenceValueOptionsQueryRunner.run(variables);

            List<String> referenceValueOptionLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.referenceValueOptions[*].label");
            assertThat(referenceValueOptionLabels)
                    .isNotEmpty()
                    .anySatisfy(label -> assertThat(label).isEqualTo("Human"));

        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(requestReferenceValueOptions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget with a custom clear action, when it is cleared, then its body is executed and reference values are removed")
    public void givenAReferenceWidgetWithACustomClearActionWhenItIsClearedThenItsBodyIsExecutedAndReferenceValuesAreRemoved() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithReferenceWidget"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasValueWithLabel("NamedElement");
            referenceWidgetId.set(referenceWidget.getId());
        });

        Runnable clearValueMutation = () -> {
            var clearReferenceInput = new ClearReferenceInput(UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get());
            var result = this.referenceClearMutationRunner.run(clearReferenceInput);

            String mutationResult = JsonPath.read(result.data(), "$.data.clearReference.__typename");
            assertThat(mutationResult).isEqualTo("SuccessPayload");

        };

        Consumer<Object> afterClearContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var nameTextfield = groupNavigator.findWidget("Name", Textfield.class);
            assertThat(nameTextfield.getValue()).isEqualTo("Cleared by custom action");

            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasNoValue();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(clearValueMutation)
                .consumeNextWith(afterClearContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in node style details, when it is cleared, then its value is removed")
    public void givenReferenceWidgetInNodeStyleDetailsWhenItIsClearedThenItsValueIsRemoved() {
        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(StudioIdentifiers.RECTANGULAR_NODE_STYLE_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var backgroundReferenceWidget = this.findBackgroundReferenceWidget(form);
            assertThat(backgroundReferenceWidget.isMany()).isFalse();
            assertThat(backgroundReferenceWidget.getReferenceValues()).hasSize(1);
            referenceWidgetId.set(backgroundReferenceWidget.getId());
        });

        Runnable clearReference = () -> {
            var clearReferenceInput = new ClearReferenceInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get());
            var result = this.referenceClearMutationRunner.run(clearReferenceInput);
            String typename = JsonPath.read(result.data(), "$.data.clearReference.__typename");
            assertThat(typename).isEqualTo("SuccessPayload");
        };

        Consumer<Object> clearedFormContentConsumer = assertRefreshedFormThat(form -> {
            var backgroundReferenceWidget = this.findBackgroundReferenceWidget(form);
            assertThat(backgroundReferenceWidget.getReferenceValues()).isEmpty();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(clearReference)
                .consumeNextWith(clearedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget, when the remove reference mutation is trigger, then value is removed")
    public void givenReferenceWidgetWhenRemoveReferenceMutationIsTriggerThenValueIsRemoved() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithReferenceWidget"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();
        var referenceValueId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasValueWithLabel("NamedElement");
            referenceValueId.set(referenceWidget.getReferenceValues().get(0).getId());
            referenceWidgetId.set(referenceWidget.getId());
        });

        Runnable removeReferenceValueMutation = () -> {
            var removeReferenceValueInput = new RemoveReferenceValueInput(UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(), referenceValueId.get());
            var result = this.referenceRemoveMutationRunner.run(removeReferenceValueInput);

            String mutationResult = JsonPath.read(result.data(), "$.data.removeReferenceValue.__typename");
            assertThat(mutationResult).isEqualTo("SuccessPayload");

        };

        Consumer<Object> afterRemoveReferenceContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasNoValue();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(removeReferenceValueMutation)
                .consumeNextWith(afterRemoveReferenceContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private ReferenceWidget findBackgroundReferenceWidget(Form form) {
        return form.getPages().stream()
                .flatMap(page -> page.getGroups().stream())
                .flatMap(group -> group.getWidgets().stream())
                .filter(ReferenceWidget.class::isInstance)
                .map(ReferenceWidget.class::cast)
                .filter(referenceWidget -> "background".equals(referenceWidget.getReferenceName()))
                .findFirst()
                .orElseThrow();
    }
}

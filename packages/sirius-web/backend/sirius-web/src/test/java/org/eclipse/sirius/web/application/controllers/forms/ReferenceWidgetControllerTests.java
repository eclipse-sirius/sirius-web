/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.RemoveReferenceValueInput;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceClearExecutor;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceClearMutationRunner;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceCreateElementExecutor;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceRemoveMutationRunner;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceValueOptionsQueryRunner;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceWidgetChildCreationDescriptionsExecutor;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceWidgetRootCreationDescriptionsExecutor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithReferenceWidgetDescriptionProvider;
import org.eclipse.sirius.web.services.forms.FormWithUnhandledReferenceWidgetDescriptionProvider;
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
    private ReferenceClearExecutor referenceClearExecutor;

    @Autowired
    private ReferenceRemoveMutationRunner referenceRemoveMutationRunner;

    @Autowired
    private ReferenceCreateElementExecutor referenceCreateElementExecutor;

    @Autowired
    private ReferenceWidgetRootCreationDescriptionsExecutor referenceWidgetRootCreationDescriptionsExecutor;

    @Autowired
    private ReferenceWidgetChildCreationDescriptionsExecutor referenceWidgetChildCreationDescriptionsExecutor;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private FormWithUnhandledReferenceWidgetDescriptionProvider formWithUnhandledReferenceWidgetDescriptionProvider;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IObjectSearchService objectSearchService;

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
    @DisplayName("Given a reference widget with a false create button precondition, when it is displayed, then the create button is hidden")
    public void givenReferenceWidgetWithFalseCreateButtonPreconditionWhenItIsDisplayedThenCreateButtonIsHidden() {
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

        Consumer<Object> contentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Hidden create button", ReferenceWidget.class);
            assertThat(referenceWidget.getCreateButton()).isNull();
        });

        StepVerifier.create(flux)
                .consumeNextWith(contentConsumer)
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
    @DisplayName("Given an editable reference widget, when it is cleared with a custom handler, then the handler clears it")
    public void givenAnEditableReferenceWidgetWhenItIsClearedWithACustomHandlerThenTheHandlerClearsIt() {
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
            assertThat(referenceWidget.isReadOnly()).isFalse();
            referenceWidgetId.set(referenceWidget.getId());
        });

        Runnable clearValueMutation = () -> this.referenceClearExecutor.execute(new ClearReferenceInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get())).isSuccess();

        Consumer<Object> afterClearContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasNoValue();
            assertThat(groupNavigator.findWidget("Name", Textfield.class).getValue()).isEqualTo("Cleared by custom action");
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
    @DisplayName("Given a reference widget with an empty clear body, when it is cleared, then the default behavior clears it")
    public void givenReferenceWidgetWithAnEmptyClearBodyWhenItIsClearedThenDefaultBehaviorClearsIt() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionIdWithDefaultClear(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithReferenceWidgetDefaultClear"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
                .flux()
                .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            assertThat(groupNavigator.findWidget("Name", Textfield.class).getValue()).isEqualTo("Human");
            var referenceWidget = groupNavigator.findWidget("Super types", ReferenceWidget.class);
            assertThat(referenceWidget).hasValueWithLabel("NamedElement");
            assertThat(referenceWidget.isReadOnly()).isFalse();
            referenceWidgetId.set(referenceWidget.getId());
        });

        Runnable clearValueMutation = () -> this.referenceClearExecutor.execute(new ClearReferenceInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get())).isSuccess();

        Consumer<Object> afterClearContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            assertThat(groupNavigator.findWidget("Super types", ReferenceWidget.class)).hasNoValue();
            assertThat(groupNavigator.findWidget("Name", Textfield.class).getValue()).isEqualTo("Human");
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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in View, when creating a root element, then the semantic object is created")
    public void givenReferenceWidgetInViewWhenCreatingRootElementThenTheSemanticObjectIsCreated() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var createdObjectId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget("Super types", ReferenceWidget.class));
            assertThat(referenceWidget.get().isReadOnly()).isFalse();
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getRootCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_DOCUMENT.toString(), DomainPackage.eNS_URI, creationDescriptionId, widget.getDescriptionId());
            createdObjectId.set(this.referenceCreateElementExecutor.execute(input).isSuccess().getObjectId());
        };

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getCreatedEntity(createdObjectId.get());
        assertThat(entity.eContainer()).isNull();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in View, when creating a child element, then the semantic object is created")
    public void givenReferenceWidgetInViewWhenCreatingChildElementThenTheSemanticObjectIsCreated() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var createdObjectId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget("Super types", ReferenceWidget.class));
            assertThat(referenceWidget.get().isReadOnly()).isFalse();
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getChildCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), null, creationDescriptionId, widget.getDescriptionId());
            createdObjectId.set(this.referenceCreateElementExecutor.execute(input).isSuccess().getObjectId());
        };

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getCreatedEntity(createdObjectId.get());
        assertThat(entity.eContainer()).isInstanceOf(Domain.class);
        assertThat(((Domain) entity.eContainer()).getTypes()).contains(entity);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in Details, when creating a root element, then the semantic object is created")
    public void givenReferenceWidgetInDetailsWhenCreatingRootElementThenTheSemanticObjectIsCreated() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var createdObjectId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Human").group("Core Properties").findWidget("Super Types", ReferenceWidget.class));
            assertThat(referenceWidget.get().isReadOnly()).isFalse();
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getRootCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_DOCUMENT.toString(), DomainPackage.eNS_URI, creationDescriptionId, widget.getDescriptionId());
            createdObjectId.set(this.referenceCreateElementExecutor.execute(input).isSuccess().getObjectId());
        };

        StepVerifier.create(this.givenDetailsReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getCreatedEntity(createdObjectId.get());
        assertThat(entity.eContainer()).isNull();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in Details, when creating a child element, then the semantic object is created")
    public void givenReferenceWidgetInDetailsWhenCreatingChildElementThenTheSemanticObjectIsCreated() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var createdObjectId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Human").group("Core Properties").findWidget("Super Types", ReferenceWidget.class));
            assertThat(referenceWidget.get().isReadOnly()).isFalse();
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getChildCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), null, creationDescriptionId, widget.getDescriptionId());
            createdObjectId.set(this.referenceCreateElementExecutor.execute(input).isSuccess().getObjectId());
        };

        StepVerifier.create(this.givenDetailsReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getCreatedEntity(createdObjectId.get());
        assertThat(entity.eContainer()).isInstanceOf(Domain.class);
        assertThat(((Domain) entity.eContainer()).getTypes()).contains(entity);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a View reference creation body using CreateInstance, when creating an element, then the created object is returned")
    public void givenViewReferenceCreationBodyUsingCreateInstanceWhenCreatingElementThenTheCreatedObjectIsReturned() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var createdObjectId = new AtomicReference<String>();
        int initialTypeCount = this.getDomainTypeCount();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget("Instance creation super types", ReferenceWidget.class));
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getChildCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), null, creationDescriptionId, widget.getDescriptionId());
            createdObjectId.set(this.referenceCreateElementExecutor.execute(input).isSuccess().getObjectId());
        };

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getCreatedEntity(createdObjectId.get());
        assertThat(entity.eContainer()).isInstanceOf(Domain.class);
        assertThat(((Domain) entity.eContainer()).getTypes()).contains(entity);
        assertThat(this.getDomainTypeCount()).isEqualTo(initialTypeCount + 1);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a custom View reference creation body, when root creation is requested, then it executes without returning an object")
    public void givenCustomViewReferenceCreationBodyWhenRootCreationIsRequestedThenItExecutesWithoutReturningAnObject() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget("Custom creation super types", ReferenceWidget.class));
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
            assertThat(referenceWidget.get()).hasValueWithLabel("NamedElement");
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getRootCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_DOCUMENT.toString(), DomainPackage.eNS_URI, creationDescriptionId, widget.getDescriptionId());
            this.referenceCreateElementExecutor.execute(input).isSuccessWithoutResult();
        };

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .consumeNextWith(assertRefreshedFormThat(form -> {
                    var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Custom creation super types", ReferenceWidget.class);
                    assertThat(widget).hasValueWithLabel("NamedElement");
                    assertThat(widget.getReferenceValues()).hasSize(1);
                }))
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var owner = this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
        assertThat(owner.getName()).isEqualTo("Custom creation executed");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a custom View reference creation body, when child creation is requested, then it executes without returning an object")
    public void givenCustomViewReferenceCreationBodyWhenChildCreationIsRequestedThenItExecutesWithoutReturningAnObject() {
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget("Custom creation super types", ReferenceWidget.class));
            assertThat(referenceWidget.get().getCreateButton()).isNotNull();
            assertThat(referenceWidget.get()).hasValueWithLabel("NamedElement");
        });

        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var creationDescriptionId = this.getChildCreationDescriptionId(formId.get(), widget);
            var input = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), null, creationDescriptionId, widget.getDescriptionId());
            this.referenceCreateElementExecutor.execute(input).isSuccessWithoutResult();
        };

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .consumeNextWith(assertRefreshedFormThat(form -> {
                    var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Custom creation super types", ReferenceWidget.class);
                    assertThat(widget).hasValueWithLabel("NamedElement");
                    assertThat(widget.getReferenceValues()).hasSize(1);
                }))
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var owner = this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
        assertThat(owner.getName()).isEqualTo("Custom creation executed");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a failing View reference creation body, when creating an element, then an error is returned and no object is created")
    public void givenFailingViewReferenceCreationBodyWhenCreatingElementThenErrorIsReturnedAndNoObjectIsCreated() {
        this.assertCreateElementRejected(this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                "Failing creation super types", "Failed to execute the create reference action", false);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a form without a create handler, when creating an element, then an error is returned and no object is created")
    public void givenFormWithoutCreateHandlerWhenCreatingElementThenErrorIsReturnedAndNoObjectIsCreated() {
        this.assertCreateElementRejected(this.formWithUnhandledReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                "Super types", "No handler found to handle the input", false);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a read-only reference widget, when creating an element, then an error is returned and no object is created")
    public void givenReadOnlyReferenceWidgetWhenCreatingElementThenErrorIsReturnedAndNoObjectIsCreated() {
        this.assertCreateElementRejected(this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                "Read-only super types", "Read-only widget cannot be edited", true);
    }

    private void assertCreateElementRejected(String descriptionId, String widgetLabel, String expectedMessage, boolean readOnly) {
        int initialTypeCount = this.getDomainTypeCount();
        var formId = new AtomicReference<String>();
        var referenceWidget = new AtomicReference<ReferenceWidget>();
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                descriptionId, StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "FormWithReferenceWidget");

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            referenceWidget.set(new FormNavigator(form).page("Page").group("Group").findWidget(widgetLabel, ReferenceWidget.class));
            assertThat(referenceWidget.get().isReadOnly()).isEqualTo(readOnly);
        });
        Runnable createElementMutation = () -> {
            var widget = referenceWidget.get();
            var createInput = new CreateElementInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                    formId.get(), widget.getId(), StudioIdentifiers.DOMAIN_OBJECT.toString(), null, "types-Entity", widget.getDescriptionId());
            this.referenceCreateElementExecutor.execute(createInput).isError().hasMessage(expectedMessage);
        };

        StepVerifier.create(this.givenCreatedFormSubscription.createAndSubscribe(input).flux().filter(FormRefreshedEventPayload.class::isInstance))
                .consumeNextWith(initialFormContentConsumer)
                .then(createElementMutation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        assertThat(this.getDomainTypeCount()).isEqualTo(initialTypeCount);
    }

    private String getRootCreationDescriptionId(String formId, ReferenceWidget widget) {
        var variables = Map.<String, Object> of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                "representationId", formId,
                "domainId", DomainPackage.eNS_URI,
                "referenceKind", widget.getReferenceKind(),
                "descriptionId", widget.getDescriptionId());
        return this.referenceWidgetRootCreationDescriptionsExecutor.execute(variables)
                .hasCreationDescriptionIds("Entity")
                .getCreationDescriptionId();
    }

    private String getChildCreationDescriptionId(String formId, ReferenceWidget widget) {
        var variables = Map.<String, Object> of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                "representationId", formId,
                "containerId", StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "referenceKind", widget.getReferenceKind(),
                "descriptionId", widget.getDescriptionId());
        return this.referenceWidgetChildCreationDescriptionsExecutor.execute(variables)
                .hasCreationDescriptionIds("types-Entity")
                .getCreationDescriptionId();
    }

    private Flux<Object> givenViewReferenceFormSubscription() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(), StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "FormWithReferenceWidget");
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux().filter(FormRefreshedEventPayload.class::isInstance);
    }

    private Flux<Object> givenDetailsReferenceFormSubscription() {
        var representationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        return this.detailsEventSubscriptionRunner.run(input).flux().filter(FormRefreshedEventPayload.class::isInstance);
    }

    private Entity getCreatedEntity(String objectId) {
        var editingContext = this.editingContextSearchService.findById(StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID).orElseThrow();
        var object = this.objectSearchService.getObject(editingContext, objectId).orElseThrow();
        assertThat(object).isInstanceOf(Entity.class);
        var entity = (Entity) object;
        assertThat(entity.eResource()).isNotNull();
        return entity;
    }

    private int getDomainTypeCount() {
        var editingContext = this.editingContextSearchService.findById(StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID).orElseThrow();
        var domain = this.objectSearchService.getObject(editingContext, StudioIdentifiers.DOMAIN_OBJECT.toString()).filter(Domain.class::isInstance).map(Domain.class::cast).orElseThrow();
        return domain.getTypes().size();
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

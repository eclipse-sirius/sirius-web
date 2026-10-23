/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.SetReferenceValueInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceSetValueExecutor;
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
 * Integration tests of reference value setting.
 *
 * @author tgiraudet
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ReferenceWidgetSetControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithReferenceWidgetDescriptionProvider formWithReferenceWidgetDescriptionProvider;

    @Autowired
    private ReferenceSetValueExecutor referenceSetValueExecutor;

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

    @Autowired
    private IIdentityService identityService;

    @Autowired
    private IReferenceMessageService referenceMessageService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget with an empty set, when setting a reference value, then the existing value is replaced and the form is refreshed")
    public void givenReferenceWidgetWithEmptySetWhenSettingReferenceValueThenExistingValueIsReplacedAndFormIsRefreshed() {
        this.assertReferenceValueSet(this.givenViewReferenceFormSubscription(), "Page", "Group", "Target type", "Human");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget with a custom set operation, when setting a reference value, then the custom body updates the reference and the name")
    public void givenReferenceWidgetWithCustomSetWhenSettingReferenceValueThenCustomBodyUpdatesReferenceAndName() {
        this.assertReferenceValueSet(this.givenViewReferenceFormSubscription(), "Page", "Group", "Target type with custom set", "Set by custom action");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget without a set, when setting a reference value, then the reference is unchanged")
    public void givenReferenceWidgetWithoutSetWhenSettingReferenceValueThenReferenceIsUnchanged() {
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Target type without set", ReferenceWidget.class);
            assertThat(widget.isReadOnly()).isFalse();
            assertThat(widget.isMany()).isFalse();
            assertThat(widget.isSelectable()).isFalse();
            assertThat(widget.getReferenceValues()).extracting(ReferenceValue::getId).containsExactly(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
            referenceWidgetId.set(widget.getId());
        });

        Runnable setReferenceValue = () -> this.referenceSetValueExecutor.execute(new SetReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.ROOT_ENTITY_OBJECT.toString())).isSuccess();

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(setReferenceValue)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        assertThat(this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()).getName()).isEqualTo("Human");
        assertThat(this.identityService.getId(this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()).getRelations().get(0).getTargetType()))
                .isEqualTo(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget with a dynamic set precondition, when semantic data changes, then selection follows the evaluated precondition")
    public void givenReferenceWidgetWithDynamicSetPreconditionWhenSemanticDataChangesThenSelectionFollowsTheEvaluatedPrecondition() {
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var group = new FormNavigator(form).page("Page").group("Group");
            var customSetWidget = group.findWidget("Target type with custom set", ReferenceWidget.class);
            assertThat(customSetWidget.isSelectable()).isTrue();
            referenceWidgetId.set(customSetWidget.getId());
            assertThat(group.findWidget("Target type with dynamic set precondition", ReferenceWidget.class).isSelectable()).isTrue();
        });

        Runnable setReferenceValue = () -> this.referenceSetValueExecutor.execute(new SetReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.ROOT_ENTITY_OBJECT.toString())).isSuccess();

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var group = new FormNavigator(form).page("Page").group("Group");
            assertThat(group.findWidget("Target type with dynamic set precondition", ReferenceWidget.class).isSelectable()).isFalse();
            assertThat(group.findWidget("Target type", ReferenceWidget.class).isSelectable()).isTrue();
        });

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(setReferenceValue)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        assertThat(this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()).getName()).isEqualTo("Set by custom action");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget in Details, when setting a reference value, then the existing value is replaced and the form is refreshed")
    public void givenReferenceWidgetInDetailsWhenSettingReferenceValueThenExistingValueIsReplacedAndFormIsRefreshed() {
        this.assertReferenceValueSet(this.givenDetailsReferenceFormSubscription(), "humans", "Core Properties", "Target Type", "Human");
    }

    private void assertReferenceValueSet(Flux<Object> flux, String pageLabel, String groupLabel, String widgetLabel, String expectedName) {
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var widget = new FormNavigator(form).page(pageLabel).group(groupLabel).findWidget(widgetLabel, ReferenceWidget.class);
            assertThat(widget.isReadOnly()).isFalse();
            assertThat(widget.isSelectable()).isTrue();
            assertThat(widget.isMany()).isFalse();
            assertThat(widget.getReferenceValues())
                    .extracting(ReferenceValue::getId)
                    .containsExactly(this.identityService.getId(this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()).getRelations().get(0).getTargetType()));
            referenceWidgetId.set(widget.getId());
        });

        Runnable setReferenceValue = () -> this.referenceSetValueExecutor.execute(new SetReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.ROOT_ENTITY_OBJECT.toString())).isSuccess();

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var widget = new FormNavigator(form).page(pageLabel).group(groupLabel).findWidget(widgetLabel, ReferenceWidget.class);
            assertThat(widget.getReferenceValues()).extracting(ReferenceValue::getId).containsExactly(
                    StudioIdentifiers.ROOT_ENTITY_OBJECT.toString());
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(setReferenceValue)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        assertThat(this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()).getName()).isEqualTo(expectedName);
        assertThat(this.identityService.getId(this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()).getRelations().get(0).getTargetType()))
                .isEqualTo(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a read-only reference widget, when setting a reference value, then an error is returned and the reference is unchanged")
    public void givenReadOnlyReferenceWidgetWhenSettingReferenceValueThenErrorIsReturnedAndReferenceIsUnchanged() {
        this.assertSetReferenceValueRejected(this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                "Read-only target type", this.referenceMessageService.unableToEditReadOnlyWidget(), true);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a form without a set handler, when setting a reference value, then an error is returned and the reference is unchanged")
    public void givenFormWithoutSetHandlerWhenSettingReferenceValueThenErrorIsReturnedAndReferenceIsUnchanged() {
        this.assertSetReferenceValueRejected(this.formWithUnhandledReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(),
                "Super types", this.referenceMessageService.noHandlerFound(), false);
    }

    private void assertSetReferenceValueRejected(String descriptionId, String widgetLabel, String expectedMessage, boolean readOnly) {
        var owner = this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString());
        var initialTargetTypeId = this.identityService.getId(owner.getRelations().get(0).getTargetType());
        var initialSuperTypeIds = this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()).getSuperTypes().stream().map(this.identityService::getId).toList();
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                descriptionId, StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "FormWithReferenceWidget");

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var widget = new FormNavigator(form).page("Page").group("Group").findWidget(widgetLabel, ReferenceWidget.class);
            assertThat(widget.isReadOnly()).isEqualTo(readOnly);
            referenceWidgetId.set(widget.getId());
        });

        Runnable setReferenceValue = () -> this.referenceSetValueExecutor.execute(new SetReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.ROOT_ENTITY_OBJECT.toString())).isError().hasMessage(expectedMessage);

        StepVerifier.create(this.givenCreatedFormSubscription.createAndSubscribe(input).flux().filter(FormRefreshedEventPayload.class::isInstance))
                .consumeNextWith(initialFormContentConsumer)
                .then(setReferenceValue)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        assertThat(this.identityService.getId(this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()).getRelations().get(0).getTargetType())).isEqualTo(initialTargetTypeId);
        assertThat(this.getCreatedEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString()).getSuperTypes()).extracting(this.identityService::getId)
                .containsExactlyElementsOf(initialSuperTypeIds);
    }

    private Flux<Object> givenViewReferenceFormSubscription() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(), StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "FormWithReferenceWidget");
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux().filter(FormRefreshedEventPayload.class::isInstance);
    }

    private Flux<Object> givenDetailsReferenceFormSubscription() {
        var relationId = this.identityService.getId(this.getCreatedEntity(StudioIdentifiers.ROOT_ENTITY_OBJECT.toString()).getRelations().get(0));
        var representationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(relationId));
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

}

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
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.RemoveReferenceValueInput;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.tests.graphql.ReferenceRemoveExecutor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithReferenceWidgetDescriptionProvider;
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
 * Integration tests of reference value removal through the View DSL.
 *
 * @author tgiraudet
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ReferenceWidgetRemoveControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithReferenceWidgetDescriptionProvider formWithReferenceWidgetDescriptionProvider;

    @Autowired
    private ReferenceRemoveExecutor referenceRemoveExecutor;

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
    @DisplayName("Given a reference widget with an empty remove, when removing a reference value, then the reference is cleared and the form is refreshed")
    public void givenReferenceWidgetWithEmptyRemoveWhenRemovingReferenceValueThenReferenceIsClearedAndFormIsRefreshed() {
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var group = new FormNavigator(form).page("Page").group("Group");
            var widget = group.findWidget("Super types with default remove", ReferenceWidget.class);
            assertThat(widget.isRemovable()).isTrue();
            assertThat(widget.getReferenceValues()).extracting(ReferenceValue::getId)
                    .containsExactly(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString());
            referenceWidgetId.set(widget.getId());
            assertThat(group.findWidget("Super types without add", ReferenceWidget.class).isRemovable()).isFalse();
        });

        Runnable removeReferenceValue = () -> this.referenceRemoveExecutor.execute(new RemoveReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString())).isSuccess();

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Super types with default remove", ReferenceWidget.class);
            assertThat(widget.getReferenceValues()).isEmpty();
            assertThat(widget.isRemovable()).isTrue();
        });

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(removeReferenceValue)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
        assertThat(entity.getSuperTypes()).isEmpty();
        assertThat(entity.getName()).isEqualTo("Human");
        assertThat(this.getEntity(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString()).eResource()).isNotNull();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a reference widget with a custom remove, when removing a reference value, then the body uses the removed EObject and the precondition is refreshed")
    public void givenReferenceWidgetWithCustomRemoveWhenRemovingReferenceValueThenBodyUsesRemovedEObjectAndPreconditionIsRefreshed() {
        var formId = new AtomicReference<String>();
        var referenceWidgetId = new AtomicReference<String>();
        var expectedName = "Removed " + this.getEntity(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString()).getName();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Super types with custom remove", ReferenceWidget.class);
            assertThat(widget.isRemovable()).isTrue();
            assertThat(widget.getReferenceValues()).extracting(ReferenceValue::getId)
                    .containsExactly(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString());
            referenceWidgetId.set(widget.getId());
        });

        Runnable removeReferenceValue = () -> this.referenceRemoveExecutor.execute(new RemoveReferenceValueInput(UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, formId.get(), referenceWidgetId.get(),
                StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString())).isSuccess();

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var widget = new FormNavigator(form).page("Page").group("Group").findWidget("Super types with custom remove", ReferenceWidget.class);
            assertThat(widget.getReferenceValues()).isEmpty();
            assertThat(widget.isRemovable()).isFalse();
        });

        StepVerifier.create(this.givenViewReferenceFormSubscription())
                .consumeNextWith(initialFormContentConsumer)
                .then(removeReferenceValue)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var entity = this.getEntity(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString());
        assertThat(entity.getSuperTypes()).isEmpty();
        assertThat(entity.getName()).isEqualTo(expectedName);
        assertThat(this.getEntity(StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString()).eResource()).isNotNull();
    }

    private Flux<Object> givenViewReferenceFormSubscription() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                this.formWithReferenceWidgetDescriptionProvider.getRepresentationDescriptionId(), StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "FormWithReferenceWidget");
        return this.givenCreatedFormSubscription.createAndSubscribe(input).flux().filter(FormRefreshedEventPayload.class::isInstance);
    }

    private Entity getEntity(String objectId) {
        var editingContext = this.editingContextSearchService.findById(StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID).orElseThrow();
        var object = this.objectSearchService.getObject(editingContext, objectId).orElseThrow();
        assertThat(object).isInstanceOf(Entity.class);
        return (Entity) object;
    }
}

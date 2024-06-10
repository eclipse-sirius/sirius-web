/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.controllers.formdescriptioneditors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.DeleteWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationDataRepository;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormDescriptionEditorSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.formdescriptioneditors.AddWidgetMutationRunner;
import org.eclipse.sirius.web.tests.services.formdescriptioneditors.DeleteWidgetMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the lifecycle of the FormDescriptionEditor representation.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class FormDescriptionEditorLifecycleControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormDescriptionEditorSubscription givenCreatedFormDescriptionEditorSubscription;

    @Autowired
    private AddWidgetMutationRunner addWidgetMutationRunner;

    @Autowired
    private DeleteWidgetMutationRunner deleteWidgetMutationRunner;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private IRepresentationDataRepository representationDataRepository;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a form description editor representation, when reload it, then the new version is sent")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorRepresentationWhenWeReloadItThenTheNewVersionIsSent() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                UUID.nameUUIDFromBytes("FormDescriptionEditor".getBytes()).toString(),
                StudioIdentifiers.FORM_DESCRIPTION_OBJECT.toString(),
                "FormDescriptionEditor"
        );
        var flux = this.givenCreatedFormDescriptionEditorSubscription.createAndSubscribe(input);

        var formDescriptionEditorId = new AtomicReference<String>();
        var representationData = new AtomicReference<RepresentationData>();
        var newWidgetId = new AtomicReference<String>();

        Consumer<FormDescriptionEditorRefreshedEventPayload> initialFormDescriptionEditorContentConsumer = payload -> Optional.of(payload)
                .map(FormDescriptionEditorRefreshedEventPayload::formDescriptionEditor)
                .ifPresentOrElse(formDescriptionEditor -> {
                    formDescriptionEditorId.set(formDescriptionEditor.getId());
                    var firstGroup = formDescriptionEditor.getPages().get(0).getGroups().get(0);
                    assertThat(firstGroup.getWidgets()).hasSize(4);

                    var representationId = new UUIDParser().parse(formDescriptionEditor.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid identifier"));
                    this.representationDataRepository.findById(representationId)
                            .ifPresentOrElse(representationData::set, () -> fail("Missing representation data"));
                }, () -> fail("Missing form description editor"));

        Runnable addWidget = () -> {
            var addWidgetInput = new AddWidgetInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    formDescriptionEditorId.get(),
                    StudioIdentifiers.GROUP_OBJECT.toString(),
                    "Textfield",
                    0
            );
            var result = this.addWidgetMutationRunner.run(addWidgetInput);
            String typename = JsonPath.read(result, "$.data.addWidget.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<FormDescriptionEditorRefreshedEventPayload> addedWidgetConsumer = payload -> Optional.of(payload)
                .map(FormDescriptionEditorRefreshedEventPayload::formDescriptionEditor)
                .ifPresentOrElse(formDescriptionEditor -> {
                    var firstGroup = formDescriptionEditor.getPages().get(0).getGroups().get(0);
                    assertThat(firstGroup.getWidgets()).hasSize(5);
                    newWidgetId.set(firstGroup.getWidgets().get(4).getId());
                }, () -> fail("Missing form description editor"));

        Runnable reloadFormDescriptionEditor = () -> {
            // Restore the previous state of the FormDescription
            var deleteWidgetInput = new DeleteWidgetInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    formDescriptionEditorId.get(),
                    newWidgetId.get());
            var result = this.deleteWidgetMutationRunner.run(deleteWidgetInput);
            String typename = JsonPath.read(result, "$.data.deleteWidget.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            // Ask the FormDescriptionEditor to reload (and thus refresh on the restored FormDescription state)
            this.representationDataRepository.save(representationData.get());
            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();

            Consumer<IEditingContextEventProcessor> editingContextEventProcessorConsumer = editingContextEventProcessor -> {
                editingContextEventProcessor.getRepresentationEventProcessors().stream()
                        .filter(representationEventProcessor -> representationEventProcessor.getRepresentation().getId().equals(formDescriptionEditorId.get()))
                        .findFirst()
                        .ifPresentOrElse(representationEventProcessor -> {
                            IInput reloadInput = UUID::randomUUID;
                            representationEventProcessor.refresh(new ChangeDescription(ChangeKind.RELOAD_REPRESENTATION, formDescriptionEditorId.get(), reloadInput));
                        }, () -> fail("Missing representation event processor"));
            };
            this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString()))
                    .findFirst()
                    .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormDescriptionEditorContentConsumer)
                .then(addWidget)
                .consumeNextWith(addedWidgetConsumer)
                .then(reloadFormDescriptionEditor)
                .consumeNextWith(initialFormDescriptionEditorContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

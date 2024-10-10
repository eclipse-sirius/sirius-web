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

package org.eclipse.sirius.web.services.undoredo;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditRadioInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RadioOption;
import org.eclipse.sirius.components.forms.tests.graphql.EditRadioMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithRadioDescriptionProvider;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.undoredo.RedoMutationRunner;
import org.eclipse.sirius.web.tests.undoredo.UndoMutationRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

/**
 * Integration tests of undo redo semantic data.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class UndoRedoMutationsPrePostProcessTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithRadioDescriptionProvider formWithRadioDescriptionProvider;

    @Autowired
    private EditRadioMutationRunner editRadioMutationRunner;

    @Autowired
    private UndoMutationRunner undoMutationRunner;

    @Autowired
    private RedoMutationRunner redoMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToRadioForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                this.formWithRadioDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                "FormWithCheckbox"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a radio widget, when it is edited, then we can undo and redo the change")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenRadioWidgetWhenItIsEditedThenItsWeCanUndoRedoTheChange() {
        var flux = this.givenSubscriptionToRadioForm();

        var formId = new AtomicReference<String>();
        var radioId = new AtomicReference<String>();
        var optionId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var radio = groupNavigator.findWidget("SuperType", Radio.class);

                    radioId.set(radio.getId());
                    assertThat(radio)
                            .hasLabel("SuperType")
                            .hasHelp("Pick a super type")
                            .hasValueWithLabel("NamedElement")
                            .isNotReadOnly();

                    var newValue = radio.getOptions().stream()
                            .filter(radioOption -> radioOption.getLabel().equals("Human"))
                            .findFirst()
                            .map(RadioOption::getId)
                            .orElseThrow(IllegalStateException::new);
                    optionId.set(newValue);
                }, () -> fail("Missing form"));

        var mutationId = UUID.randomUUID();

        Runnable editCheckbox = () -> {
            var input = new EditRadioInput(mutationId, StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), formId.get(), radioId.get(), optionId.get());
            var result = this.editRadioMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editRadio.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = payload -> Optional.of(payload)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var radio = groupNavigator.findWidget("SuperType", Radio.class);

                    assertThat(radio)
                            .hasLabel("SuperType")
                            .hasHelp("Pick a super type")
                            .hasValueWithLabel("Human")
                            .isNotReadOnly();
                }, () -> fail("Missing form"));

        Runnable undoMutation = () -> {
            var input = new UndoInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), mutationId.toString());
            var result = this.undoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Runnable redoMutation = () -> {
            var input = new RedoInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), mutationId.toString());
            var result = this.redoMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.undo.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editCheckbox)
                .consumeNextWith(updatedFormContentConsumer)
                .then(undoMutation)
                .consumeNextWith(initialFormContentConsumer)
                .then(redoMutation)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

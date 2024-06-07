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

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorFor;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorIf;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.ChartWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.DateTime;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.Link;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.MultiSelect;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormDescriptionEditorSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.formdescriptioneditors.AddWidgetMutationRunner;
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

/**
 * Integration tests of the form description editors.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormDescriptionEditorControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormDescriptionEditorSubscription givenCreatedFormDescriptionEditorSubscription;

    @Autowired
    private AddWidgetMutationRunner addWidgetMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<FormDescriptionEditorRefreshedEventPayload> givenSubscriptionToFormDescriptionEditor() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                UUID.nameUUIDFromBytes("FormDescriptionEditor".getBytes()).toString(),
                StudioIdentifiers.FORM_DESCRIPTION_OBJECT.toString(),
                "FormDescriptionEditor"
        );
        return this.givenCreatedFormDescriptionEditorSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a form description editor, when we subscribe to its events, then the representation data are received")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeSubscribeToItsEventsThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToFormDescriptionEditor();

        Consumer<FormDescriptionEditorRefreshedEventPayload> initialFormDescriptionEditorContentConsumer = payload -> Optional.of(payload)
                .map(FormDescriptionEditorRefreshedEventPayload::formDescriptionEditor)
                .ifPresentOrElse(formDescriptionEditor -> {
                    assertThat(formDescriptionEditor).isNotNull();
                }, () -> fail("Missing form description editor"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormDescriptionEditorContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a form description editor, when we add a textfield, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddTextfieldThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Textfield", Textfield.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a checkbox, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddCheckboxThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Checkbox", Checkbox.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a select, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddSelectThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Select", Select.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a multi-select, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddMultiSelectThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("MultiSelect", MultiSelect.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a button, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddButtonThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Button", Button.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a textarea, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddTextareaThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("TextArea", Textarea.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a label, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddLabelThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Label", LabelWidget.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a link, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddLinkThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Link", Link.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a radio, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddRadioThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("Radio", Radio.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a list, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddListThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("List", List.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a pie chart, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddPieChartThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("PieChart", ChartWidget.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a bar chart, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddBarChartThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("BarChart", ChartWidget.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a date time, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddDateTimeThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("DateTime", DateTime.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a flexbox container, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddFlexboxContainerThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("FlexboxContainer", FlexboxContainer.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add a for, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddForThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("FormElementFor", FormDescriptionEditorFor.class);
    }

    @Test
    @DisplayName("Given a form description editor, when we add an if, then the representation is updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenFormDescriptionEditorWhenWeAddAnIfThenTheRepresentationIsUpdated() {
        this.givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated("FormElementIf", FormDescriptionEditorIf.class);
    }

    private void givenFormDescriptionEditorWhenWeAddSomethingThenTheRepresentationIsUpdated(String widgetKind, Class<? extends AbstractWidget> expectedWidgetClass) {
        var flux = this.givenSubscriptionToFormDescriptionEditor();

        var formDescriptionEditorId = new AtomicReference<String>();

        Consumer<FormDescriptionEditorRefreshedEventPayload> initialFormDescriptionEditorContentConsumer = payload -> Optional.of(payload)
                .map(FormDescriptionEditorRefreshedEventPayload::formDescriptionEditor)
                .ifPresentOrElse(formDescriptionEditor -> {
                    assertThat(formDescriptionEditor).isNotNull();
                    var firstGroup = formDescriptionEditor.getPages().get(0).getGroups().get(0);
                    assertThat(firstGroup.getWidgets()).hasSize(4);

                    formDescriptionEditorId.set(formDescriptionEditor.getId());
                }, () -> fail("Missing form description editor"));

        Runnable addWidget = () -> {
            var addWidgetInput = new AddWidgetInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                    formDescriptionEditorId.get(),
                    StudioIdentifiers.GROUP_OBJECT.toString(),
                    widgetKind,
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
                    assertThat(firstGroup.getWidgets().get(0)).isInstanceOf(expectedWidgetClass);
                }, () -> fail("Missing form description editor"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormDescriptionEditorContentConsumer)
                .then(addWidget)
                .consumeNextWith(addedWidgetConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

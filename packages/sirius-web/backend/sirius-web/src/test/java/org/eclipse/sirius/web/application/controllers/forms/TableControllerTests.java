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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.tables.tests.assertions.TablesAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.EditCheckboxCellInput;
import org.eclipse.sirius.components.collaborative.tables.dto.EditMultiSelectCellInput;
import org.eclipse.sirius.components.collaborative.tables.dto.EditSelectCellInput;
import org.eclipse.sirius.components.collaborative.tables.dto.EditTextfieldCellInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.TableWidget;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.tests.graphql.EditCheckboxCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.EditMultiSelectCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.EditSelectCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.EditTextfieldCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.navigation.LineNavigator;
import org.eclipse.sirius.components.tables.tests.navigation.TableNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithTableEditingContextDescriptionProvider;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.jetbrains.annotations.NotNull;
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
 * Integration tests of the table widget.
 *
 * @author lfasani
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TableControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private EditTextfieldCellMutationRunner editTextfieldCellMutationRunner;

    @Autowired
    private EditSelectCellMutationRunner editSelectCellMutationRunner;

    @Autowired
    private EditCheckboxCellMutationRunner editCheckboxCellMutationRunner;

    @Autowired
    private EditMultiSelectCellMutationRunner editMultiSelectCellMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToFormWithTableWidget() {
        var input = new CreateRepresentationInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), FormWithTableEditingContextDescriptionProvider.TASK_FORM_ID,
                PapayaIdentifiers.FIRST_ITERATION_OBJECT.toString(), "FormWithTable");
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a table widget, when it is displayed, then it is properly initialized")
    @Sql(scripts = { "/scripts/papaya.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenTableWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToFormWithTableWidget();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload).filter(FormRefreshedEventPayload.class::isInstance).map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form).ifPresentOrElse(form -> {
                    var tableWidget = new FormNavigator(form).page("Iteration Page").group("Iteration Group").findWidget("Tasks", TableWidget.class);
                    assertThat(tableWidget.getTable().getColumns()).hasSize(10);
                    assertThat(tableWidget.getTable().getLines()).hasSize(3);
                    assertThat(tableWidget.getTable()).hasFieldOrPropertyWithValue("label", "Iteration 2024.3.0");
                    assertThat(tableWidget.getTable().getLines().stream().flatMap(line -> line.getCells().stream()).toList()).hasSize(10 * 3);
                    Line line = tableWidget.getTable().getLines().get(0);
                    LineNavigator lineNavigator = new LineNavigator(line);

                    TableNavigator tableNavigator = new TableNavigator(tableWidget.getTable());
                    assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("Improve some features of the deck");
                    assertThat(lineNavigator.checkboxCellByColumnId(tableNavigator.column("Is Done").getId())).hasValue(false);
                    assertThat(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId())).hasValue("P1");
                    assertThat(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies").getId())).hasValues(List.of());

                }, () -> fail("Missing form"));

        StepVerifier.create(flux).consumeNextWith(initialFormContentConsumer).thenCancel().verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a table widget, when the cells are edited, then the values are updated")
    @Sql(scripts = { "/scripts/papaya.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenTableWidgetWhenValuesAreEditedThenValuesAreUpdated() {
        var flux = this.givenSubscriptionToFormWithTableWidget();

        var formId = new AtomicReference<String>();
        var tableId = new AtomicReference<String>();
        var lineId = new AtomicReference<UUID>();
        var textfieldCellId = new AtomicReference<UUID>();
        var checkboxCellId = new AtomicReference<UUID>();
        var selectCellId = new AtomicReference<UUID>();
        var multiSelectCellId = new AtomicReference<UUID>();

        Consumer<Object> initialFormContentConsumer = payload -> Optional.of(payload).filter(FormRefreshedEventPayload.class::isInstance).map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form).ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var table = getTable(form);
                    tableId.set(table.getId());
                    Line line = table.getLines().get(0);
                    LineNavigator lineNavigator = new LineNavigator(line);
                    lineId.set(line.getId());

                    TableNavigator tableNavigator = new TableNavigator(table);
                    textfieldCellId.set(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId()).getId());

                }, () -> fail("Missing form"));

        Runnable editNameTextfieldCell = () -> {
            var input = new EditTextfieldCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId.get(), tableId.get(), textfieldCellId.get().toString(), "newName");
            var result = this.editTextfieldCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfieldCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editNameConsumer = getEditNameConsumer(tableId, selectCellId);

        Runnable editPrioritySelectCell = () -> {
            var input = new EditSelectCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId.get(), tableId.get(), selectCellId.get().toString(), "P2");
            var result = this.editSelectCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editSelectCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editPriorityConsumer = getEditPriorityConsumer(tableId, multiSelectCellId);

        Runnable editDependenciesMultiSelectCell = () -> {
            var input = new EditMultiSelectCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId.get(), tableId.get(), multiSelectCellId.get().toString(), List.of("e6e8f081-27f5-40e3-a8ab-1e6f0f13df12", "e1c5bd66-54c2-45f1-ae3a-99d3f039affd"));
            var result = this.editMultiSelectCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editMultiSelectCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editDependenciesConsumer = getEditDependenciesConsumer(tableId, checkboxCellId);

        Runnable editIsDoneCheckboxCell = () -> {
            var input = new EditCheckboxCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), formId.get(), tableId.get(), checkboxCellId.get().toString(), true);
            var result = this.editCheckboxCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editCheckboxCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editIsDoneConsumer = getEditIsDoneConsumer();

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editNameTextfieldCell)
                .consumeNextWith(editNameConsumer)
                .then(editPrioritySelectCell)
                .consumeNextWith(editPriorityConsumer)
                .then(editDependenciesMultiSelectCell)
                .consumeNextWith(editDependenciesConsumer)
                .then(editIsDoneCheckboxCell)
                .consumeNextWith(editIsDoneConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @NotNull
    private Consumer<Object> getEditIsDoneConsumer() {
        Consumer<Object> editIsDoneConsumer = payload -> Optional.of(payload)
            .filter(FormRefreshedEventPayload.class::isInstance)
            .map(FormRefreshedEventPayload.class::cast)
            .map(FormRefreshedEventPayload::form)
            .ifPresentOrElse(form -> {
                var table = getTable(form);
                TableNavigator tableNavigator = new TableNavigator(table);
                Line line = table.getLines().get(0);
                LineNavigator lineNavigator = new LineNavigator(line);

                assertThat(lineNavigator.checkboxCellByColumnId(tableNavigator.column("Is Done").getId())).hasValue(true);

            }, () -> fail("Missing form"));
        return editIsDoneConsumer;
    }

    @NotNull
    private Consumer<Object> getEditDependenciesConsumer(AtomicReference<String> tableId, AtomicReference<UUID> checkboxCellId) {
        Consumer<Object> editDependenciesConsumer = payload -> Optional.of(payload)
            .filter(FormRefreshedEventPayload.class::isInstance)
            .map(FormRefreshedEventPayload.class::cast)
            .map(FormRefreshedEventPayload::form)
            .ifPresentOrElse(form -> {
                var table = getTable(form);
                tableId.set(table.getId());
                TableNavigator tableNavigator = new TableNavigator(table);
                Line line = table.getLines().get(0);
                LineNavigator lineNavigator = new LineNavigator(line);
                checkboxCellId.set(lineNavigator.checkboxCellByColumnId(tableNavigator.column("Is Done").getId()).getId());

                assertThat(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies").getId())).hasValues(List.of("e6e8f081-27f5-40e3-a8ab-1e6f0f13df12", "e1c5bd66-54c2-45f1-ae3a-99d3f039affd"));

            }, () -> fail("Missing form"));
        return editDependenciesConsumer;
    }

    @NotNull
    private Consumer<Object> getEditPriorityConsumer(AtomicReference<String> tableId, AtomicReference<UUID> multiSelectCellId) {
        Consumer<Object> editPriorityConsumer = payload -> Optional.of(payload)
            .filter(FormRefreshedEventPayload.class::isInstance)
            .map(FormRefreshedEventPayload.class::cast)
            .map(FormRefreshedEventPayload::form)
            .ifPresentOrElse(form -> {
                var table = getTable(form);
                tableId.set(table.getId());
                TableNavigator tableNavigator = new TableNavigator(table);
                Line line = table.getLines().get(0);
                LineNavigator lineNavigator = new LineNavigator(line);
                multiSelectCellId.set(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies").getId()).getId());

                assertThat(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId())).hasValue("P2");

            }, () -> fail("Missing form"));
        return editPriorityConsumer;
    }

    @NotNull
    private Consumer<Object> getEditNameConsumer(AtomicReference<String> tableId, AtomicReference<UUID> selectCellId) {
        Consumer<Object> editNameConsumer = payload -> Optional.of(payload)
            .filter(FormRefreshedEventPayload.class::isInstance)
            .map(FormRefreshedEventPayload.class::cast)
            .map(FormRefreshedEventPayload::form)
            .ifPresentOrElse(form -> {
                var table = getTable(form);
                tableId.set(table.getId());
                TableNavigator tableNavigator = new TableNavigator(table);
                Line line = table.getLines().get(0);
                LineNavigator lineNavigator = new LineNavigator(line);

                selectCellId.set(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId()).getId());

                assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("newName");

            }, () -> fail("Missing form"));
        return editNameConsumer;
    }

    private Table getTable(Form form) {
        return new FormNavigator(form).page("Iteration Page").group("Iteration Group").findWidget("Tasks", TableWidget.class).getTable();
    }
}

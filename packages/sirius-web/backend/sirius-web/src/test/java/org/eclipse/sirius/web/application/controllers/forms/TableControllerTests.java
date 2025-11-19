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
import static org.eclipse.sirius.components.tables.tests.assertions.TablesAssertions.assertThat;
import static org.eclipse.sirius.web.tests.services.tables.assertions.TableCheckboxCellAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.EditMultiSelectCellInput;
import org.eclipse.sirius.components.collaborative.tables.dto.EditSelectCellInput;
import org.eclipse.sirius.components.collaborative.tables.dto.EditTextfieldCellInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.tests.graphql.EditMultiSelectCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.EditSelectCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.EditTextfieldCellMutationRunner;
import org.eclipse.sirius.components.tables.tests.navigation.LineNavigator;
import org.eclipse.sirius.components.tables.tests.navigation.TableNavigator;
import org.eclipse.sirius.components.widget.table.TableWidget;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.table.customcells.EditCheckboxCellInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithTableDescriptionProvider;
import org.eclipse.sirius.web.services.forms.FormWithViewTableDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.EditCheckboxCellMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.tables.navigation.LineCheckboxCellNavigator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private FormWithViewTableDescriptionProvider formWithViewTableDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToFormWithTableWidget() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                FormWithTableDescriptionProvider.TASK_FORM_ID,
                PapayaIdentifiers.FIRST_ITERATION_OBJECT.toString(),
                "FormWithTable");
        return this.givenCreatedFormSubscription.createAndSubscribe(input)
            .filter(FormRefreshedEventPayload.class::isInstance);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table widget, when it is displayed, then it is properly initialized")
    public void givenTableWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToFormWithTableWidget();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var tableWidget = new FormNavigator(form).page("Iteration Page").group("Iteration Group").findWidget("Tasks", TableWidget.class);
            assertThat(tableWidget.getTable().getColumns()).hasSize(9);
            assertThat(tableWidget.getTable().getLines()).hasSize(3);
            assertThat(tableWidget.getTable().getLines().stream().flatMap(line -> line.getCells().stream()).toList()).hasSize(9 * 3);

            Line line = tableWidget.getTable().getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);
            LineCheckboxCellNavigator checkboxCellNavigator = new LineCheckboxCellNavigator(line);

            TableNavigator tableNavigator = new TableNavigator(tableWidget.getTable());

            assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("Improve some features of the deck");
            assertThat(checkboxCellNavigator.checkboxCellByColumnId(tableNavigator.column("Done").getId())).hasValue(false);
            assertThat(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId())).hasValue("P1");
            assertThat(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies").getId())).hasValues(List.of());

        });

        StepVerifier.create(flux).consumeNextWith(initialFormContentConsumer).thenCancel().verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table widget, when the cells are edited, then the values are updated")
    public void givenTableWidgetWhenValuesAreEditedThenValuesAreUpdated() {
        var flux = this.givenSubscriptionToFormWithTableWidget();

        var formId = new AtomicReference<String>();
        var tableId = new AtomicReference<String>();
        var textfieldCellId = new AtomicReference<UUID>();
        var checkboxCellId = new AtomicReference<UUID>();
        var selectCellId = new AtomicReference<UUID>();
        var multiSelectCellId = new AtomicReference<UUID>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var table = this.getTable(form);
            tableId.set(table.getId());
            Line line = table.getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);

            TableNavigator tableNavigator = new TableNavigator(table);
            textfieldCellId.set(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId()).getId());

        });

        Runnable editNameTextfieldCell = () -> {
            var input = new EditTextfieldCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), tableId.get(), textfieldCellId.get(), "newName");
            var result = this.editTextfieldCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfieldCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editNameConsumer = this.getEditNameConsumer(tableId, selectCellId);

        Runnable editPrioritySelectCell = () -> {
            var input = new EditSelectCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), tableId.get(), selectCellId.get(), "P2");
            var result = this.editSelectCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editSelectCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editPriorityConsumer = this.getEditPriorityConsumer(tableId, multiSelectCellId);

        Runnable editDependenciesMultiSelectCell = () -> {
            var input = new EditMultiSelectCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), tableId.get(), multiSelectCellId.get(),
                    List.of("e6e8f081-27f5-40e3-a8ab-1e6f0f13df12", "e1c5bd66-54c2-45f1-ae3a-99d3f039affd"));
            var result = this.editMultiSelectCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editMultiSelectCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editDependenciesConsumer = this.getEditDependenciesConsumer(tableId, checkboxCellId);

        Runnable editIsDoneCheckboxCell = () -> {
            var input = new EditCheckboxCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), tableId.get(), checkboxCellId.get(), true);
            var result = this.editCheckboxCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editCheckboxCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editIsDoneConsumer = this.getEditIsDoneConsumer();

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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a form with a view based table widget, when it is displayed, then it is properly initialized")
    public void givenFormWithViewBasedTableWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.formWithViewTableDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "FormWithViewTable");
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
            .filter(FormRefreshedEventPayload.class::isInstance);

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var tableWidget = new FormNavigator(form).page("Page").group("Group").findWidget("Types", TableWidget.class);

            assertThat(tableWidget.getTable().getColumns()).hasSize(1);
            assertThat(tableWidget.getTable().getLines()).hasSize(4);
            assertThat(tableWidget.getTable().getLines().stream().flatMap(line -> line.getCells().stream()).toList()).hasSize(4);

            Line line = tableWidget.getTable().getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);

            TableNavigator tableNavigator = new TableNavigator(tableWidget.getTable());
            assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("Success");

        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a form with a view based table widget, when an cell is edited, then its cell body is properly executed")
    public void givenFormWithViewBasedTableWidgetWhenAnCellIsEditedThenItsCellBodyIsProperlyExecuted() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.formWithViewTableDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "FormWithViewTable");
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input)
            .filter(FormRefreshedEventPayload.class::isInstance);

        var formId = new AtomicReference<String>();
        var tableId = new AtomicReference<String>();
        var textfieldCellId = new AtomicReference<UUID>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());
            var tableWidget = new FormNavigator(form).page("Page").group("Group").findWidget("Types", TableWidget.class);

            assertThat(tableWidget.getTable().getColumns()).hasSize(1);
            assertThat(tableWidget.getTable().getLines()).hasSize(4);
            assertThat(tableWidget.getTable().getLines().stream().flatMap(line -> line.getCells().stream()).toList()).hasSize(4);

            Line line = tableWidget.getTable().getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);

            TableNavigator tableNavigator = new TableNavigator(tableWidget.getTable());
            assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("Success");

            tableId.set(tableWidget.getTable().getId());
            textfieldCellId.set(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId()).getId());

        });

        Runnable editNameTextfieldCell = () -> {
            var editNameTextfieldCellInput = new EditTextfieldCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), formId.get(), tableId.get(), textfieldCellId.get(),
                    "newName");
            var result = this.editTextfieldCellMutationRunner.run(editNameTextfieldCellInput);

            String typename = JsonPath.read(result, "$.data.editTextfieldCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> editNameConsumer = assertRefreshedFormThat(form -> {
            var tableWidget = new FormNavigator(form).page("Page").group("Group").findWidget("Types", TableWidget.class);
            Line line = tableWidget.getTable().getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);

            TableNavigator tableNavigator = new TableNavigator(tableWidget.getTable());
            assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("newName");

        });


        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editNameTextfieldCell)
                .consumeNextWith(editNameConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<Object> getEditIsDoneConsumer() {
        return assertRefreshedFormThat(form -> {
            var table = this.getTable(form);
            TableNavigator tableNavigator = new TableNavigator(table);
            Line line = table.getLines().get(0);
            LineCheckboxCellNavigator lineCheckboxCellNavigator = new LineCheckboxCellNavigator(line);

            assertThat(lineCheckboxCellNavigator.checkboxCellByColumnId(tableNavigator.column("Done").getId())).hasValue(true);

        });
    }

    private Consumer<Object> getEditDependenciesConsumer(AtomicReference<String> tableId, AtomicReference<UUID> checkboxCellId) {
        return assertRefreshedFormThat(form -> {
            var table = this.getTable(form);
            tableId.set(table.getId());
            TableNavigator tableNavigator = new TableNavigator(table);
            Line line = table.getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);
            LineCheckboxCellNavigator lineCheckboxCellNavigator = new LineCheckboxCellNavigator(line);
            checkboxCellId.set(lineCheckboxCellNavigator.checkboxCellByColumnId(tableNavigator.column("Done").getId()).getId());

            assertThat(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies")
                    .getId())).hasValues(List.of("e6e8f081-27f5-40e3-a8ab-1e6f0f13df12", "e1c5bd66-54c2-45f1-ae3a-99d3f039affd"));

        });
    }

    private Consumer<Object> getEditPriorityConsumer(AtomicReference<String> tableId, AtomicReference<UUID> multiSelectCellId) {
        return assertRefreshedFormThat(form -> {
            var table = this.getTable(form);
            tableId.set(table.getId());
            TableNavigator tableNavigator = new TableNavigator(table);
            Line line = table.getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);
            multiSelectCellId.set(lineNavigator.multiSelectCellByColumnId(tableNavigator.column("Dependencies").getId()).getId());

            assertThat(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId())).hasValue("P2");

        });
    }

    private Consumer<Object> getEditNameConsumer(AtomicReference<String> tableId, AtomicReference<UUID> selectCellId) {
        return assertRefreshedFormThat(form -> {
            var table = this.getTable(form);
            tableId.set(table.getId());
            TableNavigator tableNavigator = new TableNavigator(table);
            Line line = table.getLines().get(0);
            LineNavigator lineNavigator = new LineNavigator(line);

            selectCellId.set(lineNavigator.selectCellByColumnId(tableNavigator.column("Priority").getId()).getId());

            assertThat(lineNavigator.textfieldCellByColumnId(tableNavigator.column("Name").getId())).hasValue("newName");

        });
    }

    private Table getTable(Form form) {
        return new FormNavigator(form)
                .page("Iteration Page")
                .group("Iteration Group")
                .findWidget("Tasks", TableWidget.class)
                .getTable();
    }
}

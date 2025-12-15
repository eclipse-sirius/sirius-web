/*******************************************************************************
 * Copyright (c) 2024, 2026 CEA LIST.
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
package org.eclipse.sirius.web.application.controllers.tables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedColumnFilterThat;
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedColumnSortThat;
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedGlobalFilterThat;
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedTableThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeColumnFilterInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeColumnSortInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeGlobalFilterValueInput;
import org.eclipse.sirius.components.collaborative.tables.dto.InvokeToolMenuEntryInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeColumnFilterMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeColumnSortMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeGlobalFilterMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.InvokeToolMenuEntryMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.TableConfigurationQueryRunner;
import org.eclipse.sirius.components.tables.tests.graphql.TableEventSubscriptionRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ToolMenuEntriesQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTableSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the table representation with a papaya model.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaTableControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTableSubscription givenCreatedTableSubscription;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private ChangeGlobalFilterMutationRunner changeGlobalFilterMutationRunner;

    @Autowired
    private ChangeColumnFilterMutationRunner changeColumnFilterMutationRunner;

    @Autowired
    private ChangeColumnSortMutationRunner changeColumnSortMutationRunner;

    @Autowired
    private TableEventSubscriptionRunner tableEventSubscriptionRunner;

    @Autowired
    private TableConfigurationQueryRunner tableConfigurationQueryRunner;

    @Autowired
    private ToolMenuEntriesQueryRunner toolMenuEntriesQueryRunner;

    @Autowired
    private InvokeToolMenuEntryMutationRunner invokeToolMenuEntryMutationRunner;


    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTable() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "papaya_package_table_description",
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "Table"
        );
        return this.givenCreatedTableSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table representation, when we subscribe to its event, then the representation data are received")
    public void givenTableRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToTable();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(6);
            assertThat(table.getLines()).hasSize(4);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a refresh is triggered, then the table is refreshed")
    public void givenTableWhenRefreshTriggeredThenTableIsRefreshed() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(6);
            assertThat(table.getLines()).hasSize(4);

            tableId.set(table.getId());
        });

        Runnable refreshTable = () -> {
            Consumer<IEditingContextEventProcessor> editingContextEventProcessorConsumer = editingContextEventProcessor -> {
                editingContextEventProcessor.getRepresentationEventProcessors().stream()
                        .filter(representationEventProcessor -> representationEventProcessor.getRepresentation().getId().equals(tableId.get()))
                        .findFirst()
                        .ifPresentOrElse(representationEventProcessor -> {
                            IInput refreshInput = UUID::randomUUID;
                            representationEventProcessor.refresh(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, tableId.get(), refreshInput));
                        }, () -> fail("Missing representation event processor"));
            };


            this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                    .filter(editingContextEventProcessor -> editingContextEventProcessor.getEditingContextId().equals(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString()))
                    .findFirst()
                    .ifPresentOrElse(editingContextEventProcessorConsumer, () -> fail("Missing editing context event processor"));


            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(refreshTable)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table representation with a column header, when we subscribe to its event, then data received contain the header")
    public void givenTableRepresentationWithColumnHeaderWhenWeSubscribeToItsEventThenDataReceivedContainTheHeader() {
        var flux = this.givenSubscriptionToTable();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(6);
            assertThat(table.getColumns().get(0).getHeaderIndexLabel()).isEqualTo("A");
            assertThat(table.getColumns().get(1).getHeaderIndexLabel()).isEqualTo("B");
            assertThat(table.getColumns().get(2).getHeaderIndexLabel()).isEqualTo("C");
            assertThat(table.getColumns().get(3).getHeaderIndexLabel()).isEqualTo("D");
            assertThat(table.getColumns().get(4).getHeaderIndexLabel()).isEqualTo("E");
            assertThat(table.getColumns().get(5).getHeaderIndexLabel()).isEqualTo("F");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a global filter mutation is triggered, then a payload with the new global filter value is received")
    public void givenTableWhenGlobalFilterMutationTriggeredThenPayloadWithNewGlobalFilterValueReceived() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getGlobalFilter()).isEqualTo("");
            tableId.set(table.getId());
        });

        Runnable changeGlobalFilter = () -> {
            var changeGlobalFilterValueInput = new ChangeGlobalFilterValueInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), "New global filter value");
            var result = this.changeGlobalFilterMutationRunner.run(changeGlobalFilterValueInput);

            String typename = JsonPath.read(result.data(), "$.data.changeGlobalFilterValue.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedGlobalFilterThat(globalFilterValue -> {
            assertThat(globalFilterValue).isEqualTo("New global filter value");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(changeGlobalFilter)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a column filter mutation is triggered, then a payload with the new column filter value is received")
    public void givenTableWhenColumnFilterMutationTriggeredThenPayloadWithNewColumnFilterValueReceived() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();
        var columnId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumnFilters()).hasSize(0);

            tableId.set(table.getId());
            columnId.set(table.getColumns().get(0).getId().toString());
        });

        Runnable changeColumnFilter = () -> {
            var changeColumnFilterInput = new ChangeColumnFilterInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), List.of(new ColumnFilter(columnId.get(), "filter value")));
            var result = this.changeColumnFilterMutationRunner.run(changeColumnFilterInput);

            String typename = JsonPath.read(result.data(), "$.data.changeColumnFilter.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedColumnFilterThat(columnFilters -> {
            assertThat(columnFilters).isNotNull();
            assertThat(columnFilters).hasSize(1);
            assertThat(columnFilters.get(0).id()).isEqualTo(columnId.get());
            assertThat(columnFilters.get(0).value()).isEqualTo("filter value");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(changeColumnFilter)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table with filters, when the existing representation is opened, then persisted filters are preserved")
    public void givenTableWithFiltersWhenRepresentationIsOpenedThenPersistedFiltersArePreserved() {
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString());
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput).flux();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getGlobalFilter()).isEqualTo("PUB");
            assertThat(table.getColumnFilters()).hasSize(1);
            assertThat(table.getColumnFilters().get(0).value()).isEqualTo("LIC");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a sorting mutation is triggered, then a payload with the new sorting is received")
    public void givenTableWhenSortingMutationTriggeredThenPayloadWithNewSortingReceived() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();
        var columnId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumnSort()).hasSize(0);
            tableId.set(table.getId());
            columnId.set(table.getColumns().get(0).getId().toString());
        });

        Runnable changeColumnSort = () -> {
            var changeColumnSortInput = new ChangeColumnSortInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), List.of(new ColumnSort(columnId.get(), true)));
            var result = this.changeColumnSortMutationRunner.run(changeColumnSortInput);

            String typename = JsonPath.read(result.data(), "$.data.changeColumnSort.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedColumnSortThat(sort -> {
            assertThat(sort).isNotNull();
            assertThat(sort).hasSize(1);
            assertThat(sort.get(0).id()).isEqualTo(columnId.get());
            assertThat(sort.get(0).desc()).isEqualTo(true);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(changeColumnSort)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table persisted, when table configuration query is triggered, then configuration data are returned")
    public void givenTablePersistedWhenTableConfigurationQueryIsTriggeredThenConfigurationDataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "representationId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString(),
                "tableId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString()
        );
        var result = this.tableConfigurationQueryRunner.run(variables);


        String globalFilterResult = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.configuration.globalFilter");
        assertThat(globalFilterResult).isEqualTo("PUB");

        List<String> columnFiltersValues = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.configuration.columnFilters[*].value");
        assertThat(columnFiltersValues)
                .isNotEmpty()
                .hasSize(1)
                .anySatisfy(tableFilterId -> assertThat(tableFilterId).isEqualTo("LIC"));

        List<String> columnSortIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.configuration.columnSort[*].id");
        assertThat(columnSortIds)
                .isEmpty();

        Integer defaultPageSizeResult = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.configuration.defaultPageSize");
        assertThat(defaultPageSizeResult).isEqualTo(10);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when tool menu entries query is triggered, then all related tools are returned")
    public void givenTableWhenToolMenuEntriesQueryIsTriggeredThenAllRelatedToolsAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "representationId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString(),
                "tableId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString()
        );
        var result = this.toolMenuEntriesQueryRunner.run(variables);

        List<String> toolMenuEntriesId = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.toolMenuEntries[*].id");
        assertThat(toolMenuEntriesId).containsExactlyInAnyOrder("add-class-tool-entry");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a tool is triggered, then the tool is correctly executed")
    public void givenTableWhenAToolIsTriggeredThenTheToolIsCorrectlyExecuted() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(4);
            tableId.set(table.getId());
        });

        Runnable invokeToolMenuEntry = () -> {
            var invokeToolMenuEntryInput = new InvokeToolMenuEntryInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(),
                    tableId.get(),
                    "add-class-tool-entry");
            var result = this.invokeToolMenuEntryMutationRunner.run(invokeToolMenuEntryInput);

            String typename = JsonPath.read(result.data(), "$.data.invokeToolMenuEntry.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(5);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(invokeToolMenuEntry)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

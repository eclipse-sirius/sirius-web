/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableColumnFilterPayload;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.collaborative.tables.TableGlobalFilterValuePayload;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeColumnFilterInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeGlobalFilterValueInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeColumnFilterMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeGlobalFilterMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.TableEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTableSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
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

    private static final String MISSING_TABLE = "Missing table";

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
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private TableEventSubscriptionRunner tableEventSubscriptionRunner;


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
        return this.givenCreatedTableSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table representation, when we subscribe to its event, then the representation data are received")
    public void givenTableRepresentationWhenWeSubscribeToItsEventThenTheRepresentationDataAreReceived() {
        var flux = this.givenSubscriptionToTable();

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    assertThat(table.getLines()).hasSize(2);
                }, () -> fail(MISSING_TABLE));

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

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    assertThat(table.getLines()).hasSize(2);

                    tableId.set(table.getId());
                }, () -> fail("Missing table"));

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

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    assertThat(table.getColumns().get(0).getHeaderIndexLabel()).isEqualTo("A");
                    assertThat(table.getColumns().get(1).getHeaderIndexLabel()).isEqualTo("B");
                    assertThat(table.getColumns().get(2).getHeaderIndexLabel()).isEqualTo("C");
                    assertThat(table.getColumns().get(3).getHeaderIndexLabel()).isEqualTo("D");
                    assertThat(table.getColumns().get(4).getHeaderIndexLabel()).isEqualTo("E");
                    assertThat(table.getColumns().get(5).getHeaderIndexLabel()).isEqualTo("F");
                }, () -> fail(MISSING_TABLE));

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

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getGlobalFilter()).isEqualTo("");
                    tableId.set(table.getId());
                }, () -> fail(MISSING_TABLE));

        Runnable changeGlobalFilter = () -> {
            var changeGlobalFilterValueInput = new ChangeGlobalFilterValueInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), "New global filter value");
            var result = this.changeGlobalFilterMutationRunner.run(changeGlobalFilterValueInput);

            String typename = JsonPath.read(result, "$.data.changeGlobalFilterValue.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };


        Consumer<Object> updatedTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableGlobalFilterValuePayload.class::isInstance)
                .map(TableGlobalFilterValuePayload.class::cast)
                .map(TableGlobalFilterValuePayload::globalFilterValue)
                .ifPresentOrElse(globalFilterValue -> {
                    assertThat(globalFilterValue).isNotNull();
                    assertThat(globalFilterValue).isEqualTo("New global filter value");
                }, () -> fail("Missing global filter value"));

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

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumnFilters()).hasSize(0);
                    tableId.set(table.getId());
                    columnId.set(table.getColumns().get(0).getId().toString());
                }, () -> fail(MISSING_TABLE));

        Runnable changeColumnFilter = () -> {
            var changeColumnFilterInput = new ChangeColumnFilterInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), List.of(new ColumnFilter(columnId.get(), "filter value")));
            var result = this.changeColumnFilterMutationRunner.run(changeColumnFilterInput);

            String typename = JsonPath.read(result, "$.data.changeColumnFilter.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };


        Consumer<Object> updatedTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableColumnFilterPayload.class::isInstance)
                .map(TableColumnFilterPayload.class::cast)
                .map(TableColumnFilterPayload::columnFilters)
                .ifPresentOrElse(columnFilters -> {
                    assertThat(columnFilters).isNotNull();
                    assertThat(columnFilters).hasSize(1);
                    assertThat(columnFilters.get(0).id()).isEqualTo(columnId.get());
                    assertThat(columnFilters.get(0).value()).isEqualTo("filter value");
                }, () -> fail("Missing column filter value"));

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
        this.givenCommittedTransaction.commit();

        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString());
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();


        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getGlobalFilter()).isEqualTo("PUB");
                    assertThat(table.getColumnFilters()).hasSize(1);
                    assertThat(table.getColumnFilters().get(0).value()).isEqualTo("LIC");
                }, () -> fail(MISSING_TABLE));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

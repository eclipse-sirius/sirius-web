/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeTableColumnVisibilityInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ColumnVisibility;
import org.eclipse.sirius.components.collaborative.tables.dto.ReorderTableColumnsInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ResizeTableColumnInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.tests.graphql.ChangeTableColumnVisibilityMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ReorderTableColumnsMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ResizeTableColumnMutationRunner;
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
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the table's column with a papaya model.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class PapayaTableColumnControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String MISSING_TABLE = "Missing table";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTableSubscription givenCreatedTableSubscription;

    @Autowired
    private ResizeTableColumnMutationRunner resizeTableColumnMutationRunner;

    @Autowired
    private ReorderTableColumnsMutationRunner reorderTableColumnsMutationRunner;

    @Autowired
    private ChangeTableColumnVisibilityMutationRunner changeTableColumnVisibilityMutationRunner;


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
    @DisplayName("Given a table, when a column resize mutation is triggered, then the representation is refreshed with the new column size")
    public void givenTableWhenColumnResizeMutationTriggeredThenTheRepresentationIsRefreshedWithNewColumnSize() {
        var flux = this.givenSubscriptionToTable();

        var columnRef = new AtomicReference<Column>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    columnRef.set(table.getColumns().get(1));
                    assertThat(table.getColumns().get(1).getWidth()).isEqualTo(180);
                    tableId.set(table.getId());
                }, () -> fail(MISSING_TABLE));

        Runnable resizeColumn = () -> {
            var columnToChange = columnRef.get();
            var resizeTableColumnInput = new ResizeTableColumnInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), columnToChange.getId().toString(), 50);
            var result = this.resizeTableColumnMutationRunner.run(resizeTableColumnInput);

            String typename = JsonPath.read(result, "$.data.resizeTableColumn.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };


        Consumer<Object> updatedTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    assertThat(table.getColumns().get(1).getWidth()).isEqualTo(50);
                }, () -> fail(MISSING_TABLE));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(resizeColumn)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when column visibility changes mutation is triggered, then the representation is refreshed with the new column visibilities")
    public void givenTableWhenColumnVisibilityChangesMutationTriggeredThenTheRepresentationIsRefreshedWithNewColumnVisibilities() {
        var flux = this.givenSubscriptionToTable();

        var columnNameRef = new AtomicReference<Column>();
        var columnDescRef = new AtomicReference<Column>();
        var columnAnnotationRef = new AtomicReference<Column>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    columnNameRef.set(table.getColumns().get(0));
                    columnDescRef.set(table.getColumns().get(1));
                    columnAnnotationRef.set(table.getColumns().get(2));
                    assertThat(table.getColumns().get(0).isHidden()).isFalse();
                    assertThat(table.getColumns().get(1).isHidden()).isFalse();
                    assertThat(table.getColumns().get(2).isHidden()).isFalse();
                    assertThat(table.getColumns().get(3).isHidden()).isFalse();
                    tableId.set(table.getId());
                }, () -> fail(MISSING_TABLE));

        Runnable changeColumnVisibility = () -> {
            var changeTableColumnVisibilityInput = new ChangeTableColumnVisibilityInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(),
                    List.of(
                            new ColumnVisibility(columnNameRef.get().getId().toString(), false),
                            new ColumnVisibility(columnDescRef.get().getId().toString(), true),
                            new ColumnVisibility(columnAnnotationRef.get().getId().toString(), false)));
            var result = this.changeTableColumnVisibilityMutationRunner.run(changeTableColumnVisibilityInput);

            String typename = JsonPath.read(result, "$.data.changeTableColumnVisibility.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };


        Consumer<Object> updatedTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    assertThat(table.getColumns().get(0).isHidden()).isTrue();
                    assertThat(table.getColumns().get(1).isHidden()).isFalse();
                    assertThat(table.getColumns().get(2).isHidden()).isTrue();
                    assertThat(table.getColumns().get(3).isHidden()).isFalse();
                }, () -> fail(MISSING_TABLE));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(changeColumnVisibility)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when columns reorder change mutation is triggered, then the representation is refreshed with the new order of columns")
    public void givenTableWhenColumnsReorderChangeMutationTriggeredThenTheRepresentationIsRefreshedWithNewOrderOfColumns() {
        var flux = this.givenSubscriptionToTable();

        var columnIdsRef = new AtomicReference<List<String>>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    table.getColumns().forEach(col -> assertThat(col.getIndex()).isEqualTo(0));
                    assertThat(table.getColumns().get(0).getHeaderLabel()).isEqualTo("Icon");
                    assertThat(table.getColumns().get(1).getHeaderLabel()).isEqualTo("Name");
                    columnIdsRef.set(table.getColumns().stream().map(Column::getId).map(UUID::toString).toList());
                    tableId.set(table.getId());
                }, () -> fail(MISSING_TABLE));

        Runnable changeColumnsOrder = () -> {
            List<String> ids = new ArrayList<>(columnIdsRef.get());
            Collections.swap(ids, 0, 1); // swap first two columns, so Name is before Icon
            var changeTableColumnsOrderInput = new ReorderTableColumnsInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(),
                    tableId.get(),
                    ids
                    );
            var result = this.reorderTableColumnsMutationRunner.run(changeTableColumnsOrderInput);

            String typename = JsonPath.read(result, "$.data.reorderTableColumns.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(6);
                    for (int i = 0; i < 5; i++) {
                        assertThat(table.getColumns().get(i).getIndex()).isEqualTo(i);
                    }
                    assertThat(table.getColumns().get(0).getHeaderLabel()).isEqualTo("Name");
                    assertThat(table.getColumns().get(1).getHeaderLabel()).isEqualTo("Icon");
                }, () -> fail(MISSING_TABLE));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(changeColumnsOrder)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.InvokeRowContextMenuEntryInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.TextareaCell;
import org.eclipse.sirius.components.tables.TextfieldCell;
import org.eclipse.sirius.components.tables.tests.graphql.InvokeRowContextMenuEntryMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.RowContextMenuQueryRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.tables.ViewTableDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
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
 * Integration tests of the view table description.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class PapayaViewTableControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ViewTableDescriptionProvider viewTableDescriptionProvider;

    @Autowired
    private RowContextMenuQueryRunner rowContextMenuQueryRunner;

    @Autowired
    private InvokeRowContextMenuEntryMutationRunner invokeRowContextMenuEntryMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToViewTableRepresentation() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                this.viewTableDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "ViewTableDescription"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a simple view table description, when a subscription is created, then the table is render")
    public void givenSimpleViewTableDescriptionWhenSubscriptionIsCreatedThenTableIsRender() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(2);
            assertThat(table.getColumns().get(0).getHeaderLabel()).isEqualTo("Name");
            assertThat(table.getColumns().get(0).getHeaderIndexLabel()).isEqualTo("0");
            assertThat(table.getColumns().get(1).getHeaderLabel()).isEqualTo("Description");
            assertThat(table.getColumns().get(1).getHeaderIndexLabel()).isEqualTo("1");
            assertThat(table.getLines()).hasSize(2);
            assertThat(table.getLines().get(0).getHeaderIndexLabel()).isEqualTo("0");
            assertThat(table.getLines().get(0).getCells().get(0)).isInstanceOf(TextfieldCell.class);
            assertThat(table.getLines().get(0).getCells().get(1)).isInstanceOf(TextareaCell.class);
            assertThat(((TextfieldCell) table.getLines().get(0).getCells().get(0)).getValue()).isEqualTo("Success");
            assertThat(table.getLines().get(1).getHeaderIndexLabel()).isEqualTo("1");
            assertThat(table.getLines().get(1).getCells().get(0)).isInstanceOf(TextfieldCell.class);
            assertThat(((TextfieldCell) table.getLines().get(1).getCells().get(0)).getValue()).isEqualTo("Failure");
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a simple view table description, when a row context menu entry is retrieved and invoked, then the row context menu entry is correctly executed")
    public void givenSimpleViewTableDescriptionWhenRowContextMenuEntryIsInvokedThenRowContextMenuEntryIsCorrectlyExecuted() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        var tableId = new AtomicReference<String>();
        var rowId = new AtomicReference<UUID>();
        var rowLabel = new AtomicReference<String>();
        Consumer<Object> tableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(2);
            tableId.set(table.getId());
            rowId.set(table.getLines().get(0).getId());
            rowLabel.set(table.getLines().get(0).getHeaderLabel());
        });

        var actionId = new AtomicReference<String>();
        Runnable getContextMenuEntriesTask = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    "representationId", tableId.get(),
                    "tableId", tableId.get(),
                    "rowId", rowId.get().toString());

            var result = this.rowContextMenuQueryRunner.run(variables);
            List<String> actionLabels = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].label");
            assertThat(actionLabels).isNotEmpty().hasSize(1);
            assertThat(actionLabels.get(0)).isEqualTo("Change name");

            List<String> actionIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].id");
            actionId.set(actionIds.get(0));
        };

        Runnable invokeChangeNameAction = () -> {
            var invokeRowContextMenuEntryInput = new InvokeRowContextMenuEntryInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    tableId.get(),
                    tableId.get(),
                    rowId.get(),
                    actionId.get()
            );
            var result = this.invokeRowContextMenuEntryMutationRunner.run(invokeRowContextMenuEntryInput);

            String typename = JsonPath.read(result, "$.data.invokeRowContextMenuEntry.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines().get(0).getHeaderLabel()).isEqualTo(rowLabel + "Updated");
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .then(getContextMenuEntriesTask)
                .then(invokeChangeNameAction)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<Object> getTableSubscriptionConsumer(Consumer<Table> tableConsumer) {
        return payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(tableConsumer, () -> fail("Missing table"));
    }
}

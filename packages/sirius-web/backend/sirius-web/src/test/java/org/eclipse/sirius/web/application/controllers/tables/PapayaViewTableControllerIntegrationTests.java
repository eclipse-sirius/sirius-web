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
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedTableThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.collaborative.tables.dto.InvokeRowContextMenuEntryInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.TextareaCell;
import org.eclipse.sirius.components.tables.TextfieldCell;
import org.eclipse.sirius.components.tables.tests.graphql.InvokeRowContextMenuEntryMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.RowContextMenuQueryRunner;
import org.eclipse.sirius.components.tables.tests.graphql.TableEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.table.customcells.CheckboxCell;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.tables.MinimalViewTableDescriptionProvider;
import org.eclipse.sirius.web.services.tables.ViewTableDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
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
 * Integration tests of the view table description.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaViewTableControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ViewTableDescriptionProvider viewTableDescriptionProvider;

    @Autowired
    private MinimalViewTableDescriptionProvider minimalViewTableDescriptionProvider;

    @Autowired
    private RowContextMenuQueryRunner rowContextMenuQueryRunner;

    @Autowired
    private InvokeRowContextMenuEntryMutationRunner invokeRowContextMenuEntryMutationRunner;

    @Autowired
    private TableEventSubscriptionRunner tableEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToViewTableRepresentation() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.viewTableDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "ViewTableDescription"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    private Flux<Object> givenSubscriptionToMinimalViewTableRepresentation() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.minimalViewTableDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "MinimalViewTableDescription"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a simple view table description, when a subscription is created, then the table is render")
    public void givenSimpleViewTableDescriptionWhenSubscriptionIsCreatedThenTableIsRender() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(3);
            assertThat(table.getColumns().get(0).getHeaderLabel()).isEqualTo("Name");
            assertThat(table.getColumns().get(0).getHeaderIndexLabel()).isEqualTo("0");
            assertThat(table.getColumns().get(1).getHeaderLabel()).isEqualTo("Description");
            assertThat(table.getColumns().get(1).getHeaderIndexLabel()).isEqualTo("1");
            assertThat(table.getColumns().get(2).getHeaderLabel()).isEqualTo("Abstract");
            assertThat(table.getColumns().get(2).getHeaderIndexLabel()).isEqualTo("2");

            assertThat(table.getLines()).hasSize(6);
            assertThat(table.getLines().get(0).getHeaderIndexLabel()).isEqualTo("0");
            assertThat(table.getLines().get(0).getCells().get(0)).isInstanceOf(TextfieldCell.class);
            assertThat(table.getLines().get(0).getCells().get(1)).isInstanceOf(TextareaCell.class);
            assertThat(table.getLines().get(0).getCells().get(2)).isInstanceOf(CheckboxCell.class);
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
        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(6);
            tableId.set(table.getId());
            rowId.set(table.getLines().get(0).getId());
            rowLabel.set(table.getLines().get(0).getHeaderLabel());
        });

        var actionId = new AtomicReference<String>();
        Runnable getContextMenuEntriesTask = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", tableId.get(),
                    "tableId", tableId.get(),
                    "rowId", rowId.get().toString());

            var result = this.rowContextMenuQueryRunner.run(variables);
            List<String> actionLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].label");
            assertThat(actionLabels).isNotEmpty().hasSize(1);
            assertThat(actionLabels.get(0)).isEqualTo("Change name");

            List<String> actionIds = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].id");
            actionId.set(actionIds.get(0));
        };

        Runnable invokeChangeNameAction = () -> {
            var invokeRowContextMenuEntryInput = new InvokeRowContextMenuEntryInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(),
                    tableId.get(),
                    rowId.get(),
                    actionId.get()
            );
            var result = this.invokeRowContextMenuEntryMutationRunner.run(invokeRowContextMenuEntryInput);

            String typename = JsonPath.read(result.data(), "$.data.invokeRowContextMenuEntry.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
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

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description with a selected target object expression, when a subscription is created, then the cell target object data are correct")
    public void givenViewTableWithSelectedTargetObjectExpressionWhenSubscriptionIsCreatedThenCellTargetObjectDataAreCorrectlyExecuted() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(6);
            assertThat(table.getLines().get(0).getCells().get(0).getTargetObjectId()).isEqualTo(PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString());
            assertThat(table.getLines().get(0).getCells().get(0).getTargetObjectKind()).isEqualTo("siriusComponents://semantic?domain=papaya&entity=Package");
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description with sub elements, when a subscription is created, then the depth levels are correct")
    public void givenViewTableWithSubElementsWhenSubscriptionIsCreatedThenTheDepthLevelsAreCorrect() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.isEnableSubRows()).isTrue();
            assertThat(table.getLines()).hasSize(6);
            assertThat(table.getLines().get(0).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(1).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(2).getDepthLevel()).isEqualTo(1);
            assertThat(table.getLines().get(3).getDepthLevel()).isEqualTo(2);
            assertThat(table.getLines().get(4).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(5).getDepthLevel()).isEqualTo(0);
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description with sub elements, when a subscription is created, then hasChildren is correctly set")
    public void givenViewTableWithSubElementsWhenSubscriptionIsCreatedThenHasChildrenIsCorrectlySet() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.isEnableSubRows()).isTrue();
            assertThat(table.getLines()).hasSize(6);
            assertThat(table.getLines().get(0).isHasChildren()).isTrue();
            assertThat(table.getLines().get(1).isHasChildren()).isFalse();
            assertThat(table.getLines().get(2).isHasChildren()).isFalse();
            assertThat(table.getLines().get(3).isHasChildren()).isTrue();
            assertThat(table.getLines().get(4).isHasChildren()).isTrue();
            assertThat(table.getLines().get(5).isHasChildren()).isTrue();
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description with Failure element, when hide-failure row filter is activated, then the failure row is not in table")
    public void givenViewTableWithFailureElementWhenHideFailureRowFilterIsActivatedThenTheFailureRowNotInTable() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        AtomicReference<String> tableId = new AtomicReference<>();
        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(6);
            tableId.set(table.getId());
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        var representationId = this.representationIdBuilder.buildTableRepresentationId(tableId.get(), null, "NEXT", 1, List.of(), List.of(ViewTableDescriptionProvider.HIDE_FAILURE_ROW_FILTER_ID),
                List.of());
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var rowFilterFlux = this.tableEventSubscriptionRunner.run(tableEventInput).flux();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(1);
            assertThat(table.getLines().get(0).getHeaderLabel()).isEqualTo("Success");
        });

        StepVerifier.create(rowFilterFlux)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a simple view table description, when a subscription is created with a sort, then the table is render with rows in good order")
    public void givenSimpleViewTableDescriptionWhenSubscriptionIsCreatedWithASortThenTableIsRenderWithRowsInGoodOrder() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        var tableId = new AtomicReference<String>();
        var columnNameId = new AtomicReference<String>();
        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(3);
            assertThat(table.getLines()).hasSize(6);

            assertThat(((TextfieldCell) table.getLines().get(0).getCells().get(0)).getValue()).isEqualTo("Success");
            assertThat(((TextfieldCell) table.getLines().get(1).getCells().get(0)).getValue()).isEqualTo("Failure");
            assertThat(((TextfieldCell) table.getLines().get(2).getCells().get(0)).getValue()).isEqualTo("fooOperation");
            assertThat(((TextfieldCell) table.getLines().get(3).getCells().get(0)).getValue()).isEqualTo("fooParameter");
            assertThat(((TextfieldCell) table.getLines().get(4).getCells().get(0)).getValue()).isEqualTo("AbstractTest");
            assertThat(((TextfieldCell) table.getLines().get(5).getCells().get(0)).getValue()).isEqualTo("IntegrationTest");
            tableId.set(table.getId());
            columnNameId.set(table.getColumns().get(0).getId().toString());
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        String representationId = this.representationIdBuilder.buildTableRepresentationId(tableId.get(), null, "NEXT", 10, List.of(), List.of(), List.of(new ColumnSort(columnNameId.get(), true)));
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var expandedFlux = this.tableEventSubscriptionRunner.run(tableEventInput).flux();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Consumer<Object> sortedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(((TextfieldCell) table.getLines().get(0).getCells().get(0)).getValue()).isEqualTo("Success");
            assertThat(((TextfieldCell) table.getLines().get(1).getCells().get(0)).getValue()).isEqualTo("IntegrationTest");
            assertThat(((TextfieldCell) table.getLines().get(2).getCells().get(0)).getValue()).isEqualTo("fooParameter");
            assertThat(((TextfieldCell) table.getLines().get(3).getCells().get(0)).getValue()).isEqualTo("fooOperation");
            assertThat(((TextfieldCell) table.getLines().get(4).getCells().get(0)).getValue()).isEqualTo("Failure");
            assertThat(((TextfieldCell) table.getLines().get(5).getCells().get(0)).getValue()).isEqualTo("AbstractTest");
        });

        StepVerifier.create(expandedFlux)
                .consumeNextWith(sortedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description with page size options, when a subscription is created, then the page size options are correct")
    public void givenViewTableWithPageSizeOptionsWhenSubscriptionIsCreatedThenThePageSizeOptionsAreCorrect() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getPageSizeOptions()).hasSize(3);
            assertThat(table.getPageSizeOptions().get(0)).isEqualTo(5);
            assertThat(table.getPageSizeOptions().get(1)).isEqualTo(20);
            assertThat(table.getPageSizeOptions().get(2)).isEqualTo(50);
            assertThat(table.getDefaultPageSize()).isEqualTo(50);
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description without page size options, when a subscription is created, then the page size options are set to fallback values")
    public void givenViewTableWithoutPageSizeOptionsWhenSubscriptionIsCreatedThenThePageSizeOptionsAreSetToFallbackValues() {
        var flux = this.givenSubscriptionToMinimalViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getPageSizeOptions()).hasSize(4);
            assertThat(table.getPageSizeOptions().get(0)).isEqualTo(5);
            assertThat(table.getPageSizeOptions().get(1)).isEqualTo(10);
            assertThat(table.getPageSizeOptions().get(2)).isEqualTo(20);
            assertThat(table.getPageSizeOptions().get(3)).isEqualTo(50);
            assertThat(table.getDefaultPageSize()).isEqualTo(5);
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view table description, when a subscription is created, then the tooltip values are returned")
    public void givenViewTableWhenSubscriptionIsCreatedThenTheTooltipValuesAreReturned() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        Consumer<Object> tableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(6);
            assertThat((table.getLines().get(0).getCells().get(0)).getTooltipValue()).isEqualTo("SuccessTooltip");
            assertThat(table.getLines().get(0).getCells().get(1).getTooltipValue()).isEqualTo("");
        });

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

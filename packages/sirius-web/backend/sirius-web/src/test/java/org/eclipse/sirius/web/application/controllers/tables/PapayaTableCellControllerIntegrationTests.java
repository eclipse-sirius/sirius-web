/*******************************************************************************
 * Copyright (c) 2025 CEA LIST and others.
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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.tables.dto.EditTextareaCellInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.TextareaCell;
import org.eclipse.sirius.components.tables.tests.graphql.EditTextareaCellMutationRunner;
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
 * Integration tests of the table's cell with a papaya model.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaTableCellControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String MISSING_TABLE = "Missing table";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTableSubscription givenCreatedTableSubscription;

    @Autowired
    private EditTextareaCellMutationRunner editTextareaCellMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTable() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                "papaya_package_table_description",
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "Table"
        );
        return this.givenCreatedTableSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when an edit textarea mutation is triggered, then the representation is refreshed with the new cell content")
    public void givenTableWhenEditTextareaMutationTriggeredThenTheRepresentationIsRefreshedWithNewCellContent() {
        var flux = this.givenSubscriptionToTable();

        var cellRef = new AtomicReference<ICell>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(2);
            assertThat(table.getLines().get(0).getCells()).hasSize(5);
            assertThat(table.getLines().get(0).getCells().get(1)).isInstanceOf(TextareaCell.class);
            assertThat(table.getLines().get(0).getCells().get(1)).isInstanceOf(TextareaCell.class);
            assertThat(((TextareaCell) table.getLines().get(0).getCells().get(1)).getValue()).isEqualTo("");
            cellRef.set(table.getLines().get(0).getCells().get(1));
            tableId.set(table.getId());
        });

        Runnable editTextarea = () -> {
            var editTextareaCellInput = new EditTextareaCellInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                    tableId.get(),
                    tableId.get(),
                    cellRef.get().getId(),
                    "new description");
            var result = this.editTextareaCellMutationRunner.run(editTextareaCellInput);

            String typename = JsonPath.read(result, "$.data.editTextareaCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };


        Consumer<Object> updatedTableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(2);
            assertThat(table.getLines().get(0).getCells()).hasSize(5);
            assertThat(table.getLines().get(0).getCells().get(1)).isInstanceOf(TextareaCell.class);
            assertThat(((TextareaCell) table.getLines().get(0).getCells().get(1)).getValue()).isEqualTo("new description");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(editTextarea)
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
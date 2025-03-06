/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import org.eclipse.sirius.components.collaborative.tables.dto.EditTextfieldCellInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.TextfieldCell;
import org.eclipse.sirius.components.tables.tests.graphql.EditTextfieldCellMutationRunner;
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
 * Integration tests of the view table cell description.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaViewCellControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private ViewTableDescriptionProvider viewTableDescriptionProvider;

    @Autowired
    private EditTextfieldCellMutationRunner editTextfieldCellMutationRunner;

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
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when an cell edit mutation is triggered, then the representation is refreshed with the new cell value")
    public void givenTableWhenCellEditMutationTriggeredThenTheRepresentationIsRefreshedWithNewCellValue() {
        var flux = this.givenSubscriptionToViewTableRepresentation();

        var cellId = new AtomicReference<UUID>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            tableId.set(table.getId());
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(5);
            assertThat(table.getLines().get(0).getCells()).hasSize(2);
            assertThat(table.getLines().get(0).getCells().get(0)).isInstanceOf(TextfieldCell.class);
            assertThat(((TextfieldCell) table.getLines().get(0).getCells().get(0)).getValue()).isEqualTo("Success");
            cellId.set(table.getLines().get(0).getCells().get(0).getId());
        });

        Runnable editName = () -> {
            var input = new EditTextfieldCellInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), tableId.get(), tableId.get(), cellId.get(), "newName");
            var result = this.editTextfieldCellMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editTextfieldCell.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = this.getTableSubscriptionConsumer(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(5);
            assertThat(((TextfieldCell) table.getLines().get(0).getCells().get(0)).getValue()).isEqualTo("newName");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(editName)
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

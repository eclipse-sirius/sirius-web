/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the table icon URL controller.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TableIconURLControllerTests extends AbstractIntegrationTests {

    private static final String TABLE_EVENT_SUBSCRIPTION = """
           subscription tableEvent($input: TableEventInput!) {
             tableEvent(input: $input) {
               __typename
               ... on TableRefreshedEventPayload {
                table {
                  id
                  columns {
                    id
                    headerIconURLs
                  }
                  lines {
                    id
                    headerIconURLs
                  }
                }
              }
             }
           }
           """;

    @Autowired
    private IGivenCreatedRepresentation givenCreatedRepresentation;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a papaya package, when we subscribe to a table with icon define, then the URL of its icons are valid")
    @Sql(scripts = {"/scripts/papaya.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenPapayaPackageWhenWeSubscribeToTableWithIconThenURLOfItsIconsAreValid() {
        this.givenCommittedTransaction.commit();
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_PROJECT.toString(),
                "papaya_package_table_description",
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "Table"
        );
        String representationId = this.givenCreatedRepresentation.createRepresentation(input);
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), representationId);
        var flux = this.graphQLRequestor.subscribeToSpecification(TABLE_EVENT_SUBSCRIPTION, tableEventInput);

        Consumer<String> tableContentConsumer = payload -> Optional.of(payload)
                .ifPresentOrElse(body -> {
                    String typename = JsonPath.read(body, "$.data.tableEvent.__typename");
                    assertThat(typename).isEqualTo(TableRefreshedEventPayload.class.getSimpleName());

                    List<List<String>> columnIconURLs = JsonPath.read(body, "$.data.tableEvent.table.columns[*].headerIconURLs");
                    assertThat(columnIconURLs)
                            .isNotEmpty()
                            .allSatisfy(iconURLs -> {
                                assertThat(iconURLs)
                                        .isNotEmpty()
                                        .hasSize(1)
                                        .allSatisfy(iconURL -> assertThat(iconURL).startsWith(URLConstants.IMAGE_BASE_PATH));
                            });

                    List<List<String>> rowIconURLs = JsonPath.read(body, "$.data.tableEvent.table.lines[*].headerIconURLs");
                    assertThat(rowIconURLs)
                            .isNotEmpty()
                            .allSatisfy(iconURLs -> {
                                assertThat(iconURLs)
                                        .isNotEmpty()
                                        .hasSize(2)
                                        .allSatisfy(iconURL -> assertThat(iconURL).startsWith(URLConstants.IMAGE_BASE_PATH));
                            });
                }, () -> fail("Missing table"));

        StepVerifier.create(flux)
                .consumeNextWith(tableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
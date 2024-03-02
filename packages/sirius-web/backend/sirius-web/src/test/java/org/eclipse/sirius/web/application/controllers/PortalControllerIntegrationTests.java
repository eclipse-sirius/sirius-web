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
package org.eclipse.sirius.web.application.controllers;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of the portal controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortalControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_PORTAL_EVENT_SUBSCRIPTION = """
            subscription portalEvent($input: PortalEventInput!) {
              portalEvent(input: $input) {
                __typename
              }
            }
            """;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Test
    @DisplayName("Given a portal, when we subscribe to portal events, then the current state of the portal is sent")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenPortalWhenWeSubscribeToPortalEventsThenCurrentStateOfThePortalIsSent() {
        var input = new PortalEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());
        var flux = this.graphQLRequestor.subscribe(GET_PORTAL_EVENT_SUBSCRIPTION, input);

        Predicate<Object> portalRefreshedEventPayloadMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(portalRefreshedEventPayloadMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

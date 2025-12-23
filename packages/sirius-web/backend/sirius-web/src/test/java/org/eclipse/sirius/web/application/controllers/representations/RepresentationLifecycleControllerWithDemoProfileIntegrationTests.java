/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.representations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.graphql.tests.CreateRepresentationMutationRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.services.TestRepresentationDescription;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the lifecycle of representations with demo profile activated.
 *
 * @author gcoutable
 */
@Transactional
@ActiveProfiles("demo")
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationLifecycleControllerWithDemoProfileIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private CreateRepresentationMutationRunner createRepresentationMutationRunner;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private IMessageService messageService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context with demo profile, when a mutation to create a representation is executed, then it returns an error payload")
    public void givenEditingContextWithDemoProfileWhenMutationToCreateRepresentationIsExecutedThenItReturnsErrorPayload() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                new TestRepresentationDescription().getId(),
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                "Test representation"
        );
        var result = this.createRepresentationMutationRunner.run(input);

        String typename = JsonPath.read(result.data(), "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        var errorMessage = JsonPath.read(result.data(), "$.data.createRepresentation.message");
        assertThat(errorMessage).isEqualTo(this.messageService.unauthorized());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a library with demo profile, when we subscribe to the explorer representation, then the flux streams an error payload")
    public void givenLibraryWithDemoProfileWhenSubscribeToExplorerRepresentationThenFluxStreamsAnErrorPayload() {
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_LIBRARY_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input).flux();

        Consumer<Object> initialExplorerConsumer = object -> Optional.of(object)
                .filter(ErrorPayload.class::isInstance)
                .map(ErrorPayload.class::cast)
                .ifPresentOrElse(
                        errorPayload -> assertThat(errorPayload.message()).isEqualTo(this.messageService.unauthorized()),
                        () -> fail("It should not be authorized to subscribe")
                );

        StepVerifier.create(flux)
                .consumeNextWith(initialExplorerConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

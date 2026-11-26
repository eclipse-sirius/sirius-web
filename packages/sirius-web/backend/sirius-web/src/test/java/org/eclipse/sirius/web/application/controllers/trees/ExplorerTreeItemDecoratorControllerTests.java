/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of tree item decorators in the default Explorer.
 *
 * @author pcdavid
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
@Import(ExplorerTreeItemDecoratorControllerTestsConfiguration.class)
public class ExplorerTreeItemDecoratorControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given two decorator delegates, when the domain document is expanded, then its domain object has decorators in contribution order")
    public void givenTwoDecoratorDelegatesWhenDomainDocumentIsExpandedThenItsDomainObjectHasDecoratorsInContributionOrder() {
        StepVerifier.create(this.subscribe(List.of()))
                .consumeNextWith(payload -> {
                    var document = this.findDomainDocument(payload);
                    assertThat(document.read("$.hasChildren", Boolean.class)).isTrue();
                    assertThat(document.read("$.children", List.class)).isEmpty();
                })
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        StepVerifier.create(this.subscribe(List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString())))
                .consumeNextWith(payload -> {
                    var document = this.findDomainDocument(payload);
                    assertThat(document.read("$.decorators", List.class)).isEmpty();

                    List<Map<String, Object>> decorators = document.read("$.children[?(@.id == '%s')].decorators[*]".formatted(StudioIdentifiers.DOMAIN_OBJECT));
                    assertThat(decorators)
                            .extracting(decorator -> decorator.get("iconURL"), decorator -> decorator.get("label"))
                            .containsExactly(
                                    tuple(URLConstants.IMAGE_BASE_PATH + "/decorators/status.svg", "Reviewed"),
                                    tuple(URLConstants.IMAGE_BASE_PATH + "/decorators/synchronization.svg", null),
                                    tuple(URLConstants.IMAGE_BASE_PATH + "/decorators/team.svg", "Shared"));
                })
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Flux<Object> subscribe(List<String> expandedIds) {
        String representationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, expandedIds, List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, representationId);
        return this.explorerEventSubscriptionRunner.runToSpecification(input).flux();
    }

    private DocumentContext findDomainDocument(Object payload) {
        String documentPath = "$.data.explorerEvent.tree.children[?(@.id == '%s')]".formatted(StudioIdentifiers.DOMAIN_DOCUMENT);
        List<Map<String, Object>> documents = JsonPath.read((String) payload, documentPath);
        assertThat(documents).hasSize(1);
        return JsonPath.parse(documents.getFirst());
    }
}

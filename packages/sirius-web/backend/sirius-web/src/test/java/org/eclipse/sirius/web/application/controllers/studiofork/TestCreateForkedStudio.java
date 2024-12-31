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

package org.eclipse.sirius.web.application.controllers.studiofork;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.controllers.studiofork.graphql.CreateForkedStudioMutationRuner;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.table.dto.CreateForkedStudioInput;
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
 * Integration tests of create forked studio mutation.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class TestCreateForkedStudio extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTableSubscription givenCreatedTableSubscription;

    @Autowired
    private CreateForkedStudioMutationRuner createForkedStudioMutationRuner;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @Autowired
    private ISemanticDataRepository semanticDataRepository;

    @Autowired
    private IURLParser urlParser;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTable() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.INSTANCE_PROJECT,
                StudioIdentifiers.TABLE_DESCRIPTION_ID,
                StudioIdentifiers.ROOT_OBJECT.toString(),
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
                    assertThat(table.getColumns()).hasSize(1);
                    assertThat(table.getLines()).hasSize(2);
                }, () -> fail("MISSING_TABLE"));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table representation, when we for the studio, then the representation is updated")
    public void givenTableRepresentationWhenWeForkTheStudioThenTheRepresentationDataAreUpdated() {
        var flux = this.givenSubscriptionToTable();

        var representationId = new AtomicReference<String>();
        var forkStudioProjectId = new AtomicReference<String>();

        Runnable forkStudio = () -> {
            var input = new CreateForkedStudioInput(UUID.randomUUID(), StudioIdentifiers.INSTANCE_PROJECT, representationId.get(), "");
            var result = this.createForkedStudioMutationRuner.run(input);

            String typename = JsonPath.read(result, "$.data.createForkedStudio.__typename");
            assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());
            String projectId = JsonPath.read(result, "$.data.createForkedStudio.project.id");
            forkStudioProjectId.set(projectId);
        };

        Runnable doesProjectContainsOldDocument = () -> {
            //Check if the new project exist with an updated new name
            var project = this.projectSearchService.findById(forkStudioProjectId.get());
            assertThat(project).isPresent();
            assertThat(project.get().getName()).isEqualTo("Forked Table");

            //Check if the representation have been updated to use the new view
            var representationMetadata = this.representationMetadataSearchService.findMetadataById(UUID.fromString(representationId.get()));
            assertThat(representationMetadata).isPresent();
            var newRepresentationId = representationMetadata.get().getDescriptionId();
            assertThat(newRepresentationId).isNotEqualTo(StudioIdentifiers.TABLE_DESCRIPTION_ID);
            var sourceId = this.getSourceId(newRepresentationId);
            var sourceElementId = this.getSourceElementId(newRepresentationId);
            assertThat(sourceId).isPresent();
            assertThat(sourceElementId).isPresent();


            //Check that the content of the project contains a copy of the view
            var semanticData = this.semanticDataRepository.findByProjectId(forkStudioProjectId.get());
            assertThat(semanticData).isPresent();
            var documents = semanticData.get().getDocuments();
            var forkedDocument = documents.stream().filter(document -> document.getId().equals(UUID.fromString(sourceId.get()))).findFirst();
            assertThat(forkedDocument).isPresent();
            assertThat(forkedDocument.get().getContent()).contains(sourceElementId.get());
        };

        Consumer<Object> initialTableContentConsumer = payload -> Optional.of(payload)
                .filter(TableRefreshedEventPayload.class::isInstance)
                .map(TableRefreshedEventPayload.class::cast)
                .map(TableRefreshedEventPayload::table)
                .ifPresentOrElse(table -> {
                    representationId.set(table.getId());
                    assertThat(table).isNotNull();
                    assertThat(table.getColumns()).hasSize(1);
                    assertThat(table.getLines()).hasSize(2);
                }, () -> fail("MISSING_TABLE"));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(forkStudio)
                .then(doesProjectContainsOldDocument)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }
}

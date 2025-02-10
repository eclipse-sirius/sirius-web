/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.views;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.execution.DataFetcherResult;
import reactor.test.StepVerifier;

/**
 * Integration tests of Diagram View controllers.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagramViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private CreateChildMutationRunner createChildMutationRunner;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view, when a diagram description is created, then it is created properly")
    public void givenAViewWhenADiagramDescriptionIsCreatedThenItIsCreatedProperly() {
        this.givenCommittedTransaction.commit();

        var inputPalette = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                "c4591605-8ea8-4e92-bb17-05c4538248f8",
                "descriptions-DiagramDescription"
        );
        var result = this.createChildMutationRunner.run(inputPalette);

        String typename = JsonPath.read(result, "$.data.createChild.__typename");
        assertThat(typename).isEqualTo(CreateChildSuccessPayload.class.getSimpleName());

        var diagramDescriptionId = new AtomicReference<String>();

        String objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();
        diagramDescriptionId.set(objectId);

        String objectLabel = JsonPath.read(result, "$.data.createChild.object.label");
        assertThat(objectLabel).isNotBlank();

        String objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=diagram&entity=DiagramDescription");

        var nodeDescription = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                diagramDescriptionId.get(),
                "nodeDescriptions-NodeDescription"
        );
        result = this.createChildMutationRunner.run(nodeDescription);

        objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();

        objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=diagram&entity=NodeDescription");

        var conditionalStyle = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                objectId,
                "conditionalStyles-ConditionalNodeStyle"
        );
        result = this.createChildMutationRunner.run(conditionalStyle);

        objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();

        objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=diagram&entity=ConditionalNodeStyle");

        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(objectId));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .filter(form -> form.getPages().get(0).getGroups().get(0).getWidgets().stream().map(AbstractWidget::getLabel).allMatch(label -> Objects.equals("Condition", label)))
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


}

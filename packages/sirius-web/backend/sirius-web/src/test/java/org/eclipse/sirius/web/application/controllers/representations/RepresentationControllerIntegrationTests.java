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
package org.eclipse.sirius.web.application.controllers.representations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.graphql.EditTextfieldMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.graphql.tests.RepresentationDescriptionsQueryRunner;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.graphql.RepresentationMetadataQueryRunner;
import org.eclipse.sirius.web.tests.graphql.RepresentationsMetadataQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the representation controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RepresentationDescriptionsQueryRunner representationDescriptionsQueryRunner;

    @Autowired
    private RepresentationMetadataQueryRunner representationMetadataQueryRunner;

    @Autowired
    private RepresentationsMetadataQueryRunner representationsMetadataQueryRunner;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private EditTextfieldMutationRunner editTextfieldMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.domainEventCollector.clear();
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a persistent representation id, when a query is performed, then the representation metadata are returned")
    public void givenPersistentRepresentationIdWhenQueryIsPerformedThenTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                "representationId", TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()
        );
        var result = this.representationMetadataQueryRunner.run(variables);

        String representationId = JsonPath.read(result, "$.data.viewer.editingContext.representation.id");
        assertThat(representationId).isEqualTo(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo("siriusComponents://representation?type=Portal");

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
        assertThat(label).isEqualTo("EPackage Portal");

        List<String> iconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representation.iconURLs");
        assertThat(iconURLs)
                .isNotEmpty()
                .allSatisfy(iconURL -> assertThat(iconURL).isEqualTo("/api/images/portal-images/portal.svg"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a transient representation id, when a query is performed, then the representation metadata are returned")
    public void givenTransientRepresentationIdWhenQueryIsPerformedThenTheRepresentationMetadataAreReturned() {
        String initialExplorerId = "explorer://?expandedIds=[]&activeFilterIds=[]";
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                "representationId", initialExplorerId
        );
        var result = this.representationMetadataQueryRunner.run(variables);

        String representationId = JsonPath.read(result, "$.data.viewer.editingContext.representation.id");
        assertThat(representationId).isEqualTo(initialExplorerId);

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representation.kind");
        assertThat(kind).isEqualTo("siriusComponents://representation?type=Tree");

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representation.label");
        assertThat(label).isEqualTo("Explorer");

        List<String> iconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representation.iconURLs");
        assertThat(iconURLs)
                .isNotEmpty()
                .satisfiesOnlyOnce(iconURL -> assertThat(iconURL).isEqualTo("/api/images/explorer/explorer.svg"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context, when the representation metadata are requested, then the first ones are returned")
    public void givenEditingContextWhenTheRepresentationMetadataAreRequestedThenTheFirstOnesAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID
        );
        var result = this.representationsMetadataQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.count");
        assertThat(count).isPositive();

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.id");
        assertThat(representationIds)
                .contains(TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION.toString(), TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString());

        List<List<String>> allIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.iconURLs");
        assertThat(allIconURLs)
                .isNotEmpty()
                .allSatisfy(iconURLs -> assertThat(iconURLs)
                        .isNotEmpty()
                        .satisfiesOnlyOnce(iconURL -> assertThat(iconURL).endsWith("/api/images/portal-images/portal.svg")));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context, when the representation metadata for the Explorer view is requested, then it is returned")
    public void givenEditingContextWhenTheExplorerRepresentationMetadataIsRequestedThenItIsReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                "representationIds", List.of(ExplorerDescriptionProvider.PREFIX)
        );
        var result = this.representationsMetadataQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representations.pageInfo.count");
        assertThat(count).isEqualTo(1);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.id");
        assertThat(representationIds).containsExactly(ExplorerDescriptionProvider.PREFIX);

        String label = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[0].node.label");
        assertThat(label).isEqualTo(ExplorerDescriptionProvider.REPRESENTATION_NAME);

        String kind = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[0].node.kind");
        assertThat(kind).isEqualTo(Tree.KIND);

        List<String> iconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[0].node.iconURLs");
        assertThat(iconURLs).containsExactly("/api/images/explorer/explorer.svg");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given many representation ids, when we ask for their metadata, then representation metadata are returned")
    public void givenManyRepresentationIdsWhenWeAskForTheirMetadataThenTheRepresentationMetadataAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                "representationIds", List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION, TestIdentifiers.EPACKAGE_EMPTY_PORTAL_REPRESENTATION)
        );
        var result = this.representationsMetadataQueryRunner.run(variables);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.label");
        assertThat(representationIds)
                .isNotEmpty()
                .containsSequence("EPackage Portal", "Portal");

        List<List<String>> allIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.representations.edges[*].node.iconURLs");
        assertThat(allIconURLs)
                .isNotEmpty()
                .allSatisfy(iconURLs -> assertThat(iconURLs)
                        .isNotEmpty()
                        .satisfiesOnlyOnce(iconURL -> assertThat(iconURL).endsWith("/api/images/portal-images/portal.svg")));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object id, when a query is performed, then all the representation descriptions are returned")
    public void givenObjectIdWhenQueryIsPerformedThenAllTheRepresentationDescriptionsAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID,
                "objectId", TestIdentifiers.EPACKAGE_OBJECT.toString()
        );
        var result = this.representationDescriptionsQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.pageInfo.count");
        assertThat(count).isEqualTo(3);

        List<String> representationIds = JsonPath.read(result, "$.data.viewer.editingContext.representationDescriptions.edges[*].node.id");
        assertThat(representationIds).hasSize(3);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a Portal representation, when we subscribe to its properties events, then the form is sent")
    public void givenPortalRepresentationWhenWeSubscribeToItsPropertiesEventsThenTheFormIsSent() {
        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Consumer<Object> formContentMatcher = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("EPackage Portal").group("Core Properties");

            var labelTextField = groupNavigator.findWidget("Label", Textfield.class);
            assertThat(labelTextField).isNotNull();
            assertThat(labelTextField.getValue()).isEqualTo("EPackage Portal");

            var documentationTextArea = groupNavigator.findWidget("Documentation", Textarea.class);
            assertThat(documentationTextArea).isNotNull();
            assertThat(documentationTextArea.getValue()).isEqualTo("documentation");

            var nameLabel = groupNavigator.findWidget("Description Name", LabelWidget.class);
            assertThat(nameLabel).isNotNull();
            assertThat(nameLabel.getValue()).isEqualTo("Portal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a Portal representation, when we edit its label in the Details view, then the label is changed.")
    public void givenPortalRepresentationWhenWeEditItsLabelInTheDetailsViewThenTheLabelIsChanged() {
        this.givenCommittedTransaction.commit();

        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));
        var detailsEventInput = new DetailsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(detailsEventInput);

        var formId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> formId.set(form.getId()));

        Runnable editLabel = () -> {
            var editLabelInput = new EditTextfieldInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, formId.get(), "metadata.label", "NewPortal");
            var result = this.editTextfieldMutationRunner.run(editLabelInput);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updateFormLabel = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("NewPortal").group("Core Properties");

            var labelTextfield = groupNavigator.findWidget("Label", Textfield.class);
            assertThat(labelTextfield).isNotNull();
            assertThat(labelTextfield.getValue()).isEqualTo("NewPortal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editLabel)
                .consumeNextWith(updateFormLabel)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a Portal representation, when we edit its documentation in the Details view, then the documentation is changed.")
    public void givenPortalRepresentationWhenWeEditItsDocumentationInTheDetailsViewThenTheDocumentationIsChanged() {
        var detailsRepresentationId = this.representationIdBuilder.buildDetailsRepresentationId(List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));
        var detailsEventInput = new DetailsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(detailsEventInput);

        var formId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> formId.set(form.getId()));

        Runnable editDocumentation = () -> {
            var editDocumentationInput = new EditTextfieldInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID, formId.get(), "metadata.documentation", "This is a documentation");
            var result = this.editTextfieldMutationRunner.run(editDocumentationInput);

            String typename = JsonPath.read(result, "$.data.editTextfield.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updateFormDocumentation = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("EPackage Portal").group("Core Properties");

            var documentationTextArea = groupNavigator.findWidget("Documentation", Textarea.class);
            assertThat(documentationTextArea).isNotNull();
            assertThat(documentationTextArea.getValue()).isEqualTo("This is a documentation");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editDocumentation)
                .consumeNextWith(updateFormDocumentation)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

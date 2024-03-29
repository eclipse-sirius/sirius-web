/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditSelectInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.RichText;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.graphql.EditSelectMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.FormVariableViewPreEditingContextProcessor;
import org.eclipse.sirius.web.services.MasterDetailsFormDescriptionProvider;
import org.eclipse.sirius.web.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the form controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private EditSelectMutationRunner editSelectMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a master / details based form representation, when we edit the master part, then the details part is updated")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenMasterDetailsBasedFormRepresentationWhenWeEditTheMasterPartThenTheDetailsPartIsUpdated() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(),
                MasterDetailsFormDescriptionProvider.DESCRIPTION_ID,
                TestIdentifiers.EPACKAGE_OBJECT.toString(),
                "Master Details Form"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input);

        var formId = new AtomicReference<String>();
        var selectId = new AtomicReference<String>();

        Consumer<FormRefreshedEventPayload> initialFormContentConsumer = payload -> Optional.of(payload)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var richText = groupNavigator.findWidget("RichText", RichText.class);
                    assertThat(richText).hasValue("first");

                    var select = groupNavigator.findWidget("Select", Select.class);
                    selectId.set(select.getId());
                }, () -> fail("Missing form"));

        Runnable changeMasterValue = () -> {
            var editSelectInput = new EditSelectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT.toString(), formId.get(), selectId.get(), "second");

            var editSelectResult = this.editSelectMutationRunner.run(editSelectInput);
            String editSelectResultTypename = JsonPath.read(editSelectResult, "$.data.editSelect.__typename");
            assertThat(editSelectResultTypename).isEqualTo(SuccessPayload.class.getSimpleName());

            TestTransaction.flagForCommit();
            TestTransaction.end();
            TestTransaction.start();
        };

        Consumer<FormRefreshedEventPayload> updatedFormContentConsumer = payload -> Optional.of(payload)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var richText = groupNavigator.findWidget("RichText", RichText.class);
                    assertThat(richText).hasValue("second");
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(changeMasterValue)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Given a view based form description, when form variables are initialized, then widgets can read their value")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenViewBasedFormDescriptionWhenFormVariablesAreInitializedThenWidgetsCanReadTheirValue() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                FormVariableViewPreEditingContextProcessor.REPRESENTATION_DESCRIPTION_ID,
                TestIdentifiers.DOMAIN_OBJECT.toString(),
                "Shared Variables Form"
        );
        var flux = this.givenCreatedFormSubscription.createAndSubscribe(input);

        Consumer<FormRefreshedEventPayload> initialFormContentConsumer = payload -> Optional.of(payload)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var listWidget = groupNavigator.findWidget("List", org.eclipse.sirius.components.forms.List.class);
                    assertThat(listWidget.getItems()).hasSize(1);

                    var treeWidget = groupNavigator.findWidget("Tree", TreeWidget.class);
                    assertThat(treeWidget).hasCheckedTreeItemsSize(1);
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

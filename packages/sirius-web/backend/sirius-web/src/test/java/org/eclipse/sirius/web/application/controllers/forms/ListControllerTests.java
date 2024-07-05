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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.DeleteListItemInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.tests.graphql.DeleteListItemMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithListDescriptionProvider;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the list widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ListControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithListDescriptionProvider formWithListDescriptionProvider;

    @Autowired
    private DeleteListItemMutationRunner deleteListItemMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<FormRefreshedEventPayload> givenSubscriptionToListForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                this.formWithListDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithList"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @DisplayName("Given a list widget, when one of its item is deleted, then the list items are updated")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenListWidgetWhenOneOfItsItemIsDeletedThenTheListAreUpdated() {
        var flux = this.givenSubscriptionToListForm();

        var formId = new AtomicReference<String>();
        var listId = new AtomicReference<String>();
        var listItemId = new AtomicReference<String>();

        Consumer<FormRefreshedEventPayload> initialFormContentConsumer = payload -> Optional.of(payload)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    formId.set(form.getId());

                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var list = groupNavigator.findWidget("Entities", List.class);

                    assertThat(list.getItems()).hasSize(3);

                    listId.set(list.getId());
                    listItemId.set(list.getItems().get(0).getId());
                }, () -> fail("Missing form"));

        Runnable deleteListItem = () -> {
            var input = new DeleteListItemInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(), formId.get(), listId.get(), listItemId.get());
            var result = this.deleteListItemMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.deleteListItem.__typename");
            Assertions.assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<FormRefreshedEventPayload> updatedFormContentConsumer = payload -> Optional.of(payload)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(form -> {
                    var groupNavigator = new FormNavigator(form).page("Page").group("Group");
                    var list = groupNavigator.findWidget("Entities", List.class);

                    assertThat(list.getItems()).hasSize(2);
                }, () -> fail("Missing form"));

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(deleteListItem)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}

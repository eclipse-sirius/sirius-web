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

import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationApplicationService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Integration tests of the representation metadata pagination.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationMetadataPaginationIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IRepresentationApplicationService representationApplicationService;

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested forward 3 by 3, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedForwardThreeByThreeThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 3);

        assertThat(navigation).hasSize(4).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 3));
                case 1 -> assertThat(window).satisfies(this.containsRepresentationsFrom(4, 6));
                case 2 -> assertThat(window).satisfies(this.containsRepresentationsFrom(7, 9));
                case 3 -> assertThat(window).satisfies(this.containsRepresentationsFrom(10, 11));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested forward 5 by 5, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedForwardFiveByFiveThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 5);

        assertThat(navigation).hasSize(3).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 5));
                case 1 -> assertThat(window).satisfies(this.containsRepresentationsFrom(6, 10));
                case 2 -> assertThat(window).satisfies(this.containsRepresentationsFrom(11, 11));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested forward 10 by 10, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedForwardTenByTenThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 10);

        assertThat(navigation).hasSize(2).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 10));
                case 1 -> assertThat(window).satisfies(this.containsRepresentationsFrom(11, 11));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested forward 20 by 20, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedForwardTwentyByTwentyThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 20);

        assertThat(navigation).hasSize(1).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 11));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested backward 3 by 3, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedBackwardThreeByThreeThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of("id", UUID.fromString("4d825ada-4109-41c3-954a-b41395d56882")), ScrollPosition.Direction.BACKWARD), 3);

        assertThat(navigation).hasSize(4).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(8, 10));
                case 1 -> assertThat(window).satisfies(this.containsRepresentationsFrom(5, 7));
                case 2 -> assertThat(window).satisfies(this.containsRepresentationsFrom(2, 4));
                case 3 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 1));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested backward 5 by 5, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedBackwardFiveByFiveThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of("id", UUID.fromString("4d825ada-4109-41c3-954a-b41395d56882")), ScrollPosition.Direction.BACKWARD), 5);

        assertThat(navigation).hasSize(2).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(6, 10));
                case 1 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 5));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/representations-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of representations, when they are requested backward 10 by 10, then they are returned properly")
    public void givenSetOfRepresentationsWhenTheyAreRequestedBackwardTenByTenThenTheyAreReturnedProperly() {
        var navigation = this.navigate(ScrollPosition.of(Map.of("id", UUID.fromString("4d825ada-4109-41c3-954a-b41395d56882")), ScrollPosition.Direction.BACKWARD), 10);

        assertThat(navigation).hasSize(1).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsRepresentationsFrom(1, 10));
                default -> fail("Invalid index");
            }
        });
    }

    private Consumer<? super Iterable<? extends RepresentationMetadataDTO>> containsRepresentationsFrom(int startIndex, int endIndex) {
        return iterable -> {
            var expectedRepresentationLabels = IntStream.range(startIndex, endIndex + 1)
                    .boxed()
                    .map(index -> String.format("Representation - %02d", index))
                    .toList();

            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.SORTED), false);
            var receivedProjectNames = stream
                    .map(RepresentationMetadataDTO::label)
                    .toList();
            assertThat(receivedProjectNames).containsExactly(expectedRepresentationLabels.toArray(new String[0]));
        };
    }

    private Map<Integer, Window<RepresentationMetadataDTO>> navigate(KeysetScrollPosition initialPosition, int limit) {
        boolean shouldContinue = true;
        KeysetScrollPosition position = initialPosition;
        List<Window<RepresentationMetadataDTO>> representationReceived = new ArrayList<>();

        while (shouldContinue) {
            var window = this.representationApplicationService.findAllByEditingContextId("9f9c8867-adc1-4704-8514-2d7599775e44", position, limit);
            representationReceived.add(window);

            shouldContinue = window.hasNext();
            if (initialPosition.scrollsBackward()) {
                shouldContinue = window.hasPrevious();
            }

            if (shouldContinue) {
                UUID cursor = null;
                var count = window.getContent().size();
                if (count > 0 && initialPosition.scrollsForward()) {
                    cursor = UUID.fromString(window.getContent().get(count - 1).id());
                } else if (count > 0 && initialPosition.scrollsBackward()) {
                    cursor = UUID.fromString(window.getContent().get(0).id());
                }
                position = ScrollPosition.of(Map.of("id", cursor), initialPosition.getDirection());
            }
        }

        return IntStream.range(0, representationReceived.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), representationReceived::get));
    }
}

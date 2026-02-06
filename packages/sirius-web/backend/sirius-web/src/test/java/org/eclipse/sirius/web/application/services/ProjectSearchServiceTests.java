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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

/**
 * Used to test the project search service.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectSearchServiceTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of projects, when they are requested forward 3 by 3, then they are returned properly")
    public void givenSetOfProjectsWhenTheyAreRequestedForwardThreeByThreeThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "a666ecfd-944c-4d66-a704-b21c3b548fb1",
                "c6ca52a9-6425-442f-89e3-20ae3a0f1a2f",
                "07140f46-2a66-46a3-bca1-dcc1f1654177",
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 3);

        assertThat(navigation).hasSize(3).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(1, 3));
                case 1 -> assertThat(window).satisfies(this.containsProjectsFrom(4, 6));
                case 2 -> assertThat(window).satisfies(this.containsProjectsFrom(7, 8));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a smaller set of projects, when they are requested forward 3 by 3, then they are returned properly")
    public void givenSmallerSetOfProjectsWhenTheyAreRequestedForwardThreeByThreeThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of(), ScrollPosition.Direction.FORWARD), 3);

        assertThat(navigation).hasSize(2).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(4, 6));
                case 1 -> assertThat(window).satisfies(this.containsProjectsFrom(7, 8));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of projects, when they are requested forward 3 by 3 after a cursor, then they are returned properly")
    public void givenSetOfProjectsWhenTheyAreRequestedForwardThreeByThreeAfterCursorThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "a666ecfd-944c-4d66-a704-b21c3b548fb1",
                "c6ca52a9-6425-442f-89e3-20ae3a0f1a2f",
                "07140f46-2a66-46a3-bca1-dcc1f1654177",
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of("id", "07140f46-2a66-46a3-bca1-dcc1f1654177"), ScrollPosition.Direction.FORWARD), 3);

        assertThat(navigation).hasSize(2).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(4, 6));
                case 1 -> assertThat(window).satisfies(this.containsProjectsFrom(7, 8));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a smaller set of projects, when they are requested forward 3 by 3 after a cursor, then they are returned properly")
    public void givenSmallerSetOfProjectsWhenTheyAreRequestedForwardThreeByThreeAfterCursorThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of("id", "315ea8e8-8842-4955-8139-2974abb2e8a8"), ScrollPosition.Direction.FORWARD), 3);

        assertThat(navigation).hasSize(1).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(7, 8));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a set of projects, when they are requested backward 3 by 3, then they are returned properly")
    public void givenSetOfProjectsWhenTheyAreRequestedBackwardThreeByThreeThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "a666ecfd-944c-4d66-a704-b21c3b548fb1",
                "c6ca52a9-6425-442f-89e3-20ae3a0f1a2f",
                "07140f46-2a66-46a3-bca1-dcc1f1654177",
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of("id", "4f5bb88f-cc82-4c86-af8c-a5ac1039b07b"), ScrollPosition.Direction.BACKWARD), 3);

        assertThat(navigation).hasSize(3).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(6, 8));
                case 1 -> assertThat(window).satisfies(this.containsProjectsFrom(3, 5));
                case 2 -> assertThat(window).satisfies(this.containsProjectsFrom(1, 2));
                default -> fail("Invalid index");
            }
        });
    }

    @Test
    @Sql(scripts = {
        "/sirius-web-scripts/projects-order.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {
        "/sirius-web-scripts/cleanup.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @DisplayName("Given a smaller set of projects, when they are requested backward 3 by 3, then they are returned properly")
    public void givenSmallerSetOfProjectsWhenTheyAreRequestedBackwardThreeByThreeThenTheyAreReturnedProperly() {
        List<String> projectIds = List.of(
                "0e1fb24a-ff59-4093-aece-706bee694795",
                "190e03bd-0f0d-4863-a497-43be617a945d",
                "315ea8e8-8842-4955-8139-2974abb2e8a8",
                "3b6165ce-f57d-4047-924a-81fb2da6b03c",
                "40c3dd34-8d6b-48c9-b7f0-ce2ac397ec97"
        );
        var navigation = this.navigate(projectIds, ScrollPosition.of(Map.of("id", "4f5bb88f-cc82-4c86-af8c-a5ac1039b07b"), ScrollPosition.Direction.BACKWARD), 3);

        assertThat(navigation).hasSize(2).allSatisfy((index, window) -> {
            switch (index) {
                case 0 -> assertThat(window).satisfies(this.containsProjectsFrom(6, 8));
                case 1 -> assertThat(window).satisfies(this.containsProjectsFrom(4, 5));
                default -> fail("Invalid index");
            }
        });
    }

    private Consumer<? super Iterable<? extends Project>> containsProjectsFrom(int startIndex, int endIndex) {
        return iterable -> {
            var expectedProjectNames = IntStream.range(startIndex, endIndex + 1)
                    .boxed()
                    .map(index -> String.format("Project - %02d", index))
                    .toList();

            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.SORTED), false);
            var receivedProjectNames = stream
                    .map(Project::getName)
                    .toList();
            assertThat(receivedProjectNames).containsExactly(expectedProjectNames.toArray(new String[0]));
        };
    }

    private Map<Integer, Window<Project>> navigate(List<String> projectIds, KeysetScrollPosition initialPosition, int limit) {
        boolean shouldContinue = true;
        KeysetScrollPosition position = initialPosition;
        List<Window<Project>> projectReceived = new ArrayList<>();

        while (shouldContinue) {
            var window = this.projectSearchService.findAll(projectIds, position, limit, Map.of());
            projectReceived.add(window);

            shouldContinue = window.hasNext();
            if (initialPosition.scrollsBackward()) {
                shouldContinue = window.hasPrevious();
            }

            if (shouldContinue) {
                String cursor = null;
                var count = window.getContent().size();
                if (count > 0 && initialPosition.scrollsForward()) {
                    cursor = window.getContent().get(count - 1).getId();
                } else if (count > 0 && initialPosition.scrollsBackward()) {
                    cursor = window.getContent().get(0).getId();
                }
                position = ScrollPosition.of(Map.of("id", cursor), initialPosition.getDirection());
            }
        }

        return IntStream.range(0, projectReceived.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), projectReceived::get));
    }
}

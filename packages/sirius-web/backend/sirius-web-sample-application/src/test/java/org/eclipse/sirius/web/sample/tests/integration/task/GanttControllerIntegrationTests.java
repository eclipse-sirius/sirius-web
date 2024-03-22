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
package org.eclipse.sirius.web.sample.tests.integration.task;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.graphql.tests.CreateRepresentationMutationRunner;
import org.eclipse.sirius.web.sample.TestIdentifiers;
import org.eclipse.sirius.web.sample.tests.integration.AbstractIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the gantt controllers.
 *
 * @author lfasani
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GanttControllerIntegrationTests extends AbstractIntegrationTests {

    public static final String GANTT_REPRESENTATION_DESCRIPTION_ID = String.format(
            "siriusComponents://representationDescription?kind=ganttDescription&sourceKind=view&sourceId=%s&sourceElementId=%s", TestIdentifiers.GANTT_VIEW, TestIdentifiers.GANTT_REPRESENTATION);

    @Autowired
    private CreateRepresentationMutationRunner createRepresentationMutationRunner;

    @Test
    @DisplayName("Given a task model, when a create representation is performed, then the representation is created")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenTaskModelThenGanttRepresentationCreationSucceeds() {

        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.TASK_PROJECT.toString(),
                GANTT_REPRESENTATION_DESCRIPTION_ID,
                TestIdentifiers.TASK_PROJECT_ROOT_OBJECT.toString(),
                "GanttRepresentation"
        );
        var result = this.createRepresentationMutationRunner.run(input);
        String typename = JsonPath.read(result, "$.data.createRepresentation.__typename");
        assertThat(typename).isEqualTo(CreateRepresentationSuccessPayload.class.getSimpleName());

        String representationId = JsonPath.read(result, "$.data.createRepresentation.representation.id");
        assertThat(representationId).isNotNull();
    }
}

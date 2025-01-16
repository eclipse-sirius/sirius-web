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
package org.eclipse.sirius.web.application.controllers.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.query.dto.BooleanExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionInput;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionSuccessPayload;
import org.eclipse.sirius.web.application.views.query.dto.IntExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.ObjectExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.ObjectsExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.StringExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.VoidExpressionResult;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.EvaluateExpressionMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the query view.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private EvaluateExpressionMutationRunner evaluateExpressionMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression returning a collection of objects, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionReturningCollectionOfObjectsThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(ObjectsExpressionResult.class.getSimpleName());

        List<String> labels = JsonPath.read(result, "$.data.evaluateExpression.result.objectsValue[*].label");
        assertThat(labels)
                .isNotEmpty()
                .containsExactly("Component sirius-web-domain", "Component sirius-web-application", "Component sirius-web-infrastructure", "Component sirius-web-starter", "Component sirius-web");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression returning a single object, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionReturningSingleObjectThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)->first()";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(ObjectExpressionResult.class.getSimpleName());

        String label = JsonPath.read(result, "$.data.evaluateExpression.result.objectValue.label");
        assertThat(label).isEqualTo("Component sirius-web-domain");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression returning an integer, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionReturningIntegerThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)->size()";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(IntExpressionResult.class.getSimpleName());

        int size = JsonPath.read(result, "$.data.evaluateExpression.result.intValue");
        assertThat(size).isEqualTo(5);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression returning a boolean, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionReturningBooleanThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)->isEmpty()";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(BooleanExpressionResult.class.getSimpleName());

        boolean isEmpty = JsonPath.read(result, "$.data.evaluateExpression.result.booleanValue");
        assertThat(isEmpty).isFalse();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression returning a string, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionReturningStringThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)->first().name";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(StringExpressionResult.class.getSimpleName());

        String name = JsonPath.read(result, "$.data.evaluateExpression.result.stringValue");
        assertThat(name).isEqualTo("sirius-web-domain");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we execute an expression which does not return anything, then the result is returned")
    public void givenProjectWhenWeExecuteAnExpressionWhichDoesNotReturnAnythingThenTheResultIsReturned() {
        var expression = "aql:editingContext.allContents()->filter(papaya::Component)->first().newPackage('newpackage')";
        var result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));

        String payloadTypename = JsonPath.read(result, "$.data.evaluateExpression.__typename");
        assertThat(payloadTypename).isEqualTo(EvaluateExpressionSuccessPayload.class.getSimpleName());

        String resultTypename = JsonPath.read(result, "$.data.evaluateExpression.result.__typename");
        assertThat(resultTypename).isEqualTo(VoidExpressionResult.class.getSimpleName());

        expression = "aql:editingContext.allContents()->filter(papaya::Component)->first().packages->last().name";
        result = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_PROJECT.toString(), expression));
        String name = JsonPath.read(result, "$.data.evaluateExpression.result.stringValue");
        assertThat(name).isEqualTo("newpackage");
    }
}

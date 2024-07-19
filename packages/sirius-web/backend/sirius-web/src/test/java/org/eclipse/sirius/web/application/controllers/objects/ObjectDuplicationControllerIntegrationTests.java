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
package org.eclipse.sirius.web.application.controllers.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectSuccessPayload;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ContainmentFeatureNamesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.DuplicateObjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.EvaluateExpressionMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the object duplicate controllers.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectDuplicationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private ContainmentFeatureNamesQueryRunner containmentFeatureNamesQueryRunner;

    @Autowired
    private DuplicateObjectMutationRunner duplicateObjectMutationRunner;

    @Autowired
    private EvaluateExpressionMutationRunner evaluateExpressionMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object and a container, when containment feature names are requested, then compatible features are returned")
    public void givenObjectAndContainerWhenContainmentFeatureNamesAreRequestedThenCompatibleFeatureNamesAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                "containerId", StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString(),
                "containedObjectId", StudioIdentifiers.LABEL_ATTRIBUTE_OBJECT.toString()
        );
        var result = this.containmentFeatureNamesQueryRunner.run(variables);

        List<String> containmentFeatureNames = JsonPath.read(result, "$.data.viewer.editingContext.containmentFeatureNames[*]");
        assertThat(containmentFeatureNames)
                .isNotEmpty()
                .contains("attributes");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedThenItIsDuplicatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                StudioIdentifiers.LABEL_ATTRIBUTE_OBJECT.toString(),
                StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString(),
                "attributes",
                false,
                false,
                false
        );
        var result = this.duplicateObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.duplicateObject.__typename");
        assertThat(typename).isEqualTo(DuplicateObjectSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result, "$.data.duplicateObject.object.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result, "$.data.duplicateObject.object.label");
        assertThat(objectLabel).isNotBlank();
        assertThat(objectLabel).isEqualTo("label");

        String objectKind = JsonPath.read(result, "$.data.duplicateObject.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=domain&entity=Attribute");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated with content, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedWithContentThenItIsDuplicatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "types",
                true,
                false,
                false
        );
        var result = this.duplicateObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.duplicateObject.__typename");
        assertThat(typename).isEqualTo(DuplicateObjectSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result, "$.data.duplicateObject.object.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result, "$.data.duplicateObject.object.label");
        assertThat(objectLabel).isNotBlank();
        assertThat(objectLabel).isEqualTo("NamedElement");

        String objectKind = JsonPath.read(result, "$.data.duplicateObject.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=domain&entity=Entity");

        var expression = "aql:self.attributes";
        var queryResult = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, expression, List.of(objectId)));
        List<String> attributes = JsonPath.read(queryResult, "$.data.evaluateExpression.result.objectsValue[*]");
        assertThat(attributes).hasSize(1);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated with outgoing reference, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedWithOutgoingReferenceThenItIsDuplicatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "types",
                false,
                true,
                true
        );
        var result = this.duplicateObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.duplicateObject.__typename");
        assertThat(typename).isEqualTo(DuplicateObjectSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result, "$.data.duplicateObject.object.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result, "$.data.duplicateObject.object.label");
        assertThat(objectLabel).isNotBlank();
        assertThat(objectLabel).isEqualTo("Human");

        String objectKind = JsonPath.read(result, "$.data.duplicateObject.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=domain&entity=Entity");

        var expression = "aql:self.superTypes";
        var queryResult = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, expression, List.of(objectId)));
        List<String> attributes = JsonPath.read(queryResult, "$.data.evaluateExpression.result.objectsValue[*]");
        assertThat(attributes).hasSize(1);
    }

}

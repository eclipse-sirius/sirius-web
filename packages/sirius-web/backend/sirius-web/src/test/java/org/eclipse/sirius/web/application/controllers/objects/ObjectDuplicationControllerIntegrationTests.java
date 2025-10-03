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

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectSuccessPayload;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ContainmentFeatureNamesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.DuplicateObjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.EvaluateExpressionMutationRunner;
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

        List<Map<String, String>> containmentFeatureNames = JsonPath.read(result, "$.data.viewer.editingContext" + ".containmentFeatureNames[*]");
        assertThat(containmentFeatureNames)
                .isNotEmpty()
                .contains(Map.of("id", "attributes", "label", "Add in attributes"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedThenItIsDuplicatedProperly() {
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

    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object and a container with a custom IContainmentFeatureNameProviderDelegate, when containment feature names are requested, then the custom feature are returned")
    public void givenObjectAndContainerWithCustomIContainmentFeatureNameProviderDelegateWhenContainmentFeatureNamesAreRequestedThenCompatibleFeatureNamesAreReturned() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                "containerId", StudioIdentifiers.NAMED_ELEMENT_ENTITY_OBJECT.toString(),
                "containedObjectId", StudioIdentifiers.DOMAIN_OBJECT.toString()
        );

        var result = this.containmentFeatureNamesQueryRunner.run(variables);

        List<Map<String, String>> containmentFeatureNames = JsonPath.read(result, "$.data.viewer.editingContext.containmentFeatureNames[*]");
        assertThat(containmentFeatureNames)
                .isNotEmpty()
                .contains(Map.of(
                        "id", "fake:aCommandId",
                        "label", "Custom containment feature used in my integration test"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated with content, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedWithContentThenItIsDuplicatedProperly() {
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

        var expression = "aql:self.attributes";
        var queryResult = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, expression, List.of(objectId)));
        List<String> attributes = JsonPath.read(queryResult, "$.data.evaluateExpression.result.objectsValue[*]");
        assertThat(attributes).hasSize(1);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated with outgoing reference, then it is duplicated properly")
    public void givenObjectWhenObjectIsDuplicatedWithOutgoingReferenceThenItIsDuplicatedProperly() {
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

        var expression = "aql:self.superTypes";
        var queryResult = this.evaluateExpressionMutationRunner.run(new EvaluateExpressionInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID, expression, List.of(objectId)));
        List<String> attributes = JsonPath.read(queryResult, "$.data.evaluateExpression.result.objectsValue[*]");
        assertThat(attributes).hasSize(1);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an object, when this object is duplicated but the upper boundary is reached, then the proper error message is return")
    public void givenObjectWhenObjectIsDuplicatedButUpperBoundaryIsReachedThenErrorMessageIsReturn() {
        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                StudioIdentifiers.RECTANGULAR_NODE_STYLE_OBJECT.toString(),
                StudioIdentifiers.HUMAN_NODE_DESCRIPTION_OBJECT.toString(),
                "style",
                false,
                false,
                false
        );
        var result = this.duplicateObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.duplicateObject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        String errorMessage = JsonPath.read(result, "$.data.duplicateObject.message");
        assertThat(errorMessage).isNotBlank();
        assertThat(errorMessage).isEqualTo("Unable to create a new instance of \"RectangularNodeStyleDescription\" in feature \"style\" because it has reached its upper-bound cardinality");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an unknown object, when this object is duplicated, then the proper error message is return")
    public void givenUnknownObjectWhenObjectIsDuplicatedThenErrorMessageIsReturn() {
        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID,
                "unknown_object_id",
                StudioIdentifiers.HUMAN_NODE_DESCRIPTION_OBJECT.toString(),
                "style",
                false,
                false,
                false
        );
        var result = this.duplicateObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.duplicateObject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        String errorMessage = JsonPath.read(result, "$.data.duplicateObject.message");
        assertThat(errorMessage).isNotBlank();
        assertThat(errorMessage).isEqualTo("The object with id \"unknown_object_id\" does not exist");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an Papapya NamedElement, when this object is duplicated, then the copy is given a distinct name")
    public void givenPapayaNamedElementWhenDuplicatedThenTheCopyHasModifiedName() {
        var input = new DuplicateObjectInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "elements",
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
        assertThat(objectLabel).isEqualTo("sirius-web-domain (copy)");

    }

}

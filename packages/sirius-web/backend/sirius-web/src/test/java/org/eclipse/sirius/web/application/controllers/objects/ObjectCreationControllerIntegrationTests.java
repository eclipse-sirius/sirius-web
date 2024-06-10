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
package org.eclipse.sirius.web.application.controllers.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectSuccessPayload;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.graphql.ChildCreationDescriptionsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.graphql.CreateRootObjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DomainsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.RootObjectCreationDescriptionsQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the object creation controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectCreationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private DomainsQueryRunner domainsQueryRunner;

    @Autowired
    private RootObjectCreationDescriptionsQueryRunner rootObjectCreationDescriptionsQueryRunner;

    @Autowired
    private ChildCreationDescriptionsQueryRunner childCreationDescriptionsQueryRunner;

    @Autowired
    private CreateRootObjectMutationRunner createRootObjectMutationRunner;

    @Autowired
    private CreateChildMutationRunner createChildMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a studio, when the domains are requested, then domain and EPackage based domains are available")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenDomainsAreRequestedThenDomainAndEPackageBasedDomainsAreAvailable() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(),
                "rootDomainsOnly", true
        );
        var result = this.domainsQueryRunner.run(variables);

        List<String> domainIds = JsonPath.read(result, "$.data.viewer.editingContext.domains[*].id");
        assertThat(domainIds)
                .isNotEmpty()
                .contains("domain://buck", EcorePackage.eNS_URI, ViewPackage.eNS_URI, DiagramPackage.eNS_URI, FormPackage.eNS_URI);
    }

    @Test
    @DisplayName("Given a domain, when the root object creation descriptions are requested, then valid objects are available")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDomainWhenRootObjectCreationDescriptionsAreRequestedThenValidObjectsAreAvailable() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(),
                "domainId", "domain://buck",
                "suggested", true
        );
        var result = this.rootObjectCreationDescriptionsQueryRunner.run(variables);

        List<String> creationDescriptionIds = JsonPath.read(result, "$.data.viewer.editingContext.rootObjectCreationDescriptions[*].id");
        assertThat(creationDescriptionIds)
                .isNotEmpty()
                .contains("Root");

        List<List<String>> creationDescriptionIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.rootObjectCreationDescriptions[*].iconURL");
        assertThat(creationDescriptionIconURLs).hasSize(2);
        assertThat(creationDescriptionIconURLs.get(0)).isEqualTo(List.of("/api/images/icons/svg/Default.svg"));
        assertThat(creationDescriptionIconURLs.get(1)).isEqualTo(List.of("/api/images/icons/svg/Default.svg"));
    }

    @Test
    @DisplayName("Given an object, when the child object creation descriptions are requested, then valid objects are available")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnObjectWhenChildObjectCreationDescriptionsAreRequestedThenValidObjectsAreAvailable() {
        Map<String, Object> variables = Map.of(
                "editingContextId", StudioIdentifiers.EMPTY_STUDIO_PROJECT.toString(),
                "kind", "siriusComponents://semantic?domain=buck&entity=Root"
        );
        var result = this.childCreationDescriptionsQueryRunner.run(variables);

        List<String> creationDescriptionIds = JsonPath.read(result, "$.data.viewer.editingContext.childCreationDescriptions[*].id");
        assertThat(creationDescriptionIds)
                .isNotEmpty()
                .contains("Humans Human");

        List<List<String>> creationDescriptionIconURLs = JsonPath.read(result, "$.data.viewer.editingContext.childCreationDescriptions[*].iconURL");
        assertThat(creationDescriptionIconURLs).hasSize(1);
        assertThat(creationDescriptionIconURLs.get(0)).isEqualTo(List.of("/api/images/icons/svg/Default.svg"));
    }

    @Test
    @DisplayName("Given a document, when a root object is created, then it is created properly")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenDocumentWhenRootObjectIsCreatedThenItIsCreatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new CreateRootObjectInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                StudioIdentifiers.DOMAIN_DOCUMENT,
                DomainPackage.eNS_URI,
                "Entity"
        );
        var result = this.createRootObjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.createRootObject.__typename");
        assertThat(typename).isEqualTo(CreateRootObjectSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result, "$.data.createRootObject.object.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result, "$.data.createRootObject.object.label");
        assertThat(objectLabel).isNotBlank();

        String objectKind = JsonPath.read(result, "$.data.createRootObject.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=domain&entity=Entity");
    }

    @Test
    @DisplayName("Given an object, when a child object is created, then it is created properly")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenObjectWhenChildObjectIsCreatedThenItIsCreatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "Entity"
        );
        var result = this.createChildMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.createChild.__typename");
        assertThat(typename).isEqualTo(CreateChildSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result, "$.data.createChild.object.label");
        assertThat(objectLabel).isNotBlank();

        String objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=domain&entity=Entity");
    }
}

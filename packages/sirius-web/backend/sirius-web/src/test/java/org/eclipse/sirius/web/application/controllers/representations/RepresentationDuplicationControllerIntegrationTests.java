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

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationSuccessPayload;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DuplicateRepresentationMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the representation duplicate controllers.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationDuplicationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private DuplicateRepresentationMutationRunner duplicateRepresentationMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a representation, when this representation is duplicated, then it is duplicated properly")
    public void givenRepresentationWhenRepresentationIsDuplicatedThenItIsDuplicatedProperly() {
        this.givenCommittedTransaction.commit();

        var input = new DuplicateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString()
        );
        var result = this.duplicateRepresentationMutationRunner.run(input);

        String typename = JsonPath.read(result.data(), "$.data.duplicateRepresentation.__typename");
        assertThat(typename).isEqualTo(DuplicateRepresentationSuccessPayload.class.getSimpleName());

        String objectId = JsonPath.read(result.data(), "$.data.duplicateRepresentation.representationMetadata.id");
        assertThat(objectId).isNotBlank();

        String objectLabel = JsonPath.read(result.data(), "$.data.duplicateRepresentation.representationMetadata.label");
        assertThat(objectLabel)
                .isNotBlank()
                .isEqualTo("_copy");

        String objectKind = JsonPath.read(result.data(), "$.data.duplicateRepresentation.representationMetadata.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://representation?type=Table");
    }

}

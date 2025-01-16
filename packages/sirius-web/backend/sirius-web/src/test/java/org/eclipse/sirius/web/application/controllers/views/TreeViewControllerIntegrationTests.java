/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.views;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of Tree View controllers.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TreeViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private CreateChildMutationRunner createChildMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a view, when a tree description is created, then it is created properly")
    public void givenAViewWhenATreeDescriptionIsCreatedThenItIsCreatedProperly() {
        this.givenCommittedTransaction.commit();

        var inputPalette = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                "c4591605-8ea8-4e92-bb17-05c4538248f8",
                "descriptions-TreeDescription"
        );
        var result = this.createChildMutationRunner.run(inputPalette);

        String typename = JsonPath.read(result, "$.data.createChild.__typename");
        assertThat(typename).isEqualTo(CreateChildSuccessPayload.class.getSimpleName());

        var treeDescriptionId = new AtomicReference<String>();

        String objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();
        treeDescriptionId.set(objectId);

        String objectLabel = JsonPath.read(result, "$.data.createChild.object.label");
        assertThat(objectLabel).isNotBlank();

        String objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=tree&entity=TreeDescription");

        var treeItemLabelDescription = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                treeDescriptionId.get(),
                "treeItemLabelDescriptions-TreeItemLabelDescription"
        );
        result = this.createChildMutationRunner.run(treeItemLabelDescription);

        objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();

        objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=tree&entity=TreeItemLabelDescription");

        var singleClickTreeItemContextMenuEntry = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                treeDescriptionId.get(),
                "contextMenuEntries-SingleClickTreeItemContextMenuEntry"
        );
        result = this.createChildMutationRunner.run(singleClickTreeItemContextMenuEntry);

        objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();
        objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=tree&entity=SingleClickTreeItemContextMenuEntry");

        var fetchTreeItemContextMenuEntry = new CreateChildInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString(),
                treeDescriptionId.get(),
                "contextMenuEntries-FetchTreeItemContextMenuEntry"
        );
        result = this.createChildMutationRunner.run(fetchTreeItemContextMenuEntry);

        objectId = JsonPath.read(result, "$.data.createChild.object.id");
        assertThat(objectId).isNotBlank();
        objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
        assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=tree&entity=FetchTreeItemContextMenuEntry");

    }


}

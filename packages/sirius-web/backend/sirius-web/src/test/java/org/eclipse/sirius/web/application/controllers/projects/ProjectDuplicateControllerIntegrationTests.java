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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DuplicateProjectMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project duplicate controller.
 *
 * @author Arthur Daussy
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.capability.project.duplication=true" })
public class ProjectDuplicateControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private DuplicateProjectMutationRunner duplicateProjectMutationRunner;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IIdentityService identityService;

    @Autowired
    private ILabelService labelService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private IObjectSearchService objectSearchService;

    @Autowired
    private IProjectEditingContextService projectEditingContextService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the duplication of the project is requested, then the project with its representation and semantic data are available")
    public void checkProjectDuplication() {
        var input = new DuplicateProjectInput(UUID.randomUUID(), FlowIdentifier.PROJECT_ID);
        var result = this.duplicateProjectMutationRunner.run(input);
        String typename = JsonPath.read(result, "$.data.duplicateProject.__typename");
        assertThat(typename).isEqualTo(DuplicateProjectSuccessPayload.class.getSimpleName());
        String newProjectId = JsonPath.read(result, "$.data.duplicateProject.project.id");

        TestTransaction.flagForCommit();
        TestTransaction.end();

        var optionalOldProjectEditingContext = this.projectEditingContextService.getEditingContextId(FlowIdentifier.PROJECT_ID)
                .flatMap(this.editingContextSearchService::findById)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
        assertThat(optionalOldProjectEditingContext).isPresent();
        var oldProjectEditingContext = optionalOldProjectEditingContext.get();

        var optionalNewProjectEditingContext = this.projectEditingContextService.getEditingContextId(newProjectId)
                .flatMap(this.editingContextSearchService::findById)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);
        assertThat(optionalNewProjectEditingContext).isPresent();
        var newProjectEditingContext = optionalNewProjectEditingContext.get();

        assertThat(newProjectEditingContext.getDomain().getResourceSet().getResources())
                .hasSize(oldProjectEditingContext.getDomain().getResourceSet().getResources().size());

        Set<String> oldIds = this.collectAllIdsOnSiriusResource(oldProjectEditingContext.getDomain().getResourceSet());
        Set<String> newIds = this.collectAllIdsOnSiriusResource(newProjectEditingContext.getDomain().getResourceSet());

        assertThat(newIds)
                .as("Both ResourceSet should contain the same number of semantic elements.")
                .hasSize(oldIds.size());
        assertThat(newIds)
                .as("No semantic id should be shared between the two ResourceSet.")
                .noneMatch(oldIds::contains);

        // Check project name
        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent()
                .get()
                .extracting(Project::getName)
                .isEqualTo("Flow - Copy");

        // Check that the representation is available.
        Notifier newSystem = this.searchByLabel(newProjectEditingContext.getDomain().getResourceSet(), "NewSystem");

        List<RepresentationMetadata> representations = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticDataAndTargetObjectId(
                AggregateReference.to(UUID.fromString(newProjectEditingContext.getId())), this.identityService.getId(newSystem));
        assertThat(representations).hasSize(1);

        // Check that all targetObjectIds have been updated
        Optional<Diagram> diagram = this.representationSearchService.findById(newProjectEditingContext, representations.get(0).getId().toString(), Diagram.class);
        assertThat(diagram).isPresent();
        assertThat(diagram.get().getNodes()).allMatch(node -> this.objectSearchService.getObject(newProjectEditingContext, node.getTargetObjectId()).isPresent());
    }

    private Notifier searchByLabel(ResourceSet resourceSet, String expectedLabel) {
        var iterator = resourceSet.getAllContents();
        while (iterator.hasNext()) {
            Notifier next = iterator.next();
            StyledString label = this.labelService.getStyledLabel(next);
            if (label != null && label.toString().equals(expectedLabel)) {
                return next;
            }
        }
        return null;
    }

    private Set<String> collectAllIdsOnSiriusResource(ResourceSet resourceSet) {
        return resourceSet.getResources().stream()
                .filter(resource -> resource.getURI().toString().contains("siriusweb://"))
                .flatMap(resource -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(resource.getAllContents(), Spliterator.ORDERED), false))
                .map(this.identityService::getId)
                .collect(Collectors.toSet());
    }
}

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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import java.util.List;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.repositories.IRepresentationMetadataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of {@link org.eclipse.sirius.components.collaborative.representations.migration.IRepresentationMigrationParticipant}.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class RepresentationMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IRepresentationMetadataRepository representationMetadataRepository;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private IRepresentationContentSearchService representationContentSearchService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private IRepresentationPersistenceService representationPersistenceService;

    @Test
    @DisplayName("Given a project with an old hierarchy representation, when the representation is loaded, then its child nodes are migrated correctly")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWithAnOldHierarchyRepresentationWhenTheRepresentationIsLoadedThenItsChildNodesAreMigratedCorrectly() {
        var representationContentBeforeMigration = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY));
        assertThat(representationContentBeforeMigration).isPresent();

        // Returns output path to access to attribute value instead of returning the value
        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.AS_PATH_LIST));
        List<String> outputPaths = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$..children");
        assertThat(outputPaths).size().isGreaterThan(0);

        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY.toString(), Hierarchy.class);
        assertThat(optionalRepresentation).isPresent();

        Hierarchy hierarchy = optionalRepresentation.get();
        assertThat(hierarchy.getChildNodes()).hasSize(1);

        this.representationPersistenceService.save(null, optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM_HIERARCHY));
        assertThat(optionalUpdatedRepresentationContent).isPresent();

        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();
        List<Object> updatedChildren = JsonPath.read(updatedRepresentationContent.getContent(), "$..children");
        assertThat(updatedChildren).size().isEqualTo(0);

        var expectedUpdatedOutputPath = outputPaths.stream().map(outputPath -> outputPath.replace("children", "childNodes")).toList();
        List<String> actualUpdatedOutputPath = parseContext.parse(updatedRepresentationContent.getContent()).read("$..childNodes");
        assertThat(actualUpdatedOutputPath).isEqualTo(expectedUpdatedOutputPath);
    }

    @Test
    @DisplayName("Given a project with an old diagram representation, when the representation is loaded, then the position of diagram, nodes and edges have been removed, but not from layout data")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testRemovePositionFromDiagramPresentOnDiagramAndNodesAndEdges() {
        var representationContentBeforeMigration = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(representationContentBeforeMigration).isPresent();

        // Returns output path to access to attribute value instead of returning the value and prevent exception to be thrown when the path does not match.
        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.AS_PATH_LIST, Option.SUPPRESS_EXCEPTIONS));

        List<String> rootPositionBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.position");
        assertThat(rootPositionBeforeMigration).size().isEqualTo(1);

        List<String> nodesPositionBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.nodes..position");
        assertThat(nodesPositionBeforeMigration).size().isGreaterThan(0);

        List<String> edgesPositionBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.edges..position");
        assertThat(edgesPositionBeforeMigration).size().isGreaterThan(0);

        List<String> layoutDataPositionBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.layoutData..position");
        assertThat(layoutDataPositionBeforeMigration).size().isGreaterThan(0);

        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM.toString(), Diagram.class);
        assertThat(optionalRepresentation).isPresent();

        this.representationPersistenceService.save(null, optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(optionalUpdatedRepresentationContent).isPresent();

        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();

        List<String> updatedRootPosition = parseContext.parse(updatedRepresentationContent.getContent()).read("$.position");
        assertThat(updatedRootPosition).size().isEqualTo(0);

        List<String> updatedNodesPosition = parseContext.parse(updatedRepresentationContent.getContent()).read("$.nodes..position");
        assertThat(updatedNodesPosition).size().isEqualTo(0);

        List<String> updatedEdgesPosition = parseContext.parse(updatedRepresentationContent.getContent()).read("$.edges..position");
        assertThat(updatedEdgesPosition).size().isEqualTo(0);

        List<String> updatedLayoutDataPosition = parseContext.parse(updatedRepresentationContent.getContent()).read("$.layoutData..position");
        assertThat(updatedLayoutDataPosition).size().isGreaterThan(0);
        assertThat(updatedLayoutDataPosition).isEqualTo(layoutDataPositionBeforeMigration);
    }

    @Test
    @DisplayName("Given a project with an old diagram representation, when the representation is loaded, then the size and size of diagram, nodes and edges have been removed, but not from layout data")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testRemoveSizeFromDiagramPresentOnDiagramAndNodesAndEdges() {
        var representationContentBeforeMigration = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(representationContentBeforeMigration).isPresent();

        // Returns output path to access to attribute value instead of returning the value and prevent exception to be thrown when the path does not match.
        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.AS_PATH_LIST, Option.SUPPRESS_EXCEPTIONS));

        List<String> rootSizeBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.size");
        assertThat(rootSizeBeforeMigration).size().isEqualTo(1);

        List<String> nodesSizeBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.nodes..size");
        assertThat(nodesSizeBeforeMigration).size().isGreaterThan(0);

        List<String> edgesSizeBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.edges..size");
        assertThat(edgesSizeBeforeMigration).size().isGreaterThan(0);

        List<String> layoutDataSizeBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.layoutData..size");
        assertThat(layoutDataSizeBeforeMigration).size().isGreaterThan(0);

        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM.toString(), Diagram.class);
        assertThat(optionalRepresentation).isPresent();

        this.representationPersistenceService.save(null, optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(optionalUpdatedRepresentationContent).isPresent();

        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();

        List<String> updatedRootSize = parseContext.parse(updatedRepresentationContent.getContent()).read("$.size");
        assertThat(updatedRootSize).size().isEqualTo(0);

        List<String> updatedNodesSize = parseContext.parse(updatedRepresentationContent.getContent()).read("$.nodes..size");
        assertThat(updatedNodesSize).size().isEqualTo(0);

        List<String> updatedEdgesSize = parseContext.parse(updatedRepresentationContent.getContent()).read("$.edges..size");
        assertThat(updatedEdgesSize).size().isGreaterThan(0);

        List<String> updatedLayoutDataSize = parseContext.parse(updatedRepresentationContent.getContent()).read("$.layoutData..size");
        assertThat(updatedLayoutDataSize).size().isGreaterThan(0);
        assertThat(updatedLayoutDataSize).isEqualTo(layoutDataSizeBeforeMigration);
    }

    @ParameterizedTest
    @ValueSource(strings = {"alignment", "routingPoints", "sourceAnchorRelativePosition", "targetAnchorRelativePosition", "userResizable", "customizedProperties"})
    @DisplayName("Given a project with an old diagram representation, when the representation is loaded, then the position and size of diagram and nodes have been removed, but not from layout data")
    @Sql(scripts = {"/scripts/migration.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testRemoveAttributeFromDiagramContent(String attribute) {
        var representationContentBeforeMigration = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(representationContentBeforeMigration).isPresent();

        // Returns output path to access to attribute value instead of returning the value and prevent exception to be thrown when the path does not match.
        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.AS_PATH_LIST, Option.SUPPRESS_EXCEPTIONS));

        List<String> attributeBeforeMigration = parseContext.parse(representationContentBeforeMigration.get().getContent()).read("$.." + attribute);
        assertThat(attributeBeforeMigration).size().isGreaterThan(0);

        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var optionalRepresentation = this.representationSearchService.findById(optionalEditingContext.get(), MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM.toString(), Diagram.class);
        assertThat(optionalRepresentation).isPresent();

        this.representationPersistenceService.save(null, optionalEditingContext.get(), optionalRepresentation.get());

        var optionalUpdatedRepresentationContent = this.representationContentSearchService.findContentByRepresentationMetadata(AggregateReference.to(MigrationIdentifiers.MIGRATION_STUDIO_DIAGRAM));
        assertThat(optionalUpdatedRepresentationContent).isPresent();

        var updatedRepresentationContent = optionalUpdatedRepresentationContent.get();

        List<String> updatedNodesAttribute = parseContext.parse(updatedRepresentationContent.getContent()).read("$.." + attribute);
        assertThat(updatedNodesAttribute).size().isEqualTo(0);
    }
}

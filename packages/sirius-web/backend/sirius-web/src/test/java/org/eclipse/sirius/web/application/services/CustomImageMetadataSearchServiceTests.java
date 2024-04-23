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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.sirius.components.view.emf.api.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.api.ICustomImageMetadataSearchService;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.image.services.api.IImageSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the custom image metadata search service.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomImageMetadataSearchServiceTests extends AbstractIntegrationTests {

    @Autowired
    private IImageCreationService imageCreationService;

    @Autowired
    private IImageDeletionService imageDeletionService;

    @Autowired
    private IImageSearchService imageSearchService;

    @Autowired
    private ICustomImageMetadataSearchService customImageMetadataSearchService;

    @BeforeEach
    public void beforeEach() {
        var svg = """
            <?xml version="1.0" encoding="utf-8"?>
            <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="300" height="200">
              <rect width="100" height="80" x="0" y="70" fill="green" />
            </svg>
            """;

        var label = "Custom image";
        if (!this.imageSearchService.existsByLabel(label)) {
            this.imageCreationService.createImage(label, "image.svg", new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Test
    @DisplayName("Given project images and global images in the database, when custom image metadata are requested, both can be retrieved")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectImagesAndGlobalImagesInTheDatabaseWhenCustomImageMetadataAreRequestedThenBothCanBeRetrieved() {
        var images = this.customImageMetadataSearchService.getAvailableImages(StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString());
        assertThat(images)
                .isNotEmpty()
                .anySatisfy(image -> assertThat(image.id()).isEqualTo(StudioIdentifiers.PLACEHOLDER_IMAGE_OBJECT))
                .anySatisfy(image -> assertThat(image.label()).isEqualTo("Custom image"));
    }

    @Test
    @DisplayName("Given project images and global images in the database, when a global image is deleted, then it does not exist")
    @Sql(scripts = {"/scripts/studio.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectImagesAndGlobalImagesInTheDatabaseWhenGlobalImageIsDeletedThenItDoesNotExist() {
        var images = this.customImageMetadataSearchService.getAvailableImages(StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString());
        assertThat(images)
                .isNotEmpty()
                .anySatisfy(image -> assertThat(image.id()).isEqualTo(StudioIdentifiers.PLACEHOLDER_IMAGE_OBJECT))
                .anySatisfy(image -> assertThat(image.label()).isEqualTo("Custom image"));

        var globalImageId = images.stream()
                .filter(image -> image.label().equals("Custom image"))
                .map(CustomImageMetadata::id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing global image"));
        this.imageDeletionService.deleteImage(globalImageId);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        images = this.customImageMetadataSearchService.getAvailableImages(StudioIdentifiers.SAMPLE_STUDIO_PROJECT.toString());
        assertThat(images)
                .isNotEmpty()
                .anySatisfy(image -> assertThat(image.id()).isEqualTo(StudioIdentifiers.PLACEHOLDER_IMAGE_OBJECT))
                .noneSatisfy(image -> assertThat(image.label()).isEqualTo("Custom image"));
    }
}

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
package org.eclipse.sirius.web.application.controllers.images;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.images.dto.DeleteImageInput;
import org.eclipse.sirius.web.application.images.dto.RenameImageInput;
import org.eclipse.sirius.web.application.images.dto.UploadImageInput;
import org.eclipse.sirius.web.application.images.dto.UploadImageSuccessPayload;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.projectimage.services.api.IProjectImageSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DeleteImageMutationRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectImagesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.RenameImageMutationRunner;
import org.eclipse.sirius.web.tests.graphql.UploadImageMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the image controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageControllerIntegrationTests extends AbstractIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ProjectImagesQueryRunner projectImagesQueryRunner;

    @Autowired
    private UploadImageMutationRunner uploadImageMutationRunner;

    @Autowired
    private RenameImageMutationRunner renameImageMutationRunner;

    @Autowired
    private DeleteImageMutationRunner deleteImageMutationRunner;

    @Autowired
    private IProjectImageSearchService projectImageSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when its images are requested, then the images are returned")
    public void givenProjectWhenItsImagesAreRequestedThenTheImagesAreReturned() {
        Map<String, Object> variables = Map.of("projectId", TestIdentifiers.SYSML_SAMPLE_PROJECT.toString());
        var result = this.projectImagesQueryRunner.run(variables);

        List<String> imageLabels = JsonPath.read(result.data(), "$.data.viewer.project.images[*].label");
        assertThat(imageLabels).contains("Placeholder");

        List<String> imageURLs = JsonPath.read(result.data(), "$.data.viewer.project.images[*].url");
        assertThat(imageURLs).contains("/api/images/" + TestIdentifiers.SYSML_IMAGE);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the url of an image which exists, when its content is requested, then the image is returned")
    public void givenTheURLOfAnImageWhichExistsWhenItsContentIsRequestedThenTheImageIsReturned() {
        var uri = "http://localhost:" + port + "/api/images/icons/Resource.svg";

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the url of an image, when its content is requested, then the image is returned")
    public void givenTheURLOfImageWhenItsContentIsRequestedThenTheImageIsReturned() {
        var uri = "http://localhost:" + port + "/api/images/" + TestIdentifiers.SYSML_IMAGE;

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            var bytes = response.getBody().getInputStream().readAllBytes();
            var receivedBody = new String(bytes, StandardCharsets.UTF_8);
            var expectedBody = "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\"><rect width=\"10px\" height=\"10px\" fill=\"black\" /></svg>";
            assertThat(receivedBody).isEqualTo(expectedBody);
        } catch (IOException exception) {
            fail(exception.getMessage());
        }

    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the url of an image which does not exist, when its content is requested, then a 404 NOT FOUND is returned")
    public void givenTheURLOfAnImageWhichDoesNotExistWhenItsContentIsRequestedThen404NotFoundIsReturned() {
        var uri = "http://localhost:" + port + "/api/images/THIS_IMAGE_DOES_NOT_EXIST";

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an image, when it is uploaded, then it is available")
    public void givenAnImageWhenItIsUploadedThenItIsAvailable() {
        var content = "<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\"><rect width=\"10px\" height=\"10px\" fill=\"red\" /></svg>";
        var file = new UploadFile("image.svg", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        var input = new UploadImageInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_PROJECT, "New image", file);
        var result = this.uploadImageMutationRunner.run(input);
        var typename = JsonPath.read(result.data(), "$.data.uploadImage.__typename");
        assertThat(typename).isEqualTo(UploadImageSuccessPayload.class.getSimpleName());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        var imageId = JsonPath.read(result.data(), "$.data.uploadImage.imageId");
        var uri = "http://localhost:" + port + "/api/images/" + imageId;

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            var bytes = response.getBody().getInputStream().readAllBytes();
            var receivedBody = new String(bytes, StandardCharsets.UTF_8);
            assertThat(receivedBody).isEqualTo(content);
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an invalid image, when it is uploaded, then an error is returned")
    public void givenAnInvalidImageWhenItIsUploadedThenAnErrorIsReturned() {
        var content = "";
        var file = new UploadFile("", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        var input = new UploadImageInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_PROJECT, "New image", file);
        var result = this.uploadImageMutationRunner.run(input);
        var typename = JsonPath.read(result.data(), "$.data.uploadImage.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        TestTransaction.flagForCommit();
        TestTransaction.end();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an image, when it is renamed, then its name is updated")
    public void givenAnImageWhenItIsRenamedThenItsNameIsUpdated() {
        var optionalProjectImage = this.projectImageSearchService.findById(TestIdentifiers.SYSML_IMAGE);
        assertThat(optionalProjectImage)
                .isPresent()
                .hasValueSatisfying(projectImage -> assertThat(projectImage.getLabel()).isEqualTo("Placeholder"));

        var input = new RenameImageInput(UUID.randomUUID(), TestIdentifiers.SYSML_IMAGE, "New label");
        var result = this.renameImageMutationRunner.run(input);
        var typename = JsonPath.read(result.data(), "$.data.renameImage.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        optionalProjectImage = this.projectImageSearchService.findById(TestIdentifiers.SYSML_IMAGE);
        assertThat(optionalProjectImage)
                .isPresent()
                .hasValueSatisfying(projectImage -> assertThat(projectImage.getLabel()).isEqualTo(input.newLabel()));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an image, when its deletion is requested, then the image is not found anymore")
    public void givenAnImageWhenItsDeletionIsRequestedThenTheImageIsNotFoundAnymore() {
        var input = new DeleteImageInput(UUID.randomUUID(), TestIdentifiers.SYSML_IMAGE);
        var result = this.deleteImageMutationRunner.run(input);
        var typename = JsonPath.read(result.data(), "$.data.deleteImage.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        var uri = "http://localhost:" + port + "/api/images/" + TestIdentifiers.SYSML_IMAGE;

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

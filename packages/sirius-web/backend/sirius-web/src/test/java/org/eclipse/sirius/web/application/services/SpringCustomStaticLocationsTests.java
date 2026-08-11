/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to test the resolution of paths using Spring when static locations are configured.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "spring.web.resources.static-locations=classpath:/custom-static/"
        }
)
public class SpringCustomStaticLocationsTests extends AbstractIntegrationTests {

    @LocalServerPort
    private String port;

    private String getHTTPBaseUrl() {
        return "http://localhost:" + this.port;
    }

    @Test
    @DisplayName("Given a running server with custom static locations, when a request is sent to a non static resource, then the index.html file is returned")
    public void givenRunningServerWithCustomStaticLocationsWhenRequestIsSentToNonStaticResourceThenTheIndexHtmlFileIsReturned() {
        var index = new ClassPathResource("custom-static/index.html");
        assumeTrue(index.exists());

        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        webTestClient
                .get()
                .uri("/projects")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(body -> assertThat(body.getResponseBody())
                        .contains("<!DOCTYPE html>")
                        // Ensure the correct index.html is retrieved
                        .contains("content of the custom static html resource")
                );
    }

    @Test
    @DisplayName("Given a running server with custom static locations, when a request is sent to a custom static resource, then the resource is returned")
    public void givenRunningServerWithCustomStaticLocationsWhenRequestIsSentToCustomStaticResourceThenTheResourceIsReturned() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        webTestClient
                .get()
                .uri("/custom-static-test-resource.txt")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(body -> assertThat(body.getResponseBody()).contains("content of the custom static test resource"));

    }

}

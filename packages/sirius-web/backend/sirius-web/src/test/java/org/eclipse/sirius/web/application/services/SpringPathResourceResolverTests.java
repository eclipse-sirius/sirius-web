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

import java.util.List;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.infrastructure.mvc.SpringPathResourceResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Used to test the resolution of the paths using Spring.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringPathResourceResolverTests extends AbstractIntegrationTests {

    private final PathResourceResolver pathResourceResolver = new SpringPathResourceResolver("/api");

    @Test
    @DisplayName("Given a path resource resolver, when asked to return an API resource, then null is returned")
    public void givenPathResourceResolverWhenAskToReturnAPIResoureThenNullIsReturned() {
        var request = new MockHttpServletRequest();
        var resource = this.pathResourceResolver.resolveResource(request, "/api/graphql", List.of(new ClassPathResource("classpath:/static")), null);
        assertThat(resource).isNull();
    }
}

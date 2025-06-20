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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.core.api.IDomainSearchService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the domain search service.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DomainSearchServiceTests extends AbstractIntegrationTests {
    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IDomainSearchService domainSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given some semantic data, when the editing context is loaded, then domains can be retrieved")
    public void givenSemanticDataWhenEditingContextIsLoadedThenDomainsCanBeRetrieved() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        var domains = this.domainSearchService.findAllByEditingContext(editingContext);
        assertThat(domains).isNotEmpty();

        var hasEcore = domains.stream().anyMatch(domain -> domain.id().equals(EcorePackage.eNS_URI));
        assertThat(hasEcore).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given some semantic data, when the editing context is loaded, then root domains can be retrieved")
    public void givenSemanticDataWhenEditingContextIsLoadedThenRootDomainsCanBeRetrieved() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        var rootDomains = this.domainSearchService.findRootDomainsByEditingContext(editingContext);
        assertThat(rootDomains).isNotEmpty();

        var hasEcore = rootDomains.stream().anyMatch(domain -> domain.id().equals(EcorePackage.eNS_URI));
        assertThat(hasEcore).isTrue();
    }
}

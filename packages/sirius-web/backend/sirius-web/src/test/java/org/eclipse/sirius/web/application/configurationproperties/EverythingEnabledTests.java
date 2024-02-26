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
package org.eclipse.sirius.web.application.configurationproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test the environment created when everything is enabled.
 *
 * @author sbegaudeau
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=*" })
public class EverythingEnabledTests extends AbstractIntegrationTests {
    @Autowired
    private List<IProjectTemplateProvider> projectTemplateProviders;

    @Test
    @DisplayName("Given all Sirius Web features enabled, when project template providers are requested, then studio support is available")
    public void givenAllSiriusWebFeaturesEnabledWhenProjectTemplateProvidersAreRequestedThenStudioSupportIsAvailable() {
        var hasStudioProjectTemplateProvider = this.projectTemplateProviders.stream().anyMatch(StudioProjectTemplateProvider.class::isInstance);
        assertThat(hasStudioProjectTemplateProvider).isTrue();
    }
}

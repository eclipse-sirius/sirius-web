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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test the environment created when the support for studio is enabled.
 *
 * @author sbegaudeau
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=*", "sirius.web.disabled=studio" })
public class StudioDisabledTests extends AbstractIntegrationTests {
    @Autowired(required = false)
    private List<IProjectTemplateProvider> projectTemplateProviders = new ArrayList<>();

    @Test
    @DisplayName("Given Sirius Web studio features disabled, when project template providers are requested, then studio support is not available")
    public void givenSiriusWebStudioFeaturesDisabledWhenProjectTemplateProvidersAreRequestedThenStudioSupportIsNotAvailable() {
        var hasStudioProjectTemplateProvider = this.projectTemplateProviders.stream().anyMatch(StudioProjectTemplateProvider.class::isInstance);
        assertThat(hasStudioProjectTemplateProvider).isFalse();
    }
}

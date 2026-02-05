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
package org.eclipse.sirius.web.e2e.tests.details;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.web.papaya.views.details.NamedElementWidgetsProvider;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to contribute an additional page in the details view for e2e tests.
 *
 * @author gdaniel
 */
@Profile("test")
@Service
public class PapayaProjectAdditionalPageDescriptionProvider implements IPageDescriptionProvider {

    private final NamedElementWidgetsProvider namedElementWidgetsProvider;

    public PapayaProjectAdditionalPageDescriptionProvider(NamedElementWidgetsProvider namedElementWidgetsProvider) {
        this.namedElementWidgetsProvider = Objects.requireNonNull(namedElementWidgetsProvider);
    }

    @Override
    public PageDescription getPageDescription(IColorProvider colorProvider) {
        var testGroupDescription = this.getTestGroupDescription(colorProvider);

        return new FormBuilders().newPageDescription()
                .name("TestPage")
                .domainType("papaya:Project")
                .labelExpression("TestPage")
                .groups(testGroupDescription)
                .build();
    }

    private GroupDescription getTestGroupDescription(IColorProvider colorProvider) {
        var testGroupDescription = new FormBuilders().newGroupDescription()
                .name("Test Properties")
                .labelExpression("Test Properties")
                .semanticCandidatesExpression("aql:self")
                .displayMode(GroupDisplayMode.LIST)
                .build();
        testGroupDescription.getChildren().addAll(this.namedElementWidgetsProvider.getWidgets(colorProvider));
        return testGroupDescription;
    }
}

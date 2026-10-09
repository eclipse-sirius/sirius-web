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

import java.util.Comparator;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to change pages order in the details view for e2e tests.
 *
 * @author frouene
 */
@Profile("e2e")
@Service
public class PapayaProjectPageSorter implements IFormPostProcessor {

    private final IObjectSearchService searchService;

    public PapayaProjectPageSorter(IObjectSearchService searchService) {
        this.searchService = Objects.requireNonNull(searchService);
    }

    @Override
    public Form postProcess(Form form, VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class);
        if (optionalEditingContext.isPresent()) {
            var optionalProject = this.searchService.getObject(optionalEditingContext.get(), form.getTargetObjectId())
                    .filter(Project.class::isInstance)
                    .map(Project.class::cast);
            if (optionalProject.isPresent()) {
                return Form.newForm(form)
                        .pages(form.getPages().stream().sorted(Comparator.comparing(Page::getLabel)).toList())
                        .build();
            }
        }
        return form;
    }
}

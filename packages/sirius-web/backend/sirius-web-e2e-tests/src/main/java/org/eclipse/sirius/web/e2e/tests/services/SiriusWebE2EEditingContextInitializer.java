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

package org.eclipse.sirius.web.e2e.tests.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.e2e.tests.services.api.IE2EViewProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to initialize the editing context with representation description for e2e tests.
 *
 * @author gcoutable
 */
@Profile("test")
@Service
public class SiriusWebE2EEditingContextInitializer implements IEditingContextProcessor {

    private final List<IE2EViewProvider> e2EStudioProviders;

    public SiriusWebE2EEditingContextInitializer(List<IE2EViewProvider> e2EStudioProviders) {
        this.e2EStudioProviders = Objects.requireNonNull(e2EStudioProviders);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext emfEditingContext) {

            var editingContextViews = emfEditingContext.getViews();
            this.e2EStudioProviders.forEach(e2EStudioProvider -> {
                editingContextViews.addAll(e2EStudioProvider.createViews());
            });
        }
    }
}

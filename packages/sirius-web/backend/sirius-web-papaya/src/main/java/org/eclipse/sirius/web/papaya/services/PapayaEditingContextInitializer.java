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
package org.eclipse.sirius.web.papaya.services;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.eclipse.sirius.web.papaya.services.api.IPapayaViewProvider;
import org.springframework.stereotype.Service;

/**
 * Used to initialize the editing context of a papaya project.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaEditingContextInitializer implements IEditingContextProcessor {

    private final IPapayaViewProvider papayaViewProvider;

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    public PapayaEditingContextInitializer(IPapayaViewProvider papayaViewProvider, IPapayaCapableEditingContextPredicate papayaEditingContextPredicate) {
        this.papayaViewProvider = Objects.requireNonNull(papayaViewProvider);
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaEditingContextPredicate);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (this.papayaCapableEditingContextPredicate.test(editingContext.getId()) && editingContext instanceof EditingContext emfEditingContext) {
            var packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            packageRegistry.put(PapayaPackage.eNS_URI, PapayaPackage.eINSTANCE);

            emfEditingContext.getViews().add(this.papayaViewProvider.create());
        }
    }
}

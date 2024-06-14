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
package org.eclipse.sirius.web.services;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.papaya.services.PapayaViewProvider;
import org.springframework.stereotype.Service;

/**
 * Used to inject the view from papaya into an editing context.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaViewInjector implements Function<IEditingContext, Object> {

    private final PapayaViewProvider papayaViewProvider;

    public PapayaViewInjector(PapayaViewProvider papayaViewProvider) {
        this.papayaViewProvider = Objects.requireNonNull(papayaViewProvider);
    }

    @Override
    public Object apply(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var view = this.papayaViewProvider.create();
            emfEditingContext.getDomain().getResourceSet().getResources().add(view.eResource());

            return true;
        }
        return false;
    }
}

/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.services.editingcontext;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorInitializationHook;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IEditingContextEventProcessorInitializationHook} that lists the EPackages used by a project.
 *
 * @author arichard
 */
@Service
public class LoggingEditingContextEventProcessorInitializationHook implements IEditingContextEventProcessorInitializationHook {

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            List<String> messages = new ArrayList<>();
            emfEditingContext.getDomain().getResourceSet().getPackageRegistry().values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .forEach(ePackage -> messages.add("EPackage " + ePackage.getNsURI()));

            emfEditingContext.getDomain().getResourceSet().eAdapters().add(new LoggingAdapter(messages));
        }
    }
}

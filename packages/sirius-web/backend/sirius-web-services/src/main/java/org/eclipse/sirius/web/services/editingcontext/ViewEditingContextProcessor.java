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
package org.eclipse.sirius.web.services.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.springframework.stereotype.Service;

/**
 * Editing context processor allowing to convert all views.
 *
 * @author arichard
 */
@Service
public class ViewEditingContextProcessor implements IEditingContextProcessor {

    private final IViewLoader viewLoader;

    private final IViewConverter viewConverter;

    public ViewEditingContextProcessor(IViewLoader viewLoader, IViewConverter viewConverter) {
        this.viewLoader = viewLoader;
        this.viewConverter = viewConverter;

    }
    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().addAll(this.viewLoader.load());
        }
    }

    @Override
    public void postProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            List<EPackage> accessibleEPackages = this.getAccessibleEPackages(siriusWebEditingContext.getDomain());
            this.viewConverter.convert(siriusWebEditingContext.getViews(), accessibleEPackages).stream()
            .filter(Objects::nonNull)
            .forEach(representationDescription -> siriusWebEditingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
        }
    }

    private List<EPackage> getAccessibleEPackages(EditingDomain editingDomain) {
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();

        return packageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .toList();
    }
}

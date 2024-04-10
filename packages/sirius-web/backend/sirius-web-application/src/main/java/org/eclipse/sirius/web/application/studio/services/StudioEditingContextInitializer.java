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
package org.eclipse.sirius.web.application.studio.services;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.adapters.DeckColorAdapter;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.adapters.DiagramColorAdapter;
import org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.adapters.FormColorAdapter;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.application.studio.services.api.IStudioColorPalettesLoader;
import org.springframework.stereotype.Service;

/**
 * Used to provide the studio related features for the relevant editing context.
 *
 * @author sbegaudeau
 */
@Service
public class StudioEditingContextInitializer implements IEditingContextProcessor {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final IStudioColorPalettesLoader studioColorPalettesLoader;

    public StudioEditingContextInitializer(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate, IStudioColorPalettesLoader studioColorPalettesLoader) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
        this.studioColorPalettesLoader = Objects.requireNonNull(studioColorPalettesLoader);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        var isStudio = this.studioCapableEditingContextPredicate.test(editingContext);
        if (isStudio && editingContext instanceof IEMFEditingContext emfEditingContext) {
            var packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            packageRegistry.put(DomainPackage.eNS_URI, DomainPackage.eINSTANCE);
            packageRegistry.put(ViewPackage.eNS_URI, ViewPackage.eINSTANCE);
            packageRegistry.put(DeckPackage.eNS_URI, DeckPackage.eINSTANCE);
            packageRegistry.put(DiagramPackage.eNS_URI, DiagramPackage.eINSTANCE);
            packageRegistry.put(CustomnodesPackage.eNS_URI, CustomnodesPackage.eINSTANCE);
            packageRegistry.put(FormPackage.eNS_URI, FormPackage.eINSTANCE);
            packageRegistry.put(GanttPackage.eNS_URI, GanttPackage.eINSTANCE);

            var resourceSet = emfEditingContext.getDomain().getResourceSet();
            this.studioColorPalettesLoader.loadStudioColorPalettes(resourceSet).ifPresent(view -> {
                resourceSet.eAdapters().add(new DiagramColorAdapter(view));
                resourceSet.eAdapters().add(new FormColorAdapter(view));
                resourceSet.eAdapters().add(new DeckColorAdapter(view));
            });
        }
    }

}

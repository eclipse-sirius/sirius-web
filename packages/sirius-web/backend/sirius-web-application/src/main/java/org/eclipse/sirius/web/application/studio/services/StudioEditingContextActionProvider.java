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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionProvider;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to provide the editing context actions for studios.
 *
 * @author sbegaudeau
 */
@Service
public class StudioEditingContextActionProvider implements IEditingContextActionProvider {

    public static final String EMPTY_DOMAIN_ID = "empty_domain";

    public static final String EMPTY_VIEW_ID = "empty_view";

    public static final String EMPTY_ACTION_ID = "empty";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public StudioEditingContextActionProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public List<EditingContextAction> getEditingContextAction(IEditingContext editingContext) {
        var actions = new ArrayList<EditingContextAction>();

        var isStudio = this.studioCapableEditingContextPredicate.test(editingContext);
        if (isStudio && editingContext instanceof IEMFEditingContext emfEditingContext) {
            var nsURIs = emfEditingContext.getDomain().getResourceSet().getPackageRegistry().values()
                    .stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .map(EPackage::getNsURI)
                    .toList();

            var containsDomain = nsURIs.contains(DomainPackage.eNS_URI);
            var containsView = nsURIs.contains(ViewPackage.eNS_URI);

            if (containsDomain) {
                actions.add(new EditingContextAction(EMPTY_DOMAIN_ID, "Domain"));
            }
            if (containsView) {
                actions.add(new EditingContextAction(EMPTY_VIEW_ID, "View"));
            }
            actions.add(new EditingContextAction(EMPTY_ACTION_ID, "Others..."));
        }

        return actions;
    }
}

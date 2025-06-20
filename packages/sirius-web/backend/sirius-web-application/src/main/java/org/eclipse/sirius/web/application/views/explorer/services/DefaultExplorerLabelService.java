/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Objects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.services.api.IDefaultLabelFeatureProvider;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerLabelService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Used to provide the default behavior of the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultExplorerLabelService implements IDefaultExplorerLabelService {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IDefaultLabelFeatureProvider defaultLabelFeatureProvider;

    public DefaultExplorerLabelService(IReadOnlyObjectPredicate readOnlyObjectPredicate, IDefaultLabelFeatureProvider defaultLabelFeatureProvider) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.defaultLabelFeatureProvider = Objects.requireNonNull(defaultLabelFeatureProvider);
    }

    @Override
    public boolean isEditable(Object self) {
        boolean editable = false;
        if (!this.readOnlyObjectPredicate.test(self)) {
            if (self instanceof RepresentationMetadata) {
                editable = true;
            } else if (self instanceof Resource) {
                editable = true;
            } else if (self instanceof EObject eObject) {
                editable = this.defaultLabelFeatureProvider.getDefaultLabelEAttribute(eObject)
                        .filter(EAttribute::isChangeable)
                        .isPresent();
            }
        }
        return editable;
    }

    @Override
    public void editLabel(Object self, String newValue) {
        if (!this.readOnlyObjectPredicate.test(self) && self instanceof EObject eObject) {
            this.defaultLabelFeatureProvider.getDefaultLabelEAttribute(eObject)
                    .filter(EAttribute::isChangeable)
                    .ifPresent(eAttribute -> eObject.eSet(eAttribute, newValue));
        }
    }
}

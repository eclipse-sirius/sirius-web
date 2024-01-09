/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.web.services.editingcontext.api.IDynamicRepresentationDescriptionService;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to discover diagram descriptions dynamically from the existing user-defined documents.
 *
 * @author pcdavid
 */
@Service
public class DynamicRepresentationDescriptionService implements IDynamicRepresentationDescriptionService {

    private final IViewLoader viewLoader;

    private final IViewConverter viewConverter;

    private final boolean isStudioDefinitionEnabled;

    public DynamicRepresentationDescriptionService(IViewLoader viewLoader, IViewConverter viewConverter, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean isStudioDefinitionEnabled) {
        this.viewLoader = Objects.requireNonNull(viewLoader);
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.isStudioDefinitionEnabled = isStudioDefinitionEnabled;
    }

    @Override
    public List<IRepresentationDescription> findDynamicRepresentationDescriptions(String editingContextId, EditingDomain editingDomain) {
        List<IRepresentationDescription> dynamicRepresentationDescriptions = new ArrayList<>();
        if (this.isStudioDefinitionEnabled) {
            List<View> views = this.viewLoader.load();

            List<EPackage> accessibleEPackages = this.getAccessibleEPackages(editingDomain);
            this.viewConverter.convert(views, accessibleEPackages).stream()
                    .filter(Objects::nonNull)
                    .forEach(dynamicRepresentationDescriptions::add);
        }
        return dynamicRepresentationDescriptions;
    }

    private List<EPackage> getAccessibleEPackages(EditingDomain editingDomain) {
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();

        return packageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .toList();
    }
}

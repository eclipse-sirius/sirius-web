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
package org.eclipse.sirius.web.services.tree;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.emf.services.DefaultLabelService;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.springframework.stereotype.Service;

/**
 * Implementation of ILabelServiceDelegate to test styled tree items.
 *
 * @author mcharfadi
 */
@Service
public class EcoreSampleStyledLabelService extends DefaultLabelService implements ILabelServiceDelegate {

    private static final String NAME = "name";

    public EcoreSampleStyledLabelService(List<IRepresentationMetadataProvider> representationMetadataProviders, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, LabelFeatureProviderRegistry labelFeatureProviderRegistry, ComposedAdapterFactory composedAdapterFactory, List<IRepresentationImageProvider> representationImageProviders) {
        super(representationMetadataProviders, labelFeatureProviderRegistry, composedAdapterFactory, representationImageProviders);
    }

    @Override
    public boolean canHandle(Object object) {
        if (object instanceof EObject eObject && eObject.eClass().getEStructuralFeature(NAME) != null && eObject.eGet(eObject.eClass().getEStructuralFeature(NAME)) != null) {
            return eObject.eGet(eObject.eClass().getEStructuralFeature(NAME)).equals("Sample");
        }
        return false;
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        if (object instanceof EObject eObject) {
            String label = (String) eObject.eGet(eObject.eClass().getEStructuralFeature(NAME));

            var style = StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                    .backgroundColor("red")
                    .struckOut(true)
                    .build();
            var fragment = new StyledStringFragment(label, style);
            return new StyledString(List.of(fragment));
        }
        return StyledString.of("");
    }
}

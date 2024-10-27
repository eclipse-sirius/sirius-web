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
package org.eclipse.sirius.web.papaya.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.DefaultLabelService;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.emf.services.api.IStyledStringConverter;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.provider.PapayaItemProviderAdapterFactory;
import org.springframework.stereotype.Service;

/**
 * Used to customize the default label for papaya objects.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaLabelProvider extends DefaultLabelService implements ILabelServiceDelegate {

    private final IStyledStringConverter styledStringConverter;

    public PapayaLabelProvider(List<IRepresentationMetadataProvider> representationMetadataProviders, LabelFeatureProviderRegistry labelFeatureProviderRegistry, ComposedAdapterFactory composedAdapterFactory, List<IRepresentationImageProvider> representationImageProviders, IStyledStringConverter styledStringConverter) {
        super(representationMetadataProviders, labelFeatureProviderRegistry, composedAdapterFactory, representationImageProviders);
        this.styledStringConverter = Objects.requireNonNull(styledStringConverter);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof EObject eObject
                && eObject.eClass().getEPackage().getNsURI().equals(PapayaPackage.eNS_URI);
    }

    @Override
    public String getLabel(Object object) {
        if (object instanceof EObject eObject) {
            var adapter = new PapayaItemProviderAdapterFactory().adapt(eObject, IItemLabelProvider.class);
            if (adapter instanceof IItemLabelProvider itemLabelProvider) {
                return itemLabelProvider.getText(eObject);
            }
        }
        return super.getLabel(object);
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        if (object instanceof EObject eObject) {
            var adapter = new PapayaItemProviderAdapterFactory().adapt(eObject, IItemStyledLabelProvider.class);
            if (adapter instanceof IItemStyledLabelProvider itemStyledLabelProvider) {
                var rawStyledString = itemStyledLabelProvider.getStyledText(eObject);
                if (rawStyledString instanceof org.eclipse.emf.edit.provider.StyledString emfStyledString) {
                    return this.styledStringConverter.convert(emfStyledString);
                }
            }

        }
        return super.getStyledLabel(object);
    }
}

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

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.api.IDefaultEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelServiceDelegate;
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
public class PapayaLabelProvider implements IEMFLabelServiceDelegate {

    private final IStyledStringConverter styledStringConverter;

    private final IDefaultEMFLabelService defaultEMFLabelService;

    public PapayaLabelProvider(IStyledStringConverter styledStringConverter, IDefaultEMFLabelService defaultEMFLabelService) {
        this.styledStringConverter = Objects.requireNonNull(styledStringConverter);
        this.defaultEMFLabelService = Objects.requireNonNull(defaultEMFLabelService);
    }

    @Override
    public boolean canHandle(EObject self) {
        return self.eClass().getEPackage().getNsURI().equals(PapayaPackage.eNS_URI);
    }

    @Override
    public StyledString getStyledLabel(EObject self) {
        var adapter = new PapayaItemProviderAdapterFactory().adapt(self, IItemStyledLabelProvider.class);
        if (adapter instanceof IItemStyledLabelProvider itemStyledLabelProvider) {
            var rawStyledString = itemStyledLabelProvider.getStyledText(self);
            if (rawStyledString instanceof org.eclipse.emf.edit.provider.StyledString emfStyledString) {
                return this.styledStringConverter.convert(emfStyledString);
            }
        }
        return StyledString.of("");
    }

    @Override
    public List<String> getImagePaths(EObject self) {
        return this.defaultEMFLabelService.getImagePaths(self);
    }
}

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
package org.eclipse.sirius.web.papaya.representations.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.papaya.provider.PapayaItemProviderAdapterFactory;

/**
 * Java services common to all representations.
 *
 * @author sbegaudeau
 */
public class PapayaRepresentationServices {

    private final IObjectSearchService objectSearchService;

    public PapayaRepresentationServices(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    public String label(EObject self) {
        return Optional.ofNullable(new PapayaItemProviderAdapterFactory().adapt(self, IItemLabelProvider.class))
                .filter(IItemLabelProvider.class::isInstance)
                .map(IItemLabelProvider.class::cast)
                .map(labelProvider -> labelProvider.getText(self))
                .orElse("");
    }

    public List<EObject> getSynchronizedObjects(IEditingContext editingContext, List<String> semanticElementsIds) {
        return semanticElementsIds.stream()
                .map(id -> this.objectSearchService.getObject(editingContext, id))
                .flatMap(Optional::stream)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }
}

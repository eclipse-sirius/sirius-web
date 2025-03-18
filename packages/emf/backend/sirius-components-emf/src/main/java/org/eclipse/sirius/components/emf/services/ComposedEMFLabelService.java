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
package org.eclipse.sirius.components.emf.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.api.IDefaultEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to manipulate the label of EMF objects.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedEMFLabelService implements IEMFLabelService {

    private final IDefaultEMFLabelService defaultEMFLabelService;

    private final List<IEMFLabelServiceDelegate> emfLabelServiceDelegates;

    public ComposedEMFLabelService(IDefaultEMFLabelService defaultEMFLabelService, List<IEMFLabelServiceDelegate> emfLabelServiceDelegates) {
        this.defaultEMFLabelService = Objects.requireNonNull(defaultEMFLabelService);
        this.emfLabelServiceDelegates = Objects.requireNonNull(emfLabelServiceDelegates);
    }

    @Override
    public StyledString getStyledLabel(EObject self) {
        return this.emfLabelServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(self))
                .findFirst().map(delegate -> delegate.getStyledLabel(self))
                .orElseGet(() -> this.defaultEMFLabelService.getStyledLabel(self));
    }

    @Override
    public List<String> getImagePaths(EObject self) {
        return this.emfLabelServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(self))
                .findFirst().map(delegate -> delegate.getImagePaths(self))
                .orElseGet(() -> this.defaultEMFLabelService.getImagePaths(self));
    }
}

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
package org.eclipse.sirius.components.core.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IDefaultLabelService;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ILabelService} which delegates to {@link ILabelServiceDelegate} or fallback to
 * {@link IDefaultObjectSearchService}.
 *
 * @author mcharfadi
 */
@Service
public class ComposedLabelService implements ILabelService {

    private final List<ILabelServiceDelegate> labelServiceDelegate;

    private final IDefaultLabelService defaultLabelService;

    public ComposedLabelService(List<ILabelServiceDelegate> labelServiceDelegate, IDefaultLabelService defaultLabelService) {
        this.labelServiceDelegate = Objects.requireNonNull(labelServiceDelegate);
        this.defaultLabelService = Objects.requireNonNull(defaultLabelService);
    }
    @Override
    public String getLabel(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getLabel(object);
        }
        return this.defaultLabelService.getLabel(object);
    }

    @Override
    public String getFullLabel(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getFullLabel(object);
        }
        return this.defaultLabelService.getFullLabel(object);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getLabelField(object);
        }
        return this.defaultLabelService.getLabelField(object);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        return optionalDelegate.map(iLabelServiceDelegate -> iLabelServiceDelegate.isLabelEditable(object))
                .orElseGet(() -> this.defaultLabelService.isLabelEditable(object));
    }

    @Override
    public List<String> getImagePath(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getImagePath(object);
        }
        return this.defaultLabelService.getImagePath(object);
    }
}

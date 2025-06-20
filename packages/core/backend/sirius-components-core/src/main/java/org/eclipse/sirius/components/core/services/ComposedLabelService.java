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
package org.eclipse.sirius.components.core.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IDefaultLabelService;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
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
    public StyledString getStyledLabel(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getStyledLabel(object);
        }
        return this.defaultLabelService.getStyledLabel(object);
    }

    @Override
    public List<String> getImagePaths(Object object) {
        var optionalDelegate = this.labelServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getImagePaths(object);
        }
        return this.defaultLabelService.getImagePaths(object);
    }
}

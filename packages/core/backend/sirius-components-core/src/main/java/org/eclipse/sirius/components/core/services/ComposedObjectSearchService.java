/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IObjectSearchServiceDelegate;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IObjectService} which delegates to {@link IObjectSearchServiceDelegate} or fallback to
 * {@link IDefaultObjectSearchService}.
 *
 * @author arichard
 */
@Service
public class ComposedObjectSearchService implements IObjectSearchService {

    private final List<IObjectSearchServiceDelegate> objectSearchServiceDelegates;

    private final IDefaultObjectSearchService defaultObjectSearchService;

    public ComposedObjectSearchService(List<IObjectSearchServiceDelegate> objectSearchServiceDelegates, IDefaultObjectSearchService defaultObjectSearchService) {
        this.objectSearchServiceDelegates = Objects.requireNonNull(objectSearchServiceDelegates);
        this.defaultObjectSearchService = Objects.requireNonNull(defaultObjectSearchService);
    }
    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        var optionalDelegate = this.objectSearchServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext, objectId))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getObject(editingContext, objectId);
        }
        return this.defaultObjectSearchService.getObject(editingContext, objectId);
    }

}

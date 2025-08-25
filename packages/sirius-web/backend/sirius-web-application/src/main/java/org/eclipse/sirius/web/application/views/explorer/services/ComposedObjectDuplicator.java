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

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.web.application.views.explorer.services.api.DuplicationSettings;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultObjectDuplicator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicatorDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to duplicate objects.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedObjectDuplicator implements IObjectDuplicator {

    private final IDefaultObjectDuplicator defaultObjectDuplicator;

    private final List<IObjectDuplicatorDelegate> objectDuplicatorDelegates;


    public ComposedObjectDuplicator(IDefaultObjectDuplicator defaultObjectDuplicator, List<IObjectDuplicatorDelegate> objectDuplicatorDelegates) {
        this.defaultObjectDuplicator = Objects.requireNonNull(defaultObjectDuplicator);
        this.objectDuplicatorDelegates = Objects.requireNonNull(objectDuplicatorDelegates);
    }

    @Override
    public IStatus duplicateObject(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings) {
        return this.objectDuplicatorDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext, objectToDuplicate, containerEObject, containmentFeature, settings))
                .findFirst()
                .map(delegate -> delegate.duplicateObject(editingContext, objectToDuplicate, containerEObject, containmentFeature, settings))
                .orElseGet(() -> this.defaultObjectDuplicator.duplicateObject(editingContext, objectToDuplicate, containerEObject, containmentFeature, settings));
    }
}

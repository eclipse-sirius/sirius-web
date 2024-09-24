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
package org.eclipse.sirius.web.application.object.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.object.dto.Direction;
import org.eclipse.sirius.web.application.object.services.api.IDefaultObjectRestService;
import org.eclipse.sirius.web.application.object.services.api.IObjectRestService;
import org.eclipse.sirius.web.application.object.services.api.IObjectRestServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IObjectRestService} which delegates to {@link IObjectRestServiceDelegate} or fallback to
 * {@link IDefaultObjectRestService}.
 *
 * @author arichard
 */
@Service
public class ComposedObjectRestService implements IObjectRestService {

    private final List<IObjectRestServiceDelegate> objectRestServiceDelegate;

    private final IDefaultObjectRestService defaultObjectRestService;

    public ComposedObjectRestService(List<IObjectRestServiceDelegate> objectRestServiceDelegate, IDefaultObjectRestService defaultObjectRestService) {
        this.objectRestServiceDelegate = Objects.requireNonNull(objectRestServiceDelegate);
        this.defaultObjectRestService = Objects.requireNonNull(defaultObjectRestService);
    }

    @Override
    public List<Object> getElements(IEditingContext editingContext) {
        var optionalDelegate = this.objectRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getElements(editingContext);
        }
        return this.defaultObjectRestService.getElements(editingContext);
    }

    @Override
    public Optional<Object> getElementById(IEditingContext editingContext, String elementId) {
        var optionalDelegate = this.objectRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getElementById(editingContext, elementId);
        }
        return this.defaultObjectRestService.getElementById(editingContext, elementId);
    }

    @Override
    public List<Object> getRelationshipsByRelatedElement(IEditingContext editingContext, String elementId, Direction direction) {
        var optionalDelegate = this.objectRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getRelationshipsByRelatedElement(editingContext, elementId, direction);
        }
        return this.defaultObjectRestService.getRelationshipsByRelatedElement(editingContext, elementId, direction);
    }

    @Override
    public List<Object> getRootElements(IEditingContext editingContext) {
        var optionalDelegate = this.objectRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getRootElements(editingContext);
        }
        return this.defaultObjectRestService.getRootElements(editingContext);
    }
}

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

import org.eclipse.sirius.components.core.api.IDefaultObjectService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IObjectServiceDelegate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IObjectService} which delegates to {@link IObjectServiceDelegate} or fallback to
 * {@link IDefaultObjectService}.
 *
 * @author arichard
 */
@Service
public class ComposedObjectService implements IObjectService {

    private final List<IObjectServiceDelegate> objectServiceDelegates;

    private final IDefaultObjectService defaultObjectService;

    public ComposedObjectService(List<IObjectServiceDelegate> objectServiceDelegates, IDefaultObjectService defaultObjectService) {
        this.objectServiceDelegates = Objects.requireNonNull(objectServiceDelegates);
        this.defaultObjectService = Objects.requireNonNull(defaultObjectService);
    }

    @Override
    public String getId(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getId(object);
        }
        return this.defaultObjectService.getId(object);
    }

    @Override
    public String getLabel(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getLabel(object);
        }
        return this.defaultObjectService.getLabel(object);
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getStyledLabel(object);
        }
        return this.defaultObjectService.getStyledLabel(object);
    }

    @Override
    public String getKind(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getKind(object);
        }
        return this.defaultObjectService.getKind(object);
    }

    @Override
    public String getFullLabel(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getFullLabel(object);
        }
        return this.defaultObjectService.getFullLabel(object);
    }

    @Override
    public List<String> getImagePath(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getImagePath(object);
        }
        return this.defaultObjectService.getImagePath(object);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getObject(editingContext, objectId);
        }
        return this.defaultObjectService.getObject(editingContext, objectId);
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getContents(editingContext, objectId);
        }
        return this.defaultObjectService.getContents(editingContext, objectId);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getLabelField(object);
        }
        return this.defaultObjectService.getLabelField(object);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        var optionalDelegate = this.objectServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().isLabelEditable(object);
        }
        return this.defaultObjectService.isLabelEditable(object);
    }
}

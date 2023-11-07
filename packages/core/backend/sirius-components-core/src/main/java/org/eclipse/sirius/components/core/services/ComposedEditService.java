/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IDefaultEditService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditServiceDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IEditService} which delegates to {@link IEditServiceDelegate} or fallback to
 * {@link IDefaultEditService}.
 *
 * @author arichard
 */
@Service
public class ComposedEditService implements IEditService {

    private final List<IEditServiceDelegate> editServiceDelegates;

    private final IDefaultEditService defaultEditService;

    public ComposedEditService(List<IEditServiceDelegate> editServiceDelegates, IDefaultEditService defaultEditService) {
        this.editServiceDelegates = Objects.requireNonNull(editServiceDelegates);
        this.defaultEditService = Objects.requireNonNull(defaultEditService);
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, boolean suggested, String referenceKind) {
        var optionalDelegate = this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getRootCreationDescriptions(editingContext, domainId, suggested, referenceKind);
        }
        return this.defaultEditService.getRootCreationDescriptions(editingContext, domainId, suggested, referenceKind);
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind) {
        var optionalDelegate = this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getChildCreationDescriptions(editingContext, kind, referenceKind);
        }
        return this.defaultEditService.getChildCreationDescriptions(editingContext, kind, referenceKind);
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
        var optionalDelegate = this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext) && delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().createChild(editingContext, object, childCreationDescriptionId);
        }
        return this.defaultEditService.createChild(editingContext, object, childCreationDescriptionId);
    }

    @Override
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
        var optionalDelegate = this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId);
        }
        return this.defaultEditService.createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId);
    }

    @Override
    public void delete(Object object) {
        this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst()
                .ifPresentOrElse(delegate -> delegate.delete(object), () -> this.defaultEditService.delete(object));
    }

    @Override
    public void editLabel(Object object, String labelField, String newValue) {
        this.editServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst()
                .ifPresentOrElse(delegate -> delegate.editLabel(object, labelField, newValue), () -> this.defaultEditService.editLabel(object, labelField, newValue));
    }
}

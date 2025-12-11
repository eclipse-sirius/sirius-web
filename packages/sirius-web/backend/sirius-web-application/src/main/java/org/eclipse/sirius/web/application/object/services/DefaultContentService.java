/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.sirius.components.core.api.IDefaultContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IDefaultContentService}. 
 *
 * @author mcharfadi
 */
@Service
public class DefaultContentService implements IDefaultContentService {

    private final List<Descriptor> composedAdapterFactoryDescriptors;

    public DefaultContentService(List<Descriptor> composedAdapterFactoryDescriptors) {
        this.composedAdapterFactoryDescriptors = Objects.requireNonNull(composedAdapterFactoryDescriptors);
    }

    @Override
    public List<Object> getContents(Object object) {
        List<Object> contents = new ArrayList<>();
        if (object instanceof EObject eObject) {
            List<AdapterFactory> adapterFactories = this.composedAdapterFactoryDescriptors.stream()
                    .map(Descriptor::createAdapterFactory)
                    .toList();
            var composedAdapterFactory = new ComposedAdapterFactory(adapterFactories);
            Adapter adapter = composedAdapterFactory.adapt(eObject, IEditingDomainItemProvider.class);
            if (adapter instanceof IEditingDomainItemProvider contentProvider) {
                contents.addAll(contentProvider.getChildren(eObject));
            } else {
                contents.addAll(eObject.eContents());
            }
            composedAdapterFactory.dispose();
        }
        else if (object instanceof Resource resource) {
            // The object may be a document
            contents.addAll(resource.getContents());
        } else if (object instanceof IEditingContext editingContext) {
            Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(IEMFEditingContext::getDomain)
                    .map(EditingDomain::getResourceSet)
                    .map(ResourceSet::getResources)
                    .ifPresent(contents::addAll);
        }
        return contents;
    }
}

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
package org.eclipse.sirius.components.emf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.sirius.components.core.api.IDefaultContentService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IDefaultContentService}.
 *
 * @author mcharfadi
 */
@Service
public class DefaultContentService implements IDefaultContentService {

    private final ComposedAdapterFactory composedAdapterFactory;

    public DefaultContentService(ComposedAdapterFactory composedAdapterFactory) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }
    @Override
    public List<Object> getContents(Object object) {
        List<Object> contents = new ArrayList<>();
        if (object instanceof EObject eObject) {
            Adapter adapter = this.composedAdapterFactory.adapt(eObject, IEditingDomainItemProvider.class);
            if (adapter instanceof IEditingDomainItemProvider contentProvider) {
                contents.addAll(contentProvider.getChildren(eObject));
            } else {
                contents.addAll(eObject.eContents());
            }
        } else if (object instanceof Resource resource) {
            contents.addAll(resource.getContents());
        }
        return contents;
    }
    @Override
    public Object getParent(Object object) {
        Object parent = null;
        if (object instanceof EObject eObject) {
            Adapter adapter = this.composedAdapterFactory.adapt(eObject, IEditingDomainItemProvider.class);
            if (adapter instanceof IEditingDomainItemProvider contentProvider) {
                parent = contentProvider.getParent(eObject);
            } else {
                parent = eObject.eContainer();
            }
        }
        return parent;
    }
}

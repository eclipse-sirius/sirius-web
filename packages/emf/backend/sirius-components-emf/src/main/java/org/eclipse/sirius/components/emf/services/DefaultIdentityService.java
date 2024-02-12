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

import org.springframework.stereotype.Service;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IDefaultIdentityService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;

import java.util.Objects;

/**
 * Default implementation of {@link IDefaultIdentityService}.
 *
 * @author mcharfadi
 */
@Service
public class DefaultIdentityService implements IDefaultIdentityService {

    private static final String ID_SEPARATOR = "#";

    private final IEMFKindService emfKindService;

    public DefaultIdentityService(IEMFKindService emfKindService) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }
    @Override
    public String getId(Object object) {
        String id = null;
        if (object instanceof EObject eObject) {

            id = this.getIdFromIDAdapter(eObject);
            if (id == null) {
                id = this.getIdFromURIFragment(eObject);
            }
            if (id == null && eObject.eIsProxy()) {
                id = ((InternalEObject) eObject).eProxyURI().toString();
            }
        }
        return id;
    }

    @Override
    public String getKind(Object object) {
        if (object instanceof EObject eObject) {
            return this.emfKindService.getKind(eObject.eClass());
        }
        return "";
    }

    private String getIdFromIDAdapter(EObject eObject) {
        return eObject.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst()
                .map(IDAdapter::getId)
                .map(Object::toString)
                .orElse(null);
    }

    private String getIdFromURIFragment(EObject eObject) {
        Resource resource = eObject.eResource();
        String id = null;
        if (resource != null && resource.getURI() != null) {
            ResourceSet resourceSet = resource.getResourceSet();
            if (resourceSet != null && resourceSet.getResources().contains(resource)) {
                id = resource.getURI().lastSegment() + ID_SEPARATOR + resource.getURIFragment(eObject);
            } else {
                // In order to getObject method can retrieve the object from the id, we need to return the full URI for
                // resources that are in the PackageRegistry
                id = resource.getURI() + ID_SEPARATOR + resource.getURIFragment(eObject);
            }
        }
        return id;
    }
}

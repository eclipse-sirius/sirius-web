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
package org.eclipse.sirius.web.application.document.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.application.document.services.api.IProxyValidator;
import org.springframework.stereotype.Service;

/**
 * Used to detect proxies in a resource.
 *
 * @author sbegaudeau
 */
@Service
public class ProxyValidator implements IProxyValidator {
    @Override
    public boolean hasProxies(Resource resource) {
        Iterable<EObject> iterable = () -> EcoreUtil.getAllProperContents(resource, false);
        return StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(this::hasProxies);
    }

    private boolean hasProxies(EObject eObject) {
        return eObject.eClass().getEAllReferences().stream()
                .filter(eReference -> !eReference.isContainment() && eObject.eIsSet(eReference))
                .anyMatch(eReference -> this.hasProxies(eObject, eReference));
    }

    private boolean hasProxies(EObject eObject, EReference eReference) {
        boolean containsProxy = false;
        Object value = eObject.eGet(eReference);
        if (eReference.isMany()) {
            List<?> list = (List<?>) value;
            containsProxy = list.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .anyMatch(EObject::eIsProxy);
        } else if (value instanceof EObject eObjectValue) {
            containsProxy = eObjectValue.eIsProxy();
        }
        return containsProxy;
    }
}

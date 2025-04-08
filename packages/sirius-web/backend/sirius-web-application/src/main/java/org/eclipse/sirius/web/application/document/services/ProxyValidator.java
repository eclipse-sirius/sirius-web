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
package org.eclipse.sirius.web.application.document.services;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
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
        TreeIterator<EObject> iter = EcoreUtil.getAllProperContents(resource, false);
        while (iter.hasNext()) {
            EObject eObject = iter.next();
            for (var eReference : eObject.eClass().getEAllReferences()) {
                if (!eReference.isDerived() && eObject.eIsSet(eReference)) {
                    Object value = eObject.eGet(eReference);
                    if (this.hasProxies(eReference, value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasProxies(EReference eReference, Object value) {
        boolean containsProxy = false;
        if (eReference.isMany()) {
            for (var obj : (List<?>) value) {
                containsProxy = this.isProxyEObject(obj);
                if (containsProxy) {
                    break;
                }
            }
        } else {
            return this.isProxyEObject(value);
        }
        return containsProxy;
    }

    private boolean isProxyEObject(Object value) {
        return value instanceof EObject eObject && eObject.eIsProxy();
    }
}

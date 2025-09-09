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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesResourceFilter;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesService;
import org.springframework.stereotype.Service;

/**
 * Service in charge of rewriting proxies in an editing context using a map link old to new resource ids.
 *
 * @author Arthur Daussy
 */
@Service
public class RewriteProxiesService implements IRewriteProxiesService {

    private final List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilter;

    public RewriteProxiesService(List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilter) {
        this.rewriteProxiesResourceFilter = rewriteProxiesResourceFilter;
    }

    @Override
    public int rewriteProxies(IEMFEditingContext editingContext, Map<String, String> oldDocumentIdToNewDocumentId) {
        AdapterFactoryEditingDomain adapterFactoryEditingDomain = editingContext.getDomain();
        int totalRewrittenCount = 0;
        var resources = adapterFactoryEditingDomain.getResourceSet().getResources().stream()
                .filter(r -> this.rewriteProxiesResourceFilter.stream().allMatch(f -> f.shouldRewriteProxies(r)))
                .toList();
        for (Resource resource : resources) {
            totalRewrittenCount += this.rewriteProxyURIs(resource, oldDocumentIdToNewDocumentId);
        }
        return totalRewrittenCount;
    }

    private int rewriteProxyURIs(Resource resource, Map<String, String> oldDocumentIdToNewDocumentId) {
        AtomicInteger rewrittenCount = new AtomicInteger();
        resource.getAllContents().forEachRemaining(eObject -> {
            eObject.eCrossReferences().forEach(target -> {
                InternalEObject internalEObject = (InternalEObject) target;
                if (internalEObject != null && internalEObject.eIsProxy()) {
                    URI proxyURI = internalEObject.eProxyURI();
                    String oldDocumentId = proxyURI.path().substring(1);
                    String newDocumentId = oldDocumentIdToNewDocumentId.get(oldDocumentId);
                    if (newDocumentId != null) {
                        String prefix = IEMFEditingContext.RESOURCE_SCHEME + ":///";
                        URI newProxyURI = URI.createURI(proxyURI.toString().replace(prefix + oldDocumentId, prefix + newDocumentId));
                        internalEObject.eSetProxyURI(newProxyURI);
                        rewrittenCount.incrementAndGet();
                    }
                }
            });
        });
        return rewrittenCount.get();
    }
}

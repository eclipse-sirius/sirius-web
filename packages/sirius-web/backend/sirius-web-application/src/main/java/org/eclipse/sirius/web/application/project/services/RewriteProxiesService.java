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
import java.util.Objects;

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
 * @author adaussy
 */
@Service
public class RewriteProxiesService implements IRewriteProxiesService {
    private static final String FRAGMENT_SEPARATOR = "#";

    private final List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilters;

    public RewriteProxiesService(List<IRewriteProxiesResourceFilter> rewriteProxiesResourceFilters) {
        this.rewriteProxiesResourceFilters = Objects.requireNonNull(rewriteProxiesResourceFilters);
    }

    @Override
    public int rewriteProxies(IEMFEditingContext editingContext, Map<String, String> oldDocumentIdToNewDocumentId, Map<String, String> semanticElementsIdMappings) {
        AdapterFactoryEditingDomain adapterFactoryEditingDomain = editingContext.getDomain();
        int totalRewrittenCount = 0;
        var resources = adapterFactoryEditingDomain.getResourceSet().getResources().stream()
                .filter(resource -> this.rewriteProxiesResourceFilters.stream().allMatch(rewriteProxiesResourceFilter -> rewriteProxiesResourceFilter.shouldRewriteProxies(resource)))
                .toList();
        for (Resource resource : resources) {
            totalRewrittenCount += this.rewriteProxyURIs(resource, oldDocumentIdToNewDocumentId, semanticElementsIdMappings);
        }
        return totalRewrittenCount;
    }

    private int rewriteProxyURIs(Resource resource, Map<String, String> oldDocumentIdToNewDocumentId, Map<String, String> semanticElementsIdMappings) {
        int count = 0;

        var iterator = resource.getAllContents();
        while (iterator.hasNext()) {
            var eObject = iterator.next();

            for (var target: eObject.eCrossReferences()) {
                InternalEObject internalEObject = (InternalEObject) target;
                if (internalEObject != null && internalEObject.eIsProxy()) {
                    URI proxyURI = internalEObject.eProxyURI();
                    String oldDocumentId = proxyURI.path().substring(1);
                    String newDocumentId = oldDocumentIdToNewDocumentId.get(oldDocumentId);
                    String oldSemanticElementId = proxyURI.fragment();
                    String newSemanticElementId = semanticElementsIdMappings.getOrDefault(oldSemanticElementId, oldSemanticElementId);

                    if (newDocumentId != null) {
                        String prefix = IEMFEditingContext.RESOURCE_SCHEME + ":///";
                        URI newProxyURI = URI.createURI(proxyURI.toString()
                                .replace(prefix + oldDocumentId, prefix + newDocumentId)
                                .replace(FRAGMENT_SEPARATOR + oldSemanticElementId, FRAGMENT_SEPARATOR + newSemanticElementId));
                        internalEObject.eSetProxyURI(newProxyURI);
                        count++;
                    }
                }
            }
        }

        return count;
    }
}

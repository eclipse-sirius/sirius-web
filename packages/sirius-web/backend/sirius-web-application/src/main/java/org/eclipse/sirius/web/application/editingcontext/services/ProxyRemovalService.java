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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.UnresolvedProxyCrossReferencer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalService;
import org.springframework.stereotype.Service;

/**
 * Service to remove EMF proxies.
 *
 * @author gdaniel
 */
@Service
public class ProxyRemovalService implements IProxyRemovalService {

    private final List<IEditingContextPersistenceFilter> editingContextPersistenceFilters;

    public ProxyRemovalService(List<IEditingContextPersistenceFilter> editingContextPersistenceFilters) {
        this.editingContextPersistenceFilters = Objects.requireNonNull(editingContextPersistenceFilters);
    }

    /**
     * Removes proxies from persisted resources in the provided {@code editingContext}.
     *
     * @param editingContext
     *            the editing context
     * @return a {@link Map} containing the removed proxies from non-library resources in the provided
     *         {@code editingContext}
     */
    @Override
    public Map<EObject, Collection<Setting>> removeUnresolvedProxies(IEditingContext editingContext) {
        Map<EObject, Collection<Setting>> result = Map.of();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            ResourceSet resourceSet = emfEditingContext.getDomain().getResourceSet();
            List<Resource> nonLibraryResources = resourceSet.getResources().stream()
                    .filter(resource -> this.editingContextPersistenceFilters.stream().allMatch(editingContextPersistenceFilter -> editingContextPersistenceFilter.shouldPersist(resource)))
                    .toList();
            // Only look for proxies in Resources that are persisted in the project's semantic data
            result = UnresolvedProxyCrossReferencer.find(nonLibraryResources);
            result.forEach((proxyObject, settings) -> {
                for (Setting setting : settings) {
                    if (!setting.getEStructuralFeature().isDerived()) {
                        if (setting.getEStructuralFeature().isMany()) {
                            List<?> value = (List<?>) setting.get(false);
                            value.remove(proxyObject);
                        } else {
                            setting.unset();
                        }
                    }
                }
            });
        }
        return result;
    }

}

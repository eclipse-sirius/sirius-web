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
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IDefaultProxyRemovalService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Service to remove EMF proxies.
 *
 * @author gdaniel
 */
@Service
public class ComposedProxyRemovalService implements IProxyRemovalService {

    private final List<IProxyRemovalServiceDelegate> proxyRemovalServiceDelegates;

    private final IDefaultProxyRemovalService defaultProxyRemovalService;

    public ComposedProxyRemovalService(List<IProxyRemovalServiceDelegate> proxyRemovalServiceDelegates, IDefaultProxyRemovalService defaultProxyRemovalService) {
        this.proxyRemovalServiceDelegates = Objects.requireNonNull(proxyRemovalServiceDelegates);
        this.defaultProxyRemovalService = Objects.requireNonNull(defaultProxyRemovalService);
    }

    @Override
    public Map<EObject, Collection<EStructuralFeature.Setting>> removeUnresolvedProxies(IEditingContext editingContext) {
        Optional<IProxyRemovalServiceDelegate> optionalProxyRemovalServiceDelegate = this.proxyRemovalServiceDelegates.stream()
                .filter(proxyRemovalServiceDelegate -> proxyRemovalServiceDelegate.canHandle(editingContext))
                .findFirst();
        if (optionalProxyRemovalServiceDelegate.isPresent()) {
            return optionalProxyRemovalServiceDelegate.get().removeUnresolvedProxies(editingContext);
        } else {
            return this.defaultProxyRemovalService.removeUnresolvedProxies(editingContext);
        }
    }
}

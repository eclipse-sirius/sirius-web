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
package org.eclipse.sirius.web.application.editingcontext.services.api;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * The default implementation that removes EMF proxies.
 *
 * @author gdaniel
 */
public interface IDefaultProxyRemovalService {

    Map<EObject, Collection<EStructuralFeature.Setting>> removeUnresolvedProxies(IEditingContext editingContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gdaniel
     */
    class NoOp implements IDefaultProxyRemovalService {

        @Override
        public Map<EObject, Collection<EStructuralFeature.Setting>> removeUnresolvedProxies(IEditingContext editingContext) {
            return Map.of();
        }
    }
}

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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Map;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;

/**
 * Service in charge of rewriting proxies in an editing context using a map link old to new resource ids.
 *
 * @author adaussy
 */
public interface IRewriteProxiesService {

    /**
     * Rewrite all proxies in the editing context using a map of id mapping old resource id to new resource id.
     *
     * @param editingContext
     *            the current editing context
     * @param oldDocumentIdToNewDocumentId
     *            a map linking old resource id to new resource id
     * @param semanticElementsIdMappings
     *            a map linking old semantic elements id to the "fresh" ids of their imported equivalent
     * @return the number of rewritten proxy
     */
    int rewriteProxies(IEMFEditingContext editingContext, Map<String, String> oldDocumentIdToNewDocumentId, Map<String, String> semanticElementsIdMappings);
}

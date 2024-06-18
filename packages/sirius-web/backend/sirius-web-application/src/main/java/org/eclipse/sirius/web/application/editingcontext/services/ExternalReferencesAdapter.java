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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * An adapter attached to a resource that stores the set of other resources in the same resource set that it references.
 * This needs to be updated if the resource is modified, but otherwise can be reused as is.
 *
 * @author pcdavid
 */
public class ExternalReferencesAdapter extends AdapterImpl {

    private final Set<URI> references = new HashSet<>();

    public static ExternalReferencesAdapter getOrInstall(Resource res) {
        var existingAdapter = res.eAdapters().stream().filter(ExternalReferencesAdapter.class::isInstance).map(ExternalReferencesAdapter.class::cast).findFirst();
        if (existingAdapter.isPresent()) {
            return existingAdapter.get();
        } else {
            var adapter = new ExternalReferencesAdapter();
            res.eAdapters().add(adapter);
            return adapter;
        }
    }

    public Set<URI> getReferences() {
        return this.references;
    }

    public void update() {
        if (this.getTarget() instanceof Resource res && res.getResourceSet() != null) {
            res.getAllContents().forEachRemaining(sourceObject -> {
                sourceObject.eCrossReferences().forEach(targetObject -> {
                    var targetResource = targetObject.eResource();
                    if (targetResource != res) {
                        this.references.add(targetResource.getURI());
                    }
                });
            });
        } else {
            this.references.clear();
        }
    }

}

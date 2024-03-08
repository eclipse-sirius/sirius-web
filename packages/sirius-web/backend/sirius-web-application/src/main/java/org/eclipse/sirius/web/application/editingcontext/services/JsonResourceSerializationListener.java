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

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.emfjson.resource.JsonResource;

/**
 * Used to collect serialization data from JsonResource.
 *
 * @author sbegaudeau
 */
public class JsonResourceSerializationListener implements JsonResource.ISerializationListener {

    private final List<EPackageEntry> ePackageEntries = new ArrayList<>();

    @Override
    public void onNsHeaderEntryAdded(String nsPrefix, String nsURI) {
        this.ePackageEntries.add(new EPackageEntry(nsPrefix, nsURI));
    }

    @Override
    public void onObjectSerialized(EObject eObject, JsonElement jsonElement) {
        // Do nothing
    }

    @Override
    public void onCrossReferenceURICreated(EObject eObject, EReference eReference, String s) {
        // Do nothing
    }

    public List<EPackageEntry> getePackageEntries() {
        return this.ePackageEntries;
    }
}

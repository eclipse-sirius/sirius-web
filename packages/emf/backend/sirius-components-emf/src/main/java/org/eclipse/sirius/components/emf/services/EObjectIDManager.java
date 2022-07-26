/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.emfjson.resource.IDManager;

/**
 * Used to manage an ID for a given EObject.
 *
 * @author gcoutable
 */
public class EObjectIDManager implements IDManager {

    /**
     * Returns the ID stored in the {@link IDAdapter} of the given {@link EObject} or {@link UUID#randomUUID()} if
     * {@link IDAdapter} cannot be found.
     *
     * @param eObject
     *            The eObject
     *
     * @return The eObject new or existing ID
     */
    @Override
    public String getOrCreateId(EObject eObject) {
        // @formatter:off
        return this.findAdapter(eObject)
                .map(IDAdapter::getId)
                .orElseGet(UUID::randomUUID)
                .toString();
        // @formatter:on
    }

    /**
     * Returns the ID stored in the {@link IDAdapter} of the given {@link EObject} or {@link Optional#empty()} if
     * {@link IDAdapter} cannot be found.
     *
     * @param eObject
     *            The eObject
     *
     * @return The eObject ID if an {@link IDAdapter} exists for the given eObject or {@link Optional#empty()} otherwise
     */
    @Override
    public Optional<String> findId(EObject eObject) {
        // @formatter:off
        return this.findAdapter(eObject)
                .map(IDAdapter::getId)
                .map(UUID::toString);
        // @formatter:on
    }

    /**
     * Tries to clear the {@link IDAdapter} from the given eObject if it exists, then returns the ID if the
     * {@link IDAdapter} has existed or {@link Optional#empty()} otherwise.
     *
     * @param eObject
     *            The EObject
     *
     * @return The cleared ID if the {@link IDAdapter} has existed, {@link Optional#empty()} otherwise
     */
    @Override
    public Optional<String> clearId(EObject eObject) {
        var optionalAdapter = this.findAdapter(eObject);
        optionalAdapter.ifPresent(adapter -> eObject.eAdapters().remove(adapter));

        return optionalAdapter.map(IDAdapter::getId).map(UUID::toString);
    }

    /**
     * Removes the previous ID if the given eObject already has an {@link IDAdapter} then adds a new {@link IDAdapter}
     * holding the given id to the given eObject.
     *
     * @param eObject
     *            The eObject on which add the given ID
     * @param id
     *            The ID to add on the given eObject
     */
    @Override
    public void setId(EObject eObject, String id) {
        this.clearId(eObject);

        eObject.eAdapters().add(new IDAdapter(UUID.fromString(id)));
    }

    /**
     * Returns the {@link IDAdapter} attached to the given eObject or {@link Optional#empty()} if no {@link IDAdapter}
     * are attached.
     *
     * @param eObject
     *            The eObject on which the {@link IDAdapter} may be attached
     * @return The attached {@link IDAdapter} or {@link Optional#empty()} otherwise
     */
    private Optional<IDAdapter> findAdapter(EObject eObject) {
        // @formatter:off
        return eObject.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst();
        // @formatter:on
    }

}

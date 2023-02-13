/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.Objects;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Implementation of the editing context.
 *
 * @author sbegaudeau
 */
public class EditingContext implements IEditingContext {

    /**
     * This scheme should be used to create an URI of a resource that corresponds to a document added in the
     * EditingContext ResourceSet.
     */
    public static final String RESOURCE_SCHEME = "sirius";

    private final String id;

    private final AdapterFactoryEditingDomain editingDomain;

    public EditingContext(String id, AdapterFactoryEditingDomain editingDomain) {
        this.id = Objects.requireNonNull(id);
        this.editingDomain = Objects.requireNonNull(editingDomain);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public AdapterFactoryEditingDomain getDomain() {
        return this.editingDomain;
    }

}

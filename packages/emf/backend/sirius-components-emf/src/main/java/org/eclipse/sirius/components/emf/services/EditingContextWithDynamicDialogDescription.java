/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Implementation of the editing context that holds DynamicDialogDescription.<br/>
 * This implementation is a workaround that should be removed.
 *
 * @author lfasani
 */
public class EditingContextWithDynamicDialogDescription extends EditingContext {

    private final Map<String, IDynamicDialogDescription> dynamicDialogDescriptions;

    public EditingContextWithDynamicDialogDescription(String id, AdapterFactoryEditingDomain editingDomain, Map<String, IRepresentationDescription> representationDescriptions,
            Map<String, IDynamicDialogDescription> dynamicDialogDescriptions) {
        super(id, editingDomain, representationDescriptions);
        this.dynamicDialogDescriptions = Objects.requireNonNull(dynamicDialogDescriptions);
    }

    public Map<String, IDynamicDialogDescription> getDynamicDialogDescriptions() {
        return this.dynamicDialogDescriptions;
    }
}

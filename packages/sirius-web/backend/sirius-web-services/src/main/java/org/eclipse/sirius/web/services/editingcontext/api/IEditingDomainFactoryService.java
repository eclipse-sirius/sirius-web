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
package org.eclipse.sirius.web.services.editingcontext.api;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.web.services.documents.EditingDomainFactory;

/**
 * Service used to create the editing domain used as editing context.
 *
 * @author frouene
 */
public interface IEditingDomainFactoryService {

    AdapterFactoryEditingDomain createEditingDomain(String editingContextId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IEditingDomainFactoryService {

        @Override
        public AdapterFactoryEditingDomain createEditingDomain(String editingContextId) {
            return new EditingDomainFactory().create();
        }
    }
}

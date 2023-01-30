/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Service to discover representation descriptions dynamically from the existing user-defined documents.
 *
 * @author pcdavid
 */
public interface IDynamicRepresentationDescriptionService {
    List<IRepresentationDescription> findDynamicRepresentationDescriptions(String editingContextId, EditingDomain editingDomain);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDynamicRepresentationDescriptionService {
        @Override
        public List<IRepresentationDescription> findDynamicRepresentationDescriptions(String editingContextId, EditingDomain editingDomain) {
            return List.of();
        }
    }
}

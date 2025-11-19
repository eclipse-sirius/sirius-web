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
package org.eclipse.sirius.components.collaborative.forms.services.api;

import org.eclipse.sirius.components.collaborative.forms.dto.FormCapabilitiesDTO;

/**
 * Used to retrieve the capabilities of a form in an editing context.
 *
 * @author gcoutable
 */
public interface IFormCapabilitiesService {
    FormCapabilitiesDTO getFormCapabilities(String editingContextId, String formId);

    /**
     * Implementation which does nothing, used for testing purpose.
     */
    class NoOp implements IFormCapabilitiesService {

        @Override
        public FormCapabilitiesDTO getFormCapabilities(String editingContextId, String formId) {
            return new FormCapabilitiesDTO(true);
        }
    }
}

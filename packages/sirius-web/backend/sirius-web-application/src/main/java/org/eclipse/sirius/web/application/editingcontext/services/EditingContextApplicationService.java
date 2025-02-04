/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextApplicationService;
import org.springframework.stereotype.Service;

/**
 * Used to interact with editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextApplicationService implements IEditingContextApplicationService {

    @Override
    public String getCurrentEditingContextId(String projectId) {
        return projectId;
    }
}

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
package org.eclipse.sirius.web.services.query;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.views.query.services.api.IInterpreterJavaServiceProvider;
import org.springframework.stereotype.Service;

/**
 * Used to contribute additional services while executing queries.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaInterpreterJavaServiceProvider implements IInterpreterJavaServiceProvider {
    @Override
    public List<Class<?>> getServiceClasses(IEditingContext editingContext) {
        return List.of(PapayaQueryServices.class);
    }
}

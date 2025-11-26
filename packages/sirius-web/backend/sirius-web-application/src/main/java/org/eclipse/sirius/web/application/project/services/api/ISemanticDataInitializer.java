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
package org.eclipse.sirius.web.application.project.services.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;

/**
 * Initializes the semantic data of a newly created project.
 *
 * @author pcdavid
 */
public interface ISemanticDataInitializer {
    boolean canHandle(String projectTemplateId);

    void handle(ICause cause, IEditingContext editingContext, String projectTemplateId);
}

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
package org.eclipse.sirius.web.application.document.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.document.dto.DocumentDTO;

/**
 * Used to create documents.
 *
 * @author sbegaudeau
 */
public interface IStereotypeHandler {
    boolean canHandle(IEditingContext editingContext, String stereotypeId);

    Optional<DocumentDTO> handle(IEditingContext editingContext, String stereotypeId, String name);
}

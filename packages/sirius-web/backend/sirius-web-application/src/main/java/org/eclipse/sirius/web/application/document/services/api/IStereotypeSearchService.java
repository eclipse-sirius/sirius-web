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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Used to find the stereotypes used to create documents.
 *
 * @author sbegaudeau
 */
public interface IStereotypeSearchService {
    Page<Stereotype> findAll(IEditingContext editingContext, Pageable pageable);
}

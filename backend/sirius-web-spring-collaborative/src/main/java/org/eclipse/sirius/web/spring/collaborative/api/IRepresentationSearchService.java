/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Used to find representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationSearchService {
    <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, UUID representationId, Class<T> representationClass);
}

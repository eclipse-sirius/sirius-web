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
package org.eclipse.sirius.web.core.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.representations.IRepresentationDescription;

/**
 * Used to find representation descriptions.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDescriptionSearchService {
    Optional<IRepresentationDescription> findById(IEditingContext editingContext, UUID representationDescriptionId);
}

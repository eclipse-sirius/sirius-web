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
package org.eclipse.sirius.web.collaborative.api.services;

import java.util.UUID;

/**
 * Used to delete representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDeletionService {
    void delete(UUID representationId);

    void deleteDanglingRepresentations(UUID editingContextId);
}

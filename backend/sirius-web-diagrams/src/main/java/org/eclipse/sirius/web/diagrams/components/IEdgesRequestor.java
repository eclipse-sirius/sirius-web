/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.Edge;

/**
 * Used to find some information on a specific set of edges from a previous diagram.
 *
 * @author sbegaudeau
 */
public interface IEdgesRequestor {
    Optional<Edge> getById(UUID edgeId);
}

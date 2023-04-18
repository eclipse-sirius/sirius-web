/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;

/**
 * Payload used to indicate that the tree has been refreshed.
 *
 * @author sbegaudeau
 */
public record TreeRefreshedEventPayload(UUID id, Tree tree) implements IPayload {
    public TreeRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(tree);
    }
}

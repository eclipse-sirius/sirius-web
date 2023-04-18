/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

/**
 * Payload used to tell the frontend which items to expand and what is the corresponding max depth.
 *
 * @author pcdavid
 */
public record TreePathSuccessPayload(UUID id, TreePath treePath) implements IPayload {
    public TreePathSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(treePath);
    }
}

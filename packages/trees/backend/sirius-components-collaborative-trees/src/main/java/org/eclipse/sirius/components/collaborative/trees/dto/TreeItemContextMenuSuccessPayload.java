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
package org.eclipse.sirius.components.collaborative.trees.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to tell the frontend the list of context menu action.
 *
 * @author Jerome Gout
 */
public record TreeItemContextMenuSuccessPayload(UUID id, List<ITreeItemContextMenuEntry> actions) implements IPayload {
    public TreeItemContextMenuSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(actions);
    }
}

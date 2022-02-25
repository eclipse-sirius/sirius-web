/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to tell the frontend which items to expand and what is the corresponding max depth.
 *
 * @author pcdavid
 */
public class TreePathSuccessPayload implements IPayload {
    private final UUID id;

    private final TreePath treePath;

    public TreePathSuccessPayload(UUID id, TreePath treePath) {
        this.id = Objects.requireNonNull(id);
        this.treePath = Objects.requireNonNull(treePath);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public TreePath getTreePath() {
        return this.treePath;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, treePath: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.treePath);
    }
}

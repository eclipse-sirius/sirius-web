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
package org.eclipse.sirius.components.collaborative.trees.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration of the tree event processor.
 *
 * @author sbegaudeau
 */
public class TreeConfiguration implements IRepresentationConfiguration {

    private final String treeId;

    private final List<String> expanded;

    public TreeConfiguration(String editingContextId, List<String> expanded) {
        String uniqueId = editingContextId + expanded.toString();
        this.treeId = UUID.nameUUIDFromBytes(uniqueId.getBytes()).toString();
        this.expanded = Objects.requireNonNull(expanded);
    }

    @Override
    public String getId() {
        return this.treeId;
    }

    public List<String> getExpanded() {
        return this.expanded;
    }

}

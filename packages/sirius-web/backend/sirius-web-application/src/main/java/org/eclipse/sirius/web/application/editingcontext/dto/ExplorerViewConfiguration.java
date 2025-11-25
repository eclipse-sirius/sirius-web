/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.dto;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.TreeFilter;

/**
 * The configuration of the explorer view.
 *
 * @author gdaniel
 */
// TODO it is weird to have a dependency to TreeFilter here, do we really need (id, label, state), or id is enough?
public record ExplorerViewConfiguration(boolean isActive, List<TreeFilter> activeTreeFilters, String activeTreeDescriptionId) implements IViewConfiguration {

    public ExplorerViewConfiguration {
        Objects.requireNonNull(activeTreeFilters);
        Objects.requireNonNull(activeTreeDescriptionId);
    }

    @Override
    public String id() {
        return "explorer";
    }
}

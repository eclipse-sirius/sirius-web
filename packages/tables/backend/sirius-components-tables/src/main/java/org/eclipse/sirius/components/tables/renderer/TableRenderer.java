/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.tables.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.tables.Table;

/**
 * Renderer used to create table representations.
 *
 * @author arichard
 */
public class TableRenderer {

    public static final String PAGINATION_CURSOR = "cursor";
    public static final String PAGINATION_DIRECTION = "direction";
    public static final String PAGINATION_SIZE = "size";
    public static final String GLOBAL_FILTER_DATA = "globalFilterData";
    public static final String COLUMN_FILTERS = "columnFilters";
    public static final String EXPANDED_IDS = "expandedIds";

    private final BaseRenderer baseRenderer;

    public TableRenderer() {
        this.baseRenderer = new BaseRenderer(new TableInstancePropsValidator(), new TableComponentPropsValidator(), new TableElementFactory());
    }

    public Table render(Element element) {
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(Table.class::isInstance)
                .map(Table.class::cast)
                .orElse(null);
    }
}

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
package org.eclipse.sirius.components.tables.renderer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Cache used during the rendering of a table.
 *
 * @author lfasani
 */
public class TableRenderingCache {

    private final Map<UUID, Object> columnIdToObject = new LinkedHashMap<>();


    public void putColumnObject(UUID columnId, Object object) {
        this.columnIdToObject.put(columnId, object);
    }

    public Map<UUID, Object> getColumnIdToObject() {
        return this.columnIdToObject;
    }

}

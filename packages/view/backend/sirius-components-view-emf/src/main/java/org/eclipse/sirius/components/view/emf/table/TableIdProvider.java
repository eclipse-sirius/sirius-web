/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.view.emf.table;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TableElementDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of ITableIdProvider.
 *
 * @author frouene
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TableIdProvider implements ITableIdProvider {

    private final IObjectService objectService;

    public TableIdProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public String getId(TableDescription tableDescription) {
        String sourceId = this.getSourceIdFromElementDescription(tableDescription);
        String sourceElementId = this.objectService.getId(tableDescription);
        return TABLE_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(TableElementDescription tableElementDescription) {
        String sourceId = this.getSourceIdFromElementDescription(tableElementDescription);
        String sourceElementId = this.objectService.getId(tableElementDescription);
        var id = "";
        if (tableElementDescription instanceof CellDescription) {
            id = CELL_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
        }
        if (tableElementDescription instanceof ColumnDescription) {
            id = COLUMN_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
        }
        if (tableElementDescription instanceof RowDescription) {
            id = ROW_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
        }
        return id;
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }

}

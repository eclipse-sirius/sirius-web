/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of ITableIdProvider.
 *
 * @author frouene
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TableIdProvider implements ITableIdProvider {

    private final IIdentityService identityService;

    public TableIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String getId(TableDescription tableDescription) {
        String sourceId = this.getSourceIdFromElementDescription(tableDescription);
        String sourceElementId = this.identityService.getId(tableDescription);
        return TABLE_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(RowDescription rowDescription) {
        String sourceId = this.getSourceIdFromElementDescription(rowDescription);
        String sourceElementId = this.identityService.getId(rowDescription);
        return ROW_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(ColumnDescription columnDescription) {
        String sourceId = this.getSourceIdFromElementDescription(columnDescription);
        String sourceElementId = this.identityService.getId(columnDescription);
        return COLUMN_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(CellDescription cellDescription) {
        String sourceId = this.getSourceIdFromElementDescription(cellDescription);
        String sourceElementId = this.identityService.getId(cellDescription);
        return CELL_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }

}

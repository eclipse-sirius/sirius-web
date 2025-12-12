/*******************************************************************************
 * Copyright (c) 2025 CEA LIST and others.
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

package org.eclipse.sirius.web.papaya.representations.table;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.IRowContextMenuEntryExecutor;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * This class is the implementation of {@link IRowContextMenuEntryExecutor} for the example Delete row action.
 *
 * @author Jerome Gout
 */
@Service
public class DeleteRowContextMenuEntryExecutor implements IRowContextMenuEntryExecutor {

    private final IEditService editService;

    private final IObjectSearchService objectSearchService;

    public DeleteRowContextMenuEntryExecutor(IEditService editService, IObjectSearchService objectSearchService) {
        this.editService = Objects.requireNonNull(editService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canExecute(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row, String rowMenuContextEntryId) {
        return PackageTableRowContextMenuProvider.DELETE_ID.equals(rowMenuContextEntryId);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row, String rowMenuContextEntryId) {
        this.objectSearchService.getObject(editingContext, row.getTargetObjectId()).ifPresent(this.editService::delete);
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
    }
}

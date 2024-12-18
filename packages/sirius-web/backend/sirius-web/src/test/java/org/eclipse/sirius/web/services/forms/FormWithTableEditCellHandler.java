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
package org.eclipse.sirius.web.services.forms;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.api.IEditCellHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.web.papaya.representations.table.PapayaTableEditCellHandler;
import org.springframework.stereotype.Service;

/**
 * Provide edit table cell executor for a form with table.
 * Reused PapayaTableEditCellExecutor.
 *
 * @author frouene
 */
@Service
public class FormWithTableEditCellHandler implements IEditCellHandler {

    private final PapayaTableEditCellHandler cellHandler;

    public FormWithTableEditCellHandler(PapayaTableEditCellHandler cellHandler) {
        this.cellHandler = Objects.requireNonNull(cellHandler);
    }

    @Override
    public boolean canHandle(TableDescription tableDescription) {
        return tableDescription.getId().equals(FormWithTableDescriptionProvider.FORM_WITH_TABLE_ID);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TableDescription tableDescription, ICell cell, Line line, Column column, Object newValue) {
        return this.cellHandler.handle(editingContext, tableDescription, cell, line, column, newValue);
    }

}

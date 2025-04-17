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

package org.eclipse.sirius.components.view.emf.widget.table;

import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.tables.api.IEditCellHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.emf.table.api.IViewEditCellExecutor;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Edit cell generic handler for all tables defined using the view table DSL.
 *
 * @author frouene
 */
@Service
public class ViewFormEditCellHandler implements IEditCellHandler {

    private final ViewFormDescriptionPredicate viewFormDescriptionPredicate;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IViewEditCellExecutor viewEditCellExecutor;

    private final IViewEMFMessageService viewEMFMessageService;

    public ViewFormEditCellHandler(ViewFormDescriptionPredicate viewFormDescriptionPredicate, IViewFormDescriptionSearchService viewFormDescriptionSearchService,
            IViewEditCellExecutor viewEditCellExecutor, IViewEMFMessageService viewEMFMessageService) {
        this.viewFormDescriptionPredicate = Objects.requireNonNull(viewFormDescriptionPredicate);
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
        this.viewEditCellExecutor = Objects.requireNonNull(viewEditCellExecutor);
        this.viewEMFMessageService = Objects.requireNonNull(viewEMFMessageService);
    }

    @Override
    public boolean canHandle(TableDescription tableDescription) {
        return this.viewFormDescriptionPredicate.test(tableDescription);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TableDescription tableDescription, ICell cell, Line row, Column column, Object newValue) {
        return this.viewFormDescriptionSearchService
                .findFormElementDescriptionById(editingContext, tableDescription.getId())
                .filter(TableWidgetDescription.class::isInstance)
                .map(TableWidgetDescription.class::cast)
                .map(viewTableWidgetDescription -> {
                    if (EcoreUtil.getRootContainer(viewTableWidgetDescription) instanceof View view) {
                        return this.viewEditCellExecutor.execute(editingContext, view, viewTableWidgetDescription.getCellDescriptions(), cell, row, column, newValue);
                    }
                    return new Failure(this.viewEMFMessageService.tableCellEditError());
                })
                .orElseGet(() -> new Failure(this.viewEMFMessageService.tableCellEditError()));
    }

}

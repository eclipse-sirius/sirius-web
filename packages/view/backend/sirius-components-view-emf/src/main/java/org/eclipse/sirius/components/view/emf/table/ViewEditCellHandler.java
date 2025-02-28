/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import org.eclipse.sirius.components.collaborative.tables.api.IEditCellHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.emf.table.api.IViewEditCellExecutor;
import org.springframework.stereotype.Service;

/**
 * Edit cell generic handler for all tables defined using the view table DSL.
 *
 * @author Jerome Gout
 */
@Service
public class ViewEditCellHandler implements IEditCellHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewEditCellExecutor viewEditCellExecutor;

    private final IViewEMFMessageService viewEMFMessageService;

    public ViewEditCellHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewEditCellExecutor viewEditCellExecutor, IViewEMFMessageService viewEMFMessageService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.viewEditCellExecutor = Objects.requireNonNull(viewEditCellExecutor);
        this.viewEMFMessageService = Objects.requireNonNull(viewEMFMessageService);
    }

    @Override
    public boolean canHandle(TableDescription tableDescription) {
        return this.viewRepresentationDescriptionPredicate.test(tableDescription);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TableDescription tableDescription, ICell cell, Line row, Column column, Object newValue) {
        var optionalTableDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, tableDescription.getId())
                .filter(org.eclipse.sirius.components.view.table.TableDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.table.TableDescription.class::cast);
        if (optionalTableDescription.isPresent()) {
            return this.viewEditCellExecutor.execute(editingContext, optionalTableDescription.get(), cell, row, column, newValue);
        }
        return new Failure(this.viewEMFMessageService.tableCellEditError());
    }

}

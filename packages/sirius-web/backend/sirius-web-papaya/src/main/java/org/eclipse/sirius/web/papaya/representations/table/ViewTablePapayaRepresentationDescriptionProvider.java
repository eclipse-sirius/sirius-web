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
package org.eclipse.sirius.web.papaya.representations.table;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.generated.table.TableBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;

/**
 * Used to provide the view model used to create tables.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ViewTablePapayaRepresentationDescriptionProvider implements IRepresentationDescriptionProvider {

    private final TableBuilders tableBuilders = new TableBuilders();

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {

        var nameColumnDescription = this.tableBuilders.newColumnDescription()
                .semanticCandidatesExpression("aql:'Name'")
                .headerLabelExpression("Qualified Name")
                .initialWidthExpression("450")
                .isResizableExpression("aql:false")
                .build();

        var descriptionColumnDescription = this.tableBuilders.newColumnDescription()
                .semanticCandidatesExpression("aql:'Description'")
                .headerLabelExpression("Description")
                .initialWidthExpression("250")
                .isResizableExpression("aql:false")
                .build();

        var cellNameDescription = this.tableBuilders.newCellDescription()
                .preconditionExpression("aql:columnTargetObject == 'Name'")
                .valueExpression("aql:self.qualifiedName")
                .cellWidgetDescription(this.tableBuilders.newCellLabelWidgetDescription().build())
                .build();

        var cellDescriptionDescription = this.tableBuilders.newCellDescription()
                .preconditionExpression("aql:columnTargetObject == 'Description'")
                .valueExpression("aql:self.description")
                .cellWidgetDescription(this.tableBuilders.newCellLabelWidgetDescription().build())
                .build();

        var rowDescription = this.tableBuilders.newRowDescription()
                .semanticCandidatesExpression("aql:self.eAllContents()->filter(papaya::Package)->toPaginatedData(cursor,direction,size)")
                .initialHeightExpression("-1")
                .isResizableExpression("aql:false")
                .headerLabelExpression("aql:self.name")
                .build();

        return this.tableBuilders.newTableDescription()
                .titleExpression("Packages table")
                .domainType("papaya::Project")
                .columnDescriptions(nameColumnDescription, descriptionColumnDescription)
                .cellDescriptions(cellNameDescription, cellDescriptionDescription)
                .rowDescription(rowDescription)
                .build();
    }
}

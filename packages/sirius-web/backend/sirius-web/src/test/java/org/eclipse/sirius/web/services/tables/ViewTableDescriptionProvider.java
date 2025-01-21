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
package org.eclipse.sirius.web.services.tables;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.table.TableBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.emf.table.TableIdProvider;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a simple view based table description to tests.
 *
 * @author frouene
 */
@Service
@Conditional(OnStudioTests.class)
public class ViewTableDescriptionProvider implements IEditingContextProcessor {

    private final TableIdProvider tableIdProvider;

    private final View view;

    private TableDescription tableDescription;

    public ViewTableDescriptionProvider(TableIdProvider tableIdProvider) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.view = this.createView();
    }

    public String getRepresentationDescriptionId() {
        return this.tableIdProvider.getId(this.tableDescription);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View newView = viewBuilder.build();
        newView.getDescriptions().add(this.createTableDescription());

        newView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("ViewTableDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("ViewTableDescription"));
        resource.getContents().add(newView);

        return newView;
    }

    private TableDescription createTableDescription() {

        var columnDescription = new TableBuilders().newColumnDescription()
                .semanticCandidatesExpression("aql:Sequence{'Name', 'Description'}")
                .headerLabelExpression("aql:self")
                .headerIndexLabelExpression("aql:columnIndex")
                .build();

        var contextMenuEntry = new TableBuilders().newRowContextMenuEntry()
                .name("change-name")
                .labelExpression("Change name")
                .preconditionExpression("aql:true")
                .body(
                        new ViewBuilders().newSetValue()
                                .featureName("name")
                                .valueExpression("aql:self.name + 'Updated'")
                                .build()
                )
                .build();

        var rowDescription = new TableBuilders().newRowDescription()
                .semanticCandidatesExpression("aql:self.types")
                .headerIndexLabelExpression("aql:rowIndex")
                .headerLabelExpression("aql:self.name")
                .contextMenuEntries(contextMenuEntry)
                .build();

        var nameCellDescription = new TableBuilders().newCellDescription()
                .preconditionExpression("aql:columnTargetObject.equals('Name')")
                .valueExpression("aql:self.name")
                .cellWidgetDescription(new TableBuilders().newCellTextfieldWidgetDescription().build())
                .build();

        var descriptionCellDescription = new TableBuilders().newCellDescription()
                .preconditionExpression("aql:columnTargetObject.equals('Description')")
                .valueExpression("aql:self.description")
                .cellWidgetDescription(new TableBuilders().newCellTextareaWidgetDescription().build())
                .build();

        this.tableDescription = new TableBuilders().newTableDescription()
                .domainType("papaya::Package")
                .titleExpression("View Package Table")
                .columnDescriptions(columnDescription)
                .rowDescription(rowDescription)
                .cellDescriptions(nameCellDescription, descriptionCellDescription)
                .useStripedRowsExpression("aql:false")
                .build();

        return this.tableDescription;
    }

}

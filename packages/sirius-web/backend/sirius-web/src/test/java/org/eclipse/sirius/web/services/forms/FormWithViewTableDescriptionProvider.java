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
package org.eclipse.sirius.web.services.forms;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.form.FormDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.GroupDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.form.PageDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.table.TableBuilders;
import org.eclipse.sirius.components.view.builder.generated.tablewidget.TableWidgetBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based form description to test table widget.
 *
 * @author frouene
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithViewTableDescriptionProvider implements IEditingContextProcessor {

    private final IFormIdProvider formIdProvider;

    private final View view;

    private FormDescription formDescription;

    public FormWithViewTableDescriptionProvider(IFormIdProvider formIdProvider) {
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.formIdProvider.getId(this.formDescription);
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View tableFormView = viewBuilder.build();
        tableFormView.getDescriptions().add(this.createFormDescription());

        tableFormView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FormWithViewTableDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FormWithViewTableDescription"));
        resource.getContents().add(tableFormView);

        return tableFormView;
    }

    private FormDescription createFormDescription() {
        var groupDescription = new GroupDescriptionBuilder()
                .name("Group")
                .labelExpression("Group")
                .semanticCandidatesExpression("aql:self")
                .children(this.getTypesWidget())
                .build();

        var pageDescription = new PageDescriptionBuilder()
                .name("Page")
                .labelExpression("Page")
                .domainType("papaya:Package")
                .semanticCandidatesExpression("aql:self")
                .groups(groupDescription)
                .build();

        this.formDescription = new FormDescriptionBuilder()
                .name("Form")
                .titleExpression("aql:'FormWithViewTable'")
                .domainType("papaya:Package")
                .pages(pageDescription)
                .build();

        return this.formDescription;
    }

    private TableWidgetDescription getTypesWidget() {
        var columnDescriptions = new TableBuilders().newColumnDescription()
                .name("Name")
                .semanticCandidatesExpression("aql:'NameColumn'")
                .headerLabelExpression("Name")
                .isResizableExpression("false")
                .initialWidthExpression("-1")
                .build();

        var rowDescription = new TableBuilders().newRowDescription()
                .name("TypesRow")
                .semanticCandidatesExpression("aql:self.types->toPaginatedData()")
                .isResizableExpression("false")
                .initialHeightExpression("-1")
                .build();

        SetValue setValue = new ViewBuilders().newSetValue()
                .featureName("name")
                .valueExpression("aql:newValue")
                .build();

        var cellDescriptions = new TableBuilders().newCellDescription()
                .name("TypeCell")
                .preconditionExpression("true")
                .cellWidgetDescription(new TableBuilders()
                        .newCellTextfieldWidgetDescription()
                        .body(setValue)
                        .build())
                .valueExpression("aql:self.name")
                .build();

        return new TableWidgetBuilders().newTableWidgetDescription()
                .name("Types")
                .labelExpression("Types")
                .useStripedRowsExpression("true")
                .columnDescriptions(columnDescriptions)
                .rowDescription(rowDescription)
                .cellDescriptions(cellDescriptions)
                .build();
    }
}

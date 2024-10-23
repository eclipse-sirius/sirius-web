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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.spec.PackageSpec;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.CellDescription;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of a table on Papaya package element. <br> This is an example to demonstrate how to use a table description.
 *
 * @author Jerome Gout
 */
@Service
public class PackageTableRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TABLE_DESCRIPTION_ID = "papaya_package_table_description";

    public static final String CELL_DESCRIPTION_ID = "papaya_package_cell_description";

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final ILabelService labelService;

    private final ComposedAdapterFactory composedAdapterFactory;

    public PackageTableRepresentationDescriptionProvider(IIdentityService identityService, IObjectSearchService objectSearchService, ILabelService labelService, ComposedAdapterFactory composedAdapterFactory) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.labelService = Objects.requireNonNull(labelService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var cellDescription = CellDescription.newCellDescription(CELL_DESCRIPTION_ID)
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellTypeProvider(new CellTypeProvider())
                .cellValueProvider(new CellValueProvider(this.identityService))
                .cellOptionsIdProvider(new CellOptionIdProvider(this.identityService, this.labelService))
                .cellOptionsLabelProvider(new CellOptionLabelProvider(this.labelService))
                .cellOptionsProvider(new CellOptionsProvider(this.composedAdapterFactory))
                .newCellValueHandler(new CellNewValueHandler(this.objectSearchService))
                .build();

        var lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()))
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .semanticElementsProvider(this::getSemanticElements)
                .build();

        var tableDescription = TableDescription.newTableDescription(TABLE_DESCRIPTION_ID)
                .label("Papaya package table")
                .labelProvider(new TableLabelProvider(this.labelService))
                .canCreatePredicate(this::canCreate)
                .lineDescriptions(List.of(lineDescription))
                .columnDescriptions(this.getColumnDescriptions())
                .targetObjectIdProvider(new TableTargetObjectIdProvider(this.identityService))
                .targetObjectKindProvider(new TableTargetObjectKindProvider(this.identityService))
                .cellDescription(cellDescription)
                .build();

        return List.of(tableDescription);
    }

    private boolean canCreate(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .filter(PackageSpec.class::isInstance)
                .isPresent();
    }

    private List<Object> getAllTypesOfPackage(Package aPackage) {
        var result = aPackage.getTypes().stream()
                .map(Object.class::cast)
                .collect(Collectors.toList());
        result.addAll(aPackage.getPackages().stream()
                .flatMap(pack -> this.getAllTypesOfPackage(pack).stream())
                .toList());
        return result;
    }

    private List<Object> getSemanticElements(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, PackageSpec.class)
                .map(this::getAllTypesOfPackage)
                .orElse(List.of());
    }

    private List<ColumnDescription> getColumnDescriptions() {
        var provider = new StructuralFeatureToDisplayNameProvider(new DisplayNameProvider(this.composedAdapterFactory));
        Map<EStructuralFeature, String> featureToDisplayName = provider.getColumnsStructuralFeaturesDisplayName(PapayaFactory.eINSTANCE.createClass(), PapayaPackage.eINSTANCE.getType());

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()))
                .semanticElementsProvider(variableManager -> featureToDisplayName.keySet().stream().map(Object.class::cast).toList())
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, EStructuralFeature.class).map(featureToDisplayName::get).orElse(""))
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .build();
        return List.of(columnDescription);
    }
}

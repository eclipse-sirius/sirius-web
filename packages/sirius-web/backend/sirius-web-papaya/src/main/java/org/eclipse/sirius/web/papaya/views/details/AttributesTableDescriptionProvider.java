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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.papaya.Class;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.web.papaya.representations.table.CellTypePredicate;
import org.eclipse.sirius.web.papaya.representations.table.ColumnTargetObjectIdProvider;
import org.springframework.stereotype.Service;

/**
 * Used to contribute the attributes table.
 *
 * @author sbegaudeau
 */
@Service
public class AttributesTableDescriptionProvider {

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public AttributesTableDescriptionProvider(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    public TableDescription getTableDescription() {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        Function<VariableManager, String> targetObjectKind = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getKind)
                .orElse(null);

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getLabel)
                .orElse(null);

        Function<VariableManager, PaginatedData> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Class.class)
                .map(aClass -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(aClass.getAttributes());
                    return new PaginatedData(objects, false, false, objects.size());
                })
                .orElse(new PaginatedData(List.of(), false, false, 0));

        LineDescription lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()).toString())
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(targetObjectKind)
                .semanticElementsProvider(semanticElementsProvider)
                .headerLabelProvider(variableManager -> "")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .isResizablePredicate(variableManager -> false)
                .initialHeightProvider(variableManager -> 0)
                .build();

        var nameColumnDescription = ColumnDescription.newColumnDescription("name")
                .semanticElementsProvider(variableManager -> List.of(PapayaPackage.eINSTANCE.getNamedElement_Name()))
                .targetObjectIdProvider(new ColumnTargetObjectIdProvider())
                .targetObjectKindProvider(variableManager -> "")
                .headerLabelProvider(variableManager -> "Name")
                .headerIconURLsProvider(variableManager -> List.of())
                .headerIndexLabelProvider(variableManager -> "")
                .initialWidthProvider(variableManager -> -1)
                .isResizablePredicate(variableManager -> false)
                .filterVariantProvider(variableManager -> "")
                .build();

        List<ColumnDescription> columnDescriptions = List.of(nameColumnDescription);

        BiFunction<VariableManager, Object, String> valueProvider = (variableManager, columnTargetObject) -> {
            String value = "";
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                Object objectValue = eObject.eGet(eStructuralFeature);
                if (eStructuralFeature instanceof EReference eReference) {
                    if (!eReference.isMany() && !eReference.isContainment()) {
                        value = this.identityService.getId(objectValue);
                    }
                } else if (objectValue != null) {
                    value = objectValue.toString();
                }
            }
            return value;
        };

        var nameCellDescription = TextfieldCellDescription.newTextfieldCellDescription("name")
                .canCreatePredicate(new CellTypePredicate().isTextfieldCell())
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .cellValueProvider(valueProvider)
                .build();

        List<ICellDescription> cellDescriptions = List.of(nameCellDescription);

        return TableDescription.newTableDescription("table")
                .label("tasksTableLabel")
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(targetObjectKind)
                .labelProvider(labelProvider)
                .lineDescription(lineDescription)
                .columnDescriptions(columnDescriptions)
                .cellDescriptions(cellDescriptions)
                .iconURLsProvider(variableManager -> List.of())
                .isStripeRowPredicate(variableManager -> false)
                .build();
    }
}

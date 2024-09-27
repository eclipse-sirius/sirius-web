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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;
import org.eclipse.sirius.components.tables.descriptions.CellDescription;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description for the project management concepts.
 *
 * @author lfasani
 */
@Service
public class FormWithTableEditingContextDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TASK_FORM_ID = "taskFormDescription";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IIdentityService identityService;

    private final IObjectService objectService;

    public FormWithTableEditingContextDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IIdentityService identityService, IObjectService objectService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {

        TableWidgetDescription tableWidgetDescription = getTableWidgetDescription();

        GroupDescription taskGroup = GroupDescription.newGroupDescription("iterationGroupId")
                .idProvider(variableManager -> "iterationGroupId")
                .labelProvider(variableManager -> "Iteration Group")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(List.of(tableWidgetDescription))
                .build();

        PageDescription pageDescription = PageDescription.newPageDescription("iterationPageId")
                .idProvider(variableManager -> "iterationPageId")
                .labelProvider(variableManager -> "Iteration Page")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(List.of(taskGroup))
                .canCreatePredicate(variableManager -> true)
                .build();

        FormDescription formDescription =  FormDescription.newFormDescription(TASK_FORM_ID)
                .label("Iteration form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> "Iteration Form")
                .targetObjectIdProvider(this::getTargetObjectId)
                .canCreatePredicate(this::canCreate)
                .pageDescriptions(List.of(pageDescription))
                .build();
        return List.of(formDescription);
    }

    private TableWidgetDescription getTableWidgetDescription() {
        Function<VariableManager, List<Object>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Iteration.class)
                .map(eObject -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(eObject.getTasks());
                    return objects;
                })
                .orElse(List.of());

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getLabel)
                .orElse(null);

        List<LineDescription> lineDescriptions = new ArrayList<>();
        LineDescription lineDescription = LineDescription.newLineDescription(UUID.nameUUIDFromBytes("Table - Line".getBytes()))
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .semanticElementsProvider(semanticElementsProvider)
                .build();
        lineDescriptions.add(lineDescription);

        TableDescription tableDescription = TableDescription.newTableDescription("tasksTableId")
                .targetObjectIdProvider(this::getTargetObjectId)
                .targetObjectKindProvider(this::getTargetObjectKind)
                .labelProvider(labelProvider)
                .lineDescriptions(lineDescriptions)
                .columnDescriptions(getColumnDescriptions())
                .cellDescription(getCellDescription())
                .build();

        return TableWidgetDescription.newTableWidgetDescription("tasksTableWidgetId")
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> "Tasks")
                .targetObjectIdProvider(this::getTargetObjectId)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .tableDescription(tableDescription)
                .build();
    }

    private CellDescription getCellDescription() {
        return CellDescription.newCellDescription("cells")
                .targetObjectIdProvider(vm-> "")
                .targetObjectKindProvider(vm-> "")
                .cellTypeProvider(this.getCellTypeProvider())
                .cellValueProvider(this.getCellValueProvider())
                .cellOptionsIdProvider(this.getCellOptionsIdProvider())
                .cellOptionsLabelProvider(this.getCellOptionsLabelProvider())
                .cellOptionsProvider(this.getCellOptionsProvider())
                .newCellValueHandler(getNewCellValueHandler())
                .build();
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);
    }

    private String getTargetObjectKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getKind)
                .orElse(null);
    }

    private boolean canCreate(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class)
                .filter(Iteration.class::isInstance)
                .isPresent();
    }

    private List<ColumnDescription> getColumnDescriptions() {
        Map<EStructuralFeature, String> featureToDisplayName = this.getColumnsStructuralFeaturesDisplayName(PapayaFactory.eINSTANCE.createTask(), PapayaPackage.eINSTANCE.getTask());

        ColumnDescription columnDescription = ColumnDescription.newColumnDescription(UUID.nameUUIDFromBytes("features".getBytes()))
                .semanticElementsProvider(vm -> featureToDisplayName.keySet().stream().map(Object.class::cast).toList())
                .labelProvider(vm -> vm.get(VariableManager.SELF, EStructuralFeature.class).map(featureToDisplayName::get).orElse(""))
                .targetObjectIdProvider(vm -> vm.get(VariableManager.SELF, EStructuralFeature.class).map(EStructuralFeature::getName).orElse(""))
                .targetObjectKindProvider(vm -> "")
                .build();
        return List.of(columnDescription);
    }

    private  BiFunction<VariableManager, Object, Object> getCellValueProvider() {
        return (variableManager, columnTargetObject) -> {
            Object value = "";
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                Object objectValue = eObject.eGet(eStructuralFeature);
                if (eStructuralFeature instanceof EReference) {
                    EReference eReference = (EReference) eStructuralFeature;
                    if (eReference.isMany() && !eReference.isContainment() && objectValue instanceof EList<?>) {
                        value = ((EList<?>) objectValue).stream().map(this.objectService::getId).collect(Collectors.toList());
                    } else if (!eReference.isMany() && !eReference.isContainment()) {
                        value = this.objectService.getId(objectValue);
                    }
                } else if (objectValue != null) {
                    value = objectValue.toString();
                }
            }
            return value;
        };
    }

    private BiFunction<VariableManager, Object, String> getCellTypeProvider() {
        return (variableManager, columnTargetObject) -> {
            String type = "";
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EClassifier eType = eStructuralFeature.getEType();
                if (eStructuralFeature instanceof EAttribute) {
                    if (EcorePackage.Literals.EBOOLEAN.equals(eType) || EcorePackage.Literals.EBOOLEAN_OBJECT.equals(eType)) {
                        type = CheckboxCellElementProps.TYPE;
                    } else if (eType instanceof EEnum) {
                        type = SelectCellElementProps.TYPE;
                    }
                } else {
                    EReference eReference = (EReference) eStructuralFeature;
                    if (eReference.isMany() && !eReference.isContainment()) {
                        type = MultiSelectCellElementProps.TYPE;
                    }
                    if (!eReference.isMany() && !eReference.isContainment()) {
                        type = SelectCellElementProps.TYPE;
                    }
                }
                if (type.isEmpty()) {
                    type = TextfieldCellElementProps.TYPE;
                }
            }
            return type;
        };
    }

    private Function<VariableManager, String> getCellOptionsIdProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
            if (candidate instanceof EEnumLiteral) {
                return this.objectService.getLabel(candidate);
            }
            return this.objectService.getId(candidate);
        };
    }

    private Function<VariableManager, String> getCellOptionsLabelProvider() {
        return  variableManager -> {
            Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
            if (candidate instanceof EEnumLiteral) {
                return this.objectService.getLabel(candidate);
            }
            return this.objectService.getFullLabel(candidate);
        };
    }

    private BiFunction<VariableManager, Object, List<Object>> getCellOptionsProvider() {
        return (variableManager, columnTargetObject) -> {
            List<Object> options = new ArrayList<>();
            Optional<EObject> optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalEObject.isPresent() && columnTargetObject instanceof EStructuralFeature eStructuralFeature) {
                EObject eObject = optionalEObject.get();
                EClassifier eType = eStructuralFeature.getEType();
                if (eType instanceof EEnum) {
                    options.addAll(((EEnum) eType).getELiterals());
                } else {
                    Object adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
                    if (adapter instanceof IItemPropertySource) {
                        IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
                        IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
                        if (descriptor != null) {
                            List<Object> choiceOfValues = descriptor.getChoiceOfValues(eObject).stream()
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
                            return choiceOfValues;
                        }
                    }
                }
            }
            return options;
        };
    }

    private Map<EStructuralFeature, String> getColumnsStructuralFeaturesDisplayName(EObject eObject, EClass eClass) {
        Map<EStructuralFeature, String> featureToDisplayName = new LinkedHashMap<>();
        EList<EStructuralFeature> eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
        for (EStructuralFeature eSF : eAllStructuralFeatures) {
            if (eSF instanceof EAttribute && !eSF.isMany() && !eSF.isDerived()) {
                featureToDisplayName.put(eSF, this.getDisplayName(eObject, eSF));
            } else if (eSF instanceof EReference ref && !eSF.isDerived() && !ref.isContainment()) {
                featureToDisplayName.put(eSF, this.getDisplayName(eObject, eSF));
            }
        }
        return featureToDisplayName;
    }

    private String getDisplayName(EObject eObject, EStructuralFeature eStructuralFeature) {
        Adapter adapter = this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource) {
            IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eStructuralFeature);
            if (descriptor != null) {
                String displayName = descriptor.getDisplayName(eStructuralFeature);
                return displayName;
            }
        }
        return eStructuralFeature.getName();
    }

    private BiFunction<VariableManager, Object, IStatus> getNewCellValueHandler() {
        BiFunction<VariableManager, Object, IStatus> newCellValueHandler = (variableManager, newValue) -> {
            IStatus status = new Failure("");
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            var optionalFeatureName = variableManager.get(ColumnDescription.COLUMN_TARGET_OBJECT_ID, String.class);
            if (optionalEObject.isPresent() && optionalFeatureName.isPresent()) {
                EObject eObject = optionalEObject.get();
                String featureName = optionalFeatureName.get();
                EStructuralFeature eStructuralFeature = eObject.eClass().getEStructuralFeature(featureName);
                if (eStructuralFeature != null) {
                    EClassifier eType = eStructuralFeature.getEType();
                    if (eStructuralFeature.isMany() && eType instanceof EClass && newValue instanceof Collection<?>) {
                        EList<EObject> refElements = (EList<EObject>) eObject.eGet(eStructuralFeature);
                        List<EObject> newValuesToSet = new ArrayList<>();
                        List<String> newValues = ((Collection<?>) newValue).stream().map(elt -> elt.toString()).collect(Collectors.toList());
                        for (String newStringValue : newValues) {
                            var optionalNewValueToSet = this.objectService.getObject(optionalEditingContext.get(), newStringValue);
                            if (optionalNewValueToSet.isEmpty()) {
                                continue;
                            }
                            EObject newValueToSet = (EObject) optionalNewValueToSet.get();
                            newValuesToSet.add(newValueToSet);
                            try {
                                if (!refElements.contains(newValueToSet)) {
                                    refElements.add(newValueToSet);
                                }
                            } catch (IllegalArgumentException | ClassCastException | ArrayStoreException exception) {
                                return new Failure("");
                            }
                        }
                        refElements.removeIf(refElt -> !newValuesToSet.contains(refElt));
                    } else if (!eStructuralFeature.isMany() && eType instanceof EClass && newValue instanceof String newStringValue) {
                        var optionalNewValueToSet = this.objectService.getObject(optionalEditingContext.get(), newStringValue);
                        if (optionalNewValueToSet.isPresent()) {
                            eObject.eSet(eStructuralFeature, optionalNewValueToSet.get());
                        }
                    } else if (eType instanceof EEnum && newValue instanceof String) {
                        EEnumLiteral eEnumLiteral = ((EEnum) eType).getEEnumLiteral((String) newValue);
                        eObject.eSet(eStructuralFeature, eEnumLiteral.getInstance());
                    } else if (eType instanceof EDataType) {
                        String newValueAsString = EcoreUtil.convertToString((EDataType) eStructuralFeature.getEType(), newValue);
                        Object value = EcoreUtil.createFromString((EDataType) eStructuralFeature.getEType(), newValueAsString);
                        eObject.eSet(eStructuralFeature, value);
                    }
                    status = new Success();
                }
            }
            return status;
        };
        return newCellValueHandler;
    }
}

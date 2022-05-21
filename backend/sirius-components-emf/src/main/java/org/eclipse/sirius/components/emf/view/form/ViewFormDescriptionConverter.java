/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.charts.descriptions.IChartDescription;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.compatibility.DomainClassPredicate;
import org.eclipse.sirius.components.emf.view.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ChartWidgetDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based form description into an equivalent {@link FormDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewFormDescriptionConverter implements IRepresentationDescriptionConverter {

    public static final String NEW_VALUE = "newValue"; //$NON-NLS-1$

    private static final String DEFAULT_FORM_LABEL = "Form"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IEditService editService;

    public ViewFormDescriptionConverter(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.FormDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription representationDescription, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.FormDescription viewFormDescription = (org.eclipse.sirius.components.view.FormDescription) representationDescription;
        ViewFormDescriptionConverterSwitch dispatcher = new ViewFormDescriptionConverterSwitch(interpreter, this.editService, this.objectService);
        // @formatter:off
        List<AbstractControlDescription> controlDescriptions = viewFormDescription.getWidgets().stream()
                .map(dispatcher::doSwitch)
                .collect(Collectors.toList());


         var zoomableCirclePackingDescription = this.getZoomableCirclePackingDescription();
        // @formatter:off
        controlDescriptions.add(ChartWidgetDescription.newChartWidgetDescription("myChart") //$NON-NLS-1$
                .chartDescription(zoomableCirclePackingDescription)
                .idProvider(variableManger -> "id") //$NON-NLS-1$
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "kind") //$NON-NLS-1$
                .labelProvider(variableManager -> "label") //$NON-NLS-1$
                .messageProvider(object -> "message") //$NON-NLS-1$
                .build());

        // @formatter:on

        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().collect(Collectors.toList());

        String descriptionId = this.getDescriptionId(viewFormDescription);
        GroupDescription groupDescription = GroupDescription.newGroupDescription(descriptionId + "_group") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider()).labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .semanticElementsProvider(semanticElementsProvider).controlDescriptions(controlDescriptions).build();
        PageDescription pageDescription = PageDescription.newPageDescription(descriptionId + "_page") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider()).labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .semanticElementsProvider(semanticElementsProvider).canCreatePredicate(variableManager -> true).groupDescriptions(List.of(groupDescription)).build();

        // @formatter:on
        List<GroupDescription> groupDescriptions = List.of(groupDescription);
        List<PageDescription> pageDescriptions = List.of(pageDescription);

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            return this.self(variableManager)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(this.objectService::getId)
                .orElse(null);
        };

        return FormDescription.newFormDescription(descriptionId)
                .label(Optional.ofNullable(viewFormDescription.getName()).orElse(DEFAULT_FORM_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreatForm(viewFormDescription, variableManager, interpreter))
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }

    private HierarchyDescription getHierarchyDescription(String id, String label, String kind) {
        // @formatter:off
           Predicate<VariableManager> canCreatePredicate = variableManager -> true;

           Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                   .map(this.objectService::getId)
                   .orElse(null);

           Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                   .map(this.objectService::getLabel)
                   .orElse(null);

           Function<VariableManager, List<Object>> childSemanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, EObject.class)
                   .map(eObject -> {
                       List<Object> objects = new ArrayList<>();
                       objects.addAll(eObject.eContents());
                       return objects;
                   })
                   .orElse(List.of());

           // @formatter:on
        return new HierarchyDescription(id, label, kind, canCreatePredicate, targetObjectIdProvider, labelProvider, childSemanticElementsProvider);
    }

    private IChartDescription getZoomableCirclePackingDescription() {
        UUID id = UUID.nameUUIDFromBytes("ZoomableCirclePacking".getBytes()); //$NON-NLS-1$
        String label = "ZoomableCirclePacking"; //$NON-NLS-1$

        return this.getHierarchyDescription(id.toString(), label, IRepresentation.KIND_PREFIX + "?type=ZoomableCirclePacking"); //$NON-NLS-1$
    }

    private String computeFormLabel(org.eclipse.sirius.components.view.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = this.evaluateString(interpreter, variableManager, viewFormDescription.getTitleExpression());
        if (title == null || title.isBlank()) {
            return DEFAULT_FORM_LABEL;
        } else {
            return title;
        }
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse(""); //$NON-NLS-1$
    }

    private boolean canCreatForm(org.eclipse.sirius.components.view.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        // @formatter:off
        Optional<EClass> optionalEClass = variableManager.get(IRepresentationDescription.CLASS, EClass.class)
                .filter(new DomainClassPredicate(viewFormDescription.getDomainType()));
        // @formatter:on
        if (optionalEClass.isPresent()) {
            String preconditionExpression = viewFormDescription.getPreconditionExpression();
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

}

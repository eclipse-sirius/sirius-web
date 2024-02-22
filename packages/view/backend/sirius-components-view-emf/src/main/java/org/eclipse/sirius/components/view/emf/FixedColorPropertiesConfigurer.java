/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for {@link FixedColor} elements of the View DSL.
 * If the color comes from the default color palettes, then its properties are read-only.
 *
 * @author arichard
 */
@Component
public class FixedColorPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = "";

    private final Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

    private final IValidationService validationService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> pageLabelProvider;

    public FixedColorPropertiesConfigurer(IValidationService validationService, IObjectService objectService) {
        this.validationService = Objects.requireNonNull(validationService);
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
        this.pageLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getLabel).orElse(null);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getFixedColorProperties());
    }

    private PageDescription getFixedColorProperties() {
        String id = UUID.nameUUIDFromBytes("fixedcolor".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.addAll(this.getGeneralControlDescription());

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                    .filter(FixedColor.class::isInstance)
                    .isPresent();

        return this.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
        List<AbstractControlDescription> controls = new ArrayList<>();


        var name = this.createTextField("fixedcolor.name", "Name",
                             color -> ((UserColor) color).getName(),
                             (color, newName) -> ((UserColor) color).setName(newName),
                             ViewPackage.Literals.USER_COLOR__NAME);
        controls.add(name);

        var value = this.createTextField("fixedcolor.value", "Value",
                             color -> ((FixedColor) color).getValue(),
                             (color, newValue) -> ((FixedColor) color).setValue(newValue),
                             ViewPackage.Literals.FIXED_COLOR__VALUE);
        controls.add(value);

        return controls;
    }

    private PageDescription createSimplePageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
        return PageDescription.newPageDescription(id)
                .idProvider(variableManager -> "page")
                .labelProvider(this.pageLabelProvider)
                .semanticElementsProvider(this.semanticElementsProvider)
                .canCreatePredicate(canCreatePredicate)
                .groupDescriptions(List.of(groupDescription))
                .build();
    }

    private GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls) {
        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(this.semanticElementsProvider)
                .controlDescriptions(controls)
                .build();
    }

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(EMPTY);
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var self = variableManager.get(VariableManager.SELF, Object.class);
            if (self.isPresent()) {
                writer.accept(self.get(), newValue);
                return new Success();
            } else {
                return new Failure("");
            }
        };

        return TextfieldDescription.newTextfieldDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .isReadOnlyProvider(this.isReadOnlyProvider())
                .build();
    }

    private Function<VariableManager, List<?>> getDiagnosticsProvider(Object feature) {
        return variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);

            if (optionalSelf.isPresent()) {
                EObject self = optionalSelf.get();
                List<Object> diagnostics = this.validationService.validate(self, feature);
                return diagnostics;
            }

            return List.of();
        };
    }

    private String kindProvider(Object object) {
        String kind = "Unknown";
        if (object instanceof Diagnostic diagnostic) {
            switch (diagnostic.getSeverity()) {
                case org.eclipse.emf.common.util.Diagnostic.ERROR:
                    kind = "Error";
                    break;
                case org.eclipse.emf.common.util.Diagnostic.WARNING:
                    kind = "Warning";
                    break;
                case org.eclipse.emf.common.util.Diagnostic.INFO:
                    kind = "Info";
                    break;
                default:
                    kind = "Unknown";
                    break;
            }
        }
        return kind;
    }

    private String messageProvider(Object object) {
        if (object instanceof Diagnostic diagnostic) {
            return diagnostic.getMessage();
        }
        return "";
    }

    private Function<VariableManager, Boolean> isReadOnlyProvider() {
        return variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, UserColor.class);
            if (optionalSelf.isPresent()) {
                UserColor color = optionalSelf.get();
                return ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(color.eResource().getURI().toString());
            }
            return false;
        };
    }
}

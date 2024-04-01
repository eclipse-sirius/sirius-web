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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
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

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final List<IValidationService> validationServices;

    public FixedColorPropertiesConfigurer(IIdentityService identityService, ILabelService labelService, List<IValidationService> validationServices) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.validationServices = Objects.requireNonNull(validationServices);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getFixedColorProperties());
    }

    private PageDescription getFixedColorProperties() {
        String id = UUID.nameUUIDFromBytes("fixedcolor".getBytes()).toString();

        List<AbstractControlDescription> controls = this.getGeneralControlDescription();
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
        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        Function<VariableManager, String> pageLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getLabel)
                .orElse(null);

        return PageDescription.newPageDescription(id)
                .idProvider(variableManager -> "page")
                .labelProvider(pageLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .canCreatePredicate(canCreatePredicate)
                .groupDescriptions(List.of(groupDescription))
                .build();
    }

    private GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls) {
        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(semanticElementsProvider)
                .controlDescriptions(controls)
                .build();
    }

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(reader)
                .orElse("");

        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var self = variableManager.get(VariableManager.SELF, Object.class);
            if (self.isPresent()) {
                writer.accept(self.get(), newValue);
                return new Success();
            } else {
                return new Failure("");
            }
        };

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return TextfieldDescription.newTextfieldDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(semanticTargetIdProvider)
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
                return this.validationServices.stream()
                        .map(validationService -> validationService.validate(self, feature))
                        .flatMap(Collection::stream)
                        .toList();
            }

            return List.of();
        };
    }

    private String kindProvider(Object object) {
        if (object instanceof Diagnostic diagnostic) {
            return switch (diagnostic.getSeverity()) {
                case org.eclipse.emf.common.util.Diagnostic.ERROR -> "Error";
                case org.eclipse.emf.common.util.Diagnostic.WARNING -> "Warning";
                case org.eclipse.emf.common.util.Diagnostic.INFO -> "Info";
                default -> "Unknown";
            };
        }
        return "Unknown";
    }

    private String messageProvider(Object object) {
        return Optional.ofNullable(object)
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .map(Diagnostic::getMessage)
                .orElse("");
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

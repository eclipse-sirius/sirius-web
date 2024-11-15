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

package org.eclipse.sirius.web.application.representation.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.springframework.stereotype.Service;

/**
 * Used to provide support for details view for RepresentationMetadata.
 *
 * @author Jerome Gout
 */
@Service
public class RepresentationMetadataDetailsViewPageDescriptionProvider implements IPropertiesDescriptionRegistryConfigurer {
    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    public RepresentationMetadataDetailsViewPageDescriptionProvider(IIdentityService identityService, ILabelService labelService,
            IRepresentationMetadataUpdateService representationMetadataUpdateService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getPageDescription());
    }

    private PageDescription getPageDescription() {
        String id = UUID.nameUUIDFromBytes("representation-metadata".getBytes()).toString();

        List<AbstractControlDescription> controls = this.createControlDescriptions();
        GroupDescription groupDescription = this.createGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(RepresentationMetadata.class::isInstance)
                .isPresent();

        return this.createPageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<AbstractControlDescription> createControlDescriptions() {
        List<AbstractControlDescription> controls = new ArrayList<>();

        BiFunction<Object, String, IStatus> representationLabelWriter = (metadata, newLabel) -> {
            // delegate the renaming of the representation to EditingContextEventProcessor
            var representationId = ((RepresentationMetadata) metadata).getId();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(EditingContextEventProcessor.REPRESENTATION_ID, representationId.toString());
            parameters.put(EditingContextEventProcessor.REPRESENTATION_LABEL, newLabel);
            return new Success(ChangeKind.REPRESENTATION_TO_RENAME, parameters);
        };

        var label = this.createTextField("metadata.label", "Label",
                metadata -> ((RepresentationMetadata) metadata).getLabel(),
                representationLabelWriter);
        controls.add(label);
        return controls;
    }


    private GroupDescription createGroupDescription(List<AbstractControlDescription> controls) {
        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "Core properties")
                .semanticElementsProvider(semanticElementsProvider)
                .controlDescriptions(controls)
                .build();
    }

    private PageDescription createPageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
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

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiFunction<Object, String, IStatus> writer) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(reader)
                .orElse("");

        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var self = variableManager.get(VariableManager.SELF, Object.class);
            if (self.isPresent()) {
                return writer.apply(self.get(), newValue);
            } else {
                return new Failure("");
            }
        };

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return org.eclipse.sirius.components.forms.description.TextfieldDescription.newTextfieldDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(semanticTargetIdProvider)
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .isReadOnlyProvider(variableManager -> false)
                .build();
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
}

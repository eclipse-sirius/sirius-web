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

package org.eclipse.sirius.web.application.representation.services;

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
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.LabelDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
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

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public RepresentationMetadataDetailsViewPageDescriptionProvider(IIdentityService identityService, ILabelService labelService,
            IRepresentationMetadataUpdateService representationMetadataUpdateService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getPageDescription());
    }

    private PageDescription getPageDescription() {
        String id = UUID.nameUUIDFromBytes("representation-metadata".getBytes()).toString();

        List<AbstractControlDescription> controls = this.createControlDescriptions();
        GroupDescription groupDescription = this.createGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(RepresentationMetadata.class::isInstance)
                .isPresent();

        return this.createPageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<AbstractControlDescription> createControlDescriptions() {
        var labelControl = this.createLabelTextField();
        var documentationControl = this.createDocumentationTextArea();
        var representationDescriptionNameControl = this.createRepresentationDescriptionNameLabel();
        return List.of(labelControl, documentationControl, representationDescriptionNameControl);
    }

    private GroupDescription createGroupDescription(List<AbstractControlDescription> controls) {
        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(semanticElementsProvider)
                .controlDescriptions(controls)
                .build();
    }

    private PageDescription createPageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();
        Function<VariableManager, String> pageLabelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getStyledLabel)
                .map(Object::toString)
                .orElse(null);

        return PageDescription.newPageDescription(id)
                .idProvider(variableManager -> "page")
                .labelProvider(pageLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .canCreatePredicate(canCreatePredicate)
                .groupDescriptions(List.of(groupDescription))
                .build();
    }

    private TextfieldDescription createLabelTextField() {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, RepresentationMetadata.class)
                .map(RepresentationMetadata::getLabel)
                .orElse("");

        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var self = variableManager.get(VariableManager.SELF, RepresentationMetadata.class);
            if (self.isPresent()) {
                var representationMetadata = self.get();
                var editingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
                if (editingContext.isPresent()) {
                    var input = new RenameRepresentationInput(UUID.randomUUID(), editingContext.get().getId(), representationMetadata.getId().toString(), newValue);
                    var result = this.representationMetadataUpdateService.updateLabel(input, representationMetadata.getId(), newValue);
                    if (result instanceof org.eclipse.sirius.web.domain.services.Success) {
                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put(EditingContextEventProcessor.REPRESENTATION_ID, representationMetadata.getId().toString());
                        parameters.put(EditingContextEventProcessor.REPRESENTATION_LABEL, newValue);
                        return new Success(ChangeKind.REPRESENTATION_RENAMING, parameters);
                    }
                }
            }
            return new Failure("");
        };

        return TextfieldDescription.newTextfieldDescription("metadata.label")
                .idProvider(variableManager -> "metadata.label")
                .targetObjectIdProvider(this::semanticTargetIdProvider)
                .labelProvider(variableManager -> "Label")
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .isReadOnlyProvider(variableManager -> false)
                .build();
    }

    private TextareaDescription createDocumentationTextArea() {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, RepresentationMetadata.class)
                .map(RepresentationMetadata::getDocumentation)
                .orElse("");

        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var self = variableManager.get(VariableManager.SELF, RepresentationMetadata.class);
            if (self.isPresent()) {
                var representationMetadata = self.get();
                return Optional.of(this.representationMetadataUpdateService.updateDocumentation(null, representationMetadata.getId(), newValue))
                        .filter(org.eclipse.sirius.web.domain.services.Success.class::isInstance)
                        .map(success -> (IStatus) new Success(ChangeKind.REPRESENTATION_METADATA_UPDATE, Map.of()))
                        .orElseGet(() -> new Failure(""));
            }
            return new Failure("");
        };

        return TextareaDescription.newTextareaDescription("metadata.documentation")
                .idProvider(variableManager -> "metadata.documentation")
                .targetObjectIdProvider(this::semanticTargetIdProvider)
                .labelProvider(variableManager -> "Documentation")
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

    private String semanticTargetIdProvider(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);
    }

    private LabelDescription createRepresentationDescriptionNameLabel() {
        return LabelDescription.newLabelDescription("metadata.representationDescriptionName")
                .idProvider(variableManager -> "metadata.representationDescriptionName")
                .kindProvider(this::kindProvider)
                .labelProvider(variableManager -> "Description Name")
                .targetObjectIdProvider(this::semanticTargetIdProvider)
                .valueProvider(this::representationDescriptionNameValueProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .styleProvider(variableManager -> null)
                .build();
    }

    private String representationDescriptionNameValueProvider(VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalSelf = variableManager.get(VariableManager.SELF, RepresentationMetadata.class);
        if (optionalEditingContext.isPresent() && optionalSelf.isPresent()) {
            return this.representationDescriptionSearchService.findById(optionalEditingContext.get(), optionalSelf.get().getDescriptionId())
                    .map(IRepresentationDescription::getLabel)
                    .orElse("");
        }
        return "";
    }
}

/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.emf.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationDescriptionsProvider;
import org.eclipse.sirius.components.collaborative.api.RepresentationDescriptionMetadata;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler to find/compute all the representation descriptions accessible from a given editing context which apply
 * to a particular type of object.
 *
 * @author pcdavid
 */
@Service
public class EditingContextRepresentationDescriptionsEventHandler implements IEditingContextEventHandler {
    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IEMFKindService emfKindService;

    private final IEMFMessageService emfMessageService;

    private final IObjectService objectService;

    private final List<IRepresentationDescriptionsProvider> representationDescriptionsProviders;

    public EditingContextRepresentationDescriptionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IEMFKindService emfKindService,
            IEMFMessageService emfMessageService, IObjectService objectService, List<IRepresentationDescriptionsProvider> representationDescriptionsProviders) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
        this.objectService = Objects.requireNonNull(objectService);
        this.representationDescriptionsProviders = Objects.requireNonNull(representationDescriptionsProviders);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextRepresentationDescriptionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        var optionalObject = Optional.empty();
        if (input instanceof EditingContextRepresentationDescriptionsInput) {
            EditingContextRepresentationDescriptionsInput editingContextRepresentationDescriptionsInput = (EditingContextRepresentationDescriptionsInput) input;
            String objectId = editingContextRepresentationDescriptionsInput.objectId();
            optionalObject = this.objectService.getObject(editingContext, objectId);
        }
        if (optionalObject.isPresent()) {
            List<RepresentationDescriptionMetadata> result = this.findAllCompatibleRepresentationDescriptions(editingContext, optionalObject.get());
            payloadSink.tryEmitValue(new EditingContextRepresentationDescriptionsPayload(input.id(), result));
        } else {
            String message = this.emfMessageService.invalidInput(input.getClass().getSimpleName(), EditingContextRepresentationDescriptionsInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(input.id(), message));
        }
        changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input));
    }

    private List<RepresentationDescriptionMetadata> findAllCompatibleRepresentationDescriptions(IEditingContext editingContext, Object object) {
        List<RepresentationDescriptionMetadata> result = new ArrayList<>();
        var kind = this.objectService.getKind(object);
        var clazz = this.resolveKind(editingContext, kind);

        if (clazz.isPresent()) {
            var allRepresentationDescriptions = this.representationDescriptionSearchService.findAll(editingContext);

            for (IRepresentationDescription description : allRepresentationDescriptions.values()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, object);
                variableManager.put(IRepresentationDescription.CLASS, clazz.get());
                Predicate<VariableManager> canCreatePredicate = description.getCanCreatePredicate();
                boolean canCreate = canCreatePredicate.test(variableManager);
                if (canCreate) {
                    Object targetObject = object;
                    this.representationDescriptionsProviders.forEach(provider -> {
                        if (provider.canHandle(description)) {
                            var descriptions = provider.handle(editingContext, targetObject, description);
                            result.addAll(descriptions);
                        }
                    });
                }
            }
        }
        return result;
    }

    private Optional<Object> resolveKind(IEditingContext editingContext, String kind) {
        Optional<Registry> optionalRegistry = this.getPackageRegistry(editingContext);
        if (optionalRegistry.isPresent() && !kind.isBlank() && kind.startsWith(SemanticKindConstants.PREFIX)) {
            var ePackageRegistry = optionalRegistry.get();
            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            // @formatter:off
            return this.emfKindService.findEPackage(ePackageRegistry, ePackageName)
                    .map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast);
            // @formatter:on
        } else {
            return Optional.empty();
        }
    }

    private Optional<Registry> getPackageRegistry(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext) {
            Registry packageRegistry = ((IEMFEditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return Optional.of(packageRegistry);
        } else {
            return Optional.empty();
        }
    }
}

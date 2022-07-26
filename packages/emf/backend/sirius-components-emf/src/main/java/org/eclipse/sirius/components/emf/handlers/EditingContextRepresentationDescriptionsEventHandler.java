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
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.EditingContext;
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

    public EditingContextRepresentationDescriptionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IEMFKindService emfKindService,
            IEMFMessageService emfMessageService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextRepresentationDescriptionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        if (input instanceof EditingContextRepresentationDescriptionsInput) {
            EditingContextRepresentationDescriptionsInput editingContextRepresentationDescriptionsInput = (EditingContextRepresentationDescriptionsInput) input;
            var result = this.findAllCompatibleRepresentationDescriptions(editingContext, editingContextRepresentationDescriptionsInput.getKind());
            payloadSink.tryEmitValue(new EditingContextRepresentationDescriptionsPayload(input.getId(), result));
        } else {
            String message = this.emfMessageService.invalidInput(input.getClass().getSimpleName(), CreateChildInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(input.getId(), message));
        }
        changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input));
    }

    private List<IRepresentationDescription> findAllCompatibleRepresentationDescriptions(IEditingContext editingContext, String kind) {
        List<IRepresentationDescription> result = new ArrayList<>();

        Optional<Object> optionalClazz = this.resolveKind(editingContext, kind);
        if (optionalClazz.isPresent()) {
            var allRepresentationDescriptions = this.representationDescriptionSearchService.findAll(editingContext);

            for (IRepresentationDescription description : allRepresentationDescriptions.values()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(IRepresentationDescription.CLASS, optionalClazz.get());
                Predicate<VariableManager> canCreatePredicate = description.getCanCreatePredicate();
                boolean canCreate = canCreatePredicate.test(variableManager);
                if (canCreate) {
                    result.add(description);
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
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return Optional.of(packageRegistry);
        } else {
            return Optional.empty();
        }
    }
}

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
package org.eclipse.sirius.components.compatibility.services.representations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.IRepresentationDescriptionsProvider;
import org.eclipse.sirius.components.collaborative.api.RepresentationDescriptionMetadata;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.services.api.IODesignRegistry;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to provide the {@link RepresentationDescriptionMetadata}s of a given object for the compatibility layer.
 *
 * @author arichard
 */
@Service
public class CompatibilityRepresentationDescriptionsProvider implements IRepresentationDescriptionsProvider {

    private final IIdentifierProvider identifierProvider;

    private final IODesignRegistry odesignRegistry;

    private final IAQLInterpreterFactory interpreterFactory;

    public CompatibilityRepresentationDescriptionsProvider(IIdentifierProvider identifierProvider, IODesignRegistry odesignRegistry, IAQLInterpreterFactory interpreterFactory) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.odesignRegistry = Objects.requireNonNull(odesignRegistry);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
    }

    @Override
    public boolean canHandle(IRepresentationDescription representationDescription) {
        String id = UUID.nameUUIDFromBytes(representationDescription.getId().getBytes()).toString();
        return this.identifierProvider.findVsmElementId(representationDescription.getId()).isPresent();
    }

    @Override
    public List<RepresentationDescriptionMetadata> handle(IEditingContext editingContext, Object object, IRepresentationDescription representationDescription) {
        List<RepresentationDescriptionMetadata> result = new ArrayList<>();
        var optionalVsmElementId = this.identifierProvider.findVsmElementId(representationDescription.getId());

        // @formatter:off
        var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                .map(EObject::eResource)
                .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                .filter(Objects::nonNull)
                .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                .findFirst();
        // @formatter:on

        if (optionalSiriusDiagramDescription.isPresent()) {
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription = optionalSiriusDiagramDescription.get();
            String defaultName = this.getDefaultName(siriusDiagramDescription, editingContext, siriusDiagramDescription).orElse(representationDescription.getLabel());
            result.add(new RepresentationDescriptionMetadata(representationDescription.getId(), representationDescription.getLabel(), defaultName));

        }
        return result;
    }

    private Optional<String> getDefaultName(org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription, IEditingContext editingContext, Object self) {
        String titleExpression = siriusDiagramDescription.getTitleExpression();
        if (titleExpression != null && !titleExpression.isBlank()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, self);
            variableManager.put(Environment.ENVIRONMENT, Environment.SIRIUS_COMPONENTS);
            AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
            return interpreter.evaluateExpression(variableManager.getVariables(), titleExpression).asString();
        }
        return Optional.empty();
    }
}

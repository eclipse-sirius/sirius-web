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
package org.eclipse.sirius.components.emf.compatibility.modeloperations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.core.api.WorkbenchSelection;
import org.eclipse.sirius.components.core.api.WorkbenchSelectionEntry;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.tool.Navigation;

/**
 * Handles the navigation operation.
 *
 * @author arichard
 */
public class NavigationOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final Navigation navigation;

    public NavigationOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, IRepresentationMetadataSearchService representationMetadataSearchService,
            AQLInterpreter interpreter, Navigation navigation) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.navigation = Objects.requireNonNull(navigation);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        Success success = new Success();
        boolean createIfNotExistent = this.navigation.isCreateIfNotExistent();
        if (!createIfNotExistent) {
            DiagramDescription diagramDescription = this.navigation.getDiagramDescription();
            String diagramDescriptionIdString = this.identifierProvider.getIdentifier(diagramDescription);

            UUID diagramDescriptionId = UUID.fromString(diagramDescriptionIdString);

            Object self = variables.get(VariableManager.SELF);
            String selfId = this.objectService.getId(self);

            // @formatter:off
            List<WorkbenchSelectionEntry> entries = this.representationMetadataSearchService.findAll(selfId).stream()
                    .filter(representationMetadata -> representationMetadata.getDescriptionId().equals(diagramDescriptionId))
                    .map(representationMetadata -> {
                        String id = representationMetadata.getId();
                        String label = representationMetadata.getLabel();
                        String kind = representationMetadata.getKind();
                        return new WorkbenchSelectionEntry(id, label, kind);
                    }).collect(Collectors.toList());
            // @formatter:on
            if (!entries.isEmpty()) {
                WorkbenchSelection newSelection = new WorkbenchSelection(entries);
                success.getParameters().put(Success.NEW_SELECTION, newSelection);
            }
        }
        return success;
    }

}

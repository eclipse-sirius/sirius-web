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
package org.eclipse.sirius.components.compatibility.emf.modeloperations;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
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

    private final Navigation navigation;

    public NavigationOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider,
            AQLInterpreter interpreter, Navigation navigation) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.navigation = Objects.requireNonNull(navigation);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        Success success = new Success();

        Object editingContextVariable = variables.get(IEditingContext.EDITING_CONTEXT);
        boolean createIfNotExistent = this.navigation.isCreateIfNotExistent();
        if (!createIfNotExistent && editingContextVariable instanceof IEditingContext editingContext) {
            DiagramDescription diagramDescription = this.navigation.getDiagramDescription();
            String diagramDescriptionId = this.identifierProvider.getIdentifier(diagramDescription);

            Object self = variables.get(VariableManager.SELF);
            String selfId = this.objectService.getId(self);

            List<WorkbenchSelectionEntry> entries = List.of();
            if (!entries.isEmpty()) {
                WorkbenchSelection newSelection = new WorkbenchSelection(entries);
                success.getParameters().put(Success.NEW_SELECTION, newSelection);
            }
        }
        return success;
    }

}

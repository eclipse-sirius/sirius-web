/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.dto.KeyBinding;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemPaletteProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.FetchTreeItemTool;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.SingleClickTreeItemTool;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IKeyBindingConverter;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.springframework.stereotype.Service;

/**
 * Tree item palette provider for view tree model.
 *
 * @author mchjarfadi
 */
@Service
public class ViewTreeItemPaletteProvider implements ITreeItemPaletteProvider {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IKeyBindingConverter keyBindingConverter;

    public ViewTreeItemPaletteProvider(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory, IKeyBindingConverter keyBindingConverter) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.keyBindingConverter = Objects.requireNonNull(keyBindingConverter);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return this.viewRepresentationDescriptionPredicate.test(treeDescription);
    }

    @Override
    public Palette getPalette(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        List<IPaletteEntry> paletteEntries = new ArrayList<>();

        var optionalTreeDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, treeDescription.getId())
                .filter(org.eclipse.sirius.components.view.tree.TreeDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.tree.TreeDescription.class::cast);
        if (optionalTreeDescription.isPresent()) {
            var viewTreeDescription = optionalTreeDescription.get();

            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTreeDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
            variableManager.put(TreeDescription.TREE, tree);
            variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
            variableManager.put(TreeDescription.ID, treeItem.getId());
            var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(variableManager);
            variableManager.put(RepresentationVariables.SELF.name(), semanticTreeItemObject);

            paletteEntries = viewTreeDescription.getContextMenuEntries().stream()
                    .filter(viewAction -> this.isValidActionPrecondition(viewAction, variableManager, interpreter))
                    .map(treeItemContextMenuEntry -> this.convertContextAction(treeItemContextMenuEntry, variableManager, interpreter))
                    .toList();
        }
        return new Palette("", List.of(), paletteEntries);
    }

    private IPaletteEntry convertContextAction(TreeItemContextMenuEntry viewTreeItemContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        IPaletteEntry result = null;
        var id = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewTreeItemContextAction).toString().getBytes()).toString();
        List<KeyBinding> keyBindings = viewTreeItemContextAction.getKeyBindings().stream()
                .map(this.keyBindingConverter::createKeyBinding)
                .flatMap(Optional::stream)
                .toList();
        switch (viewTreeItemContextAction) {
            case SingleClickTreeItemContextMenuEntry SingleClickTreeItemTool -> {
                var label = new StringValueProvider(interpreter, SingleClickTreeItemTool.getLabelExpression()).apply(variableManager);
                var iconURL = new MultiValueProvider(interpreter, SingleClickTreeItemTool.getIconURLExpression(), String.class).apply(variableManager);
                result = new SingleClickTreeItemTool(id, label, iconURL, SingleClickTreeItemTool.isWithImpactAnalysis(), keyBindings);
            }
            case FetchTreeItemContextMenuEntry fetchTreeItemContextMenuEntry -> {
                var label = new StringValueProvider(interpreter, fetchTreeItemContextMenuEntry.getLabelExpression()).apply(variableManager);
                var iconURL = new MultiValueProvider(interpreter, fetchTreeItemContextMenuEntry.getIconURLExpression(), String.class).apply(variableManager);
                result = new FetchTreeItemTool(id, label, iconURL, keyBindings);
            }
            case CustomTreeItemContextMenuEntry customTreeItemContextMenuEntry ->
                // Use a SingleClickTreeItemTool instance with a dedicated ID to pass the information to the frontend.
                result = new SingleClickTreeItemTool(customTreeItemContextMenuEntry.getContributionId(), "", List.of(), customTreeItemContextMenuEntry.isWithImpactAnalysis(), keyBindings);
            default -> {
            }
        }
        return result;
    }

    private boolean isValidActionPrecondition(TreeItemContextMenuEntry viewContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var precondition = viewContextAction.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            return this.evaluateBoolean(variableManager, interpreter, precondition);
        }
        return false;
    }

    private Boolean evaluateBoolean(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asBoolean()
                .orElse(true);
    }

}

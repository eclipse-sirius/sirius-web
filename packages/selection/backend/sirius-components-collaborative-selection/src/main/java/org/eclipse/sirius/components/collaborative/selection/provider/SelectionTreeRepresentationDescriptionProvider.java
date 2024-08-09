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
package org.eclipse.sirius.components.collaborative.selection.provider;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeDescriptionBuilder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.springframework.stereotype.Service;

/**
 * A specific implementation to provide a TreeDescription to display the Selection Dialog elements as a tree.
 *
 * @author fbarbin
 */
@Service
public class SelectionTreeRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private static final String SELECTION_DIALOG_TREE_PREFIX = null;

    public static final String SELECTION_DIALOG_TREE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("selection_dialog_tree_description".getBytes()).toString();

    public static final String REPRESENTATION_NAME = "Selection Dialog Tree Representation";

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final  IURLParser urlParser;

    private final ITreeDescriptionBuilder treeDescriptionBuilder;



    public SelectionTreeRepresentationDescriptionProvider(ITreeDescriptionBuilder treeDescriptionBuilder, IRepresentationDescriptionSearchService representationDescriptionSearchService, IURLParser urlParser) {
        this.treeDescriptionBuilder = Objects.requireNonNull(treeDescriptionBuilder);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Predicate<VariableManager> selectionTreeDescriptionCanCreatePredicate = variableManager -> variableManager.get(TreeConfiguration.TREE_ID, String.class)
                .map(treeId -> treeId.startsWith(SELECTION_DIALOG_TREE_PREFIX)).orElse(false);
        Function<VariableManager, Boolean> selectionTreeDescriptionIsSelectableProvider = variableManager -> {
            return true;
        };

        var treeDescription = this.treeDescriptionBuilder.createSelectableElementsTreeDescription(SELECTION_DIALOG_TREE_DESCRIPTION_ID, REPRESENTATION_NAME, selectionTreeDescriptionCanCreatePredicate, selectionTreeDescriptionIsSelectableProvider, null);

        return List.of(treeDescription);
    }



    private Optional<SelectionDescription> getSelectionDescription(VariableManager variableManager) {
        Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        if (optionalEditingContext.isPresent()) {
            Optional<String> optionalDescriptionId = this.getParamterValue(variableManager, "descriptionId");
            return optionalDescriptionId
                    .flatMap(descriptionId -> this.representationDescriptionSearchService.findById(optionalEditingContext.get(), descriptionId))
                    .filter(SelectionDescription.class::isInstance)
                    .map(SelectionDescription.class::cast);
        }
        else {
            return Optional.empty();
        }
    }

    private Optional<String> getParamterValue(VariableManager variableManager, String parameterKey) {
        return variableManager.get(TreeConfiguration.TREE_ID, String.class)
              .map(this.urlParser::getParameterValues)
              .map(parameters -> parameters.get(parameterKey))
              .filter(values -> !values.isEmpty())
              .map(values -> values.get(0));
    }




}

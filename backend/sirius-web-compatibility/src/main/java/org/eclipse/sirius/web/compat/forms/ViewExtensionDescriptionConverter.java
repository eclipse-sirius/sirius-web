/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.web.compat.api.IAQLInterpreterFactory;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.compat.services.forms.api.IViewExtensionDescriptionConverter;
import org.eclipse.sirius.web.compat.services.representations.IdentifiedElementLabelProvider;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.forms.description.PageDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius {@link ViewExtensionDescription} to an Sirius Web {@link FormDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewExtensionDescriptionConverter implements IViewExtensionDescriptionConverter {

    private final IObjectService objectService;

    private final IAQLInterpreterFactory interpreterFactory;

    private final IIdentifierProvider identifierProvider;

    private final ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    private final IdentifiedElementLabelProvider identifiedElementLabelProvider;

    public ViewExtensionDescriptionConverter(IObjectService objectService, IAQLInterpreterFactory interpreterFactory, IIdentifierProvider identifierProvider,
            ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory, IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider,
            IdentifiedElementLabelProvider identifiedElementLabelProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.semanticCandidatesProviderFactory = Objects.requireNonNull(semanticCandidatesProviderFactory);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
        this.identifiedElementLabelProvider = Objects.requireNonNull(identifiedElementLabelProvider);
    }

    @Override
    public FormDescription convert(ViewExtensionDescription viewExtensionDescription) {
        AQLInterpreter interpreter = this.interpreterFactory.create(viewExtensionDescription);
        PageDescriptionConverter pageDescriptionConverter = new PageDescriptionConverter(interpreter, this.identifierProvider, this.semanticCandidatesProviderFactory);
        GroupDescriptionConverter groupDescriptionConverter = new GroupDescriptionConverter(interpreter, this.objectService, this.identifierProvider, this.modelOperationHandlerSwitchProvider);

        // @formatter:off
        Map<org.eclipse.sirius.properties.GroupDescription, GroupDescription> siriusGroup2SiriusWebGroup = new HashMap<>();
        List<GroupDescription> groupDescriptions = viewExtensionDescription.getCategories().stream()
                .flatMap(category -> category.getPages().stream())
                .flatMap(page -> page.getGroups().stream())
                .map(groupDescription -> groupDescriptionConverter.convert(groupDescription, siriusGroup2SiriusWebGroup))
                .collect(Collectors.toList());

        List<PageDescription> pageDescriptions = viewExtensionDescription.getCategories().stream()
                .flatMap(category -> category.getPages().stream())
                .map(pageDescription -> pageDescriptionConverter.convert(pageDescription, siriusGroup2SiriusWebGroup))
                .collect(Collectors.toList());

        Function<VariableManager, String> labelProvider = variableManager -> Optional.ofNullable(variableManager.getVariables().get(VariableManager.SELF))
                .map(this.objectService::getFullLabel)
                .orElse("Properties"); //$NON-NLS-1$

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(UUID.fromString(this.identifierProvider.getIdentifier(viewExtensionDescription)))
                .label(this.identifiedElementLabelProvider.getLabel(viewExtensionDescription))
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .canCreatePredicate(variableManager -> false)
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }
}

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
package org.eclipse.sirius.web.services.relatedelements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.collaborative.forms.api.IRelatedElementsDescriptionProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Provides a tree widget description with incoming, current and outgoing trees.
 *
 * @author pcdavid
 */
@Service
public class DefaultRelatedElementsDescriptionProvider implements IRelatedElementsDescriptionProvider {

    public static final UUID FORM_DESCRIPTION_ID = UUID.nameUUIDFromBytes("defaultRelatedElementsForm".getBytes()); //$NON-NLS-1$

    private static final String GROUP_DESCRIPTION_ID = "defaultRelatedElementsGroup"; //$NON-NLS-1$

    private static final String PAGE_DESCRIPTION_ID = "defaultRelatedElementsPage"; //$NON-NLS-1$

    private static final String FORM_TITLE = "Related Elements"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final AdapterFactory adapterFactory;

    public DefaultRelatedElementsDescriptionProvider(IObjectService objectService, ComposedAdapterFactory adapterFactory) {
        this.objectService = Objects.requireNonNull(objectService);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    @Override
    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = List.of(this.getGroupDescription());

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(FORM_DESCRIPTION_ID.toString())
                .label(FORM_TITLE)
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> FORM_TITLE)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(List.of(this.getPageDescription(groupDescriptions)))
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        controlDescriptions.add(new IncomingTreeProvider(this.objectService, this.adapterFactory).getTreeDescription());
        controlDescriptions.add(new CurrentTreeProvider(this.objectService, this.adapterFactory).getTreeDescription());
        controlDescriptions.add(new OutgoingTreeProvider(this.objectService, this.adapterFactory).getTreeDescription());

        // @formatter:off
        return GroupDescription.newGroupDescription(GROUP_DESCRIPTION_ID)
                .idProvider(variableManager -> FORM_TITLE)
                .labelProvider(variableManager -> FORM_TITLE)
                .displayModeProvider(variableManager -> GroupDisplayMode.TOGGLEABLE_AREAS)
                .semanticElementsProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().collect(Collectors.toList()))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        // @formatter:off
        return PageDescription.newPageDescription(PAGE_DESCRIPTION_ID)
                .idProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                                                              .map(this.objectService::getId)
                                                              .orElseGet(() -> UUID.randomUUID().toString()))
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                                                                 .map(this.objectService::getLabel)
                                                                 .orElse("")) //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().collect(Collectors.toList()))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
        // @formatter:on
    }
}

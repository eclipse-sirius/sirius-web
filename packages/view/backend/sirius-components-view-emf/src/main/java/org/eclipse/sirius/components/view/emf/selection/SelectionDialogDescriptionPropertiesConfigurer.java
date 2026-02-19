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
package org.eclipse.sirius.components.view.emf.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesConfigurerService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesWidgetCreationService;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for the selection dialog description.
 *
 * @author gcoutable
 */
@Component
public class SelectionDialogDescriptionPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private final IPropertiesWidgetCreationService propertiesWidgetCreationService;

    private final IPropertiesConfigurerService propertiesConfigurerService;

    private final IViewEMFMessageService messageService;

    public SelectionDialogDescriptionPropertiesConfigurer(IPropertiesWidgetCreationService propertiesWidgetCreationService, IPropertiesConfigurerService propertiesConfigurerService,
            IViewEMFMessageService messageService) {
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getSelectionDialogDescriptionPageDescriptionProperties());
    }

    private PageDescription getSelectionDialogDescriptionPageDescriptionProperties() {

        List<AbstractControlDescription> controls = new ArrayList<>();

        var optional = this.propertiesWidgetCreationService.createCheckbox("selectionDialogPageDescription.optional",
                "Optional",
                desc -> ((SelectionDialogDescription) desc).isOptional(),
                (desc, newValue) -> ((SelectionDialogDescription) desc).setOptional(newValue),
                DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__OPTIONAL,
                Optional.of(variableManager -> "Check this option to add a widget to the Selection Dialog allowing to confirm the dialog without making a selection"));
        controls.add(optional);

        var noSelectionLabel = this.propertiesWidgetCreationService.createTextFieldWithHelperText("selectionDialogPageDescription.noSelectionLabel",
                "No Selection Label",
                desc -> String.valueOf(((SelectionDialogDescription) desc).getNoSelectionLabel()),
                (desc, newValue) -> ((SelectionDialogDescription) desc).setNoSelectionLabel(newValue),
                DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_LABEL,
                Optional.of(variableManager -> "The label of the widget added by making the selection optional. It should describe the behavior when the Selection Dialog is confirmed without selection. If no label is provided while the selection is optional, then the default label will be: \"Confirm without selection\""));
        controls.add(noSelectionLabel);

        var selectionMessage = this.propertiesWidgetCreationService.createTextFieldWithHelperText("selectionDialogPageDescription.selectionMessage",
                "Selection Message",
                desc -> String.valueOf(((SelectionDialogDescription) desc).getSelectionMessage()),
                (desc, newValue) -> ((SelectionDialogDescription) desc).setSelectionMessage(newValue),
                DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_MESSAGE,
                Optional.of(variableManager -> "Used to describe the behavior when the Selection Dialog is confirmed while a selection has been made. If no message is provided the default message will be: \"Use an existing element\""));
        controls.add(selectionMessage);

        var multiple = this.propertiesWidgetCreationService.createCheckbox("selectionDialogPageDescription.multiple",
                "Multiple",
                desc -> ((SelectionDialogDescription) desc).isMultiple(),
                (desc, newValue) -> ((SelectionDialogDescription) desc).setMultiple(newValue),
                DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__MULTIPLE,
                Optional.of(variableManager -> "Whether the selection dialog allows many element to be selected"));
        controls.add(multiple);

        var groupDescription = GroupDescription.newGroupDescription(UUID.nameUUIDFromBytes("selectionDialogGroupDescription".getBytes()).toString())
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> this.messageService.coreProperties())
                .semanticElementsProvider(this.propertiesConfigurerService.getSemanticElementsProvider())
                .controlDescriptions(controls)
                .build();

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(SelectionDialogDescription.class::isInstance)
                .isPresent();

        return PageDescription.newPageDescription(UUID.nameUUIDFromBytes("selectionDialogPageDescription".getBytes()).toString())
                .idProvider(variableManager -> "page")
                .labelProvider(variableManager -> "SelectionDialogDescription")
                .canCreatePredicate(canCreatePagePredicate)
                .semanticElementsProvider(this.propertiesConfigurerService.getSemanticElementsProvider())
                .groupDescriptions(List.of(groupDescription))
                .build();
    }
}

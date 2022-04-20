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
package org.eclipse.sirius.components.formdescriptioneditors.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.formdescriptioneditors.description.AbstractFormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorTextfieldDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ViewPackage;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the spreadsheet representation description.
 *
 * @author arichard
 */
@Configuration
public class FormDescriptionEditorDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private final IObjectService objectService;

    public FormDescriptionEditorDescriptionRegistryConfigurer(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        UUID id = UUID.nameUUIDFromBytes("FormDescriptionEditor".getBytes()); //$NON-NLS-1$
        String label = "FormDescriptionEditor"; //$NON-NLS-1$

        //@// @formatter:off
        Predicate<VariableManager> canCreatePredicate = variableManager -> variableManager.get(IRepresentationDescription.CLASS, Object.class)
                .filter(ViewPackage.eINSTANCE.getFormDescription()::equals)
                .isPresent();
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        List<AbstractFormDescriptionEditorWidgetDescription> widgetsDescriptions = new ArrayList<>();

        UUID textfieldId = UUID.nameUUIDFromBytes("FormDescriptionEditorTextfield".getBytes()); //$NON-NLS-1$
        String textfieldLabel = "Textfield"; //$NON-NLS-1$
        FormDescriptionEditorTextfieldDescription textfieldDescription = FormDescriptionEditorTextfieldDescription.newFormDescriptionEditorTextfieldDescription(textfieldId.toString())
                .label(textfieldLabel)
                .build();
        widgetsDescriptions.add(textfieldDescription);

        FormDescriptionEditorDescription formDescriptionEditorDescription = FormDescriptionEditorDescription.newFormDescriptionEditorDescription(id.toString())
                .label(label)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(canCreatePredicate)
                .widgetDescriptions(widgetsDescriptions)
                .build();

        registry.add(formDescriptionEditorDescription);
        // @formatter:on
    }
}

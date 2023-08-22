/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.util.ReferenceSwitch;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the Reference widget preview in the context of a Form Description Editor.
 *
 * @author pcdavid
 */
@Service
public class ReferenceWidgetPreviewConverterProvider implements IWidgetPreviewConverterProvider {

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        return new ReferenceSwitch<>() {
            @Override
            public AbstractWidgetDescription caseReferenceWidgetDescription(org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription referenceDescription) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, referenceDescription);
                String id = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
                var builder = ReferenceWidgetDescription.newReferenceWidgetDescription(UUID.randomUUID().toString())
                        .idProvider(vm -> id)
                        .targetObjectIdProvider(vm -> "")
                        .labelProvider(vm -> ReferenceWidgetPreviewConverterProvider.this.getWidgetLabel(referenceDescription, "Reference"))
                        .iconURLProvider(variableManager -> "")
                        .isReadOnlyProvider(variableManager -> false)
                        .itemsProvider(variableManager -> List.of())
                        .optionsProvider(variableManager -> List.of())
                        .itemIdProvider(variableManager -> "")
                        .itemKindProvider(variableManager -> "")
                        .itemLabelProvider(variableManager -> "")
                        .itemKindProvider(variableManager -> "")
                        .itemLabelProvider(variableManager -> "")
                        .itemImageURLProvider(variableManager -> "")
                        .settingProvider(variableManager -> {
                            // We need a non-null Setting instance which does not depend on any actual instance model.
                            EObject owner = EcorePackage.Literals.ECLASS;
                            return ((InternalEObject) owner).eSetting(EcorePackage.Literals.ECLASS__EALL_STRUCTURAL_FEATURES);
                        })
                        .ownerIdProvider(variableManager -> "")
                        .itemClickHandlerProvider(variableManager -> new Success())
                        .styleProvider(variableManager -> ReferenceWidgetPreviewConverterProvider.this.getWidgetStyle(referenceDescription, variableManager));
                if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
                    String helpText = ReferenceWidgetPreviewConverterProvider.this.getWidgetHelpText(referenceDescription);
                    builder.helpTextProvider(variableManager -> helpText);
                }
                return builder.build();
            }

        };
    }

    public String getWidgetLabel(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = widgetDescription.getName();
        String labelExpression = widgetDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith("aql:")) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }

    public String getWidgetHelpText(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription) {
        String helpText = "Help";
        String helpExpression = widgetDescription.getHelpExpression();
        if (helpExpression != null && !helpExpression.isBlank() && !helpExpression.startsWith("aql:")) {
            helpText = helpExpression;
        }
        return helpText;
    }

    private ReferenceWidgetStyle getWidgetStyle(org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        ReferenceWidgetDescriptionStyle style = referenceDescription.getStyle();
        if (style == null) {
            return null;
        }
        return new ReferenceWidgetStyleProvider(style).apply(variableManager);
    }

}

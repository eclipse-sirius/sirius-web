/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The provider of the id of the widget created.
 *
 * @author sbegaudeau
 */
public class WidgetIdProvider implements Function<VariableManager, String> {

    @Override
    public String apply(VariableManager variableManager) {
        var optionalParentElementId = variableManager.get(FormComponent.PARENT_ELEMENT_ID, String.class);
        var optionalControlDescriptionId = variableManager.get(FormComponent.CONTROL_DESCRIPTION_ID, String.class);
        var optionalTargetObjectId = variableManager.get(FormComponent.TARGET_OBJECT_ID, String.class);
        var optionalWidgetLabel = variableManager.get(FormComponent.WIDGET_LABEL, String.class);
        if (optionalParentElementId.isPresent() && optionalControlDescriptionId.isPresent() && optionalTargetObjectId.isPresent() && optionalWidgetLabel.isPresent()) {
            return this.computeWidgetId(optionalParentElementId.get(), optionalControlDescriptionId.get(), optionalTargetObjectId.get(), optionalWidgetLabel.get());
        }
        return "";
    }

    private String computeWidgetId(String parentElementId, String controlDescriptionId, String targetObjectId, String label) {
        String rawIdentifier = parentElementId + controlDescriptionId + targetObjectId + label;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }

}

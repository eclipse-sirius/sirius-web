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
package org.eclipse.sirius.components.widget.reference;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the rendering of reference widget components.
 *
 * @author tgiraudet
 */
public class ReferenceWidgetComponentTests {

    @Test
    @DisplayName("Given a reference widget, when its clear button is configured, then it is rendered")
    public void givenReferenceWidgetWhenItsClearButtonIsConfiguredThenItIsRendered() {
        var component = new ReferenceWidgetComponent(new ReferenceWidgetComponentProps(new VariableManager(), this.newReferenceWidgetDescription(true)));

        var props = (ReferenceElementProps) component.render().getProps();

        assertThat(props.getChildren())
                .anySatisfy(element -> assertThat(element.getType()).isEqualTo(ReferenceWidgetClearButtonComponent.class));
    }

    @Test
    @DisplayName("Given a reference widget, when its clear button is not configured, then it is not rendered")
    public void givenReferenceWidgetWhenItsClearButtonIsNotConfiguredThenItIsNotRendered() {
        var component = new ReferenceWidgetComponent(new ReferenceWidgetComponentProps(new VariableManager(), this.newReferenceWidgetDescription(false)));

        var props = (ReferenceElementProps) component.render().getProps();

        assertThat(props.getChildren())
                .noneSatisfy(element -> assertThat(element.getType()).isEqualTo(ReferenceWidgetClearButtonComponent.class));
    }

    private ReferenceWidgetDescription newReferenceWidgetDescription(boolean hasClearButton) {
        var builder = ReferenceWidgetDescription.newReferenceWidgetDescription("referenceWidgetDescriptionId")
                .idProvider(variableManager -> "referenceWidgetId")
                .targetObjectIdProvider(variableManager -> "targetObjectId")
                .labelProvider(variableManager -> "Reference widget")
                .itemsProvider(variableManager -> List.of())
                .optionsProvider(variableManager -> List.of())
                .itemIdProvider(variableManager -> "itemId")
                .itemLabelProvider(variableManager -> "Item")
                .itemKindProvider(variableManager -> "itemKind")
                .itemIconURLProvider(variableManager -> List.of())
                .ownerKindProvider(variableManager -> "ownerKind")
                .referenceKindProvider(variableManager -> "referenceKind")
                .isContainmentProvider(variableManager -> false)
                .isManyProvider(variableManager -> false)
                .styleProvider(variableManager -> null)
                .ownerIdProvider(variableManager -> "ownerId")
                .diagnosticsProvider(variableManager -> List.of())
                .referenceNameProvider(variableManager -> "referenceName")
                .kindProvider(object -> "")
                .messageProvider(object -> "");
        if (hasClearButton) {
            builder.clearButtonDescription(ReferenceWidgetClearButtonDescription.newReferenceWidgetClearButtonDescription().build());
        }
        return builder.build();
    }
}

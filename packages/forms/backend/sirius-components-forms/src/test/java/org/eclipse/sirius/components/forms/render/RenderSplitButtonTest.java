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
package org.eclipse.sirius.components.forms.render;

import java.util.List;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.SplitButton;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Tests the rendering of a SplitButton component.
 *
 * @author mcharfadi
 */
public class RenderSplitButtonTest {

    private static final String SPLITBUTTON_DESCRIPTION_ID = "splitButtonDescriptionId";

    private static final String SPLITBUTTON_ID = "splitButtonId";

    private static final String BUTTON_DESCRIPTION_ID = "buttonDescriptionId";

    private static final String BUTTON_ID = "buttonId";

    private static final String BUTTON_DESCRIPTION_ID2 = "buttonDescriptionId2";

    private static final String BUTTON_ID2 = "buttonId2";

    private static final String LABEL = "label";

    private static final String BACKGROUNDCOLOR = "value";

    private ButtonStyle style;

    private Object self;

    @BeforeEach
    public void setup() {
        this.style = ButtonStyle.newButtonStyle()
                .backgroundColor(BACKGROUNDCOLOR)
                .foregroundColor("white")
                .fontSize(12)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .build();
        this.self = new Object();
    }

    @Test
    public void testRenderSplitButtonWithoutActions() {
        SplitButtonDescription splitButtonDescription = SplitButtonDescription.newSplitButtonDescription(SPLITBUTTON_DESCRIPTION_ID)
                .targetObjectIdProvider(this.constantProvider(SPLITBUTTON_ID))
                .idProvider(this.constantProvider(SPLITBUTTON_ID))
                .labelProvider(this.constantProvider(LABEL))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .actions(List.of())
                .build();

        FormDescription formDescription = this.createSingleWidgetForm(splitButtonDescription);
        Form form = this.renderForm(formDescription);

        assertThat(form).isNotNull();
        assertThat(form.getPages()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets().get(0)).isInstanceOf(SplitButton.class);
        SplitButton renderedSplitButton = (SplitButton) form.getPages().get(0).getGroups().get(0).getWidgets().get(0);
        assertThat(renderedSplitButton).isNotNull();
        assertThat(renderedSplitButton.getActions()).isEmpty();
    }

    @Test
    public void testRenderSplitButtonWithActions() {
        ButtonDescription buttonDescription = createButtonDescription(BUTTON_DESCRIPTION_ID, BUTTON_DESCRIPTION_ID, LABEL);
        ButtonDescription buttonDescription2 = createButtonDescription(BUTTON_DESCRIPTION_ID2, BUTTON_DESCRIPTION_ID2, LABEL);

        SplitButtonDescription splitButtonDescription = SplitButtonDescription.newSplitButtonDescription(SPLITBUTTON_DESCRIPTION_ID)
                .targetObjectIdProvider(this.constantProvider(SPLITBUTTON_DESCRIPTION_ID))
                .idProvider(this.constantProvider(SPLITBUTTON_DESCRIPTION_ID))
                .labelProvider(this.constantProvider(LABEL))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .actions(List.of(buttonDescription, buttonDescription2))
                .build();

        FormDescription formDescription = this.createSingleWidgetForm(splitButtonDescription);
        Form form = this.renderForm(formDescription);

        assertThat(form).isNotNull();
        assertThat(form.getPages()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets().get(0)).isInstanceOf(SplitButton.class);
        SplitButton renderedSplitButton = (SplitButton) form.getPages().get(0).getGroups().get(0).getWidgets().get(0);
        assertThat(renderedSplitButton).isNotNull();
        assertThat(renderedSplitButton.getActions()).hasSize(2);
        Button renderedButton = renderedSplitButton.getActions().get(0);
        assertThat(renderedButton).isNotNull();
        assertThat(renderedButton.getStyle().getBackgroundColor()).isEqualTo(BACKGROUNDCOLOR);
        assertThat(renderedButton.getId()).isEqualTo(BUTTON_DESCRIPTION_ID);
        Button renderedButton2 = renderedSplitButton.getActions().get(1);
        assertThat(renderedButton2).isNotNull();
        assertThat(renderedButton2.getId()).isEqualTo(BUTTON_DESCRIPTION_ID2);
    }

    private ButtonDescription createButtonDescription(String descriptionId, String id, String label) {
        return ButtonDescription.newButtonDescription(descriptionId)
                .targetObjectIdProvider(this.constantProvider(id))
                .idProvider(this.constantProvider(id))
                .labelProvider(this.constantProvider(label))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .imageURLProvider(variableManager -> "")
                .buttonLabelProvider((variableManager) -> LABEL)
                .pushButtonHandler((variableManager) -> new Success())
                .styleProvider((variableManager) -> style)
                .build();
    }

    private Form renderForm(FormDescription formDescription) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, this.self);
        FormComponentProps props = new FormComponentProps(variableManager, formDescription, List.of());
        return new FormRenderer(List.of()).render(new Element(FormComponent.class, props));
    }

    private FormDescription createSingleWidgetForm(SplitButtonDescription splitButtonDescription) {
        GroupDescription groupDescription = GroupDescription.newGroupDescription("groupDescriptionId")
                .idProvider(this.constantProvider("groupId"))
                .labelProvider(this.constantProvider("groupLabel"))
                .displayModeProvider(this.constantProvider(GroupDisplayMode.LIST))
                .toolbarActionDescriptions(List.of())
                .controlDescriptions(List.of(splitButtonDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        PageDescription pageDescription = PageDescription.newPageDescription("pageDescriptionId")
                .idProvider(this.constantProvider("pageId"))
                .labelProvider(this.constantProvider("pageLabel"))
                .canCreatePredicate(variableManager -> true)
                .groupDescriptions(List.of(groupDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        return FormDescription.newFormDescription("formDescriptionId")
                .targetObjectIdProvider(this.constantProvider(SPLITBUTTON_DESCRIPTION_ID))
                .label("formDescriptionLabel")
                .idProvider(this.constantProvider("formId"))
                .labelProvider(this.constantProvider("formLabel"))
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(List.of(pageDescription))
                .targetObjectIdProvider(this.constantProvider("selfId"))
                .build();
    }

    private <T> Function<VariableManager, T> constantProvider(T value) {
        return (VariableManager variableManager) -> value;
    }
}

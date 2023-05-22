/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.TextfieldStyle;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the rendering of a text field component.
 *
 * @author pcdavid
 */
public class RenderTextfieldTest {

    private TextfieldStyle style;

    private Object self;

    @BeforeEach
    public void setup() {
        // @formatter:off
        this.style = TextfieldStyle.newTextfieldStyle()
                .foregroundColor("black")
                .backgroundColor("white")
                .fontSize(12)
                .italic(false)
                .bold(false)
                .underline(false)
                .strikeThrough(false)
                .build();
        // @formatter:on
        this.self = new Object();
    }

    @Test
    public void testRenderTextfieldWithoutProposalProvider() {
        // @formatter:off
        TextfieldDescription textDescription = TextfieldDescription.newTextfieldDescription("textfieldDescriptionId")
                .idProvider(this.constantProvider("testfieldId"))
                .labelProvider(this.constantProvider("label"))
                .valueProvider(this.constantProvider("value"))
                .newValueHandler((v, s) -> new Success()).diagnosticsProvider(variableManager -> List.of()).kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(this.constantProvider(this.style)).build();

        // @formatter:on

        FormDescription formDescription = this.createSingleWidgetForm(textDescription);
        Form form = this.renderForm(formDescription);

        assertThat(form).isNotNull();
        assertThat(form.getPages()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets().get(0)).isInstanceOf(Textfield.class);
        Textfield renderedTextField = (Textfield) form.getPages().get(0).getGroups().get(0).getWidgets().get(0);
        assertThat(renderedTextField.getCompletionProposalsProvider()).isNull();
        assertThat(renderedTextField.isSupportsCompletion()).isFalse();
    }

    @Test
    public void testRenderTextfieldWithProposalProvider() {
        List<CompletionProposal> proposals = List.of(new CompletionProposal("Proposal", "textToInsert", 0));

        // @formatter:off
        TextfieldDescription textDescription = TextfieldDescription.newTextfieldDescription("textfieldDescriptionId")
                .idProvider(this.constantProvider("testfieldId"))
                .labelProvider(this.constantProvider("label"))
                .valueProvider(this.constantProvider("value"))
                .completionProposalsProvider(this.constantProvider(proposals))
                .newValueHandler((v, s) -> new Success()).diagnosticsProvider(variableManager -> List.of()).kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .styleProvider(this.constantProvider(this.style)).build();
        // @formatter:on

        FormDescription formDescription = this.createSingleWidgetForm(textDescription);
        Form form = this.renderForm(formDescription);

        assertThat(form).isNotNull();
        assertThat(form.getPages()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets()).hasSize(1);
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets().get(0)).isInstanceOf(Textfield.class);
        Textfield renderedTextField = (Textfield) form.getPages().get(0).getGroups().get(0).getWidgets().get(0);
        assertThat(renderedTextField.getCompletionProposalsProvider()).isNotNull();
        assertThat(renderedTextField.isSupportsCompletion()).isTrue();
        assertThat(renderedTextField.getCompletionProposalsProvider().apply(new CompletionRequest("", 0))).isEqualTo(proposals);
    }

    private Form renderForm(FormDescription formDescription) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, this.self);
        FormComponentProps props = new FormComponentProps(variableManager, formDescription);
        Form form = new FormRenderer().render(new Element(FormComponent.class, props));
        return form;
    }

    private FormDescription createSingleWidgetForm(TextfieldDescription textDescription) {
        // @formatter:off
        GroupDescription groupDescription = GroupDescription.newGroupDescription("groupDescriptionId")
                .idProvider(this.constantProvider("groupId"))
                .labelProvider(this.constantProvider("groupLabel"))
                .displayModeProvider(this.constantProvider(GroupDisplayMode.LIST))
                .toolbarActionDescriptions(List.of())
                .controlDescriptions(List.of(textDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        PageDescription pageDescription = PageDescription.newPageDescription("pageDescriptionId")
                .idProvider(this.constantProvider("pageId"))
                .labelProvider(this.constantProvider("pageLabel"))
                .canCreatePredicate(variableManager -> true)
                .groupDescriptions(List.of(groupDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        FormDescription formDescription = FormDescription.newFormDescription("formDescriptionId")
                .label("formDescriptionLabel")
                .idProvider(this.constantProvider("formId"))
                .labelProvider(this.constantProvider("formLabel"))
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(List.of(pageDescription))
                .targetObjectIdProvider(this.constantProvider("selfId"))
                .build();
        return formDescription;
        // @formatter:on
    }

    private <T> Function<VariableManager, T> constantProvider(T value) {
        return (VariableManager variableManager) -> value;
    }
}

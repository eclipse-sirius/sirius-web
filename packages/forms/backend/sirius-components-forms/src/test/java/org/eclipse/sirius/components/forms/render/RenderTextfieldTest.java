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
                .foregroundColor("black") //$NON-NLS-1$
                .backgroundColor("white") //$NON-NLS-1$
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
        TextfieldDescription textDescription = TextfieldDescription.newTextfieldDescription("textfieldDescriptionId") //$NON-NLS-1$
                .idProvider(this.constantProvider("testfieldId")) //$NON-NLS-1$
                .labelProvider(this.constantProvider("label")) //$NON-NLS-1$
                .valueProvider(this.constantProvider("value")) //$NON-NLS-1$
                .newValueHandler((v, s) -> new Success()).diagnosticsProvider(variableManager -> List.of()).kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
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
        List<CompletionProposal> proposals = List.of(new CompletionProposal("Proposal", "textToInsert", 0)); //$NON-NLS-1$ //$NON-NLS-2$

        // @formatter:off
        TextfieldDescription textDescription = TextfieldDescription.newTextfieldDescription("textfieldDescriptionId") //$NON-NLS-1$
                .idProvider(this.constantProvider("testfieldId")) //$NON-NLS-1$
                .labelProvider(this.constantProvider("label")) //$NON-NLS-1$
                .valueProvider(this.constantProvider("value")) //$NON-NLS-1$
                .completionProposalsProvider(this.constantProvider(proposals))
                .newValueHandler((v, s) -> new Success()).diagnosticsProvider(variableManager -> List.of()).kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
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
        assertThat(renderedTextField.getCompletionProposalsProvider().apply(new CompletionRequest("", 0))).isEqualTo(proposals); //$NON-NLS-1$
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
        GroupDescription groupDescription = GroupDescription.newGroupDescription("groupDescriptionId") //$NON-NLS-1$
                .idProvider(this.constantProvider("groupId")) //$NON-NLS-1$
                .labelProvider(this.constantProvider("groupLabel")) //$NON-NLS-1$
                .displayModeProvider(this.constantProvider(GroupDisplayMode.LIST))
                .toolbarActionDescriptions(List.of())
                .controlDescriptions(List.of(textDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        PageDescription pageDescription = PageDescription.newPageDescription("pageDescriptionId") //$NON-NLS-1$
                .idProvider(this.constantProvider("pageId")) //$NON-NLS-1$
                .labelProvider(this.constantProvider("pageLabel")) //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .groupDescriptions(List.of(groupDescription))
                .semanticElementsProvider(this.constantProvider(List.of(this.self)))
                .build();
        FormDescription formDescription = FormDescription.newFormDescription("formDescriptionId") //$NON-NLS-1$
                .label("formDescriptionLabel") //$NON-NLS-1$
                .idProvider(this.constantProvider("formId")) //$NON-NLS-1$
                .labelProvider(this.constantProvider("formLabel")) //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .groupDescriptions(List.of(groupDescription))
                .pageDescriptions(List.of(pageDescription))
                .targetObjectIdProvider(this.constantProvider("selfId")) //$NON-NLS-1$
                .build();
        return formDescription;
        // @formatter:on
    }

    private <T> Function<VariableManager, T> constantProvider(T value) {
        return (VariableManager variableManager) -> value;
    }
}

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
package org.eclipse.sirius.components.collaborative.forms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.ToolbarAction;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the form query service.
 *
 * @author frouene
 */
public class FormQueryServiceTests {

    private static final String FORM_ID = UUID.randomUUID().toString();

    @Test
    public void testFindWidget() {

        Function<String, IStatus> newValueHandler = newValue -> new Success();
        Supplier<IStatus> pushHandler = Success::new;

        Textfield textfield = Textfield.newTextfield("TextfieldId")
                .label("label")
                .value("Previous value")
                .newValueHandler(newValueHandler)
                .diagnostics(List.of())
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(List.of(textfield))
                .build();

        ToolbarAction toolbarAction = ToolbarAction.newToolbarAction("pageToolbarId")
                .label("toolbar label")
                .pushButtonHandler(pushHandler)
                .diagnostics(List.of())
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(List.of(group))
                .toolbarActions(List.of(toolbarAction))
                .build();

        Form form = Form.newForm(FORM_ID)
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(List.of(page))
                .build();

        FormQueryService formQueryService = new FormQueryService();

        var result = formQueryService.findWidget(form, "TextfieldId");
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isInstanceOf(Textfield.class);

        result = formQueryService.findWidget(form, "pageToolbarId");
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isInstanceOf(ToolbarAction.class);
    }

}

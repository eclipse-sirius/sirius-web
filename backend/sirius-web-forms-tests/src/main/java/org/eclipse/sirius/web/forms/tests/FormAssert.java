/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.web.forms.tests.FormAssertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Group;
import org.eclipse.sirius.web.forms.Page;

/**
 * Custom assertion class used to performs some tests on a form.
 *
 * @author lfasani
 */
public class FormAssert extends AbstractAssert<FormAssert, Form> {

    public FormAssert(Form form) {
        super(form, FormAssert.class);
    }

    /**
     * Asserts that the actual form has the same properties as the given form (without taking into account the position
     * and size of any elements.
     *
     * @param form
     *            The given form
     * @param idPolicy
     *            Indicates if the identifiers of the form elements should match too
     * @return The current form assert
     */
    public FormAssert matchesRecursively(Form form, IdPolicy idPolicy) {
        this.matches(form, idPolicy);

        if (this.actual != null && form != null) {
            assertThat(this.actual.getPages()).hasSize(form.getPages().size());

            if (this.actual.getPages().size() == form.getPages().size()) {
                int size = this.actual.getPages().size();
                for (int i = 0; i < size; i++) {
                    Page actualPage = this.actual.getPages().get(i);
                    Page page = form.getPages().get(i);
                    assertThat(actualPage).matchesRecursively(page, idPolicy);
                }
            }
        }

        return this;
    }

    /**
     * Asserts that the actual form has the same properties as the given form.
     *
     * @param form
     *            The given form
     * @param idPolicy
     *            Indicates if the identifiers of the form elements should match too
     * @return The current form assert
     */
    public FormAssert matches(Form form, IdPolicy idPolicy) {
        if (form != null) {
            this.isNotNull();

            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(this.actual.getId()).isEqualTo(form.getId());
            }

            assertThat(this.actual.getLabel()).isEqualTo(form.getLabel());
        } else {
            this.isNull();
        }

        return this;
    }

    public void isValid() {
        this.isNotNull();

        List<String> ids = new ArrayList<>();
        this.actual.getPages().forEach(page -> this.visitPageId(ids, page));
    }

    private void visitPageId(List<String> ids, Page page) {
        if (ids.contains(page.getId())) {
            this.failWithMessage("The id of the page <%s> already exists in the form", page.getId()); //$NON-NLS-1$
        }
        ids.add(page.getId());

        page.getGroups().forEach(group -> this.visitGroupId(ids, group));
    }

    private void visitGroupId(List<String> ids, Group group) {
        if (ids.contains(group.getId())) {
            this.failWithMessage("The id of the group <%s> already exists in the form", group.getId()); //$NON-NLS-1$
        }
        ids.add(group.getId());

        group.getWidgets().forEach(widget -> this.visitWidgetId(ids, widget));
    }

    private void visitWidgetId(List<String> ids, AbstractWidget widget) {
        if (widget != null) {
            if (ids.contains(widget.getId())) {
                this.failWithMessage("The id of the widget <%s> already exists in the form", widget.getId()); //$NON-NLS-1$
            }
            ids.add(widget.getId());
        }
    }
}

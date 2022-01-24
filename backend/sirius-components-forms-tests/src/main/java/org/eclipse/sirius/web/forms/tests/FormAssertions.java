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

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Group;
import org.eclipse.sirius.web.forms.ListItem;
import org.eclipse.sirius.web.forms.Page;

/**
 * Entry point of all the AssertJ assertions with the form specific ones too.
 *
 * @author lfasani
 */
public class FormAssertions extends Assertions {
    public static FormAssert assertThat(Form form) {
        return new FormAssert(form);
    }

    public static PageAssert assertThat(Page page) {
        return new PageAssert(page);
    }

    public static GroupAssert assertThat(Group group) {
        return new GroupAssert(group);
    }

    public static WidgetAssert assertThat(AbstractWidget widget) {
        return new WidgetAssert(widget);
    }

    public static ListItemAssert assertThat(ListItem listItem) {
        return new ListItemAssert(listItem);
    }
}

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

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Group;

/**
 * Custom assertion class used to perform some tests on a group.
 *
 * @author lfasani
 */
public class GroupAssert extends AbstractAssert<GroupAssert, Group> {

    public GroupAssert(Group group) {
        super(group, GroupAssert.class);
    }

    public GroupAssert matchesRecursively(Group group, IdPolicy idPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(group.getId());
        }

        assertThat(this.actual.getLabel()).isEqualTo(group.getLabel());

        if (this.actual.getWidgets().size() == group.getWidgets().size()) {
            int size = this.actual.getWidgets().size();
            for (int i = 0; i < size; i++) {
                AbstractWidget actualWidget = this.actual.getWidgets().get(i);
                AbstractWidget widget = group.getWidgets().get(i);
                assertThat(actualWidget).matches(widget, idPolicy);
            }
        }

        return this;
    }

}

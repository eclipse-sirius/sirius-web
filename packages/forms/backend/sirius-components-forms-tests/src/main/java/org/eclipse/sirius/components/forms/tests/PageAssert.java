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
package org.eclipse.sirius.components.forms.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.forms.tests.FormAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;

/**
 * Custom assertion class used to perform some tests on a page.
 *
 * @author lfasani
 */
public class PageAssert extends AbstractAssert<PageAssert, Page> {
    public PageAssert(Page page) {
        super(page, PageAssert.class);
    }

    public PageAssert matchesRecursively(Page page, IdPolicy idPolicy) {
        if (page != null) {
            this.isNotNull();

            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(this.actual.getId()).isEqualTo(page.getId());
            }

            assertThat(this.actual.getLabel()).isEqualTo(page.getLabel());

            if (this.actual.getGroups().size() == page.getGroups().size()) {
                int size = this.actual.getGroups().size();
                for (int i = 0; i < size; i++) {
                    Group actualGroup = this.actual.getGroups().get(i);
                    Group group = page.getGroups().get(i);
                    assertThat(actualGroup).matchesRecursively(group, idPolicy);
                }
            }
        }

        return this;
    }
}

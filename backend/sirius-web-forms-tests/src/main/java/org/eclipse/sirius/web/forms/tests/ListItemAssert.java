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

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.forms.ListItem;

/**
 * Custom assertion class used to perform some tests on a ListItem.
 *
 * @author lfasani
 */
public class ListItemAssert extends AbstractAssert<ListItemAssert, ListItem> {

    public ListItemAssert(ListItem actual) {
        super(actual, ListItemAssert.class);
    }

    public ListItemAssert matches(ListItem listItem, IdPolicy idPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(listItem.getId());
        }

        assertThat(this.actual.getLabel()).isEqualTo(listItem.getLabel());

        return this;
    }

}

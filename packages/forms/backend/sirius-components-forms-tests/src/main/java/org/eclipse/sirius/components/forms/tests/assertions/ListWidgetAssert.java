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
package org.eclipse.sirius.components.forms.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;

/**
 * Custom assertion used to perform some tests on a List widget.
 *
 * @author gcoutable
 */
public class ListWidgetAssert extends AbstractAssert<ListWidgetAssert, org.eclipse.sirius.components.forms.List> {

    public ListWidgetAssert(org.eclipse.sirius.components.forms.List list) {
        super(list, ListWidgetAssert.class);
    }

    public ListWidgetAssert hasListItemWithLabel(String label) {
        var hasListItemWithLabel = this.actual.getItems().stream()
                .anyMatch(listItem -> listItem.getLabel().equals(label));
        assertThat(hasListItemWithLabel)
                .withFailMessage("Expecting at least one list item with the label %s but none has been found.", label)
                .isTrue();

        return this;
    }
}

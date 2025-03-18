/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.tables.ColumnSort;

/**
 * Java services for table representations.
 *
 * @author frouene
 */
public class PapayaTableServices {

    public List<NamedElement> sortNamedElement(List<Object> objects, List<ColumnSort> columnSort) {
        var namedElements = new ArrayList<>(objects.stream().filter(NamedElement.class::isInstance).map(NamedElement.class::cast).toList());
        for (int i = columnSort.size() - 1; i >= 0; i--) {
            var sort = columnSort.get(i);
            if ("Name".equals(sort.id())) {
                if (sort.desc()) {
                    namedElements.sort(Comparator.comparing(NamedElement::getName, String.CASE_INSENSITIVE_ORDER).reversed());
                } else {
                    namedElements.sort(Comparator.comparing(NamedElement::getName, String.CASE_INSENSITIVE_ORDER));
                }
            }
            if ("Description".equals(sort.id())) {
                if (sort.desc()) {
                    namedElements.sort(Comparator.comparing(NamedElement::getDescription, String.CASE_INSENSITIVE_ORDER).reversed());
                } else {
                    namedElements.sort(Comparator.comparing(NamedElement::getDescription, String.CASE_INSENSITIVE_ORDER));
                }
            }
        }
        return namedElements;
    }
}

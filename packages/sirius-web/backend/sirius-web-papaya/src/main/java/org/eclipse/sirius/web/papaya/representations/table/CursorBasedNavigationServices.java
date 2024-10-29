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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;

/**
 * Used to navigate in a tree of EMF objects using a cursor based approach.
 *
 * @author sbegaudeau
 */
public class CursorBasedNavigationServices {

    public PaginatedData collect(EObject self, EObject cursor, String direction, int size) {
        return this.collect(self, cursor, direction, size, object -> true);
    }

    public PaginatedData collect(EObject self, EObject cursor, String direction, int size, Predicate<EObject> filterPredicate) {
        var rootEObject = self;
        if (cursor != null) {
            rootEObject = cursor;
        }

        boolean isPrevDirection = "PREV".equalsIgnoreCase(direction);

        Iterator<EObject> iterator = new ForwardTreeIterator(rootEObject, cursor == null, filterPredicate);
        if (isPrevDirection) {
            iterator = new BackwardTreeIterator(rootEObject, false, filterPredicate);
        }

        int count = 0;
        List<Object> result = new ArrayList<>();
        while (count < size && iterator.hasNext()) {
            result.add(iterator.next());
            count = count + 1;
        }
        if (isPrevDirection) {
            Collections.reverse(result);
        }
        boolean hasPreviousPage;
        if (isPrevDirection) {
            hasPreviousPage = iterator.hasNext();
        } else {
            hasPreviousPage = cursor != null;
        }
        boolean hasNextPage;
        if (isPrevDirection) {
            hasNextPage = true;
        } else {
            hasNextPage = count == size && iterator.hasNext();
        }
        return new PaginatedData(result, hasPreviousPage, hasNextPage, -1);
    }
}

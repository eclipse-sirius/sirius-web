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
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * Used to navigate backward in an EMF tree.
 *
 * @author sbegaudeau
 */
public class BackwardTreeIterator implements Iterator<EObject> {

    private final Predicate<EObject> filterPredicate;

    private final EObject rootEObject;

    private final boolean includeRoot;

    private List<Iterator<EObject>> iterators;

    private Iterator<EObject> nextPruneIterator;

    private Iterator<EObject> nextRemoveIterator;

    public BackwardTreeIterator(EObject rootEObject, boolean includeRoot, Predicate<EObject> filterPredicate) {
        this.rootEObject = Objects.requireNonNull(rootEObject);
        this.includeRoot = includeRoot;
        this.filterPredicate = Objects.requireNonNull(filterPredicate);
    }

    @Override
    public boolean hasNext() {
        boolean hasNext;
        if (this.iterators == null && !this.includeRoot) {
            hasNext = this.hasAnyPreviousSiblingsOrParent();
        } else {
            hasNext = this.hasMoreElements();
        }
        return hasNext;
    }

    private boolean hasAnyPreviousSiblingsOrParent() {
        var iterator = this.getPreviousSiblingsOrParent(this.rootEObject);
        this.iterators = new ArrayList<>();
        this.iterators.add(iterator);
        return iterator.hasNext();
    }

    private Iterator<EObject> getPreviousSiblingsOrParent(EObject eObject) {
        Iterator<EObject> iterator = null;
        var currentEObject = eObject;
        while (iterator == null && currentEObject != null) {
            if (currentEObject.eContainer() != null) {
                var siblings = currentEObject.eContainer().eContents();
                var index = siblings.indexOf(currentEObject);
                if (index >= 1) {
                    var previousSibling = siblings.get(index - 1);
                    var lastChild = this.getLastChild(previousSibling);
                    if (this.filterPredicate.test(lastChild)) {
                        iterator = List.of(lastChild).iterator();
                    }
                    currentEObject = lastChild;
                } else {
                    if (this.filterPredicate.test(currentEObject.eContainer())) {
                        iterator = List.of(currentEObject.eContainer()).iterator();
                    }
                    currentEObject = currentEObject.eContainer();
                }
            } else {
                currentEObject = currentEObject.eContainer();
            }
        }
        if (iterator == null) {
            iterator = Collections.emptyIterator();
        }
        return iterator;
    }

    private EObject getLastChild(EObject eObject) {
        EList<EObject> nextChildren = eObject.eContents();
        if (nextChildren.isEmpty()) {
            return eObject;
        }
        EObject lastChild = nextChildren.get(nextChildren.size() - 1);

        while (!lastChild.eContents().isEmpty()) {
            nextChildren = lastChild.eContents();
            lastChild = nextChildren.get(nextChildren.size() - 1);
        }
        return lastChild;
    }

    private boolean hasMoreElements() {
        return this.iterators == null
                || !this.iterators.isEmpty() && this.iterators.get(this.iterators.size() - 1).hasNext();
    }


    @Override
    public EObject next() {
        EObject result = null;
        if (this.iterators == null) {
            this.nextPruneIterator = this.getPreviousSiblingsOrParent(this.rootEObject);
            this.iterators = new ArrayList<>();
            this.iterators.add(this.nextPruneIterator);
            if (this.includeRoot) {
                result = this.rootEObject;
            }
        } else if (!this.iterators.isEmpty()) {
            var currentIterator = this.iterators.get(this.iterators.size() - 1);
            result = currentIterator.next();
            this.nextRemoveIterator = currentIterator;

            this.nextPruneIterator = null;

            while (!currentIterator.hasNext()) {
                this.iterators.remove(this.iterators.size() - 1);
                if (this.iterators.isEmpty()) {
                    var iterator = this.getPreviousSiblingsOrParent(result);
                    if (iterator.hasNext()) {
                        this.nextPruneIterator = iterator;
                        this.iterators.add(iterator);
                    }
                    break;
                }
                currentIterator = this.iterators.get(this.iterators.size() - 1);
            }
        }

        return result;
    }

    @Override
    public void remove() {
        if (this.nextRemoveIterator != null) {
            this.nextRemoveIterator.remove();
        }
    }

    public void prune() {
        if (this.nextPruneIterator != null) {
            if (!this.iterators.isEmpty() && this.iterators.get(this.iterators.size() - 1) == this.nextPruneIterator) {
                this.iterators.remove(this.iterators.size() - 1);

                while (!this.iterators.isEmpty() && !this.iterators.get(this.iterators.size() - 1).hasNext()) {
                    this.iterators.remove(this.iterators.size() - 1);
                }
            }

            this.nextPruneIterator = null;
        }
    }
}

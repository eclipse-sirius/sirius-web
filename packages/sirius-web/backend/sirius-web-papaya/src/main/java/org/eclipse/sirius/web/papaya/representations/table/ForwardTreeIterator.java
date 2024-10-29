/*******************************************************************************
 * Copyright (c) 2024 Obeo and others.
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

import org.eclipse.emf.ecore.EObject;

/**
 * Used to navigate forward in an EMF tree.
 *
 * @author sbegaudeau
 */
public class ForwardTreeIterator implements Iterator<EObject> {

    private final Predicate<EObject> filterPredicate;
    private EObject rootEObject;

    private boolean includeRoot;

    private List<Iterator<EObject>> iterators;

    private Iterator<EObject> nextPruneIterator;

    private Iterator<EObject> nextRemoveIterator;

    public ForwardTreeIterator(EObject rootEObject, boolean includeRoot, Predicate<EObject> filterPredicate) {
        this.rootEObject = Objects.requireNonNull(rootEObject);
        this.filterPredicate = Objects.requireNonNull(filterPredicate);
        this.includeRoot = includeRoot && filterPredicate.test(rootEObject);
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = false;
        if (this.iterators == null && !this.includeRoot) {
            hasNext = this.hasAnyElement();
        } else {
            hasNext = this.hasMoreElements();
        }
        if (!hasNext) {
            hasNext = this.hasNextRelative();
        }
        return hasNext;
    }

    private boolean hasAnyElement() {
        var iterator = this.getChildren(this.rootEObject);
        this.iterators = new ArrayList<>();
        var hasNext = iterator.hasNext();
        if (hasNext) {
            this.iterators.add(iterator);
        }
        return hasNext;
    }

    private Iterator<EObject> getChildren(EObject eObject) {
        return eObject.eContents().stream().filter(this.filterPredicate).iterator();
    }

    private Iterator<EObject> getNextRelatives(EObject eObject) {
        Iterator<EObject> nextRelativesIterator = null;

        var currentEObject = eObject;
        while (nextRelativesIterator == null && currentEObject != null) {
            if (currentEObject.eContainer() != null) {
                var siblings = currentEObject.eContainer().eContents();
                var index = siblings.indexOf(currentEObject);
                if (index + 1 < siblings.size()) {
                    var nextSiblingMatching = this.findNextMatchingEObjectRecursive(siblings.get(index + 1));
                    if (nextSiblingMatching != null) {
                        nextRelativesIterator = List.of(nextSiblingMatching).iterator();
                    }
                }
            }
            currentEObject = currentEObject.eContainer();
        }

        if (nextRelativesIterator == null) {
            nextRelativesIterator = Collections.emptyIterator();
        }
        return nextRelativesIterator;
    }

    private EObject findNextMatchingEObjectRecursive(EObject current) {
        if (current == null) {
            return null;
        }

        EObject result = null;

        if (this.filterPredicate.test(current)) {
            result = current;
        } else {
            for (EObject child : current.eContents()) {
                result = this.findNextMatchingEObjectRecursive(child);
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }

    private boolean hasMoreElements() {
        return this.iterators == null
                || !this.iterators.isEmpty() && this.iterators.get(this.iterators.size() - 1).hasNext();
    }

    private boolean hasNextRelative() {
        return this.getNextRelatives(this.rootEObject).hasNext();
    }

    @Override
    public EObject next() {
        EObject result = null;
        if (this.iterators == null) {
            this.nextPruneIterator = this.getChildren(this.rootEObject);
            this.iterators = new ArrayList<>();
            if (this.nextPruneIterator.hasNext()) {
                this.iterators.add(this.nextPruneIterator);
            }
            if (this.includeRoot) {
                result = this.rootEObject;
            }
        } else if (this.iterators.isEmpty()) {
            var currentIterator = this.getNextRelatives(this.rootEObject);
            result = currentIterator.next();

            this.rootEObject = result;
            this.includeRoot = false;
            this.iterators = null;
            this.nextPruneIterator = currentIterator;
        } else {
            var currentIterator = this.iterators.get(this.iterators.size() - 1);
            result = currentIterator.next();
            this.nextRemoveIterator = currentIterator;

            var iterator = this.getChildren(result);
            if (iterator.hasNext()) {
                this.nextPruneIterator = iterator;
                this.iterators.add(iterator);
            } else {
                this.nextPruneIterator = null;

                while (!currentIterator.hasNext()) {
                    this.iterators.remove(this.iterators.size() - 1);
                    if (this.iterators.isEmpty()) {
                        break;
                    }
                    var nextIterator = this.iterators.get(this.iterators.size() - 1);
                    currentIterator = nextIterator;
                }
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

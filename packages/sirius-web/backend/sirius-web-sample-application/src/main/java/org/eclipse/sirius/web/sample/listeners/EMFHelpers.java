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
package org.eclipse.sirius.web.sample.listeners;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

/**
 * EMF utilities.
 *
 * @author aresekb
 */
public final class EMFHelpers {

    private EMFHelpers() {
        // Don't instantiate the helper class
    }

    public static <T extends EObject> Optional<T> findContainer(EObject object, Class<T> containerClass) {
        Optional<T> result;
        var container = object.eContainer();
        if (container == null) {
            result = Optional.empty();
        } else if (containerClass.isInstance(container)) {
            result = Optional.of((T) container);
        } else {
            result = findContainer(container, containerClass);
        }
        return result;
    }

}

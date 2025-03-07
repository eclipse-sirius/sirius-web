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
package org.eclipse.sirius.web.application.object.services;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.object.services.api.IDefaultReadOnlyObjectPredicate;
import org.springframework.stereotype.Service;

/**
 * The default service used to test if an object is read-only.
 *
 * @author gdaniel
 */
@Service
public class DefaultReadOnlyObjectPredicate implements IDefaultReadOnlyObjectPredicate {

    @Override
    public boolean test(Object object) {
        boolean result = false;
        if (object instanceof EObject eObject) {
            result = Optional.ofNullable(eObject.eResource())
                    .map(resource -> resource.eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance))
                    .orElse(false);
        } else if (object instanceof Resource resource) {
            result = resource.eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance);
        }
        return result;
    }

}

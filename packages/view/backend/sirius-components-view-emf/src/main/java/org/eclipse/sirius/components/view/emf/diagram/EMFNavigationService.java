/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.emf.ecore.EObject;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IEMFNavigationService}.
 *
 * @author pcdavid
 */
@Service
public class EMFNavigationService implements IEMFNavigationService {
    @Override
    public <T extends EObject> T getAncestor(Class<T> type, EObject object) {
        T result = null;
        if (type.isInstance(object)) {
            result = type.cast(object);
        } else if (object != null) {
            result = this.getAncestor(type, object.eContainer());
        }
        return result;
    }
}

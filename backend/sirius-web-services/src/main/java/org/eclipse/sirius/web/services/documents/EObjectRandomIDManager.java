/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.services.documents;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.emfjson.resource.IDManager;

/**
 * Used to provide an random ID for a given EObject.
 *
 * @author gcoutable
 */
public class EObjectRandomIDManager implements IDManager {

    @Override
    public String getOrCreateId(EObject eObject) {
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<String> findId(EObject eObject) {
        return Optional.empty();
    }

    @Override
    public Optional<String> clearId(EObject eObject) {
        return Optional.empty();
    }

    @Override
    public void setId(EObject eObject, String id) {
        // Do nothing
    }

}

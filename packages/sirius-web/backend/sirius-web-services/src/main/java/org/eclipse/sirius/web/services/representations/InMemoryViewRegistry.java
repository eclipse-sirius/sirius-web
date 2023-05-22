/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.springframework.stereotype.Service;

/**
 * Used to register views loaded in memory.
 *
 * @author pcdavid
 */
@Service
public class InMemoryViewRegistry implements IInMemoryViewRegistry {

    private final Map<String, View> idToView = new HashMap<>();

    @Override
    public void register(View view) {
        String viewId = view.eResource().getURI().toString().split("///")[1];
        this.idToView.put(viewId, view);
    }

    @Override
    public Optional<View> findViewById(String viewId) {
        return Optional.ofNullable(this.idToView.get(viewId));
    }

}

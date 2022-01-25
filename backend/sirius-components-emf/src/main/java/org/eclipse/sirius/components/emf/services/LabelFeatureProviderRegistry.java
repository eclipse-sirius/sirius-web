/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The registry of {@link ILabelFeatureProvider}.
 *
 * @author arichard
 */
public class LabelFeatureProviderRegistry {

    private final Map<String, ILabelFeatureProvider> map = new HashMap<>();

    public ILabelFeatureProvider put(String key, ILabelFeatureProvider value) {
        return this.map.put(key, value);
    }

    public Optional<ILabelFeatureProvider> getLabelFeatureProvider(String nsUri) {
        return Optional.ofNullable(this.map.get(nsUri));
    }
}

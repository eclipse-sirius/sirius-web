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
package org.eclipse.sirius.web.services.duplicate;

import java.util.List;

import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureProviderDelegate;
import org.springframework.stereotype.Service;

/**
 * {@link IContainmentFeatureProviderDelegate} used to test the delegation of the computation of feature names in an integration test.
 *
 * @author Arthur Daussy
 */
@Service
public class CustomTestContainmentFeatureNameProvider implements IContainmentFeatureProviderDelegate {

    @Override
    public boolean canHandle(Object container, Object child) {
        return child instanceof Domain domain
                && "buck".equals(domain.getName())
                && container instanceof Entity entity
                && "NamedElement".equals(entity.getName());
    }

    @Override
    public List<ContainmentFeature> getContainmentFeatures(Object container, Object child) {
        return List.of(new ContainmentFeature("fake:aCommandId", "Custom containment feature used in my integration test"));
    }
}

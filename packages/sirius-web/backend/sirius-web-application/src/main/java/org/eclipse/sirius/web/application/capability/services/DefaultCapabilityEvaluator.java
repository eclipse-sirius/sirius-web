/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.capability.services;

import org.eclipse.sirius.web.application.capability.services.api.IDefaultCapabilityEvaluator;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Service;

/**
 * Used to provide a default vote for capabilities.
 *
 * @author sbegaudeau
 */
@Service
@Fallback
public class DefaultCapabilityEvaluator implements IDefaultCapabilityEvaluator {
    @Override
    public boolean hasCapability() {
        return true;
    }
}

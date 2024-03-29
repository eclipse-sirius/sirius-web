/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.description;

import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Common superclass of all the controls.
 *
 * @author sbegaudeau
 */
public abstract class AbstractControlDescription {
    protected String id;

    protected Function<VariableManager, String> targetObjectIdProvider;

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }
}

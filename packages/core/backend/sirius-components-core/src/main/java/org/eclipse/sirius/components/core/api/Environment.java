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
package org.eclipse.sirius.components.core.api;

import java.util.Objects;

/**
 * Used to distinguish the environment in which our representation descriptions are executed.
 *
 * @author arichard
 */
public class Environment {

    public static final String ENVIRONMENT = "environment";

    public static final String SIRIUS_COMPONENTS = "siriusComponents";

    private final String name;

    public Environment(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }
}

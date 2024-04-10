/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.domain.annotations;

/**
 * Used to define the boundaries of a module.
 *
 * @author sbegaudeau
 */
public @interface Module {
    String name();
    String[] allowedDependencies() default {};
    String[] exposedPackages() default {};
}

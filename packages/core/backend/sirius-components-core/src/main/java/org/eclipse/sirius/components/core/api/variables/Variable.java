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
package org.eclipse.sirius.components.core.api.variables;


import java.util.List;

/**
 * The description of a variable which can be used by various operations.
 *
 * @param name The name
 * @param types The various types of the object that can be affected to the variable
 * @param documentation The documentation
 *
 * @author sbegaudeau
 */
public record Variable(String name, List<Class<?>> types, String documentation) {
}

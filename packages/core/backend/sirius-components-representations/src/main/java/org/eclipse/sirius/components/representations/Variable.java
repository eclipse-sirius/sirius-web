/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.representations;


/**
 * The description of a variable which can be used by various operations.
 *
 * @param name The name
 * @param type The type
 * @param documentation The documentation
 *
 * @author sbegaudeau
 */
public record Variable(String name, Class<?> type, boolean isMany, String documentation) {
}

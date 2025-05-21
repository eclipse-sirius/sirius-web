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
package org.eclipse.sirius.components.diagrams.renderer;

/**
 * Object wrapping the value of a label style property as well as the information about whether it is customized or not.
 *
 * @param <T>
 *         Type of the property
 */
public record LabelAppearanceProperty<T>(T value, boolean customized) {

}

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
package org.eclipse.sirius.components.diagrams.layout.api.configuration;

/**
 * The styling of the font used for a label text.
 * <p>
 * This merges all the (supported) CSS attributes which, together with the font size, can impact the layout of a label's
 * text.
 *
 * @param bold
 *            {@code font-weight: bold}
 * @param italic
 *            {@code font-style: italic}
 * @param underline
 *            {@code text-decoration-line: underline}
 * @param strikeThrough
 *            {@code text-decoration-line: line-through}
 *
 * @author sbegaudeau
 */
public record FontStyle(boolean bold, boolean italic, boolean underline, boolean strikeThrough) {
    public static final FontStyle DEFAULT = new FontStyle(false, false, false, false);
}

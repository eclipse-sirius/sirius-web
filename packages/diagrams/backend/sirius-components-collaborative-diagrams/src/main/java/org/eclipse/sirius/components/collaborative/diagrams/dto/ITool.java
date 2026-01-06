/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;

import org.eclipse.sirius.components.collaborative.dto.KeyBinding;

/**
 * Interface implemented by all tools.
 *
 * @author mcharfadi
 */
public interface ITool extends IPaletteEntry {

    String label();

    List<String> iconURL();

    List<KeyBinding> keyBindings();
}

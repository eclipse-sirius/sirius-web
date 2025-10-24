/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tools;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Interface implemented by all tools.
 *
 * @technical-debt This class and its related concepts such as {@link ToolSection} and {@link Palette} should be deleted.
 * See the documentation of {@link Palette} for additional details.
 *
 * @author hmarchadour
 * @since v0.1.0
 */
public interface ITool {

    String getId();

    String getLabel();

    Function<VariableManager, IStatus> getHandler();

    List<String> getIconURL();
}

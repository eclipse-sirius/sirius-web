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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.layout.incremental.INodeIncrementalLayoutEngine;

/**
 * Used to provide an instance of the layout engine handler switch which will then be used to execute node incremental
 * layout engine.
 *
 * @author gcoutable
 */
public interface ILayoutEngineHandlerSwitchProvider {
    Function<String, Optional<INodeIncrementalLayoutEngine>> getLayoutEngineHandlerSwitch();
}

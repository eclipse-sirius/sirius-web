/*******************************************************************************
 * Copyright (c) 2022 Obeo
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.view.NodeStyleDescription;

/**
 * Interface that allows to provide custom node styles.
 *
 * @author lfasani
 */
public interface INodeStyleProvider {

    Optional<String> getNodeType(NodeStyleDescription nodeStyle);

    Optional<INodeStyle> createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId);
}

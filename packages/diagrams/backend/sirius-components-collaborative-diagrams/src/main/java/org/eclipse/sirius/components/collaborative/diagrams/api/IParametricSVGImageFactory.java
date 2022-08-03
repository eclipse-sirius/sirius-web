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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.EnumMap;
import java.util.Optional;

/**
 * Factory used to compute dynamically the svg image of node.
 *
 * @author lfasani
 */
public interface IParametricSVGImageFactory {

    Optional<byte[]> createSvg(String svgType, EnumMap<SVGAttribute, String> attributesValues);
}

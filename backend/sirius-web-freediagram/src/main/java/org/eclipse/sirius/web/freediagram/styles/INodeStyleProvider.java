/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.freediagram.styles;

import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Node style provider.
 *
 * @author sdrapeau
 *
 */
public interface INodeStyleProvider {

    String getNodeType(VariableManager variableManager);

    INodeStyle getNodeStyle(VariableManager variableManager);

    LabelStyleDescription getLabelStyleDescription();

}

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
package org.eclipse.sirius.components.tables.components;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;

/**
 * Used to provide custom cell widget in table.
 *
 * @author frouene
 */
public interface ICustomCellDescriptor {

    Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props);

    Optional<Boolean> validateInstanceProps(String type, IProps props);

    Optional<Object> instantiate(String type, IProps elementProps, List<Object> children);

    Optional<Element> createElement(VariableManager variableManager, ICellDescription cellDescription, UUID cellId, UUID columnId, Object columnTargetObject);
}

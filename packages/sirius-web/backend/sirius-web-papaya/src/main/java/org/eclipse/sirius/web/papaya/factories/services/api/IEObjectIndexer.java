/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.factories.services.api;

import org.eclipse.sirius.components.papaya.Class;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Enum;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.Record;
import org.eclipse.sirius.components.papaya.Type;

/**
 * Used to index eObjects.
 *
 * @author sbegaudeau
 */
public interface IEObjectIndexer {

    Type getType(String qualifiedName);

    Class getClass(String qualifiedName);

    Interface getInterface(String qualifiedName);

    Record getRecord(String qualifiedName);

    Enum getEnum(String qualifiedName);

    Component getComponent(String name);
}

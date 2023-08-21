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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Tool Section</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.ToolSection#getName <em>Name</em>}</li>
 * </ul>
 *
 * @model abstract="true"
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getToolSection()
 */
public interface ToolSection extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is <code>"Tool"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model default="Tool" dataType="org.eclipse.sirius.components.view.Identifier" required="true"
     * @generated
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getToolSection_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.ToolSection#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

} // ToolSection

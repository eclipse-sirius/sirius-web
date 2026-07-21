/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell Label Widget Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.table.CellLabelWidgetDescription#getIconExpression <em>Icon Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.table.TablePackage#getCellLabelWidgetDescription()
 * @model
 * @generated
 */
public interface CellLabelWidgetDescription extends CellWidgetDescription {

    /**
     * Returns the value of the '<em><b>Icon Expression</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Icon Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setIconExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellLabelWidgetDescription_IconExpression()
     */
    String getIconExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellLabelWidgetDescription#getIconExpression <em>Icon Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon Expression</em>' attribute.
	 * @see #getIconExpression()
	 * @generated
	 */
    void setIconExpression(String value);

} // CellLabelWidgetDescription

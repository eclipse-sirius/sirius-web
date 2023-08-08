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
package org.eclipse.sirius.components.view.gantt;

import org.eclipse.sirius.components.view.LabelStyle;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Task Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getLabelColorExpression <em>Label Color
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getBackgroundColorExpression <em>Background
 * Color Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getProgressColorExpression <em>Progress
 * Color Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskStyleDescription()
 * @model abstract="true"
 * @generated
 */
public interface TaskStyleDescription extends LabelStyle {
    /**
     * Returns the value of the '<em><b>Label Color Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Color Expression</em>' attribute.
     * @see #setLabelColorExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskStyleDescription_LabelColorExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelColorExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getLabelColorExpression <em>Label Color
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Color Expression</em>' attribute.
     * @see #getLabelColorExpression()
     * @generated
     */
    void setLabelColorExpression(String value);

    /**
     * Returns the value of the '<em><b>Background Color Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background Color Expression</em>' attribute.
     * @see #setBackgroundColorExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskStyleDescription_BackgroundColorExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getBackgroundColorExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getBackgroundColorExpression <em>Background
     * Color Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color Expression</em>' attribute.
     * @see #getBackgroundColorExpression()
     * @generated
     */
    void setBackgroundColorExpression(String value);

    /**
     * Returns the value of the '<em><b>Progress Color Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Progress Color Expression</em>' attribute.
     * @see #setProgressColorExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskStyleDescription_ProgressColorExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getProgressColorExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskStyleDescription#getProgressColorExpression <em>Progress
     * Color Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Progress Color Expression</em>' attribute.
     * @see #getProgressColorExpression()
     * @generated
     */
    void setProgressColorExpression(String value);

} // TaskStyleDescription

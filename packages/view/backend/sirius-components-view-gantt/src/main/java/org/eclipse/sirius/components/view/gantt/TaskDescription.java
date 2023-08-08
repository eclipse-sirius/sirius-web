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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Task Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getSemanticCandidatesExpression <em>Semantic
 * Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getTaskInfoExpression <em>Task Info
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription()
 * @model
 * @generated
 */
public interface TaskDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_SemanticCandidatesExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #getSemanticCandidatesExpression()
     * @generated
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Task Detail Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Task Detail Expression</em>' attribute.
     * @see #setTaskDetailExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_TaskDetailExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTaskDetailExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getTaskDetailExpression
     * <em>Task Detail Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Task Detail Expression</em>' attribute.
     * @see #getTaskDetailExpression()
     * @generated
     */
    void setTaskDetailExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(TaskStyleDescription)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_Style()
     * @model containment="true" required="true"
     * @generated
     */
    TaskStyleDescription getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getStyle <em>Style</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(TaskStyleDescription value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalTaskStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Sub Task Element Descriptions</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Sub Task Element Descriptions</em>' containment reference.
     * @see #setSubTaskElementDescriptions(TaskDescription)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_SubTaskElementDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<TaskDescription> getSubTaskElementDescriptions();

    /**
     * Returns the value of the '<em><b>Reused Task Element Descriptions</b></em>' reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.gantt.TaskDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reused Task Element Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_ReusedTaskElementDescriptions()
     * @model
     * @generated
     */
    EList<TaskDescription> getReusedTaskElementDescriptions();

} // TaskDescription

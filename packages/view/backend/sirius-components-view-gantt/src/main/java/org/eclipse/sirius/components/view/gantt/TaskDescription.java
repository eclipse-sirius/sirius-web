/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_Name()
     * @model default="" dataType="org.eclipse.sirius.components.view.Identifier" required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_DomainType()
     * @model dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getDomainType <em>Domain
     * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

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
     * Returns the value of the '<em><b>Name Expression</b></em>' attribute. The default value is
     * <code>"aql:self.name"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name Expression</em>' attribute.
     * @see #setNameExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_NameExpression()
     * @model default="aql:self.name" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getNameExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getNameExpression <em>Name
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name Expression</em>' attribute.
     * @see #getNameExpression()
     * @generated
     */
    void setNameExpression(String value);

    /**
     * Returns the value of the '<em><b>Description Expression</b></em>' attribute. The default value is
     * <code>"aql:self.description"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Description Expression</em>' attribute.
     * @see #setDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_DescriptionExpression()
     * @model default="aql:self.description" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDescriptionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getDescriptionExpression
     * <em>Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description Expression</em>' attribute.
     * @see #getDescriptionExpression()
     * @generated
     */
    void setDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>Start Time Expression</b></em>' attribute. The default value is
     * <code>"aql:self.startTime"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Start Time Expression</em>' attribute.
     * @see #setStartTimeExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_StartTimeExpression()
     * @model default="aql:self.startTime" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getStartTimeExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getStartTimeExpression
     * <em>Start Time Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Start Time Expression</em>' attribute.
     * @see #getStartTimeExpression()
     * @generated
     */
    void setStartTimeExpression(String value);

    /**
     * Returns the value of the '<em><b>End Time Expression</b></em>' attribute. The default value is
     * <code>"aql:self.endTime"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Time Expression</em>' attribute.
     * @see #setEndTimeExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_EndTimeExpression()
     * @model default="aql:self.endTime" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getEndTimeExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getEndTimeExpression
     * <em>End Time Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Time Expression</em>' attribute.
     * @see #getEndTimeExpression()
     * @generated
     */
    void setEndTimeExpression(String value);

    /**
     * Returns the value of the '<em><b>Progress Expression</b></em>' attribute. The default value is
     * <code>"aql:self.progress"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Progress Expression</em>' attribute.
     * @see #setProgressExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_ProgressExpression()
     * @model default="aql:self.progress" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getProgressExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getProgressExpression
     * <em>Progress Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Progress Expression</em>' attribute.
     * @see #getProgressExpression()
     * @generated
     */
    void setProgressExpression(String value);

    /**
     * Returns the value of the '<em><b>Compute Start End Dynamically Expression</b></em>' attribute. The default value
     * is <code>"aql:self.computeStartEndDynamically"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Compute Start End Dynamically Expression</em>' attribute.
     * @see #setComputeStartEndDynamicallyExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_ComputeStartEndDynamicallyExpression()
     * @model default="aql:self.computeStartEndDynamically"
     *        dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getComputeStartEndDynamicallyExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getComputeStartEndDynamicallyExpression
     * <em>Compute Start End Dynamically Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Compute Start End Dynamically Expression</em>' attribute.
     * @see #getComputeStartEndDynamicallyExpression()
     * @generated
     */
    void setComputeStartEndDynamicallyExpression(String value);

    /**
     * Returns the value of the '<em><b>Task Dependencies Expression</b></em>' attribute. The default value is
     * <code>"aql:self.dependencies"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Task Dependencies Expression</em>' attribute.
     * @see #setTaskDependenciesExpression(String)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getTaskDescription_TaskDependenciesExpression()
     * @model default="aql:self.dependencies" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTaskDependenciesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.gantt.TaskDescription#getTaskDependenciesExpression <em>Task
     * Dependencies Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Task Dependencies Expression</em>' attribute.
     * @see #getTaskDependenciesExpression()
     * @generated
     */
    void setTaskDependenciesExpression(String value);

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

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
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getTaskElementDescriptions <em>Task Element
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTool <em>Create Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getEditTool <em>Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDropTool <em>Drop Tool</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription()
 * @model
 * @generated
 */
public interface GanttDescription extends RepresentationDescription {
    /**
     * Returns the value of the '<em><b>Task Element Descriptions</b></em>' containment reference list. The list
     * contents are of type {@link org.eclipse.sirius.components.view.gantt.TaskDescription}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Task Element Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_TaskElementDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<TaskDescription> getTaskElementDescriptions();

    /**
     * Returns the value of the '<em><b>Background Color</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Background Color</em>' containment reference.
     * @see #setBackgroundColor(UserColor)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_BackgroundColor()
     * @model containment="true"
     * @generated
     */
    UserColor getBackgroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getBackgroundColor
     * <em>Background Color</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' containment reference.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Create Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Create Tool</em>' containment reference.
     * @see #setCreateTool(CreateTaskTool)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_CreateTool()
     * @model containment="true"
     * @generated
     */
    CreateTaskTool getCreateTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getCreateTool <em>Create
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Create Tool</em>' containment reference.
     * @see #getCreateTool()
     * @generated
     */
    void setCreateTool(CreateTaskTool value);

    /**
     * Returns the value of the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Edit Tool</em>' containment reference.
     * @see #setEditTool(EditTaskTool)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_EditTool()
     * @model containment="true"
     * @generated
     */
    EditTaskTool getEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getEditTool <em>Edit
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Edit Tool</em>' containment reference.
     * @see #getEditTool()
     * @generated
     */
    void setEditTool(EditTaskTool value);

    /**
     * Returns the value of the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Tool</em>' containment reference.
     * @see #setDeleteTool(DeleteTaskTool)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_DeleteTool()
     * @model containment="true"
     * @generated
     */
    DeleteTaskTool getDeleteTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDeleteTool <em>Delete
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Tool</em>' containment reference.
     * @see #getDeleteTool()
     * @generated
     */
    void setDeleteTool(DeleteTaskTool value);

    /**
     * Returns the value of the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Drop Tool</em>' containment reference.
     * @see #setDropTool(DropTaskTool)
     * @see org.eclipse.sirius.components.view.gantt.GanttPackage#getGanttDescription_DropTool()
     * @model containment="true"
     * @generated
     */
    DropTaskTool getDropTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.gantt.GanttDescription#getDropTool <em>Drop
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Drop Tool</em>' containment reference.
     * @see #getDropTool()
     * @generated
     */
    void setDropTool(DropTaskTool value);

} // GanttDescription

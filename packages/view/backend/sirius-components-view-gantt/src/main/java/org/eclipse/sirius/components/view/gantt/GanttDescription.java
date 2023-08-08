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

} // GanttDescription

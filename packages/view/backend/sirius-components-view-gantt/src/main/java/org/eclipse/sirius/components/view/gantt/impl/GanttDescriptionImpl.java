/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.gantt.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.CreateTaskTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.DeleteTaskTool;
import org.eclipse.sirius.components.view.gantt.DropTaskTool;
import org.eclipse.sirius.components.view.gantt.EditTaskTool;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getTaskElementDescriptions <em>Task Element Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getCreateTool <em>Create Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getEditTool <em>Edit Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDeleteTool <em>Delete Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDropTool <em>Drop Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getCreateTaskDependencyTool <em>Create Task Dependency Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDeleteTaskDependencyTool <em>Delete Task Dependency Tool</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDateRoundingExpression <em>Date Rounding Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GanttDescriptionImpl extends RepresentationDescriptionImpl implements GanttDescription {
    /**
	 * The cached value of the '{@link #getTaskElementDescriptions() <em>Task Element Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTaskElementDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<TaskDescription> taskElementDescriptions;

    /**
     * The cached value of the '{@link #getCreateTool() <em>Create Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCreateTool()
     * @generated
     * @ordered
     */
    protected CreateTaskTool createTool;

    /**
	 * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getEditTool()
	 * @generated
	 * @ordered
	 */
    protected EditTaskTool editTool;

    /**
     * The cached value of the '{@link #getDeleteTool() <em>Delete Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeleteTool()
     * @generated
     * @ordered
     */
    protected DeleteTaskTool deleteTool;

    /**
	 * The cached value of the '{@link #getDropTool() <em>Drop Tool</em>}' containment reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getDropTool()
	 * @generated
	 * @ordered
	 */
    protected DropTaskTool dropTool;

    /**
	 * The cached value of the '{@link #getCreateTaskDependencyTool() <em>Create Task Dependency Tool</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCreateTaskDependencyTool()
	 * @generated
	 * @ordered
	 */
    protected CreateTaskDependencyTool createTaskDependencyTool;

    /**
	 * The cached value of the '{@link #getDeleteTaskDependencyTool() <em>Delete Task Dependency Tool</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDeleteTaskDependencyTool()
	 * @generated
	 * @ordered
	 */
    protected DeleteTaskDependencyTool deleteTaskDependencyTool;

    /**
     * The default value of the '{@link #getDateRoundingExpression() <em>Date Rounding Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDateRoundingExpression()
     * @generated
     * @ordered
     */
    protected static final String DATE_ROUNDING_EXPRESSION_EDEFAULT = "12H";

    /**
     * The cached value of the '{@link #getDateRoundingExpression() <em>Date Rounding Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDateRoundingExpression()
     * @generated
     * @ordered
     */
    protected String dateRoundingExpression = DATE_ROUNDING_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected GanttDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return GanttPackage.Literals.GANTT_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<TaskDescription> getTaskElementDescriptions() {
		if (taskElementDescriptions == null)
		{
			taskElementDescriptions = new EObjectContainmentEList<TaskDescription>(TaskDescription.class, this, GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS);
		}
		return taskElementDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CreateTaskTool getCreateTool() {
		return createTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCreateTool(CreateTaskTool newCreateTool, NotificationChain msgs) {
		CreateTaskTool oldCreateTool = createTool;
		createTool = newCreateTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, oldCreateTool, newCreateTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCreateTool(CreateTaskTool newCreateTool) {
		if (newCreateTool != createTool)
		{
			NotificationChain msgs = null;
			if (createTool != null)
				msgs = ((InternalEObject)createTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, null, msgs);
			if (newCreateTool != null)
				msgs = ((InternalEObject)newCreateTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, null, msgs);
			msgs = basicSetCreateTool(newCreateTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, newCreateTool, newCreateTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EditTaskTool getEditTool() {
		return editTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetEditTool(EditTaskTool newEditTool, NotificationChain msgs) {
		EditTaskTool oldEditTool = editTool;
		editTool = newEditTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEditTool(EditTaskTool newEditTool) {
		if (newEditTool != editTool)
		{
			NotificationChain msgs = null;
			if (editTool != null)
				msgs = ((InternalEObject)editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, null, msgs);
			if (newEditTool != null)
				msgs = ((InternalEObject)newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, null, msgs);
			msgs = basicSetEditTool(newEditTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeleteTaskTool getDeleteTool() {
		return deleteTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDeleteTool(DeleteTaskTool newDeleteTool, NotificationChain msgs) {
		DeleteTaskTool oldDeleteTool = deleteTool;
		deleteTool = newDeleteTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, oldDeleteTool, newDeleteTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDeleteTool(DeleteTaskTool newDeleteTool) {
		if (newDeleteTool != deleteTool)
		{
			NotificationChain msgs = null;
			if (deleteTool != null)
				msgs = ((InternalEObject)deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, null, msgs);
			if (newDeleteTool != null)
				msgs = ((InternalEObject)newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, null, msgs);
			msgs = basicSetDeleteTool(newDeleteTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, newDeleteTool, newDeleteTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DropTaskTool getDropTool() {
		return dropTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDropTool(DropTaskTool newDropTool, NotificationChain msgs) {
		DropTaskTool oldDropTool = dropTool;
		dropTool = newDropTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, oldDropTool, newDropTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDropTool(DropTaskTool newDropTool) {
		if (newDropTool != dropTool)
		{
			NotificationChain msgs = null;
			if (dropTool != null)
				msgs = ((InternalEObject)dropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, null, msgs);
			if (newDropTool != null)
				msgs = ((InternalEObject)newDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, null, msgs);
			msgs = basicSetDropTool(newDropTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, newDropTool, newDropTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CreateTaskDependencyTool getCreateTaskDependencyTool() {
		return createTaskDependencyTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCreateTaskDependencyTool(CreateTaskDependencyTool newCreateTaskDependencyTool, NotificationChain msgs) {
		CreateTaskDependencyTool oldCreateTaskDependencyTool = createTaskDependencyTool;
		createTaskDependencyTool = newCreateTaskDependencyTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, oldCreateTaskDependencyTool, newCreateTaskDependencyTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCreateTaskDependencyTool(CreateTaskDependencyTool newCreateTaskDependencyTool) {
		if (newCreateTaskDependencyTool != createTaskDependencyTool)
		{
			NotificationChain msgs = null;
			if (createTaskDependencyTool != null)
				msgs = ((InternalEObject)createTaskDependencyTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, null, msgs);
			if (newCreateTaskDependencyTool != null)
				msgs = ((InternalEObject)newCreateTaskDependencyTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, null, msgs);
			msgs = basicSetCreateTaskDependencyTool(newCreateTaskDependencyTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, newCreateTaskDependencyTool, newCreateTaskDependencyTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeleteTaskDependencyTool getDeleteTaskDependencyTool() {
		return deleteTaskDependencyTool;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDeleteTaskDependencyTool(DeleteTaskDependencyTool newDeleteTaskDependencyTool, NotificationChain msgs) {
		DeleteTaskDependencyTool oldDeleteTaskDependencyTool = deleteTaskDependencyTool;
		deleteTaskDependencyTool = newDeleteTaskDependencyTool;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL, oldDeleteTaskDependencyTool, newDeleteTaskDependencyTool);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDeleteTaskDependencyTool(DeleteTaskDependencyTool newDeleteTaskDependencyTool) {
		if (newDeleteTaskDependencyTool != deleteTaskDependencyTool)
		{
			NotificationChain msgs = null;
			if (deleteTaskDependencyTool != null)
				msgs = ((InternalEObject)deleteTaskDependencyTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL, null, msgs);
			if (newDeleteTaskDependencyTool != null)
				msgs = ((InternalEObject)newDeleteTaskDependencyTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL, null, msgs);
			msgs = basicSetDeleteTaskDependencyTool(newDeleteTaskDependencyTool, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL, newDeleteTaskDependencyTool, newDeleteTaskDependencyTool));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDateRoundingExpression() {
		return dateRoundingExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDateRoundingExpression(String newDateRoundingExpression) {
		String oldDateRoundingExpression = dateRoundingExpression;
		dateRoundingExpression = newDateRoundingExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION, oldDateRoundingExpression, dateRoundingExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
				return ((InternalEList<?>)getTaskElementDescriptions()).basicRemove(otherEnd, msgs);
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
				return basicSetCreateTool(null, msgs);
			case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
				return basicSetEditTool(null, msgs);
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
				return basicSetDeleteTool(null, msgs);
			case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
				return basicSetDropTool(null, msgs);
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
				return basicSetCreateTaskDependencyTool(null, msgs);
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL:
				return basicSetDeleteTaskDependencyTool(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
				return getTaskElementDescriptions();
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
				return getCreateTool();
			case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
				return getEditTool();
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
				return getDeleteTool();
			case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
				return getDropTool();
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
				return getCreateTaskDependencyTool();
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL:
				return getDeleteTaskDependencyTool();
			case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
				return getDateRoundingExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
				getTaskElementDescriptions().clear();
				getTaskElementDescriptions().addAll((Collection<? extends TaskDescription>)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
				setCreateTool((CreateTaskTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditTaskTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
				setDeleteTool((DeleteTaskTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
				setDropTool((DropTaskTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
				setCreateTaskDependencyTool((CreateTaskDependencyTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL:
				setDeleteTaskDependencyTool((DeleteTaskDependencyTool)newValue);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
				setDateRoundingExpression((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
				getTaskElementDescriptions().clear();
				return;
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
				setCreateTool((CreateTaskTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
				setEditTool((EditTaskTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
				setDeleteTool((DeleteTaskTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
				setDropTool((DropTaskTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
				setCreateTaskDependencyTool((CreateTaskDependencyTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL:
				setDeleteTaskDependencyTool((DeleteTaskDependencyTool)null);
				return;
			case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
				setDateRoundingExpression(DATE_ROUNDING_EXPRESSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
				return taskElementDescriptions != null && !taskElementDescriptions.isEmpty();
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
				return createTool != null;
			case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
				return editTool != null;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
				return deleteTool != null;
			case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
				return dropTool != null;
			case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
				return createTaskDependencyTool != null;
			case GanttPackage.GANTT_DESCRIPTION__DELETE_TASK_DEPENDENCY_TOOL:
				return deleteTaskDependencyTool != null;
			case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
				return DATE_ROUNDING_EXPRESSION_EDEFAULT == null ? dateRoundingExpression != null : !DATE_ROUNDING_EXPRESSION_EDEFAULT.equals(dateRoundingExpression);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (dateRoundingExpression: ");
		result.append(dateRoundingExpression);
		result.append(')');
		return result.toString();
	}

} // GanttDescriptionImpl

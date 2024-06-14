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
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getTaskElementDescriptions <em>Task
 * Element Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getCreateTool <em>Create
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getEditTool <em>Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDeleteTool <em>Delete
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDropTool <em>Drop Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getCreateTaskDependencyTool <em>Create
 * Task Dependency Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDateRoundingExpression <em>Date
 * Rounding Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GanttDescriptionImpl extends RepresentationDescriptionImpl implements GanttDescription {
    /**
     * The cached value of the '{@link #getTaskElementDescriptions() <em>Task Element Descriptions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
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
     * The cached value of the '{@link #getEditTool() <em>Edit Tool</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
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
     * The cached value of the '{@link #getDropTool() <em>Drop Tool</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getDropTool()
     * @generated
     * @ordered
     */
    protected DropTaskTool dropTool;

    /**
     * The cached value of the '{@link #getCreateTaskDependencyTool() <em>Create Task Dependency Tool</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCreateTaskDependencyTool()
     * @generated
     * @ordered
     */
    protected CreateTaskDependencyTool createTaskDependencyTool;

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
     *
     * @generated
     */
    protected GanttDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GanttPackage.Literals.GANTT_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TaskDescription> getTaskElementDescriptions() {
        if (this.taskElementDescriptions == null) {
            this.taskElementDescriptions = new EObjectContainmentEList<>(TaskDescription.class, this, GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS);
        }
        return this.taskElementDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateTaskTool getCreateTool() {
        return this.createTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCreateTool(CreateTaskTool newCreateTool, NotificationChain msgs) {
        CreateTaskTool oldCreateTool = this.createTool;
        this.createTool = newCreateTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, oldCreateTool, newCreateTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCreateTool(CreateTaskTool newCreateTool) {
        if (newCreateTool != this.createTool) {
            NotificationChain msgs = null;
            if (this.createTool != null)
                msgs = ((InternalEObject) this.createTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, null, msgs);
            if (newCreateTool != null)
                msgs = ((InternalEObject) newCreateTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, null, msgs);
            msgs = this.basicSetCreateTool(newCreateTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL, newCreateTool, newCreateTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditTaskTool getEditTool() {
        return this.editTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetEditTool(EditTaskTool newEditTool, NotificationChain msgs) {
        EditTaskTool oldEditTool = this.editTool;
        this.editTool = newEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, oldEditTool, newEditTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEditTool(EditTaskTool newEditTool) {
        if (newEditTool != this.editTool) {
            NotificationChain msgs = null;
            if (this.editTool != null)
                msgs = ((InternalEObject) this.editTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, null, msgs);
            if (newEditTool != null)
                msgs = ((InternalEObject) newEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, null, msgs);
            msgs = this.basicSetEditTool(newEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL, newEditTool, newEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTaskTool getDeleteTool() {
        return this.deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDeleteTool(DeleteTaskTool newDeleteTool, NotificationChain msgs) {
        DeleteTaskTool oldDeleteTool = this.deleteTool;
        this.deleteTool = newDeleteTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, oldDeleteTool, newDeleteTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDeleteTool(DeleteTaskTool newDeleteTool) {
        if (newDeleteTool != this.deleteTool) {
            NotificationChain msgs = null;
            if (this.deleteTool != null)
                msgs = ((InternalEObject) this.deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, null, msgs);
            if (newDeleteTool != null)
                msgs = ((InternalEObject) newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, null, msgs);
            msgs = this.basicSetDeleteTool(newDeleteTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL, newDeleteTool, newDeleteTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropTaskTool getDropTool() {
        return this.dropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDropTool(DropTaskTool newDropTool, NotificationChain msgs) {
        DropTaskTool oldDropTool = this.dropTool;
        this.dropTool = newDropTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, oldDropTool, newDropTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDropTool(DropTaskTool newDropTool) {
        if (newDropTool != this.dropTool) {
            NotificationChain msgs = null;
            if (this.dropTool != null)
                msgs = ((InternalEObject) this.dropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, null, msgs);
            if (newDropTool != null)
                msgs = ((InternalEObject) newDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, null, msgs);
            msgs = this.basicSetDropTool(newDropTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DROP_TOOL, newDropTool, newDropTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateTaskDependencyTool getCreateTaskDependencyTool() {
        return this.createTaskDependencyTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetCreateTaskDependencyTool(CreateTaskDependencyTool newCreateTaskDependencyTool, NotificationChain msgs) {
        CreateTaskDependencyTool oldCreateTaskDependencyTool = this.createTaskDependencyTool;
        this.createTaskDependencyTool = newCreateTaskDependencyTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, oldCreateTaskDependencyTool,
                    newCreateTaskDependencyTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCreateTaskDependencyTool(CreateTaskDependencyTool newCreateTaskDependencyTool) {
        if (newCreateTaskDependencyTool != this.createTaskDependencyTool) {
            NotificationChain msgs = null;
            if (this.createTaskDependencyTool != null)
                msgs = ((InternalEObject) this.createTaskDependencyTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, null, msgs);
            if (newCreateTaskDependencyTool != null)
                msgs = ((InternalEObject) newCreateTaskDependencyTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, null, msgs);
            msgs = this.basicSetCreateTaskDependencyTool(newCreateTaskDependencyTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL, newCreateTaskDependencyTool, newCreateTaskDependencyTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDateRoundingExpression() {
        return this.dateRoundingExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDateRoundingExpression(String newDateRoundingExpression) {
        String oldDateRoundingExpression = this.dateRoundingExpression;
        this.dateRoundingExpression = newDateRoundingExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION, oldDateRoundingExpression, this.dateRoundingExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                return ((InternalEList<?>) this.getTaskElementDescriptions()).basicRemove(otherEnd, msgs);
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.basicSetCreateTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.basicSetEditTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.basicSetDropTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
                return this.basicSetCreateTaskDependencyTool(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                return this.getTaskElementDescriptions();
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.getCreateTool();
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.getEditTool();
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.getDeleteTool();
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.getDropTool();
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
                return this.getCreateTaskDependencyTool();
            case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
                return this.getDateRoundingExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                this.getTaskElementDescriptions().clear();
                this.getTaskElementDescriptions().addAll((Collection<? extends TaskDescription>) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                this.setCreateTool((CreateTaskTool) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditTaskTool) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                this.setDeleteTool((DeleteTaskTool) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                this.setDropTool((DropTaskTool) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
                this.setCreateTaskDependencyTool((CreateTaskDependencyTool) newValue);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
                this.setDateRoundingExpression((String) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                this.getTaskElementDescriptions().clear();
                return;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                this.setCreateTool((CreateTaskTool) null);
                return;
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                this.setEditTool((EditTaskTool) null);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                this.setDeleteTool((DeleteTaskTool) null);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                this.setDropTool((DropTaskTool) null);
                return;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
                this.setCreateTaskDependencyTool((CreateTaskDependencyTool) null);
                return;
            case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
                this.setDateRoundingExpression(DATE_ROUNDING_EXPRESSION_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                return this.taskElementDescriptions != null && !this.taskElementDescriptions.isEmpty();
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.createTool != null;
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.editTool != null;
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.deleteTool != null;
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.dropTool != null;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TASK_DEPENDENCY_TOOL:
                return this.createTaskDependencyTool != null;
            case GanttPackage.GANTT_DESCRIPTION__DATE_ROUNDING_EXPRESSION:
                return DATE_ROUNDING_EXPRESSION_EDEFAULT == null ? this.dateRoundingExpression != null : !DATE_ROUNDING_EXPRESSION_EDEFAULT.equals(this.dateRoundingExpression);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (dateRoundingExpression: ");
        result.append(this.dateRoundingExpression);
        result.append(')');
        return result.toString();
    }

} // GanttDescriptionImpl

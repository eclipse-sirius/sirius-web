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
import org.eclipse.sirius.components.view.UserColor;
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
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getCreateTool <em>Create
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getEditTool <em>Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDeleteTool <em>Delete
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.GanttDescriptionImpl#getDropTool <em>Drop Tool</em>}</li>
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
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected UserColor backgroundColor;

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
    public UserColor getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetBackgroundColor(UserColor newBackgroundColor, NotificationChain msgs) {
        UserColor oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR, oldBackgroundColor, newBackgroundColor);
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
    public void setBackgroundColor(UserColor newBackgroundColor) {
        if (newBackgroundColor != this.backgroundColor) {
            NotificationChain msgs = null;
            if (this.backgroundColor != null)
                msgs = ((InternalEObject) this.backgroundColor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR, null, msgs);
            if (newBackgroundColor != null)
                msgs = ((InternalEObject) newBackgroundColor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR, null, msgs);
            msgs = this.basicSetBackgroundColor(newBackgroundColor, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR, newBackgroundColor, newBackgroundColor));
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
                return ((InternalEList<?>) this.getTaskElementDescriptions()).basicRemove(otherEnd, msgs);
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
                return this.basicSetBackgroundColor(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.basicSetCreateTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.basicSetEditTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.basicSetDropTool(null, msgs);
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
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
                return this.getBackgroundColor();
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.getCreateTool();
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.getEditTool();
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.getDeleteTool();
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.getDropTool();
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
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
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
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
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
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
                return this.createTool != null;
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
                return this.editTool != null;
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
                return this.deleteTool != null;
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
                return this.dropTool != null;
        }
        return super.eIsSet(featureID);
    }

} // GanttDescriptionImpl

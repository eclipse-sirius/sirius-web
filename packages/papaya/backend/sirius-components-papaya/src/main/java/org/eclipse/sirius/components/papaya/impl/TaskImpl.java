/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya.impl;

import java.time.Instant;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Priority;
import org.eclipse.sirius.components.papaya.Task;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Task</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getPriority <em>Priority</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getCost <em>Cost</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getTargets <em>Targets</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getTasks <em>Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getStartDate <em>Start Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getEndDate <em>End Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#isDone <em>Done</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskImpl extends NamedElementImpl implements Task {
    /**
     * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected static final Priority PRIORITY_EDEFAULT = Priority.P1;

    /**
     * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected Priority priority = PRIORITY_EDEFAULT;

    /**
     * The default value of the '{@link #getCost() <em>Cost</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getCost()
     * @generated
     * @ordered
     */
    protected static final int COST_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getCost() <em>Cost</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getCost()
     * @generated
     * @ordered
     */
    protected int cost = COST_EDEFAULT;

    /**
     * The cached value of the '{@link #getTargets() <em>Targets</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTargets()
     * @generated
     * @ordered
     */
    protected EList<ModelElement> targets;

    /**
     * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> tasks;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final Instant START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected Instant startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final Instant END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected Instant endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #isDone() <em>Done</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isDone()
     * @generated
     * @ordered
     */
    protected static final boolean DONE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDone() <em>Done</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isDone()
     * @generated
     * @ordered
     */
    protected boolean done = DONE_EDEFAULT;

    /**
     * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getDependencies()
     * @generated
     * @ordered
     */
    protected EList<Task> dependencies;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TaskImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.TASK;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPriority(Priority newPriority) {
        Priority oldPriority = this.priority;
        this.priority = newPriority == null ? PRIORITY_EDEFAULT : newPriority;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__PRIORITY, oldPriority, this.priority));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCost(int newCost) {
        int oldCost = this.cost;
        this.cost = newCost;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__COST, oldCost, this.cost));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ModelElement> getTargets() {
        if (this.targets == null) {
            this.targets = new EObjectResolvingEList<>(ModelElement.class, this, PapayaPackage.TASK__TARGETS);
        }
        return this.targets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getTasks() {
        if (this.tasks == null) {
            this.tasks = new EObjectContainmentEList<>(Task.class, this, PapayaPackage.TASK__TASKS);
        }
        return this.tasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Instant getStartDate() {
        return this.startDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStartDate(Instant newStartDate) {
        Instant oldStartDate = this.startDate;
        this.startDate = newStartDate;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__START_DATE, oldStartDate, this.startDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Instant getEndDate() {
        return this.endDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEndDate(Instant newEndDate) {
        Instant oldEndDate = this.endDate;
        this.endDate = newEndDate;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__END_DATE, oldEndDate, this.endDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isDone() {
        return this.done;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDone(boolean newDone) {
        boolean oldDone = this.done;
        this.done = newDone;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__DONE, oldDone, this.done));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new EObjectResolvingEList<>(Task.class, this, PapayaPackage.TASK__DEPENDENCIES);
        }
        return this.dependencies;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case PapayaPackage.TASK__TASKS:
                return ((InternalEList<?>) this.getTasks()).basicRemove(otherEnd, msgs);
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
            case PapayaPackage.TASK__PRIORITY:
                return this.getPriority();
            case PapayaPackage.TASK__COST:
                return this.getCost();
            case PapayaPackage.TASK__TARGETS:
                return this.getTargets();
            case PapayaPackage.TASK__TASKS:
                return this.getTasks();
            case PapayaPackage.TASK__START_DATE:
                return this.getStartDate();
            case PapayaPackage.TASK__END_DATE:
                return this.getEndDate();
            case PapayaPackage.TASK__DONE:
                return this.isDone();
            case PapayaPackage.TASK__DEPENDENCIES:
                return this.getDependencies();
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
            case PapayaPackage.TASK__PRIORITY:
                this.setPriority((Priority) newValue);
                return;
            case PapayaPackage.TASK__COST:
                this.setCost((Integer) newValue);
                return;
            case PapayaPackage.TASK__TARGETS:
                this.getTargets().clear();
                this.getTargets().addAll((Collection<? extends ModelElement>) newValue);
                return;
            case PapayaPackage.TASK__TASKS:
                this.getTasks().clear();
                this.getTasks().addAll((Collection<? extends Task>) newValue);
                return;
            case PapayaPackage.TASK__START_DATE:
                this.setStartDate((Instant) newValue);
                return;
            case PapayaPackage.TASK__END_DATE:
                this.setEndDate((Instant) newValue);
                return;
            case PapayaPackage.TASK__DONE:
                this.setDone((Boolean) newValue);
                return;
            case PapayaPackage.TASK__DEPENDENCIES:
                this.getDependencies().clear();
                this.getDependencies().addAll((Collection<? extends Task>) newValue);
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
            case PapayaPackage.TASK__PRIORITY:
                this.setPriority(PRIORITY_EDEFAULT);
                return;
            case PapayaPackage.TASK__COST:
                this.setCost(COST_EDEFAULT);
                return;
            case PapayaPackage.TASK__TARGETS:
                this.getTargets().clear();
                return;
            case PapayaPackage.TASK__TASKS:
                this.getTasks().clear();
                return;
            case PapayaPackage.TASK__START_DATE:
                this.setStartDate(START_DATE_EDEFAULT);
                return;
            case PapayaPackage.TASK__END_DATE:
                this.setEndDate(END_DATE_EDEFAULT);
                return;
            case PapayaPackage.TASK__DONE:
                this.setDone(DONE_EDEFAULT);
                return;
            case PapayaPackage.TASK__DEPENDENCIES:
                this.getDependencies().clear();
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
            case PapayaPackage.TASK__PRIORITY:
                return this.priority != PRIORITY_EDEFAULT;
            case PapayaPackage.TASK__COST:
                return this.cost != COST_EDEFAULT;
            case PapayaPackage.TASK__TARGETS:
                return this.targets != null && !this.targets.isEmpty();
            case PapayaPackage.TASK__TASKS:
                return this.tasks != null && !this.tasks.isEmpty();
            case PapayaPackage.TASK__START_DATE:
                return START_DATE_EDEFAULT == null ? this.startDate != null : !START_DATE_EDEFAULT.equals(this.startDate);
            case PapayaPackage.TASK__END_DATE:
                return END_DATE_EDEFAULT == null ? this.endDate != null : !END_DATE_EDEFAULT.equals(this.endDate);
            case PapayaPackage.TASK__DONE:
                return this.done != DONE_EDEFAULT;
            case PapayaPackage.TASK__DEPENDENCIES:
                return this.dependencies != null && !this.dependencies.isEmpty();
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
        result.append(" (priority: ");
        result.append(this.priority);
        result.append(", cost: ");
        result.append(this.cost);
        result.append(", startDate: ");
        result.append(this.startDate);
        result.append(", endDate: ");
        result.append(this.endDate);
        result.append(", done: ");
        result.append(this.done);
        result.append(')');
        return result.toString();
    }

} // TaskImpl

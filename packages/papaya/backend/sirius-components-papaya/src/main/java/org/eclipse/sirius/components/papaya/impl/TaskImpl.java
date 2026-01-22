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
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getCost <em>Cost</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getTargets <em>Targets</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#isDone <em>Done</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.TaskImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskImpl extends NamedElementImpl implements Task {
    /**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
    protected static final Priority PRIORITY_EDEFAULT = Priority.P1;

    /**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The cached value of the '{@link #getTargets() <em>Targets</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getTargets()
	 * @generated
	 * @ordered
	 */
    protected EList<ModelElement> targets;

    /**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
    protected EList<Task> tasks;

    /**
	 * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStartDate()
	 * @generated
	 * @ordered
	 */
    protected static final Instant START_DATE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStartDate()
	 * @generated
	 * @ordered
	 */
    protected Instant startDate = START_DATE_EDEFAULT;

    /**
	 * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
    protected static final Instant END_DATE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
    protected EList<Task> dependencies;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TaskImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.TASK;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Priority getPriority() {
		return priority;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPriority(Priority newPriority) {
		Priority oldPriority = priority;
		priority = newPriority == null ? PRIORITY_EDEFAULT : newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__PRIORITY, oldPriority, priority));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getCost() {
		return cost;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCost(int newCost) {
		int oldCost = cost;
		cost = newCost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__COST, oldCost, cost));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ModelElement> getTargets() {
		if (targets == null)
		{
			targets = new EObjectResolvingEList<ModelElement>(ModelElement.class, this, PapayaPackage.TASK__TARGETS);
		}
		return targets;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Task> getTasks() {
		if (tasks == null)
		{
			tasks = new EObjectContainmentEList<Task>(Task.class, this, PapayaPackage.TASK__TASKS);
		}
		return tasks;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Instant getStartDate() {
		return startDate;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStartDate(Instant newStartDate) {
		Instant oldStartDate = startDate;
		startDate = newStartDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__START_DATE, oldStartDate, startDate));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Instant getEndDate() {
		return endDate;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEndDate(Instant newEndDate) {
		Instant oldEndDate = endDate;
		endDate = newEndDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__END_DATE, oldEndDate, endDate));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isDone() {
		return done;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDone(boolean newDone) {
		boolean oldDone = done;
		done = newDone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.TASK__DONE, oldDone, done));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Task> getDependencies() {
		if (dependencies == null)
		{
			dependencies = new EObjectResolvingEList<Task>(Task.class, this, PapayaPackage.TASK__DEPENDENCIES);
		}
		return dependencies;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PapayaPackage.TASK__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
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
			case PapayaPackage.TASK__PRIORITY:
				return getPriority();
			case PapayaPackage.TASK__COST:
				return getCost();
			case PapayaPackage.TASK__TARGETS:
				return getTargets();
			case PapayaPackage.TASK__TASKS:
				return getTasks();
			case PapayaPackage.TASK__START_DATE:
				return getStartDate();
			case PapayaPackage.TASK__END_DATE:
				return getEndDate();
			case PapayaPackage.TASK__DONE:
				return isDone();
			case PapayaPackage.TASK__DEPENDENCIES:
				return getDependencies();
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
			case PapayaPackage.TASK__PRIORITY:
				setPriority((Priority)newValue);
				return;
			case PapayaPackage.TASK__COST:
				setCost((Integer)newValue);
				return;
			case PapayaPackage.TASK__TARGETS:
				getTargets().clear();
				getTargets().addAll((Collection<? extends ModelElement>)newValue);
				return;
			case PapayaPackage.TASK__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends Task>)newValue);
				return;
			case PapayaPackage.TASK__START_DATE:
				setStartDate((Instant)newValue);
				return;
			case PapayaPackage.TASK__END_DATE:
				setEndDate((Instant)newValue);
				return;
			case PapayaPackage.TASK__DONE:
				setDone((Boolean)newValue);
				return;
			case PapayaPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Task>)newValue);
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
			case PapayaPackage.TASK__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case PapayaPackage.TASK__COST:
				setCost(COST_EDEFAULT);
				return;
			case PapayaPackage.TASK__TARGETS:
				getTargets().clear();
				return;
			case PapayaPackage.TASK__TASKS:
				getTasks().clear();
				return;
			case PapayaPackage.TASK__START_DATE:
				setStartDate(START_DATE_EDEFAULT);
				return;
			case PapayaPackage.TASK__END_DATE:
				setEndDate(END_DATE_EDEFAULT);
				return;
			case PapayaPackage.TASK__DONE:
				setDone(DONE_EDEFAULT);
				return;
			case PapayaPackage.TASK__DEPENDENCIES:
				getDependencies().clear();
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
			case PapayaPackage.TASK__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case PapayaPackage.TASK__COST:
				return cost != COST_EDEFAULT;
			case PapayaPackage.TASK__TARGETS:
				return targets != null && !targets.isEmpty();
			case PapayaPackage.TASK__TASKS:
				return tasks != null && !tasks.isEmpty();
			case PapayaPackage.TASK__START_DATE:
				return START_DATE_EDEFAULT == null ? startDate != null : !START_DATE_EDEFAULT.equals(startDate);
			case PapayaPackage.TASK__END_DATE:
				return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
			case PapayaPackage.TASK__DONE:
				return done != DONE_EDEFAULT;
			case PapayaPackage.TASK__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
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
		result.append(" (priority: ");
		result.append(priority);
		result.append(", cost: ");
		result.append(cost);
		result.append(", startDate: ");
		result.append(startDate);
		result.append(", endDate: ");
		result.append(endDate);
		result.append(", done: ");
		result.append(done);
		result.append(')');
		return result.toString();
	}

} // TaskImpl

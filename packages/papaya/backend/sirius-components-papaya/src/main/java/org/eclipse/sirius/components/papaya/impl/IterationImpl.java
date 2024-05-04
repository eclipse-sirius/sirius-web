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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Task;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Iteration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.IterationImpl#getStartDate <em>Start Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.IterationImpl#getEndDate <em>End Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.IterationImpl#getTasks <em>Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.IterationImpl#getContributions <em>Contributions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IterationImpl extends NamedElementImpl implements Iteration {
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
     * The cached value of the '{@link #getTasks() <em>Tasks</em>}' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> tasks;

    /**
     * The cached value of the '{@link #getContributions() <em>Contributions</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getContributions()
     * @generated
     * @ordered
     */
    protected EList<Contribution> contributions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IterationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.ITERATION;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.ITERATION__START_DATE, oldStartDate, this.startDate));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.ITERATION__END_DATE, oldEndDate, this.endDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getTasks() {
        if (this.tasks == null) {
            this.tasks = new EObjectResolvingEList<>(Task.class, this, PapayaPackage.ITERATION__TASKS);
        }
        return this.tasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Contribution> getContributions() {
        if (this.contributions == null) {
            this.contributions = new EObjectResolvingEList<>(Contribution.class, this, PapayaPackage.ITERATION__CONTRIBUTIONS);
        }
        return this.contributions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.ITERATION__START_DATE:
                return this.getStartDate();
            case PapayaPackage.ITERATION__END_DATE:
                return this.getEndDate();
            case PapayaPackage.ITERATION__TASKS:
                return this.getTasks();
            case PapayaPackage.ITERATION__CONTRIBUTIONS:
                return this.getContributions();
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
            case PapayaPackage.ITERATION__START_DATE:
                this.setStartDate((Instant) newValue);
                return;
            case PapayaPackage.ITERATION__END_DATE:
                this.setEndDate((Instant) newValue);
                return;
            case PapayaPackage.ITERATION__TASKS:
                this.getTasks().clear();
                this.getTasks().addAll((Collection<? extends Task>) newValue);
                return;
            case PapayaPackage.ITERATION__CONTRIBUTIONS:
                this.getContributions().clear();
                this.getContributions().addAll((Collection<? extends Contribution>) newValue);
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
            case PapayaPackage.ITERATION__START_DATE:
                this.setStartDate(START_DATE_EDEFAULT);
                return;
            case PapayaPackage.ITERATION__END_DATE:
                this.setEndDate(END_DATE_EDEFAULT);
                return;
            case PapayaPackage.ITERATION__TASKS:
                this.getTasks().clear();
                return;
            case PapayaPackage.ITERATION__CONTRIBUTIONS:
                this.getContributions().clear();
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
            case PapayaPackage.ITERATION__START_DATE:
                return START_DATE_EDEFAULT == null ? this.startDate != null : !START_DATE_EDEFAULT.equals(this.startDate);
            case PapayaPackage.ITERATION__END_DATE:
                return END_DATE_EDEFAULT == null ? this.endDate != null : !END_DATE_EDEFAULT.equals(this.endDate);
            case PapayaPackage.ITERATION__TASKS:
                return this.tasks != null && !this.tasks.isEmpty();
            case PapayaPackage.ITERATION__CONTRIBUTIONS:
                return this.contributions != null && !this.contributions.isEmpty();
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
        result.append(" (startDate: ");
        result.append(this.startDate);
        result.append(", endDate: ");
        result.append(this.endDate);
        result.append(')');
        return result.toString();
    }

} // IterationImpl

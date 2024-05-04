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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Task;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Contribution</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ContributionImpl#getRelatedTasks <em>Related Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ContributionImpl#getTargets <em>Targets</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.ContributionImpl#isDone <em>Done</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContributionImpl extends NamedElementImpl implements Contribution {
    /**
     * The cached value of the '{@link #getRelatedTasks() <em>Related Tasks</em>}' reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getRelatedTasks()
     * @generated
     * @ordered
     */
    protected EList<Task> relatedTasks;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContributionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.CONTRIBUTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Task> getRelatedTasks() {
        if (this.relatedTasks == null) {
            this.relatedTasks = new EObjectResolvingEList<>(Task.class, this, PapayaPackage.CONTRIBUTION__RELATED_TASKS);
        }
        return this.relatedTasks;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ModelElement> getTargets() {
        if (this.targets == null) {
            this.targets = new EObjectResolvingEList<>(ModelElement.class, this, PapayaPackage.CONTRIBUTION__TARGETS);
        }
        return this.targets;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.CONTRIBUTION__DONE, oldDone, this.done));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.CONTRIBUTION__RELATED_TASKS:
                return this.getRelatedTasks();
            case PapayaPackage.CONTRIBUTION__TARGETS:
                return this.getTargets();
            case PapayaPackage.CONTRIBUTION__DONE:
                return this.isDone();
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
            case PapayaPackage.CONTRIBUTION__RELATED_TASKS:
                this.getRelatedTasks().clear();
                this.getRelatedTasks().addAll((Collection<? extends Task>) newValue);
                return;
            case PapayaPackage.CONTRIBUTION__TARGETS:
                this.getTargets().clear();
                this.getTargets().addAll((Collection<? extends ModelElement>) newValue);
                return;
            case PapayaPackage.CONTRIBUTION__DONE:
                this.setDone((Boolean) newValue);
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
            case PapayaPackage.CONTRIBUTION__RELATED_TASKS:
                this.getRelatedTasks().clear();
                return;
            case PapayaPackage.CONTRIBUTION__TARGETS:
                this.getTargets().clear();
                return;
            case PapayaPackage.CONTRIBUTION__DONE:
                this.setDone(DONE_EDEFAULT);
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
            case PapayaPackage.CONTRIBUTION__RELATED_TASKS:
                return this.relatedTasks != null && !this.relatedTasks.isEmpty();
            case PapayaPackage.CONTRIBUTION__TARGETS:
                return this.targets != null && !this.targets.isEmpty();
            case PapayaPackage.CONTRIBUTION__DONE:
                return this.done != DONE_EDEFAULT;
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
        result.append(" (done: ");
        result.append(this.done);
        result.append(')');
        return result.toString();
    }

} // ContributionImpl

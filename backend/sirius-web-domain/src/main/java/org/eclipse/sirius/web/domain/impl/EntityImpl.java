/**
 * Copyright (c) 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.domain.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Relation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Entity</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.impl.EntityImpl#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.impl.EntityImpl#getRelations <em>Relations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EntityImpl extends NamedElementImpl implements Entity {
    /**
     * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getAttributes()
     * @generated
     * @ordered
     */
    protected EList<Attribute> attributes;

    /**
     * The cached value of the '{@link #getRelations() <em>Relations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getRelations()
     * @generated
     * @ordered
     */
    protected EList<Relation> relations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EntityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DomainPackage.Literals.ENTITY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Attribute> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new EObjectContainmentEList<>(Attribute.class, this, DomainPackage.ENTITY__ATTRIBUTES);
        }
        return this.attributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Relation> getRelations() {
        if (this.relations == null) {
            this.relations = new EObjectContainmentEList<>(Relation.class, this, DomainPackage.ENTITY__RELATIONS);
        }
        return this.relations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case DomainPackage.ENTITY__ATTRIBUTES:
            return ((InternalEList<?>) this.getAttributes()).basicRemove(otherEnd, msgs);
        case DomainPackage.ENTITY__RELATIONS:
            return ((InternalEList<?>) this.getRelations()).basicRemove(otherEnd, msgs);
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
        case DomainPackage.ENTITY__ATTRIBUTES:
            return this.getAttributes();
        case DomainPackage.ENTITY__RELATIONS:
            return this.getRelations();
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
        case DomainPackage.ENTITY__ATTRIBUTES:
            this.getAttributes().clear();
            this.getAttributes().addAll((Collection<? extends Attribute>) newValue);
            return;
        case DomainPackage.ENTITY__RELATIONS:
            this.getRelations().clear();
            this.getRelations().addAll((Collection<? extends Relation>) newValue);
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
        case DomainPackage.ENTITY__ATTRIBUTES:
            this.getAttributes().clear();
            return;
        case DomainPackage.ENTITY__RELATIONS:
            this.getRelations().clear();
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
        case DomainPackage.ENTITY__ATTRIBUTES:
            return this.attributes != null && !this.attributes.isEmpty();
        case DomainPackage.ENTITY__RELATIONS:
            return this.relations != null && !this.relations.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // EntityImpl

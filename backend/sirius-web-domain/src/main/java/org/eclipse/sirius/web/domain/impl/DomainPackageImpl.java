/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.domain.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.web.domain.Attribute;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainFactory;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.domain.Relation;
import org.eclipse.sirius.web.domain.Type;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class DomainPackageImpl extends EPackageImpl implements DomainPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass domainEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass entityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass attributeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass relationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum typeEEnum = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.web.domain.DomainPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DomainPackageImpl() {
        super(eNS_URI, DomainFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link DomainPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DomainPackage init() {
        if (isInited)
            return (DomainPackage) EPackage.Registry.INSTANCE.getEPackage(DomainPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredDomainPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        DomainPackageImpl theDomainPackage = registeredDomainPackage instanceof DomainPackageImpl ? (DomainPackageImpl) registeredDomainPackage : new DomainPackageImpl();

        isInited = true;

        // Create package meta-data objects
        theDomainPackage.createPackageContents();

        // Initialize created meta-data
        theDomainPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDomainPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(DomainPackage.eNS_URI, theDomainPackage);
        return theDomainPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDomain() {
        return this.domainEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDomain_Name() {
        return (EAttribute) this.domainEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDomain_Uri() {
        return (EAttribute) this.domainEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Types() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEntity() {
        return this.entityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEntity_Name() {
        return (EAttribute) this.entityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEntity_Attributes() {
        return (EReference) this.entityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEntity_Relations() {
        return (EReference) this.entityEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAttribute() {
        return this.attributeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAttribute_Name() {
        return (EAttribute) this.attributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAttribute_Type() {
        return (EAttribute) this.attributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRelation() {
        return this.relationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRelation_Name() {
        return (EAttribute) this.relationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRelation_TargetTypes() {
        return (EReference) this.relationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getType() {
        return this.typeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DomainFactory getDomainFactory() {
        return (DomainFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.domainEClass = this.createEClass(DOMAIN);
        this.createEAttribute(this.domainEClass, DOMAIN__NAME);
        this.createEAttribute(this.domainEClass, DOMAIN__URI);
        this.createEReference(this.domainEClass, DOMAIN__TYPES);

        this.entityEClass = this.createEClass(ENTITY);
        this.createEAttribute(this.entityEClass, ENTITY__NAME);
        this.createEReference(this.entityEClass, ENTITY__ATTRIBUTES);
        this.createEReference(this.entityEClass, ENTITY__RELATIONS);

        this.attributeEClass = this.createEClass(ATTRIBUTE);
        this.createEAttribute(this.attributeEClass, ATTRIBUTE__NAME);
        this.createEAttribute(this.attributeEClass, ATTRIBUTE__TYPE);

        this.relationEClass = this.createEClass(RELATION);
        this.createEAttribute(this.relationEClass, RELATION__NAME);
        this.createEReference(this.relationEClass, RELATION__TARGET_TYPES);

        // Create enums
        this.typeEEnum = this.createEEnum(TYPE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.domainEClass, Domain.class, "Domain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getDomain_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Domain.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDomain_Uri(), this.ecorePackage.getEString(), "uri", null, 0, 1, Domain.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Types(), this.getEntity(), null, "types", null, 0, -1, Domain.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        this.initEAttribute(this.getEntity_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Entity.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEntity_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, Entity.class, //$NON-NLS-1$
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEntity_Relations(), this.getRelation(), null, "relations", null, 0, -1, Entity.class, //$NON-NLS-1$
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getAttribute_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Attribute.class, //$NON-NLS-1$
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAttribute_Type(), this.getType(), "type", null, 0, 1, Attribute.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.relationEClass, Relation.class, "Relation", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRelation_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, Relation.class, !IS_TRANSIENT, //$NON-NLS-1$
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRelation_TargetTypes(), this.getEntity(), null, "targetTypes", null, 0, -1, Relation.class, //$NON-NLS-1$
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        this.initEEnum(this.typeEEnum, Type.class, "Type"); //$NON-NLS-1$
        this.addEEnumLiteral(this.typeEEnum, Type.STRING);
        this.addEEnumLiteral(this.typeEEnum, Type.BOOLEAN);
        this.addEEnumLiteral(this.typeEEnum, Type.NUMBER);

        // Create resource
        this.createResource(eNS_URI);
    }

} // DomainPackageImpl

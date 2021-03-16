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
 *     Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.domain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.domain.DomainFactory
 * @model kind="package"
 * @generated
 */
public interface DomainPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "domain"; //$NON-NLS-1$

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/domain"; //$NON-NLS-1$

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "domain"; //$NON-NLS-1$

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DomainPackage eINSTANCE = org.eclipse.sirius.web.domain.impl.DomainPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.domain.impl.DomainImpl <em>Domain</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.domain.impl.DomainImpl
     * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getDomain()
     * @generated
     */
    int DOMAIN = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN__NAME = 0;

    /**
     * The feature id for the '<em><b>Uri</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN__URI = 1;

    /**
     * The feature id for the '<em><b>Types</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN__TYPES = 2;

    /**
     * The number of structural features of the '<em>Domain</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Domain</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.domain.impl.EntityImpl <em>Entity</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.domain.impl.EntityImpl
     * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getEntity()
     * @generated
     */
    int ENTITY = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTITY__NAME = 0;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTITY__ATTRIBUTES = 1;

    /**
     * The feature id for the '<em><b>Relations</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTITY__RELATIONS = 2;

    /**
     * The number of structural features of the '<em>Entity</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTITY_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Entity</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ENTITY_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.domain.impl.AttributeImpl <em>Attribute</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.domain.impl.AttributeImpl
     * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getAttribute()
     * @generated
     */
    int ATTRIBUTE = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE__NAME = 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE__TYPE = 1;

    /**
     * The number of structural features of the '<em>Attribute</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Attribute</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.domain.impl.RelationImpl <em>Relation</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.domain.impl.RelationImpl
     * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getRelation()
     * @generated
     */
    int RELATION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RELATION__NAME = 0;

    /**
     * The feature id for the '<em><b>Containment</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RELATION__CONTAINMENT = 1;

    /**
     * The feature id for the '<em><b>Target Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RELATION__TARGET_TYPE = 2;

    /**
     * The number of structural features of the '<em>Relation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RELATION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Relation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RELATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.domain.Type <em>Type</em>}' enum. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.domain.Type
     * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getType()
     * @generated
     */
    int TYPE = 4;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.web.domain.Domain <em>Domain</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Domain</em>'.
     * @see org.eclipse.sirius.web.domain.Domain
     * @generated
     */
    EClass getDomain();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Domain#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.web.domain.Domain#getName()
     * @see #getDomain()
     * @generated
     */
    EAttribute getDomain_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Domain#getUri <em>Uri</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Uri</em>'.
     * @see org.eclipse.sirius.web.domain.Domain#getUri()
     * @see #getDomain()
     * @generated
     */
    EAttribute getDomain_Uri();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.web.domain.Domain#getTypes
     * <em>Types</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Types</em>'.
     * @see org.eclipse.sirius.web.domain.Domain#getTypes()
     * @see #getDomain()
     * @generated
     */
    EReference getDomain_Types();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.web.domain.Entity <em>Entity</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Entity</em>'.
     * @see org.eclipse.sirius.web.domain.Entity
     * @generated
     */
    EClass getEntity();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Entity#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.web.domain.Entity#getName()
     * @see #getEntity()
     * @generated
     */
    EAttribute getEntity_Name();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.web.domain.Entity#getAttributes <em>Attributes</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Attributes</em>'.
     * @see org.eclipse.sirius.web.domain.Entity#getAttributes()
     * @see #getEntity()
     * @generated
     */
    EReference getEntity_Attributes();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.web.domain.Entity#getRelations <em>Relations</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Relations</em>'.
     * @see org.eclipse.sirius.web.domain.Entity#getRelations()
     * @see #getEntity()
     * @generated
     */
    EReference getEntity_Relations();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.web.domain.Attribute <em>Attribute</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Attribute</em>'.
     * @see org.eclipse.sirius.web.domain.Attribute
     * @generated
     */
    EClass getAttribute();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Attribute#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.web.domain.Attribute#getName()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Attribute#getType
     * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.sirius.web.domain.Attribute#getType()
     * @see #getAttribute()
     * @generated
     */
    EAttribute getAttribute_Type();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.web.domain.Relation <em>Relation</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Relation</em>'.
     * @see org.eclipse.sirius.web.domain.Relation
     * @generated
     */
    EClass getRelation();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Relation#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.web.domain.Relation#getName()
     * @see #getRelation()
     * @generated
     */
    EAttribute getRelation_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.domain.Relation#isContainment
     * <em>Containment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Containment</em>'.
     * @see org.eclipse.sirius.web.domain.Relation#isContainment()
     * @see #getRelation()
     * @generated
     */
    EAttribute getRelation_Containment();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.sirius.web.domain.Relation#getTargetType <em>Target
     * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Target Type</em>'.
     * @see org.eclipse.sirius.web.domain.Relation#getTargetType()
     * @see #getRelation()
     * @generated
     */
    EReference getRelation_TargetType();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.web.domain.Type <em>Type</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Type</em>'.
     * @see org.eclipse.sirius.web.domain.Type
     * @generated
     */
    EEnum getType();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DomainFactory getDomainFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.sirius.web.domain.impl.DomainImpl <em>Domain</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.domain.impl.DomainImpl
         * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getDomain()
         * @generated
         */
        EClass DOMAIN = eINSTANCE.getDomain();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DOMAIN__NAME = eINSTANCE.getDomain_Name();

        /**
         * The meta object literal for the '<em><b>Uri</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DOMAIN__URI = eINSTANCE.getDomain_Uri();

        /**
         * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__TYPES = eINSTANCE.getDomain_Types();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.web.domain.impl.EntityImpl <em>Entity</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.domain.impl.EntityImpl
         * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getEntity()
         * @generated
         */
        EClass ENTITY = eINSTANCE.getEntity();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

        /**
         * The meta object literal for the '<em><b>Relations</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ENTITY__RELATIONS = eINSTANCE.getEntity_Relations();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.web.domain.impl.AttributeImpl <em>Attribute</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.domain.impl.AttributeImpl
         * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getAttribute()
         * @generated
         */
        EClass ATTRIBUTE = eINSTANCE.getAttribute();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.web.domain.impl.RelationImpl <em>Relation</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.domain.impl.RelationImpl
         * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getRelation()
         * @generated
         */
        EClass RELATION = eINSTANCE.getRelation();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RELATION__NAME = eINSTANCE.getRelation_Name();

        /**
         * The meta object literal for the '<em><b>Containment</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RELATION__CONTAINMENT = eINSTANCE.getRelation_Containment();

        /**
         * The meta object literal for the '<em><b>Target Type</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference RELATION__TARGET_TYPE = eINSTANCE.getRelation_TargetType();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.web.domain.Type <em>Type</em>}' enum. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.domain.Type
         * @see org.eclipse.sirius.web.domain.impl.DomainPackageImpl#getType()
         * @generated
         */
        EEnum TYPE = eINSTANCE.getType();

    }

} // DomainPackage

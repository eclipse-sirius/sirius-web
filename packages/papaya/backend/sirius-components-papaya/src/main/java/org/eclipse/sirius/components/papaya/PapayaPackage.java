/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.eclipse.sirius.components.papaya.PapayaFactory
 * @model kind="package"
 * @generated
 */
public interface PapayaPackage extends EPackage {
    /**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNAME = "papaya";

    /**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_URI = "https://www.eclipse.org/sirius-web/papaya";

    /**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_PREFIX = "papaya";

    /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    PapayaPackage eINSTANCE = org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl.init();

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ModelElementImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getModelElement()
	 * @generated
	 */
    int MODEL_ELEMENT = 0;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MODEL_ELEMENT__TAGS = 0;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MODEL_ELEMENT__LINKS = 1;

    /**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MODEL_ELEMENT_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MODEL_ELEMENT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.TagImpl <em>Tag</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.impl.TagImpl
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTag()
     * @generated
     */
    int TAG = 1;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TAG__KEY = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TAG__VALUE = 1;

    /**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TAG_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TAG_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.LinkImpl <em>Link</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.impl.LinkImpl
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getLink()
     * @generated
     */
    int LINK = 2;

    /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINK__KIND = 0;

    /**
     * The feature id for the '<em><b>Source</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK__SOURCE = 1;

    /**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINK_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINK_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ReferencingLinkImpl <em>Referencing Link</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ReferencingLinkImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getReferencingLink()
	 * @generated
	 */
    int REFERENCING_LINK = 3;

    /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REFERENCING_LINK__KIND = LINK__KIND;

    /**
     * The feature id for the '<em><b>Source</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REFERENCING_LINK__SOURCE = LINK__SOURCE;

    /**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REFERENCING_LINK__TARGET = LINK_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Referencing Link</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REFERENCING_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Referencing Link</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REFERENCING_LINK_OPERATION_COUNT = LINK_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ContainingLinkImpl <em>Containing Link</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ContainingLinkImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContainingLink()
	 * @generated
	 */
    int CONTAINING_LINK = 4;

    /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINING_LINK__KIND = LINK__KIND;

    /**
     * The feature id for the '<em><b>Source</b></em>' container reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINING_LINK__SOURCE = LINK__SOURCE;

    /**
     * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINING_LINK__TARGET = LINK_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Containing Link</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINING_LINK_FEATURE_COUNT = LINK_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Containing Link</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINING_LINK_OPERATION_COUNT = LINK_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.NamedElementImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getNamedElement()
	 * @generated
	 */
    int NAMED_ELEMENT = 5;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT__TAGS = MODEL_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT__LINKS = MODEL_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT__NAME = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT__DESCRIPTION = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int NAMED_ELEMENT_OPERATION_COUNT = MODEL_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.Container <em>Container</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.Container
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContainer()
	 * @generated
	 */
    int CONTAINER = 6;

    /**
	 * The feature id for the '<em><b>Folders</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINER__FOLDERS = 0;

    /**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINER__ELEMENTS = 1;

    /**
     * The number of structural features of the '<em>Container</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTAINER_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Container</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTAINER_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.FolderImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getFolder()
	 * @generated
	 */
    int FOLDER = 7;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Folders</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__FOLDERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER__ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.FolderElement <em>Folder Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.FolderElement
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getFolderElement()
	 * @generated
	 */
    int FOLDER_ELEMENT = 8;

    /**
	 * The number of structural features of the '<em>Folder Element</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER_ELEMENT_FEATURE_COUNT = 0;

    /**
	 * The number of operations of the '<em>Folder Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FOLDER_ELEMENT_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ProjectImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getProject()
	 * @generated
	 */
    int PROJECT = 9;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Folders</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__FOLDERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Homepage</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__HOMEPAGE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Project</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalCapabilityImpl <em>Operational Capability</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalCapabilityImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalCapability()
	 * @generated
	 */
    int OPERATIONAL_CAPABILITY = 10;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Constraints</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY__CONSTRAINTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Operational Capability</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Operational Capability</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_CAPABILITY_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalEntityImpl <em>Operational Entity</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalEntityImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalEntity()
	 * @generated
	 */
    int OPERATIONAL_ENTITY = 11;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ENTITY__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ENTITY__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ENTITY__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ENTITY__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The number of structural features of the '<em>Operational Entity</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ENTITY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Operational Entity</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATIONAL_ENTITY_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalActorImpl <em>Operational Actor</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalActorImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalActor()
	 * @generated
	 */
    int OPERATIONAL_ACTOR = 12;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Operational Actor</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Operational Actor</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTOR_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalProcessImpl <em>Operational Process</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalProcessImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalProcess()
	 * @generated
	 */
    int OPERATIONAL_PROCESS = 13;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_PROCESS__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_PROCESS__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_PROCESS__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_PROCESS__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The number of structural features of the '<em>Operational Process</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_PROCESS_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Operational Process</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATIONAL_PROCESS_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl <em>Operational Activity</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalActivity()
	 * @generated
	 */
    int OPERATIONAL_ACTIVITY = 14;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Precondition</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__PRECONDITION = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Postcondition</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY__POSTCONDITION = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Operational Activity</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_ACTIVITY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Operational Activity</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATIONAL_ACTIVITY_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl <em>Operational Interaction</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalInteraction()
	 * @generated
	 */
    int OPERATIONAL_INTERACTION = 15;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__FREQUENCY = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__PROTOCOL = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Latency</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION__LATENCY = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of structural features of the '<em>Operational Interaction</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The number of operations of the '<em>Operational Interaction</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATIONAL_INTERACTION_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.IterationImpl <em>Iteration</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.IterationImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getIteration()
	 * @generated
	 */
    int ITERATION = 16;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__START_DATE = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__END_DATE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Tasks</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION__TASKS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Contributions</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ITERATION__CONTRIBUTIONS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Iteration</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ITERATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The number of operations of the '<em>Iteration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ITERATION_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.TaskImpl <em>Task</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.impl.TaskImpl
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTask()
     * @generated
     */
    int TASK = 17;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__PRIORITY = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Cost</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__COST = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Targets</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__TARGETS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__TASKS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__START_DATE = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__END_DATE = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>Done</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK__DONE = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TASK__DEPENDENCIES = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
	 * The number of operations of the '<em>Task</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TASK_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ContributionImpl <em>Contribution</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ContributionImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContribution()
	 * @generated
	 */
    int CONTRIBUTION = 18;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Related Tasks</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTRIBUTION__RELATED_TASKS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Targets</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__TARGETS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Done</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION__DONE = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Contribution</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTRIBUTION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Contribution</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTRIBUTION_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ComponentImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponent()
	 * @generated
	 */
    int COMPONENT = 19;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT__DEPENDENCIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>All Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__ALL_DEPENDENCIES = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Used As Dependency By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__USED_AS_DEPENDENCY_BY = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__COMPONENTS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>All Components</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT__ALL_COMPONENTS = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__PACKAGES = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__PORTS = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
	 * The feature id for the '<em><b>Provided Services</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__PROVIDED_SERVICES = NAMED_ELEMENT_FEATURE_COUNT + 7;

    /**
	 * The feature id for the '<em><b>Required Services</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT__REQUIRED_SERVICES = NAMED_ELEMENT_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Component</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 9;

    /**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentPortImpl <em>Component Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ComponentPortImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponentPort()
	 * @generated
	 */
    int COMPONENT_PORT = 20;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT__PROTOCOL = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Component Port</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Component Port</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_PORT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentExchangeImpl <em>Component Exchange</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ComponentExchangeImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponentExchange()
	 * @generated
	 */
    int COMPONENT_EXCHANGE = 21;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Ports</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE__PORTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Component Exchange</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMPONENT_EXCHANGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Component Exchange</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COMPONENT_EXCHANGE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ProvidedServiceImpl <em>Provided Service</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ProvidedServiceImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getProvidedService()
	 * @generated
	 */
    int PROVIDED_SERVICE = 22;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Contracts</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE__CONTRACTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Provided Service</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Provided Service</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROVIDED_SERVICE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.RequiredServiceImpl <em>Required Service</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.RequiredServiceImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRequiredService()
	 * @generated
	 */
    int REQUIRED_SERVICE = 23;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Contracts</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE__CONTRACTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Required Service</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Required Service</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REQUIRED_SERVICE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.AnnotableElement <em>Annotable Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.AnnotableElement
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotableElement()
	 * @generated
	 */
    int ANNOTABLE_ELEMENT = 24;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ANNOTABLE_ELEMENT__ANNOTATIONS = 0;

    /**
	 * The number of structural features of the '<em>Annotable Element</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTABLE_ELEMENT_FEATURE_COUNT = 1;

    /**
	 * The number of operations of the '<em>Annotable Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTABLE_ELEMENT_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.PackageImpl <em>Package</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.PackageImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPackage()
	 * @generated
	 */
    int PACKAGE = 25;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PACKAGE__ANNOTATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__QUALIFIED_NAME = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__TYPES = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE__PACKAGES = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of structural features of the '<em>Package</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The number of operations of the '<em>Package</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PACKAGE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.TypeImpl <em>Type</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.impl.TypeImpl
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getType()
     * @generated
     */
    int TYPE = 26;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TYPE__ANNOTATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__QUALIFIED_NAME = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__VISIBILITY = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE__TYPES = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.TypedElementImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTypedElement()
	 * @generated
	 */
    int TYPED_ELEMENT = 27;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TYPED_ELEMENT__ANNOTATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TYPED_ELEMENT__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPED_ELEMENT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.GenericTypeImpl <em>Generic Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.GenericTypeImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getGenericType()
	 * @generated
	 */
    int GENERIC_TYPE = 28;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int GENERIC_TYPE__TAGS = MODEL_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int GENERIC_TYPE__LINKS = MODEL_ELEMENT__LINKS;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GENERIC_TYPE__ANNOTATIONS = MODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Raw Type</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int GENERIC_TYPE__RAW_TYPE = MODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Type Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int GENERIC_TYPE__TYPE_ARGUMENTS = MODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Generic Type</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GENERIC_TYPE_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Generic Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int GENERIC_TYPE_OPERATION_COUNT = MODEL_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.AnnotationImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotation()
	 * @generated
	 */
    int ANNOTATION = 29;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__TAGS = TYPE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__LINKS = TYPE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__NAME = TYPE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__DESCRIPTION = TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ANNOTATION__ANNOTATIONS = TYPE__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__QUALIFIED_NAME = TYPE__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__VISIBILITY = TYPE__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__TYPES = TYPE__TYPES;

    /**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION__FIELDS = TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Annotation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ANNOTATION_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.AnnotationFieldImpl <em>Annotation Field</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.AnnotationFieldImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotationField()
	 * @generated
	 */
    int ANNOTATION_FIELD = 30;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD__TAGS = TYPED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD__LINKS = TYPED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD__NAME = TYPED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD__DESCRIPTION = TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ANNOTATION_FIELD__ANNOTATIONS = TYPED_ELEMENT__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ANNOTATION_FIELD__TYPE = TYPED_ELEMENT__TYPE;

    /**
	 * The number of structural features of the '<em>Annotation Field</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Annotation Field</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ANNOTATION_FIELD_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ClassifierImpl <em>Classifier</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ClassifierImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getClassifier()
	 * @generated
	 */
    int CLASSIFIER = 31;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__TAGS = TYPE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__LINKS = TYPE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__NAME = TYPE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__DESCRIPTION = TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CLASSIFIER__ANNOTATIONS = TYPE__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__QUALIFIED_NAME = TYPE__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__VISIBILITY = TYPE__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__TYPES = TYPE__TYPES;

    /**
	 * The feature id for the '<em><b>Type Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER__TYPE_PARAMETERS = TYPE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Classifier</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CLASSIFIER_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Classifier</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASSIFIER_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.TypeParameterImpl <em>Type Parameter</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.TypeParameterImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTypeParameter()
	 * @generated
	 */
    int TYPE_PARAMETER = 32;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The number of structural features of the '<em>Type Parameter</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Type Parameter</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TYPE_PARAMETER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl <em>Interface</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.InterfaceImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInterface()
	 * @generated
	 */
    int INTERFACE = 33;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__TAGS = CLASSIFIER__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__LINKS = CLASSIFIER__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__NAME = CLASSIFIER__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__DESCRIPTION = CLASSIFIER__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE__ANNOTATIONS = CLASSIFIER__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__QUALIFIED_NAME = CLASSIFIER__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__VISIBILITY = CLASSIFIER__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__TYPES = CLASSIFIER__TYPES;

    /**
	 * The feature id for the '<em><b>Type Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__TYPE_PARAMETERS = CLASSIFIER__TYPE_PARAMETERS;

    /**
	 * The feature id for the '<em><b>Extends</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__EXTENDS = CLASSIFIER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Extended By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE__EXTENDED_BY = CLASSIFIER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Implemented By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE__IMPLEMENTED_BY = CLASSIFIER_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Subtypes</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__SUBTYPES = CLASSIFIER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>All Subtypes</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE__ALL_SUBTYPES = CLASSIFIER_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE__OPERATIONS = CLASSIFIER_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Interface</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INTERFACE_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 6;

    /**
	 * The number of operations of the '<em>Interface</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE_OPERATION_COUNT = CLASSIFIER_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation <em>Interface Implementation</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.InterfaceImplementation
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInterfaceImplementation()
	 * @generated
	 */
    int INTERFACE_IMPLEMENTATION = 34;

    /**
	 * The feature id for the '<em><b>Implements</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE_IMPLEMENTATION__IMPLEMENTS = 0;

    /**
	 * The number of structural features of the '<em>Interface Implementation</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE_IMPLEMENTATION_FEATURE_COUNT = 1;

    /**
	 * The number of operations of the '<em>Interface Implementation</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INTERFACE_IMPLEMENTATION_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ClassImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getClass_()
	 * @generated
	 */
    int CLASS = 35;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__TAGS = CLASSIFIER__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__LINKS = CLASSIFIER__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__NAME = CLASSIFIER__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__DESCRIPTION = CLASSIFIER__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CLASS__ANNOTATIONS = CLASSIFIER__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__QUALIFIED_NAME = CLASSIFIER__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__VISIBILITY = CLASSIFIER__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__TYPES = CLASSIFIER__TYPES;

    /**
	 * The feature id for the '<em><b>Type Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__TYPE_PARAMETERS = CLASSIFIER__TYPE_PARAMETERS;

    /**
	 * The feature id for the '<em><b>Implements</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__IMPLEMENTS = CLASSIFIER_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__ABSTRACT = CLASSIFIER_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__FINAL = CLASSIFIER_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__STATIC = CLASSIFIER_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__EXTENDS = CLASSIFIER_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Extended By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CLASS__EXTENDED_BY = CLASSIFIER_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>All Extended By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__ALL_EXTENDED_BY = CLASSIFIER_FEATURE_COUNT + 6;

    /**
	 * The feature id for the '<em><b>Constructors</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__CONSTRUCTORS = CLASSIFIER_FEATURE_COUNT + 7;

    /**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__ATTRIBUTES = CLASSIFIER_FEATURE_COUNT + 8;

    /**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS__OPERATIONS = CLASSIFIER_FEATURE_COUNT + 9;

    /**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 10;

    /**
	 * The number of operations of the '<em>Class</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CLASS_OPERATION_COUNT = CLASSIFIER_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ConstructorImpl <em>Constructor</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ConstructorImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getConstructor()
	 * @generated
	 */
    int CONSTRUCTOR = 36;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONSTRUCTOR__ANNOTATIONS = ANNOTABLE_ELEMENT__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONSTRUCTOR__PARAMETERS = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONSTRUCTOR__VISIBILITY = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Constructor</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONSTRUCTOR_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Constructor</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONSTRUCTOR_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.AttributeImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAttribute()
	 * @generated
	 */
    int ATTRIBUTE = 37;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__TAGS = TYPED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__LINKS = TYPED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__NAME = TYPED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__DESCRIPTION = TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE__ANNOTATIONS = TYPED_ELEMENT__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE__TYPE = TYPED_ELEMENT__TYPE;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__VISIBILITY = TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__FINAL = TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE__STATIC = TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Attribute</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ATTRIBUTE_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ATTRIBUTE_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.OperationImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperation()
	 * @generated
	 */
    int OPERATION = 38;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__TAGS = TYPED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__LINKS = TYPED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__NAME = TYPED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__DESCRIPTION = TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATION__ANNOTATIONS = TYPED_ELEMENT__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATION__TYPE = TYPED_ELEMENT__TYPE;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__VISIBILITY = TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__ABSTRACT = TYPED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__FINAL = TYPED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__STATIC = TYPED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION__PARAMETERS = TYPED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Operation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATION_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 5;

    /**
	 * The number of operations of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int OPERATION_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ParameterImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getParameter()
	 * @generated
	 */
    int PARAMETER = 39;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__TAGS = TYPED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__LINKS = TYPED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__NAME = TYPED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER__DESCRIPTION = TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PARAMETER__ANNOTATIONS = TYPED_ELEMENT__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PARAMETER__TYPE = TYPED_ELEMENT__TYPE;

    /**
     * The number of structural features of the '<em>Parameter</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PARAMETER_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PARAMETER_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.RecordImpl <em>Record</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.RecordImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRecord()
	 * @generated
	 */
    int RECORD = 40;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__TAGS = CLASSIFIER__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__LINKS = CLASSIFIER__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__NAME = CLASSIFIER__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__DESCRIPTION = CLASSIFIER__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECORD__ANNOTATIONS = CLASSIFIER__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__QUALIFIED_NAME = CLASSIFIER__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__VISIBILITY = CLASSIFIER__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__TYPES = CLASSIFIER__TYPES;

    /**
	 * The feature id for the '<em><b>Type Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__TYPE_PARAMETERS = CLASSIFIER__TYPE_PARAMETERS;

    /**
	 * The feature id for the '<em><b>Implements</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__IMPLEMENTS = CLASSIFIER_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__COMPONENTS = CLASSIFIER_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD__OPERATIONS = CLASSIFIER_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Record</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Record</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_OPERATION_COUNT = CLASSIFIER_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.RecordComponentImpl <em>Record Component</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.RecordComponentImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRecordComponent()
	 * @generated
	 */
    int RECORD_COMPONENT = 41;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT__TAGS = TYPED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT__LINKS = TYPED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT__NAME = TYPED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT__DESCRIPTION = TYPED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECORD_COMPONENT__ANNOTATIONS = TYPED_ELEMENT__ANNOTATIONS;

    /**
     * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECORD_COMPONENT__TYPE = TYPED_ELEMENT__TYPE;

    /**
	 * The number of structural features of the '<em>Record Component</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Record Component</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RECORD_COMPONENT_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.DataTypeImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getDataType()
	 * @generated
	 */
    int DATA_TYPE = 42;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__TAGS = TYPE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__LINKS = TYPE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__NAME = TYPE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__DESCRIPTION = TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DATA_TYPE__ANNOTATIONS = TYPE__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__QUALIFIED_NAME = TYPE__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__VISIBILITY = TYPE__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE__TYPES = TYPE__TYPES;

    /**
     * The number of structural features of the '<em>Data Type</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DATA_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DATA_TYPE_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.EnumImpl <em>Enum</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.impl.EnumImpl
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEnum()
     * @generated
     */
    int ENUM = 43;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__TAGS = TYPE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__LINKS = TYPE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__NAME = TYPE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__DESCRIPTION = TYPE__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ENUM__ANNOTATIONS = TYPE__ANNOTATIONS;

    /**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__QUALIFIED_NAME = TYPE__QUALIFIED_NAME;

    /**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__VISIBILITY = TYPE__VISIBILITY;

    /**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__TYPES = TYPE__TYPES;

    /**
	 * The feature id for the '<em><b>Literals</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM__LITERALS = TYPE_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Enum</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Enum</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_OPERATION_COUNT = TYPE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.EnumLiteralImpl <em>Enum Literal</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.EnumLiteralImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEnumLiteral()
	 * @generated
	 */
    int ENUM_LITERAL = 44;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_LITERAL__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_LITERAL__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_LITERAL__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_LITERAL__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Annotations</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ENUM_LITERAL__ANNOTATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Enum Literal</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ENUM_LITERAL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Enum Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ENUM_LITERAL_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl <em>Application Concern</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getApplicationConcern()
	 * @generated
	 */
    int APPLICATION_CONCERN = 45;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Controllers</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__CONTROLLERS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Services</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__SERVICES = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__EVENTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Commands</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__COMMANDS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__QUERIES = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>Domains</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN__DOMAINS = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
	 * The number of structural features of the '<em>Application Concern</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int APPLICATION_CONCERN_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Application Concern</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int APPLICATION_CONCERN_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ControllerImpl <em>Controller</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ControllerImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getController()
	 * @generated
	 */
    int CONTROLLER = 46;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Publications</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__PUBLICATIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Subscriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__SUBSCRIPTIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Calls</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER__CALLS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Controller</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONTROLLER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Controller</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CONTROLLER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.DomainImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getDomain()
	 * @generated
	 */
    int DOMAIN = 47;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Services</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__SERVICES = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__REPOSITORIES = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__EVENTS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Commands</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__COMMANDS = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Queries</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN__QUERIES = NAMED_ELEMENT_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DOMAIN__DEPENDENCIES = NAMED_ELEMENT_FEATURE_COUNT + 5;

    /**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

    /**
	 * The number of operations of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOMAIN_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl <em>Service</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ServiceImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getService()
	 * @generated
	 */
    int SERVICE = 48;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Subscriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__SUBSCRIPTIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Publications</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__PUBLICATIONS = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Calls</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE__CALLS = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Service</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

    /**
	 * The number of operations of the '<em>Service</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SERVICE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.MessageListener <em>Message Listener</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.MessageListener
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessageListener()
	 * @generated
	 */
    int MESSAGE_LISTENER = 49;

    /**
	 * The feature id for the '<em><b>Subscriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_LISTENER__SUBSCRIPTIONS = 0;

    /**
	 * The number of structural features of the '<em>Message Listener</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_LISTENER_FEATURE_COUNT = 1;

    /**
	 * The number of operations of the '<em>Message Listener</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_LISTENER_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.MessageEmitter <em>Message Emitter</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.MessageEmitter
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessageEmitter()
	 * @generated
	 */
    int MESSAGE_EMITTER = 50;

    /**
	 * The feature id for the '<em><b>Publications</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_EMITTER__PUBLICATIONS = 0;

    /**
	 * The number of structural features of the '<em>Message Emitter</em>' class.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_EMITTER_FEATURE_COUNT = 1;

    /**
	 * The number of operations of the '<em>Message Emitter</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_EMITTER_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.Message <em>Message</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.Message
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessage()
     * @generated
     */
    int MESSAGE = 51;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Emitted By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE__EMITTED_BY = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Listened By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MESSAGE__LISTENED_BY = NAMED_ELEMENT_FEATURE_COUNT + 1;

    /**
	 * The number of structural features of the '<em>Message</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

    /**
	 * The number of operations of the '<em>Message</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int MESSAGE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.EventImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEvent()
	 * @generated
	 */
    int EVENT = 52;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__TAGS = MESSAGE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__LINKS = MESSAGE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__NAME = MESSAGE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__DESCRIPTION = MESSAGE__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Emitted By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__EMITTED_BY = MESSAGE__EMITTED_BY;

    /**
     * The feature id for the '<em><b>Listened By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EVENT__LISTENED_BY = MESSAGE__LISTENED_BY;

    /**
	 * The feature id for the '<em><b>Caused By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT__CAUSED_BY = MESSAGE_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT_FEATURE_COUNT = MESSAGE_FEATURE_COUNT + 1;

    /**
	 * The number of operations of the '<em>Event</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int EVENT_OPERATION_COUNT = MESSAGE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.CommandImpl <em>Command</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.CommandImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getCommand()
	 * @generated
	 */
    int COMMAND = 53;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND__TAGS = MESSAGE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND__LINKS = MESSAGE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND__NAME = MESSAGE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND__DESCRIPTION = MESSAGE__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Emitted By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND__EMITTED_BY = MESSAGE__EMITTED_BY;

    /**
     * The feature id for the '<em><b>Listened By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COMMAND__LISTENED_BY = MESSAGE__LISTENED_BY;

    /**
	 * The number of structural features of the '<em>Command</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND_FEATURE_COUNT = MESSAGE_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Command</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMAND_OPERATION_COUNT = MESSAGE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.QueryImpl <em>Query</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.QueryImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getQuery()
	 * @generated
	 */
    int QUERY = 54;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY__TAGS = MESSAGE__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY__LINKS = MESSAGE__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY__NAME = MESSAGE__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY__DESCRIPTION = MESSAGE__DESCRIPTION;

    /**
	 * The feature id for the '<em><b>Emitted By</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY__EMITTED_BY = MESSAGE__EMITTED_BY;

    /**
     * The feature id for the '<em><b>Listened By</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int QUERY__LISTENED_BY = MESSAGE__LISTENED_BY;

    /**
	 * The number of structural features of the '<em>Query</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY_FEATURE_COUNT = MESSAGE_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Query</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int QUERY_OPERATION_COUNT = MESSAGE_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.RepositoryImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRepository()
	 * @generated
	 */
    int REPOSITORY = 55;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REPOSITORY__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REPOSITORY__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REPOSITORY__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REPOSITORY__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
     * The number of structural features of the '<em>Repository</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REPOSITORY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REPOSITORY_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.ChannelImpl <em>Channel</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.ChannelImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getChannel()
	 * @generated
	 */
    int CHANNEL = 56;

    /**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL__TAGS = NAMED_ELEMENT__TAGS;

    /**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL__LINKS = NAMED_ELEMENT__LINKS;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL__NAME = NAMED_ELEMENT__NAME;

    /**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

    /**
	 * The number of structural features of the '<em>Channel</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 0;

    /**
	 * The number of operations of the '<em>Channel</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CHANNEL_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.SubscriptionImpl <em>Subscription</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.SubscriptionImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getSubscription()
	 * @generated
	 */
    int SUBSCRIPTION = 57;

    /**
	 * The feature id for the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SUBSCRIPTION__CHANNEL = 0;

    /**
	 * The feature id for the '<em><b>Message</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SUBSCRIPTION__MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Subscription</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SUBSCRIPTION_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Subscription</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SUBSCRIPTION_OPERATION_COUNT = 0;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.impl.PublicationImpl <em>Publication</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.impl.PublicationImpl
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPublication()
	 * @generated
	 */
    int PUBLICATION = 58;

    /**
	 * The feature id for the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PUBLICATION__CHANNEL = 0;

    /**
	 * The feature id for the '<em><b>Message</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PUBLICATION__MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Publication</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PUBLICATION_FEATURE_COUNT = 2;

    /**
	 * The number of operations of the '<em>Publication</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PUBLICATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.papaya.Priority <em>Priority</em>}' enum. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.papaya.Priority
     * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPriority()
     * @generated
     */
    int PRIORITY = 59;

    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.papaya.Visibility <em>Visibility</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.papaya.Visibility
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getVisibility()
	 * @generated
	 */
    int VISIBILITY = 60;

    /**
	 * The meta object id for the '<em>Instant</em>' data type.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see java.time.Instant
	 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInstant()
	 * @generated
	 */
    int INSTANT = 61;

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see org.eclipse.sirius.components.papaya.ModelElement
	 * @generated
	 */
    EClass getModelElement();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.ModelElement#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tags</em>'.
	 * @see org.eclipse.sirius.components.papaya.ModelElement#getTags()
	 * @see #getModelElement()
	 * @generated
	 */
    EReference getModelElement_Tags();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.ModelElement#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.eclipse.sirius.components.papaya.ModelElement#getLinks()
	 * @see #getModelElement()
	 * @generated
	 */
    EReference getModelElement_Links();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Tag <em>Tag</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tag</em>'.
     * @see org.eclipse.sirius.components.papaya.Tag
     * @generated
     */
    EClass getTag();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Tag#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.sirius.components.papaya.Tag#getKey()
	 * @see #getTag()
	 * @generated
	 */
    EAttribute getTag_Key();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Tag#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.sirius.components.papaya.Tag#getValue()
	 * @see #getTag()
	 * @generated
	 */
    EAttribute getTag_Value();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Link <em>Link</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Link</em>'.
     * @see org.eclipse.sirius.components.papaya.Link
     * @generated
     */
    EClass getLink();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Link#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.sirius.components.papaya.Link#getKind()
	 * @see #getLink()
	 * @generated
	 */
    EAttribute getLink_Kind();

    /**
	 * Returns the meta object for the container reference '{@link org.eclipse.sirius.components.papaya.Link#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see org.eclipse.sirius.components.papaya.Link#getSource()
	 * @see #getLink()
	 * @generated
	 */
    EReference getLink_Source();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ReferencingLink <em>Referencing Link</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referencing Link</em>'.
	 * @see org.eclipse.sirius.components.papaya.ReferencingLink
	 * @generated
	 */
    EClass getReferencingLink();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.ReferencingLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.sirius.components.papaya.ReferencingLink#getTarget()
	 * @see #getReferencingLink()
	 * @generated
	 */
    EReference getReferencingLink_Target();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ContainingLink <em>Containing Link</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Containing Link</em>'.
	 * @see org.eclipse.sirius.components.papaya.ContainingLink
	 * @generated
	 */
    EClass getContainingLink();

    /**
	 * Returns the meta object for the containment reference '{@link org.eclipse.sirius.components.papaya.ContainingLink#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.sirius.components.papaya.ContainingLink#getTarget()
	 * @see #getContainingLink()
	 * @generated
	 */
    EReference getContainingLink_Target();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.sirius.components.papaya.NamedElement
	 * @generated
	 */
    EClass getNamedElement();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.sirius.components.papaya.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
    EAttribute getNamedElement_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.NamedElement#getDescription <em>Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.eclipse.sirius.components.papaya.NamedElement#getDescription()
     * @see #getNamedElement()
     * @generated
     */
    EAttribute getNamedElement_Description();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Container <em>Container</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.sirius.components.papaya.Container
	 * @generated
	 */
    EClass getContainer();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Container#getFolders <em>Folders</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Folders</em>'.
	 * @see org.eclipse.sirius.components.papaya.Container#getFolders()
	 * @see #getContainer()
	 * @generated
	 */
    EReference getContainer_Folders();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Container#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.sirius.components.papaya.Container#getElements()
	 * @see #getContainer()
	 * @generated
	 */
    EReference getContainer_Elements();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Folder <em>Folder</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Folder</em>'.
     * @see org.eclipse.sirius.components.papaya.Folder
     * @generated
     */
    EClass getFolder();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.FolderElement <em>Folder Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder Element</em>'.
	 * @see org.eclipse.sirius.components.papaya.FolderElement
	 * @generated
	 */
    EClass getFolderElement();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Project <em>Project</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Project</em>'.
     * @see org.eclipse.sirius.components.papaya.Project
     * @generated
     */
    EClass getProject();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Project#getHomepage <em>Homepage</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Homepage</em>'.
	 * @see org.eclipse.sirius.components.papaya.Project#getHomepage()
	 * @see #getProject()
	 * @generated
	 */
    EAttribute getProject_Homepage();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalCapability <em>Operational Capability</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Capability</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalCapability
	 * @generated
	 */
    EClass getOperationalCapability();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalCapability#getConstraints <em>Constraints</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Constraints</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalCapability#getConstraints()
     * @see #getOperationalCapability()
     * @generated
     */
    EAttribute getOperationalCapability_Constraints();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalEntity <em>Operational Entity</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Entity</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalEntity
	 * @generated
	 */
    EClass getOperationalEntity();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalActor <em>Operational Actor</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Actor</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalActor
	 * @generated
	 */
    EClass getOperationalActor();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.OperationalActor#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalActor#getType()
	 * @see #getOperationalActor()
	 * @generated
	 */
    EAttribute getOperationalActor_Type();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalProcess <em>Operational Process</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Process</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalProcess
	 * @generated
	 */
    EClass getOperationalProcess();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalActivity <em>Operational Activity</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Activity</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalActivity
	 * @generated
	 */
    EClass getOperationalActivity();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPrecondition <em>Precondition</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalActivity#getPrecondition()
     * @see #getOperationalActivity()
     * @generated
     */
    EAttribute getOperationalActivity_Precondition();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPostcondition <em>Postcondition</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Postcondition</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalActivity#getPostcondition()
     * @see #getOperationalActivity()
     * @generated
     */
    EAttribute getOperationalActivity_Postcondition();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.OperationalInteraction <em>Operational Interaction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Interaction</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalInteraction
	 * @generated
	 */
    EClass getOperationalInteraction();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getType <em>Type</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.OperationalInteraction#getType()
	 * @see #getOperationalInteraction()
	 * @generated
	 */
    EAttribute getOperationalInteraction_Type();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getFrequency <em>Frequency</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Frequency</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalInteraction#getFrequency()
     * @see #getOperationalInteraction()
     * @generated
     */
    EAttribute getOperationalInteraction_Frequency();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getProtocol <em>Protocol</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Protocol</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalInteraction#getProtocol()
     * @see #getOperationalInteraction()
     * @generated
     */
    EAttribute getOperationalInteraction_Protocol();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getLatency <em>Latency</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Latency</em>'.
     * @see org.eclipse.sirius.components.papaya.OperationalInteraction#getLatency()
     * @see #getOperationalInteraction()
     * @generated
     */
    EAttribute getOperationalInteraction_Latency();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Iteration <em>Iteration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iteration</em>'.
	 * @see org.eclipse.sirius.components.papaya.Iteration
	 * @generated
	 */
    EClass getIteration();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Iteration#getStartDate <em>Start Date</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date</em>'.
	 * @see org.eclipse.sirius.components.papaya.Iteration#getStartDate()
	 * @see #getIteration()
	 * @generated
	 */
    EAttribute getIteration_StartDate();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Iteration#getEndDate <em>End Date</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date</em>'.
	 * @see org.eclipse.sirius.components.papaya.Iteration#getEndDate()
	 * @see #getIteration()
	 * @generated
	 */
    EAttribute getIteration_EndDate();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Iteration#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Tasks</em>'.
	 * @see org.eclipse.sirius.components.papaya.Iteration#getTasks()
	 * @see #getIteration()
	 * @generated
	 */
    EReference getIteration_Tasks();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Iteration#getContributions <em>Contributions</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Contributions</em>'.
     * @see org.eclipse.sirius.components.papaya.Iteration#getContributions()
     * @see #getIteration()
     * @generated
     */
    EReference getIteration_Contributions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Task <em>Task</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Task</em>'.
     * @see org.eclipse.sirius.components.papaya.Task
     * @generated
     */
    EClass getTask();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Task#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getPriority()
	 * @see #getTask()
	 * @generated
	 */
    EAttribute getTask_Priority();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Task#getCost <em>Cost</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cost</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getCost()
	 * @see #getTask()
	 * @generated
	 */
    EAttribute getTask_Cost();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Task#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Targets</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getTargets()
	 * @see #getTask()
	 * @generated
	 */
    EReference getTask_Targets();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Task#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getTasks()
	 * @see #getTask()
	 * @generated
	 */
    EReference getTask_Tasks();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Task#getStartDate <em>Start Date</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getStartDate()
	 * @see #getTask()
	 * @generated
	 */
    EAttribute getTask_StartDate();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Task#getEndDate <em>End Date</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getEndDate()
	 * @see #getTask()
	 * @generated
	 */
    EAttribute getTask_EndDate();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Task#isDone <em>Done</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Done</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#isDone()
	 * @see #getTask()
	 * @generated
	 */
    EAttribute getTask_Done();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Task#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.eclipse.sirius.components.papaya.Task#getDependencies()
	 * @see #getTask()
	 * @generated
	 */
    EReference getTask_Dependencies();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Contribution <em>Contribution</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contribution</em>'.
	 * @see org.eclipse.sirius.components.papaya.Contribution
	 * @generated
	 */
    EClass getContribution();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Contribution#getRelatedTasks <em>Related Tasks</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Related Tasks</em>'.
     * @see org.eclipse.sirius.components.papaya.Contribution#getRelatedTasks()
     * @see #getContribution()
     * @generated
     */
    EReference getContribution_RelatedTasks();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Contribution#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Targets</em>'.
	 * @see org.eclipse.sirius.components.papaya.Contribution#getTargets()
	 * @see #getContribution()
	 * @generated
	 */
    EReference getContribution_Targets();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Contribution#isDone <em>Done</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Done</em>'.
	 * @see org.eclipse.sirius.components.papaya.Contribution#isDone()
	 * @see #getContribution()
	 * @generated
	 */
    EAttribute getContribution_Done();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Component <em>Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see org.eclipse.sirius.components.papaya.Component
	 * @generated
	 */
    EClass getComponent();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Component#getDependencies <em>Dependencies</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Dependencies</em>'.
     * @see org.eclipse.sirius.components.papaya.Component#getDependencies()
     * @see #getComponent()
     * @generated
     */
    EReference getComponent_Dependencies();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Component#getAllDependencies <em>All Dependencies</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>All Dependencies</em>'.
     * @see org.eclipse.sirius.components.papaya.Component#getAllDependencies()
     * @see #getComponent()
     * @generated
     */
    EReference getComponent_AllDependencies();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Component#getUsedAsDependencyBy <em>Used As Dependency By</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used As Dependency By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Component#getUsedAsDependencyBy()
	 * @see #getComponent()
	 * @generated
	 */
    EReference getComponent_UsedAsDependencyBy();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Component#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see org.eclipse.sirius.components.papaya.Component#getComponents()
	 * @see #getComponent()
	 * @generated
	 */
    EReference getComponent_Components();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Component#getAllComponents <em>All Components</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>All Components</em>'.
     * @see org.eclipse.sirius.components.papaya.Component#getAllComponents()
     * @see #getComponent()
     * @generated
     */
    EReference getComponent_AllComponents();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Component#getPackages <em>Packages</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Packages</em>'.
	 * @see org.eclipse.sirius.components.papaya.Component#getPackages()
	 * @see #getComponent()
	 * @generated
	 */
    EReference getComponent_Packages();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Component#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.eclipse.sirius.components.papaya.Component#getPorts()
	 * @see #getComponent()
	 * @generated
	 */
    EReference getComponent_Ports();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.Component#getProvidedServices <em>Provided Services</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Provided Services</em>'.
     * @see org.eclipse.sirius.components.papaya.Component#getProvidedServices()
     * @see #getComponent()
     * @generated
     */
    EReference getComponent_ProvidedServices();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.Component#getRequiredServices <em>Required Services</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Required Services</em>'.
     * @see org.eclipse.sirius.components.papaya.Component#getRequiredServices()
     * @see #getComponent()
     * @generated
     */
    EReference getComponent_RequiredServices();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ComponentPort <em>Component Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Port</em>'.
	 * @see org.eclipse.sirius.components.papaya.ComponentPort
	 * @generated
	 */
    EClass getComponentPort();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.ComponentPort#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol</em>'.
	 * @see org.eclipse.sirius.components.papaya.ComponentPort#getProtocol()
	 * @see #getComponentPort()
	 * @generated
	 */
    EAttribute getComponentPort_Protocol();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ComponentExchange <em>Component Exchange</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Exchange</em>'.
	 * @see org.eclipse.sirius.components.papaya.ComponentExchange
	 * @generated
	 */
    EClass getComponentExchange();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.ComponentExchange#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ports</em>'.
	 * @see org.eclipse.sirius.components.papaya.ComponentExchange#getPorts()
	 * @see #getComponentExchange()
	 * @generated
	 */
    EReference getComponentExchange_Ports();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ProvidedService <em>Provided Service</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Service</em>'.
	 * @see org.eclipse.sirius.components.papaya.ProvidedService
	 * @generated
	 */
    EClass getProvidedService();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.ProvidedService#getContracts <em>Contracts</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Contracts</em>'.
     * @see org.eclipse.sirius.components.papaya.ProvidedService#getContracts()
     * @see #getProvidedService()
     * @generated
     */
    EReference getProvidedService_Contracts();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.RequiredService <em>Required Service</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Service</em>'.
	 * @see org.eclipse.sirius.components.papaya.RequiredService
	 * @generated
	 */
    EClass getRequiredService();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.RequiredService#getContracts <em>Contracts</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Contracts</em>'.
     * @see org.eclipse.sirius.components.papaya.RequiredService#getContracts()
     * @see #getRequiredService()
     * @generated
     */
    EReference getRequiredService_Contracts();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.AnnotableElement <em>Annotable Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable Element</em>'.
	 * @see org.eclipse.sirius.components.papaya.AnnotableElement
	 * @generated
	 */
    EClass getAnnotableElement();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.AnnotableElement#getAnnotations <em>Annotations</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Annotations</em>'.
     * @see org.eclipse.sirius.components.papaya.AnnotableElement#getAnnotations()
     * @see #getAnnotableElement()
     * @generated
     */
    EReference getAnnotableElement_Annotations();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Package <em>Package</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Package</em>'.
     * @see org.eclipse.sirius.components.papaya.Package
     * @generated
     */
    EClass getPackage();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Package#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.eclipse.sirius.components.papaya.Package#getQualifiedName()
	 * @see #getPackage()
	 * @generated
	 */
    EAttribute getPackage_QualifiedName();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Package#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.sirius.components.papaya.Package#getTypes()
	 * @see #getPackage()
	 * @generated
	 */
    EReference getPackage_Types();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Package#getPackages <em>Packages</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Packages</em>'.
	 * @see org.eclipse.sirius.components.papaya.Package#getPackages()
	 * @see #getPackage()
	 * @generated
	 */
    EReference getPackage_Packages();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Type <em>Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Type</em>'.
     * @see org.eclipse.sirius.components.papaya.Type
     * @generated
     */
    EClass getType();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Type#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.eclipse.sirius.components.papaya.Type#getQualifiedName()
	 * @see #getType()
	 * @generated
	 */
    EAttribute getType_QualifiedName();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Type#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.sirius.components.papaya.Type#getVisibility()
	 * @see #getType()
	 * @generated
	 */
    EAttribute getType_Visibility();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Type#getTypes <em>Types</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.sirius.components.papaya.Type#getTypes()
	 * @see #getType()
	 * @generated
	 */
    EReference getType_Types();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.sirius.components.papaya.TypedElement
	 * @generated
	 */
    EClass getTypedElement();

    /**
	 * Returns the meta object for the containment reference '{@link org.eclipse.sirius.components.papaya.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
    EReference getTypedElement_Type();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.GenericType <em>Generic Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.GenericType
	 * @generated
	 */
    EClass getGenericType();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.GenericType#getRawType <em>Raw Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Raw Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.GenericType#getRawType()
	 * @see #getGenericType()
	 * @generated
	 */
    EReference getGenericType_RawType();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.GenericType#getTypeArguments <em>Type Arguments</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Type Arguments</em>'.
     * @see org.eclipse.sirius.components.papaya.GenericType#getTypeArguments()
     * @see #getGenericType()
     * @generated
     */
    EReference getGenericType_TypeArguments();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.sirius.components.papaya.Annotation
	 * @generated
	 */
    EClass getAnnotation();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Annotation#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fields</em>'.
	 * @see org.eclipse.sirius.components.papaya.Annotation#getFields()
	 * @see #getAnnotation()
	 * @generated
	 */
    EReference getAnnotation_Fields();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.AnnotationField <em>Annotation Field</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Field</em>'.
	 * @see org.eclipse.sirius.components.papaya.AnnotationField
	 * @generated
	 */
    EClass getAnnotationField();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier</em>'.
	 * @see org.eclipse.sirius.components.papaya.Classifier
	 * @generated
	 */
    EClass getClassifier();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.Classifier#getTypeParameters <em>Type Parameters</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Type Parameters</em>'.
     * @see org.eclipse.sirius.components.papaya.Classifier#getTypeParameters()
     * @see #getClassifier()
     * @generated
     */
    EReference getClassifier_TypeParameters();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.TypeParameter <em>Type Parameter</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Parameter</em>'.
	 * @see org.eclipse.sirius.components.papaya.TypeParameter
	 * @generated
	 */
    EClass getTypeParameter();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Interface <em>Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface</em>'.
	 * @see org.eclipse.sirius.components.papaya.Interface
	 * @generated
	 */
    EClass getInterface();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Interface#getExtends <em>Extends</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extends</em>'.
	 * @see org.eclipse.sirius.components.papaya.Interface#getExtends()
	 * @see #getInterface()
	 * @generated
	 */
    EReference getInterface_Extends();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Interface#getExtendedBy <em>Extended By</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extended By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Interface#getExtendedBy()
	 * @see #getInterface()
	 * @generated
	 */
    EReference getInterface_ExtendedBy();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Interface#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.eclipse.sirius.components.papaya.Interface#getOperations()
	 * @see #getInterface()
	 * @generated
	 */
    EReference getInterface_Operations();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Interface#getImplementedBy <em>Implemented By</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Implemented By</em>'.
     * @see org.eclipse.sirius.components.papaya.Interface#getImplementedBy()
     * @see #getInterface()
     * @generated
     */
    EReference getInterface_ImplementedBy();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Interface#getSubtypes <em>Subtypes</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subtypes</em>'.
	 * @see org.eclipse.sirius.components.papaya.Interface#getSubtypes()
	 * @see #getInterface()
	 * @generated
	 */
    EReference getInterface_Subtypes();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Interface#getAllSubtypes <em>All Subtypes</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>All Subtypes</em>'.
     * @see org.eclipse.sirius.components.papaya.Interface#getAllSubtypes()
     * @see #getInterface()
     * @generated
     */
    EReference getInterface_AllSubtypes();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation <em>Interface Implementation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface Implementation</em>'.
	 * @see org.eclipse.sirius.components.papaya.InterfaceImplementation
	 * @generated
	 */
    EClass getInterfaceImplementation();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation#getImplements <em>Implements</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Implements</em>'.
     * @see org.eclipse.sirius.components.papaya.InterfaceImplementation#getImplements()
     * @see #getInterfaceImplementation()
     * @generated
     */
    EReference getInterfaceImplementation_Implements();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Class <em>Class</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Class</em>'.
     * @see org.eclipse.sirius.components.papaya.Class
     * @generated
     */
    EClass getClass_();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Class#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#isAbstract()
	 * @see #getClass_()
	 * @generated
	 */
    EAttribute getClass_Abstract();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Class#isFinal <em>Final</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#isFinal()
	 * @see #getClass_()
	 * @generated
	 */
    EAttribute getClass_Final();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Class#isStatic <em>Static</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#isStatic()
	 * @see #getClass_()
	 * @generated
	 */
    EAttribute getClass_Static();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.Class#getExtends <em>Extends</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extends</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#getExtends()
	 * @see #getClass_()
	 * @generated
	 */
    EReference getClass_Extends();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Class#getExtendedBy <em>Extended By</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extended By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#getExtendedBy()
	 * @see #getClass_()
	 * @generated
	 */
    EReference getClass_ExtendedBy();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.Class#getAllExtendedBy <em>All Extended By</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>All Extended By</em>'.
     * @see org.eclipse.sirius.components.papaya.Class#getAllExtendedBy()
     * @see #getClass_()
     * @generated
     */
    EReference getClass_AllExtendedBy();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Class#getConstructors <em>Constructors</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constructors</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#getConstructors()
	 * @see #getClass_()
	 * @generated
	 */
    EReference getClass_Constructors();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Class#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#getAttributes()
	 * @see #getClass_()
	 * @generated
	 */
    EReference getClass_Attributes();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Class#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.eclipse.sirius.components.papaya.Class#getOperations()
	 * @see #getClass_()
	 * @generated
	 */
    EReference getClass_Operations();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Constructor <em>Constructor</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constructor</em>'.
	 * @see org.eclipse.sirius.components.papaya.Constructor
	 * @generated
	 */
    EClass getConstructor();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Constructor#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.sirius.components.papaya.Constructor#getParameters()
	 * @see #getConstructor()
	 * @generated
	 */
    EReference getConstructor_Parameters();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Constructor#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.sirius.components.papaya.Constructor#getVisibility()
	 * @see #getConstructor()
	 * @generated
	 */
    EAttribute getConstructor_Visibility();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.sirius.components.papaya.Attribute
	 * @generated
	 */
    EClass getAttribute();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Attribute#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.sirius.components.papaya.Attribute#getVisibility()
	 * @see #getAttribute()
	 * @generated
	 */
    EAttribute getAttribute_Visibility();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Attribute#isFinal <em>Final</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see org.eclipse.sirius.components.papaya.Attribute#isFinal()
	 * @see #getAttribute()
	 * @generated
	 */
    EAttribute getAttribute_Final();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Attribute#isStatic <em>Static</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static</em>'.
	 * @see org.eclipse.sirius.components.papaya.Attribute#isStatic()
	 * @see #getAttribute()
	 * @generated
	 */
    EAttribute getAttribute_Static();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation
	 * @generated
	 */
    EClass getOperation();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Operation#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation#getVisibility()
	 * @see #getOperation()
	 * @generated
	 */
    EAttribute getOperation_Visibility();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Operation#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation#isAbstract()
	 * @see #getOperation()
	 * @generated
	 */
    EAttribute getOperation_Abstract();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Operation#isFinal <em>Final</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation#isFinal()
	 * @see #getOperation()
	 * @generated
	 */
    EAttribute getOperation_Final();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.papaya.Operation#isStatic <em>Static</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation#isStatic()
	 * @see #getOperation()
	 * @generated
	 */
    EAttribute getOperation_Static();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Operation#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.sirius.components.papaya.Operation#getParameters()
	 * @see #getOperation()
	 * @generated
	 */
    EReference getOperation_Parameters();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.sirius.components.papaya.Parameter
	 * @generated
	 */
    EClass getParameter();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Record <em>Record</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Record</em>'.
     * @see org.eclipse.sirius.components.papaya.Record
     * @generated
     */
    EClass getRecord();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Record#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see org.eclipse.sirius.components.papaya.Record#getComponents()
	 * @see #getRecord()
	 * @generated
	 */
    EReference getRecord_Components();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Record#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.eclipse.sirius.components.papaya.Record#getOperations()
	 * @see #getRecord()
	 * @generated
	 */
    EReference getRecord_Operations();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.RecordComponent <em>Record Component</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Record Component</em>'.
	 * @see org.eclipse.sirius.components.papaya.RecordComponent
	 * @generated
	 */
    EClass getRecordComponent();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see org.eclipse.sirius.components.papaya.DataType
	 * @generated
	 */
    EClass getDataType();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Enum <em>Enum</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Enum</em>'.
     * @see org.eclipse.sirius.components.papaya.Enum
     * @generated
     */
    EClass getEnum();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Enum#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Literals</em>'.
	 * @see org.eclipse.sirius.components.papaya.Enum#getLiterals()
	 * @see #getEnum()
	 * @generated
	 */
    EReference getEnum_Literals();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.EnumLiteral <em>Enum Literal</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Literal</em>'.
	 * @see org.eclipse.sirius.components.papaya.EnumLiteral
	 * @generated
	 */
    EClass getEnumLiteral();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.ApplicationConcern <em>Application Concern</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Concern</em>'.
	 * @see org.eclipse.sirius.components.papaya.ApplicationConcern
	 * @generated
	 */
    EClass getApplicationConcern();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getControllers <em>Controllers</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Controllers</em>'.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getControllers()
     * @see #getApplicationConcern()
     * @generated
     */
    EReference getApplicationConcern_Controllers();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getServices <em>Services</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Services</em>'.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getServices()
     * @see #getApplicationConcern()
     * @generated
     */
    EReference getApplicationConcern_Services();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getEvents()
	 * @see #getApplicationConcern()
	 * @generated
	 */
    EReference getApplicationConcern_Events();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getCommands <em>Commands</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Commands</em>'.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getCommands()
     * @see #getApplicationConcern()
     * @generated
     */
    EReference getApplicationConcern_Commands();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getQueries <em>Queries</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Queries</em>'.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getQueries()
     * @see #getApplicationConcern()
     * @generated
     */
    EReference getApplicationConcern_Queries();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getDomains <em>Domains</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Domains</em>'.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern#getDomains()
     * @see #getApplicationConcern()
     * @generated
     */
    EReference getApplicationConcern_Domains();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Controller <em>Controller</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Controller</em>'.
	 * @see org.eclipse.sirius.components.papaya.Controller
	 * @generated
	 */
    EClass getController();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Controller#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Calls</em>'.
	 * @see org.eclipse.sirius.components.papaya.Controller#getCalls()
	 * @see #getController()
	 * @generated
	 */
    EReference getController_Calls();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Domain <em>Domain</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Domain</em>'.
     * @see org.eclipse.sirius.components.papaya.Domain
     * @generated
     */
    EClass getDomain();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Domain#getServices <em>Services</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Services</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getServices()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Services();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Domain#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getRepositories()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Repositories();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Domain#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getEvents()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Events();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Domain#getCommands <em>Commands</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Commands</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getCommands()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Commands();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.papaya.Domain#getQueries <em>Queries</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Queries</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getQueries()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Queries();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Domain#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.eclipse.sirius.components.papaya.Domain#getDependencies()
	 * @see #getDomain()
	 * @generated
	 */
    EReference getDomain_Dependencies();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Service <em>Service</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Service</em>'.
     * @see org.eclipse.sirius.components.papaya.Service
     * @generated
     */
    EClass getService();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Service#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Calls</em>'.
	 * @see org.eclipse.sirius.components.papaya.Service#getCalls()
	 * @see #getService()
	 * @generated
	 */
    EReference getService_Calls();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.MessageListener <em>Message Listener</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message Listener</em>'.
	 * @see org.eclipse.sirius.components.papaya.MessageListener
	 * @generated
	 */
    EClass getMessageListener();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.papaya.MessageListener#getSubscriptions <em>Subscriptions</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Subscriptions</em>'.
     * @see org.eclipse.sirius.components.papaya.MessageListener#getSubscriptions()
     * @see #getMessageListener()
     * @generated
     */
    EReference getMessageListener_Subscriptions();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.MessageEmitter <em>Message Emitter</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message Emitter</em>'.
	 * @see org.eclipse.sirius.components.papaya.MessageEmitter
	 * @generated
	 */
    EClass getMessageEmitter();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.papaya.MessageEmitter#getPublications <em>Publications</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Publications</em>'.
     * @see org.eclipse.sirius.components.papaya.MessageEmitter#getPublications()
     * @see #getMessageEmitter()
     * @generated
     */
    EReference getMessageEmitter_Publications();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Message <em>Message</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Message</em>'.
     * @see org.eclipse.sirius.components.papaya.Message
     * @generated
     */
    EClass getMessage();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Message#getEmittedBy <em>Emitted By</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Emitted By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Message#getEmittedBy()
	 * @see #getMessage()
	 * @generated
	 */
    EReference getMessage_EmittedBy();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Message#getListenedBy <em>Listened By</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Listened By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Message#getListenedBy()
	 * @see #getMessage()
	 * @generated
	 */
    EReference getMessage_ListenedBy();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Event <em>Event</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Event</em>'.
     * @see org.eclipse.sirius.components.papaya.Event
     * @generated
     */
    EClass getEvent();

    /**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.components.papaya.Event#getCausedBy <em>Caused By</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Caused By</em>'.
	 * @see org.eclipse.sirius.components.papaya.Event#getCausedBy()
	 * @see #getEvent()
	 * @generated
	 */
    EReference getEvent_CausedBy();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Command <em>Command</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Command</em>'.
     * @see org.eclipse.sirius.components.papaya.Command
     * @generated
     */
    EClass getCommand();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Query <em>Query</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Query</em>'.
     * @see org.eclipse.sirius.components.papaya.Query
     * @generated
     */
    EClass getQuery();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see org.eclipse.sirius.components.papaya.Repository
	 * @generated
	 */
    EClass getRepository();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Channel <em>Channel</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Channel</em>'.
     * @see org.eclipse.sirius.components.papaya.Channel
     * @generated
     */
    EClass getChannel();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Subscription <em>Subscription</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subscription</em>'.
	 * @see org.eclipse.sirius.components.papaya.Subscription
	 * @generated
	 */
    EClass getSubscription();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.Subscription#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Channel</em>'.
	 * @see org.eclipse.sirius.components.papaya.Subscription#getChannel()
	 * @see #getSubscription()
	 * @generated
	 */
    EReference getSubscription_Channel();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.Subscription#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Message</em>'.
	 * @see org.eclipse.sirius.components.papaya.Subscription#getMessage()
	 * @see #getSubscription()
	 * @generated
	 */
    EReference getSubscription_Message();

    /**
	 * Returns the meta object for class '{@link org.eclipse.sirius.components.papaya.Publication <em>Publication</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Publication</em>'.
	 * @see org.eclipse.sirius.components.papaya.Publication
	 * @generated
	 */
    EClass getPublication();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.Publication#getChannel <em>Channel</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Channel</em>'.
	 * @see org.eclipse.sirius.components.papaya.Publication#getChannel()
	 * @see #getPublication()
	 * @generated
	 */
    EReference getPublication_Channel();

    /**
	 * Returns the meta object for the reference '{@link org.eclipse.sirius.components.papaya.Publication#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Message</em>'.
	 * @see org.eclipse.sirius.components.papaya.Publication#getMessage()
	 * @see #getPublication()
	 * @generated
	 */
    EReference getPublication_Message();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.papaya.Priority <em>Priority</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Priority</em>'.
     * @see org.eclipse.sirius.components.papaya.Priority
     * @generated
     */
    EEnum getPriority();

    /**
	 * Returns the meta object for enum '{@link org.eclipse.sirius.components.papaya.Visibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Visibility</em>'.
	 * @see org.eclipse.sirius.components.papaya.Visibility
	 * @generated
	 */
    EEnum getVisibility();

    /**
	 * Returns the meta object for data type '{@link java.time.Instant <em>Instant</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the meta object for data type '<em>Instant</em>'.
	 * @see java.time.Instant
	 * @model instanceClass="java.time.Instant"
	 * @generated
	 */
    EDataType getInstant();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
    PapayaFactory getPapayaFactory();

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
	 * @generated
	 */
    interface Literals {
        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ModelElementImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getModelElement()
		 * @generated
		 */
        EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

        /**
         * The meta object literal for the '<em><b>Tags</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MODEL_ELEMENT__TAGS = eINSTANCE.getModelElement_Tags();

        /**
         * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MODEL_ELEMENT__LINKS = eINSTANCE.getModelElement_Links();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.TagImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTag()
		 * @generated
		 */
        EClass TAG = eINSTANCE.getTag();

        /**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TAG__KEY = eINSTANCE.getTag_Key();

        /**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TAG__VALUE = eINSTANCE.getTag_Value();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.LinkImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getLink()
		 * @generated
		 */
        EClass LINK = eINSTANCE.getLink();

        /**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute LINK__KIND = eINSTANCE.getLink_Kind();

        /**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference LINK__SOURCE = eINSTANCE.getLink_Source();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ReferencingLinkImpl <em>Referencing Link</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ReferencingLinkImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getReferencingLink()
		 * @generated
		 */
        EClass REFERENCING_LINK = eINSTANCE.getReferencingLink();

        /**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference REFERENCING_LINK__TARGET = eINSTANCE.getReferencingLink_Target();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ContainingLinkImpl <em>Containing Link</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ContainingLinkImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContainingLink()
		 * @generated
		 */
        EClass CONTAINING_LINK = eINSTANCE.getContainingLink();

        /**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference CONTAINING_LINK__TARGET = eINSTANCE.getContainingLink_Target();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.NamedElementImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getNamedElement()
		 * @generated
		 */
        EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

        /**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE.getNamedElement_Description();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.Container <em>Container</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.Container
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContainer()
		 * @generated
		 */
        EClass CONTAINER = eINSTANCE.getContainer();

        /**
         * The meta object literal for the '<em><b>Folders</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINER__FOLDERS = eINSTANCE.getContainer_Folders();

        /**
         * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONTAINER__ELEMENTS = eINSTANCE.getContainer_Elements();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.FolderImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getFolder()
		 * @generated
		 */
        EClass FOLDER = eINSTANCE.getFolder();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.FolderElement <em>Folder Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.FolderElement
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getFolderElement()
		 * @generated
		 */
        EClass FOLDER_ELEMENT = eINSTANCE.getFolderElement();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ProjectImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getProject()
		 * @generated
		 */
        EClass PROJECT = eINSTANCE.getProject();

        /**
		 * The meta object literal for the '<em><b>Homepage</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT__HOMEPAGE = eINSTANCE.getProject_Homepage();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalCapabilityImpl <em>Operational Capability</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalCapabilityImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalCapability()
		 * @generated
		 */
        EClass OPERATIONAL_CAPABILITY = eINSTANCE.getOperationalCapability();

        /**
		 * The meta object literal for the '<em><b>Constraints</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_CAPABILITY__CONSTRAINTS = eINSTANCE.getOperationalCapability_Constraints();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalEntityImpl <em>Operational Entity</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalEntityImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalEntity()
		 * @generated
		 */
        EClass OPERATIONAL_ENTITY = eINSTANCE.getOperationalEntity();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalActorImpl <em>Operational Actor</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalActorImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalActor()
		 * @generated
		 */
        EClass OPERATIONAL_ACTOR = eINSTANCE.getOperationalActor();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_ACTOR__TYPE = eINSTANCE.getOperationalActor_Type();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalProcessImpl <em>Operational Process</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalProcessImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalProcess()
		 * @generated
		 */
        EClass OPERATIONAL_PROCESS = eINSTANCE.getOperationalProcess();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl <em>Operational Activity</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalActivity()
		 * @generated
		 */
        EClass OPERATIONAL_ACTIVITY = eINSTANCE.getOperationalActivity();

        /**
		 * The meta object literal for the '<em><b>Precondition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_ACTIVITY__PRECONDITION = eINSTANCE.getOperationalActivity_Precondition();

        /**
		 * The meta object literal for the '<em><b>Postcondition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_ACTIVITY__POSTCONDITION = eINSTANCE.getOperationalActivity_Postcondition();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl <em>Operational Interaction</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperationalInteraction()
		 * @generated
		 */
        EClass OPERATIONAL_INTERACTION = eINSTANCE.getOperationalInteraction();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_INTERACTION__TYPE = eINSTANCE.getOperationalInteraction_Type();

        /**
		 * The meta object literal for the '<em><b>Frequency</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_INTERACTION__FREQUENCY = eINSTANCE.getOperationalInteraction_Frequency();

        /**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_INTERACTION__PROTOCOL = eINSTANCE.getOperationalInteraction_Protocol();

        /**
		 * The meta object literal for the '<em><b>Latency</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATIONAL_INTERACTION__LATENCY = eINSTANCE.getOperationalInteraction_Latency();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.IterationImpl <em>Iteration</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.IterationImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getIteration()
		 * @generated
		 */
        EClass ITERATION = eINSTANCE.getIteration();

        /**
		 * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute ITERATION__START_DATE = eINSTANCE.getIteration_StartDate();

        /**
		 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute ITERATION__END_DATE = eINSTANCE.getIteration_EndDate();

        /**
		 * The meta object literal for the '<em><b>Tasks</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference ITERATION__TASKS = eINSTANCE.getIteration_Tasks();

        /**
		 * The meta object literal for the '<em><b>Contributions</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference ITERATION__CONTRIBUTIONS = eINSTANCE.getIteration_Contributions();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.TaskImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTask()
		 * @generated
		 */
        EClass TASK = eINSTANCE.getTask();

        /**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TASK__PRIORITY = eINSTANCE.getTask_Priority();

        /**
		 * The meta object literal for the '<em><b>Cost</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TASK__COST = eINSTANCE.getTask_Cost();

        /**
		 * The meta object literal for the '<em><b>Targets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference TASK__TARGETS = eINSTANCE.getTask_Targets();

        /**
         * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TASK__TASKS = eINSTANCE.getTask_Tasks();

        /**
		 * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TASK__START_DATE = eINSTANCE.getTask_StartDate();

        /**
		 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TASK__END_DATE = eINSTANCE.getTask_EndDate();

        /**
		 * The meta object literal for the '<em><b>Done</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TASK__DONE = eINSTANCE.getTask_Done();

        /**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference TASK__DEPENDENCIES = eINSTANCE.getTask_Dependencies();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ContributionImpl <em>Contribution</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ContributionImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getContribution()
		 * @generated
		 */
        EClass CONTRIBUTION = eINSTANCE.getContribution();

        /**
		 * The meta object literal for the '<em><b>Related Tasks</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference CONTRIBUTION__RELATED_TASKS = eINSTANCE.getContribution_RelatedTasks();

        /**
		 * The meta object literal for the '<em><b>Targets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference CONTRIBUTION__TARGETS = eINSTANCE.getContribution_Targets();

        /**
		 * The meta object literal for the '<em><b>Done</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute CONTRIBUTION__DONE = eINSTANCE.getContribution_Done();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ComponentImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponent()
		 * @generated
		 */
        EClass COMPONENT = eINSTANCE.getComponent();

        /**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference COMPONENT__DEPENDENCIES = eINSTANCE.getComponent_Dependencies();

        /**
         * The meta object literal for the '<em><b>All Dependencies</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__ALL_DEPENDENCIES = eINSTANCE.getComponent_AllDependencies();

        /**
         * The meta object literal for the '<em><b>Used As Dependency By</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__USED_AS_DEPENDENCY_BY = eINSTANCE.getComponent_UsedAsDependencyBy();

        /**
         * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__COMPONENTS = eINSTANCE.getComponent_Components();

        /**
		 * The meta object literal for the '<em><b>All Components</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference COMPONENT__ALL_COMPONENTS = eINSTANCE.getComponent_AllComponents();

        /**
         * The meta object literal for the '<em><b>Packages</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__PACKAGES = eINSTANCE.getComponent_Packages();

        /**
         * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__PORTS = eINSTANCE.getComponent_Ports();

        /**
         * The meta object literal for the '<em><b>Provided Services</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__PROVIDED_SERVICES = eINSTANCE.getComponent_ProvidedServices();

        /**
         * The meta object literal for the '<em><b>Required Services</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COMPONENT__REQUIRED_SERVICES = eINSTANCE.getComponent_RequiredServices();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentPortImpl <em>Component Port</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ComponentPortImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponentPort()
		 * @generated
		 */
        EClass COMPONENT_PORT = eINSTANCE.getComponentPort();

        /**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute COMPONENT_PORT__PROTOCOL = eINSTANCE.getComponentPort_Protocol();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ComponentExchangeImpl <em>Component Exchange</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ComponentExchangeImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getComponentExchange()
		 * @generated
		 */
        EClass COMPONENT_EXCHANGE = eINSTANCE.getComponentExchange();

        /**
		 * The meta object literal for the '<em><b>Ports</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference COMPONENT_EXCHANGE__PORTS = eINSTANCE.getComponentExchange_Ports();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ProvidedServiceImpl <em>Provided Service</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ProvidedServiceImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getProvidedService()
		 * @generated
		 */
        EClass PROVIDED_SERVICE = eINSTANCE.getProvidedService();

        /**
		 * The meta object literal for the '<em><b>Contracts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROVIDED_SERVICE__CONTRACTS = eINSTANCE.getProvidedService_Contracts();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.RequiredServiceImpl <em>Required Service</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.RequiredServiceImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRequiredService()
		 * @generated
		 */
        EClass REQUIRED_SERVICE = eINSTANCE.getRequiredService();

        /**
		 * The meta object literal for the '<em><b>Contracts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference REQUIRED_SERVICE__CONTRACTS = eINSTANCE.getRequiredService_Contracts();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.AnnotableElement <em>Annotable Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.AnnotableElement
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotableElement()
		 * @generated
		 */
        EClass ANNOTABLE_ELEMENT = eINSTANCE.getAnnotableElement();

        /**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference ANNOTABLE_ELEMENT__ANNOTATIONS = eINSTANCE.getAnnotableElement_Annotations();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.PackageImpl <em>Package</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.PackageImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPackage()
		 * @generated
		 */
        EClass PACKAGE = eINSTANCE.getPackage();

        /**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PACKAGE__QUALIFIED_NAME = eINSTANCE.getPackage_QualifiedName();

        /**
         * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PACKAGE__TYPES = eINSTANCE.getPackage_Types();

        /**
         * The meta object literal for the '<em><b>Packages</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PACKAGE__PACKAGES = eINSTANCE.getPackage_Packages();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.TypeImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getType()
		 * @generated
		 */
        EClass TYPE = eINSTANCE.getType();

        /**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute TYPE__QUALIFIED_NAME = eINSTANCE.getType_QualifiedName();

        /**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute TYPE__VISIBILITY = eINSTANCE.getType_Visibility();

        /**
         * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TYPE__TYPES = eINSTANCE.getType_Types();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.TypedElementImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTypedElement()
		 * @generated
		 */
        EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

        /**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.GenericTypeImpl <em>Generic Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.GenericTypeImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getGenericType()
		 * @generated
		 */
        EClass GENERIC_TYPE = eINSTANCE.getGenericType();

        /**
		 * The meta object literal for the '<em><b>Raw Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference GENERIC_TYPE__RAW_TYPE = eINSTANCE.getGenericType_RawType();

        /**
         * The meta object literal for the '<em><b>Type Arguments</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GENERIC_TYPE__TYPE_ARGUMENTS = eINSTANCE.getGenericType_TypeArguments();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.AnnotationImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotation()
		 * @generated
		 */
        EClass ANNOTATION = eINSTANCE.getAnnotation();

        /**
         * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ANNOTATION__FIELDS = eINSTANCE.getAnnotation_Fields();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.AnnotationFieldImpl <em>Annotation Field</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.AnnotationFieldImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAnnotationField()
		 * @generated
		 */
        EClass ANNOTATION_FIELD = eINSTANCE.getAnnotationField();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ClassifierImpl <em>Classifier</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ClassifierImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getClassifier()
		 * @generated
		 */
        EClass CLASSIFIER = eINSTANCE.getClassifier();

        /**
         * The meta object literal for the '<em><b>Type Parameters</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLASSIFIER__TYPE_PARAMETERS = eINSTANCE.getClassifier_TypeParameters();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.TypeParameterImpl <em>Type Parameter</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.TypeParameterImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getTypeParameter()
		 * @generated
		 */
        EClass TYPE_PARAMETER = eINSTANCE.getTypeParameter();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.InterfaceImpl <em>Interface</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.InterfaceImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInterface()
		 * @generated
		 */
        EClass INTERFACE = eINSTANCE.getInterface();

        /**
		 * The meta object literal for the '<em><b>Extends</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE__EXTENDS = eINSTANCE.getInterface_Extends();

        /**
		 * The meta object literal for the '<em><b>Extended By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE__EXTENDED_BY = eINSTANCE.getInterface_ExtendedBy();

        /**
         * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference INTERFACE__OPERATIONS = eINSTANCE.getInterface_Operations();

        /**
		 * The meta object literal for the '<em><b>Implemented By</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE__IMPLEMENTED_BY = eINSTANCE.getInterface_ImplementedBy();

        /**
		 * The meta object literal for the '<em><b>Subtypes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE__SUBTYPES = eINSTANCE.getInterface_Subtypes();

        /**
		 * The meta object literal for the '<em><b>All Subtypes</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE__ALL_SUBTYPES = eINSTANCE.getInterface_AllSubtypes();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation <em>Interface Implementation</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.InterfaceImplementation
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInterfaceImplementation()
		 * @generated
		 */
        EClass INTERFACE_IMPLEMENTATION = eINSTANCE.getInterfaceImplementation();

        /**
		 * The meta object literal for the '<em><b>Implements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference INTERFACE_IMPLEMENTATION__IMPLEMENTS = eINSTANCE.getInterfaceImplementation_Implements();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ClassImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getClass_()
		 * @generated
		 */
        EClass CLASS = eINSTANCE.getClass_();

        /**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute CLASS__ABSTRACT = eINSTANCE.getClass_Abstract();

        /**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute CLASS__FINAL = eINSTANCE.getClass_Final();

        /**
		 * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute CLASS__STATIC = eINSTANCE.getClass_Static();

        /**
		 * The meta object literal for the '<em><b>Extends</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference CLASS__EXTENDS = eINSTANCE.getClass_Extends();

        /**
		 * The meta object literal for the '<em><b>Extended By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference CLASS__EXTENDED_BY = eINSTANCE.getClass_ExtendedBy();

        /**
		 * The meta object literal for the '<em><b>All Extended By</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference CLASS__ALL_EXTENDED_BY = eINSTANCE.getClass_AllExtendedBy();

        /**
         * The meta object literal for the '<em><b>Constructors</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLASS__CONSTRUCTORS = eINSTANCE.getClass_Constructors();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLASS__ATTRIBUTES = eINSTANCE.getClass_Attributes();

        /**
         * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CLASS__OPERATIONS = eINSTANCE.getClass_Operations();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ConstructorImpl <em>Constructor</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ConstructorImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getConstructor()
		 * @generated
		 */
        EClass CONSTRUCTOR = eINSTANCE.getConstructor();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONSTRUCTOR__PARAMETERS = eINSTANCE.getConstructor_Parameters();

        /**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute CONSTRUCTOR__VISIBILITY = eINSTANCE.getConstructor_Visibility();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.AttributeImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getAttribute()
		 * @generated
		 */
        EClass ATTRIBUTE = eINSTANCE.getAttribute();

        /**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute ATTRIBUTE__VISIBILITY = eINSTANCE.getAttribute_Visibility();

        /**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute ATTRIBUTE__FINAL = eINSTANCE.getAttribute_Final();

        /**
		 * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute ATTRIBUTE__STATIC = eINSTANCE.getAttribute_Static();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.OperationImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getOperation()
		 * @generated
		 */
        EClass OPERATION = eINSTANCE.getOperation();

        /**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATION__VISIBILITY = eINSTANCE.getOperation_Visibility();

        /**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATION__ABSTRACT = eINSTANCE.getOperation_Abstract();

        /**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATION__FINAL = eINSTANCE.getOperation_Final();

        /**
		 * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EAttribute OPERATION__STATIC = eINSTANCE.getOperation_Static();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OPERATION__PARAMETERS = eINSTANCE.getOperation_Parameters();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ParameterImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getParameter()
		 * @generated
		 */
        EClass PARAMETER = eINSTANCE.getParameter();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.RecordImpl <em>Record</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.RecordImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRecord()
		 * @generated
		 */
        EClass RECORD = eINSTANCE.getRecord();

        /**
         * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECORD__COMPONENTS = eINSTANCE.getRecord_Components();

        /**
         * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RECORD__OPERATIONS = eINSTANCE.getRecord_Operations();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.RecordComponentImpl <em>Record Component</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.RecordComponentImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRecordComponent()
		 * @generated
		 */
        EClass RECORD_COMPONENT = eINSTANCE.getRecordComponent();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.DataTypeImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getDataType()
		 * @generated
		 */
        EClass DATA_TYPE = eINSTANCE.getDataType();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.EnumImpl <em>Enum</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.EnumImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEnum()
		 * @generated
		 */
        EClass ENUM = eINSTANCE.getEnum();

        /**
         * The meta object literal for the '<em><b>Literals</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ENUM__LITERALS = eINSTANCE.getEnum_Literals();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.EnumLiteralImpl <em>Enum Literal</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.EnumLiteralImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEnumLiteral()
		 * @generated
		 */
        EClass ENUM_LITERAL = eINSTANCE.getEnumLiteral();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl <em>Application Concern</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ApplicationConcernImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getApplicationConcern()
		 * @generated
		 */
        EClass APPLICATION_CONCERN = eINSTANCE.getApplicationConcern();

        /**
         * The meta object literal for the '<em><b>Controllers</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference APPLICATION_CONCERN__CONTROLLERS = eINSTANCE.getApplicationConcern_Controllers();

        /**
         * The meta object literal for the '<em><b>Services</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference APPLICATION_CONCERN__SERVICES = eINSTANCE.getApplicationConcern_Services();

        /**
         * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference APPLICATION_CONCERN__EVENTS = eINSTANCE.getApplicationConcern_Events();

        /**
         * The meta object literal for the '<em><b>Commands</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference APPLICATION_CONCERN__COMMANDS = eINSTANCE.getApplicationConcern_Commands();

        /**
         * The meta object literal for the '<em><b>Queries</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference APPLICATION_CONCERN__QUERIES = eINSTANCE.getApplicationConcern_Queries();

        /**
		 * The meta object literal for the '<em><b>Domains</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference APPLICATION_CONCERN__DOMAINS = eINSTANCE.getApplicationConcern_Domains();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ControllerImpl <em>Controller</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ControllerImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getController()
		 * @generated
		 */
        EClass CONTROLLER = eINSTANCE.getController();

        /**
		 * The meta object literal for the '<em><b>Calls</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference CONTROLLER__CALLS = eINSTANCE.getController_Calls();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.DomainImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getDomain()
		 * @generated
		 */
        EClass DOMAIN = eINSTANCE.getDomain();

        /**
         * The meta object literal for the '<em><b>Services</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__SERVICES = eINSTANCE.getDomain_Services();

        /**
         * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__REPOSITORIES = eINSTANCE.getDomain_Repositories();

        /**
         * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__EVENTS = eINSTANCE.getDomain_Events();

        /**
         * The meta object literal for the '<em><b>Commands</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__COMMANDS = eINSTANCE.getDomain_Commands();

        /**
         * The meta object literal for the '<em><b>Queries</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DOMAIN__QUERIES = eINSTANCE.getDomain_Queries();

        /**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference DOMAIN__DEPENDENCIES = eINSTANCE.getDomain_Dependencies();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ServiceImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getService()
		 * @generated
		 */
        EClass SERVICE = eINSTANCE.getService();

        /**
		 * The meta object literal for the '<em><b>Calls</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference SERVICE__CALLS = eINSTANCE.getService_Calls();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.MessageListener <em>Message Listener</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.MessageListener
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessageListener()
		 * @generated
		 */
        EClass MESSAGE_LISTENER = eINSTANCE.getMessageListener();

        /**
         * The meta object literal for the '<em><b>Subscriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MESSAGE_LISTENER__SUBSCRIPTIONS = eINSTANCE.getMessageListener_Subscriptions();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.MessageEmitter <em>Message Emitter</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.MessageEmitter
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessageEmitter()
		 * @generated
		 */
        EClass MESSAGE_EMITTER = eINSTANCE.getMessageEmitter();

        /**
         * The meta object literal for the '<em><b>Publications</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MESSAGE_EMITTER__PUBLICATIONS = eINSTANCE.getMessageEmitter_Publications();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.Message <em>Message</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.Message
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getMessage()
		 * @generated
		 */
        EClass MESSAGE = eINSTANCE.getMessage();

        /**
		 * The meta object literal for the '<em><b>Emitted By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MESSAGE__EMITTED_BY = eINSTANCE.getMessage_EmittedBy();

        /**
		 * The meta object literal for the '<em><b>Listened By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference MESSAGE__LISTENED_BY = eINSTANCE.getMessage_ListenedBy();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.EventImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getEvent()
		 * @generated
		 */
        EClass EVENT = eINSTANCE.getEvent();

        /**
		 * The meta object literal for the '<em><b>Caused By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference EVENT__CAUSED_BY = eINSTANCE.getEvent_CausedBy();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.CommandImpl <em>Command</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.CommandImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getCommand()
		 * @generated
		 */
        EClass COMMAND = eINSTANCE.getCommand();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.QueryImpl <em>Query</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.QueryImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getQuery()
		 * @generated
		 */
        EClass QUERY = eINSTANCE.getQuery();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.RepositoryImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getRepository()
		 * @generated
		 */
        EClass REPOSITORY = eINSTANCE.getRepository();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.ChannelImpl <em>Channel</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.ChannelImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getChannel()
		 * @generated
		 */
        EClass CHANNEL = eINSTANCE.getChannel();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.SubscriptionImpl <em>Subscription</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.SubscriptionImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getSubscription()
		 * @generated
		 */
        EClass SUBSCRIPTION = eINSTANCE.getSubscription();

        /**
		 * The meta object literal for the '<em><b>Channel</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference SUBSCRIPTION__CHANNEL = eINSTANCE.getSubscription_Channel();

        /**
		 * The meta object literal for the '<em><b>Message</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference SUBSCRIPTION__MESSAGE = eINSTANCE.getSubscription_Message();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.impl.PublicationImpl <em>Publication</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.impl.PublicationImpl
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPublication()
		 * @generated
		 */
        EClass PUBLICATION = eINSTANCE.getPublication();

        /**
		 * The meta object literal for the '<em><b>Channel</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference PUBLICATION__CHANNEL = eINSTANCE.getPublication_Channel();

        /**
		 * The meta object literal for the '<em><b>Message</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!--
         * end-user-doc -->
		 * @generated
		 */
        EReference PUBLICATION__MESSAGE = eINSTANCE.getPublication_Message();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.Priority <em>Priority</em>}' enum.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.Priority
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getPriority()
		 * @generated
		 */
        EEnum PRIORITY = eINSTANCE.getPriority();

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.papaya.Visibility <em>Visibility</em>}' enum.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.papaya.Visibility
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getVisibility()
		 * @generated
		 */
        EEnum VISIBILITY = eINSTANCE.getVisibility();

        /**
		 * The meta object literal for the '<em>Instant</em>' data type.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see java.time.Instant
		 * @see org.eclipse.sirius.components.papaya.impl.PapayaPackageImpl#getInstant()
		 * @generated
		 */
        EDataType INSTANT = eINSTANCE.getInstant();

    }

} // PapayaPackage

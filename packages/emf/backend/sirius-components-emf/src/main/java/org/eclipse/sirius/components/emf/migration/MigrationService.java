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
package org.eclipse.sirius.components.emf.migration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;
import org.eclipse.sirius.emfjson.resource.JsonResource;

/**
 * Specialized BasicExtendedMetaData.
 *
 * @author mcharfadi
 */
public class MigrationService extends BasicExtendedMetaData implements JsonResource.IJsonResourceProcessor {

    private final List<IMigrationParticipant> migrationParticipants;
    private MigrationData documentMigrationData;
    private List<IMigrationParticipant> migrationParticipantsCandidates;

    public MigrationService(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
        this.documentMigrationData = new MigrationData("none", "0");
        this.updateCandidates();
    }

    private boolean isCandidateVersion(IMigrationParticipant migrationParticipant) {
        return migrationParticipant.getVersion().compareTo(this.documentMigrationData.migrationVersion()) > 0;
    }

    private void updateCandidates() {
        this.migrationParticipantsCandidates =  this.migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion))
                .toList();
    }

    public MigrationData getMostRecentParticipantMigrationData() {
        var migrationParticipantsCandidate = this.migrationParticipants.stream()
                .filter(this::isCandidateVersion)
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion).reversed())
                .map(migrationParticipant -> new MigrationData("none", migrationParticipant.getVersion()))
                .findFirst();

        return migrationParticipantsCandidate.orElse(this.documentMigrationData);
    }

    @Override
    public EStructuralFeature getElement(EClass eClass, String namespace, String eStructuralFeatureName) {
        EStructuralFeature structuralFeature = eClass.getEStructuralFeature(eStructuralFeatureName);
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            EStructuralFeature newStructuralFeature = migrationParticipant.getEStructuralFeature(eClass, eStructuralFeatureName);
            if (newStructuralFeature != null) {
                structuralFeature = newStructuralFeature;
            }
        }
        return structuralFeature;
    }

    @Override
    public EClassifier getType(EPackage ePackage, String typeName) {
        EClassifier eClassifier = ePackage.getEClassifier(typeName);
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            EClassifier newEClassifier = migrationParticipant.getEClassifier(ePackage, typeName);
            if (newEClassifier != null) {
                eClassifier = newEClassifier;
            }
        }
        return eClassifier;
    }

    @Override
    public EPackage getPackage(String nsURI) {
        EPackage ePackage = super.getPackage(nsURI);
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            EPackage newEPackage = migrationParticipant.getPackage(nsURI);
            if (newEPackage != null) {
                ePackage = newEPackage;
            }
        }
        return ePackage;
    }

    @Override
    public void preDeserialization(JsonResource resource, JsonObject jsonObject) {
        this.setMigrationDataFromDocumentContent(jsonObject);
        this.setResourceMetadataAdapterMigrationData(resource, this.documentMigrationData);
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            migrationParticipant.preDeserialization(resource, jsonObject);
            this.setResourceMetadataAdapterMigrationData(resource, new MigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()));
        }
    }

    @Override
    public void postSerialization(JsonResource resource, JsonObject jsonObject) {
        this.getOptionalResourceMetadataAdapter(resource).ifPresent(resourceMetadataAdapter -> {
            if (resourceMetadataAdapter.getMigrationData() != null) {
                var rootMigration = new Gson().toJsonTree(resourceMetadataAdapter.getMigrationData(), MigrationData.class);
                if (rootMigration != null) {
                    jsonObject.add(MigrationData.JSON_OBJECT_ROOT, rootMigration);
                }
            }
        });
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            migrationParticipant.postSerialization(resource, jsonObject);
        }
    }

    @Override
    public void postObjectLoading(EObject eObject, JsonObject jsonObject, boolean isTopObject) {
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            migrationParticipant.postObjectLoading(eObject, jsonObject);
        }
    }

    @Override
    public Object getValue(EObject object, EStructuralFeature feature, Object value) {
        Object returnValue = value;
        for (IMigrationParticipant migrationParticipant : this.migrationParticipantsCandidates) {
            Object newValue = migrationParticipant.getValue(object, feature, value);
            if (newValue != null) {
                returnValue = newValue;
            }
        }
        return returnValue;
    }

    private Optional<ResourceMetadataAdapter> getOptionalResourceMetadataAdapter(JsonResource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst();
    }

    private void setResourceMetadataAdapterMigrationData(JsonResource resource, MigrationData migrationData) {
        this.getOptionalResourceMetadataAdapter(resource).ifPresent(resourceMetadataAdapter -> resourceMetadataAdapter.setMigrationData(migrationData));
    }

    private void setMigrationDataFromDocumentContent(JsonObject jsonObject) {
        var optionalMigrationData = Optional.ofNullable(jsonObject.getAsJsonObject(MigrationData.JSON_OBJECT_ROOT))
                .map(migrationRootElement -> new Gson().fromJson(migrationRootElement, MigrationData.class)).stream().findFirst();
        optionalMigrationData.ifPresent(migrationData -> {
            this.documentMigrationData = migrationData;
            this.updateCandidates();
        });
    }
}

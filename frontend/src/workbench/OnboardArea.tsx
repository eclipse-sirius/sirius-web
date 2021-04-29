/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { useLazyQuery } from '@apollo/client';
import gql from 'graphql-tag';
import { NewDocumentArea } from 'onboarding/NewDocumentArea';
import { NewRepresentationArea } from 'onboarding/NewRepresentationArea';
import { RepresentationsArea } from 'onboarding/RepresentationsArea';
import { Permission } from 'project/Permission';
import React, { useEffect, useState } from 'react';
import styles from './OnboardArea.module.css';
import { OnboardAreaProps } from './OnboardArea.types';

const getOnboardDataQuery = gql`
  query getOnboardData($projectId: ID!, $classId: ID!) {
    viewer {
      editingContext(editingContextId: $projectId) {
        stereotypeDescriptions {
          id
          label
        }
        representationDescriptions(classId: $classId) {
          edges {
            node {
              id
              label
            }
          }
        }
      }
      project(projectId: $projectId) {
        representations {
          __typename
          id
          label
        }
      }
    }
  }
`;

const MAX_DISPLAY = 5;

const INITIAL_STATE = {
  stereotypeDescriptions: [],
  representationDescriptions: [],
  representations: [],
};

export const OnboardArea = ({ projectId, selection, setSelection }: OnboardAreaProps) => {
  const [state, setState] = useState(INITIAL_STATE);
  const { stereotypeDescriptions, representationDescriptions, representations } = state;

  const classId = selection ? selection.kind : '';

  const [getOnboardData, { loading, data, error }] = useLazyQuery(getOnboardDataQuery);
  useEffect(() => {
    getOnboardData({ variables: { projectId, classId } });
  }, [projectId, classId, getOnboardData]);
  useEffect(() => {
    if (!loading && !error && data?.viewer) {
      const { viewer } = data;
      let representationDescriptions = viewer.editingContext.representationDescriptions.edges.map((edge) => edge.node);
      setState({
        representations: viewer.project.representations,
        stereotypeDescriptions: viewer.editingContext.stereotypeDescriptions,
        representationDescriptions,
      });
    }
  }, [projectId, classId, loading, data, error]);

  return (
    <div className={styles.onboardArea}>
      <div className={styles.onboardContent}>
        <Permission requiredAccessLevel="EDIT">
          <NewDocumentArea
            stereotypeDescriptions={stereotypeDescriptions}
            projectId={projectId}
            setSelection={setSelection}
            maxDisplay={MAX_DISPLAY}
          />
        </Permission>
        <Permission requiredAccessLevel="EDIT">
          <NewRepresentationArea
            representationDescriptions={representationDescriptions}
            projectId={projectId}
            selection={selection}
            setSelection={setSelection}
            maxDisplay={MAX_DISPLAY}
          />
        </Permission>
        <RepresentationsArea representations={representations} setSelection={setSelection} maxDisplay={MAX_DISPLAY} />
      </div>
    </div>
  );
};

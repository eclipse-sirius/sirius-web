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
import { useProject } from 'project/ProjectProvider';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './OnboardArea.module.css';

const getOnboardDataQuery = gql`
  query getOnboardData($projectId: ID!, $classId: ID!) {
    viewer {
      stereotypeDescriptions {
        id
        label
      }
      project(projectId: $projectId) {
        representations {
          __typename
          id
          label
        }
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
  }
`;

const propTypes = {
  selection: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
};

const MAX_DISPLAY = 5;

const INITIAL_STATE = {
  stereotypeDescriptions: [],
  representationDescriptions: [],
  representations: [],
};

export const OnboardArea = ({ selection, setSelection }) => {
  const { id } = useProject() as any;
  const [state, setState] = useState(INITIAL_STATE);
  const { stereotypeDescriptions, representationDescriptions, representations } = state;

  const classId = selection ? selection.kind : '';

  const [getOnboardData, { loading, data, error }] = useLazyQuery(getOnboardDataQuery);
  useEffect(() => {
    getOnboardData({ variables: { projectId: id, classId } });
  }, [id, classId, getOnboardData]);
  useEffect(() => {
    if (!loading && !error && data?.viewer) {
      const { viewer } = data;
      let representationDescriptions = viewer.representationDescriptions.edges.map((edge) => edge.node);
      setState({
        representations: viewer.project.representations,
        stereotypeDescriptions: viewer.stereotypeDescriptions,
        representationDescriptions,
      });
    }
  }, [id, classId, loading, data, error]);

  return (
    <div className={styles.onboardArea}>
      <div className={styles.onboardContent}>
        <Permission requiredAccessLevel="EDIT">
          <NewDocumentArea
            stereotypeDescriptions={stereotypeDescriptions}
            projectId={id}
            setSelection={setSelection}
            maxDisplay={MAX_DISPLAY}
          />
        </Permission>
        <Permission requiredAccessLevel="EDIT">
          <NewRepresentationArea
            representationDescriptions={representationDescriptions}
            projectId={id}
            selection={selection}
            setSelection={setSelection}
            maxDisplay={MAX_DISPLAY}
          />
        </Permission>
        <RepresentationsArea
          representations={representations}
          // projectId={id} TODO RepresentationsArea has no such prop
          setSelection={setSelection}
          maxDisplay={MAX_DISPLAY}
        />
      </div>
    </div>
  );
};

OnboardArea.propTypes = propTypes;

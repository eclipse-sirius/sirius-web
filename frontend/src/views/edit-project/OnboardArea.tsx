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
import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { useLazyQuery } from 'common/GraphQLHooks';
import { useProject } from 'project/ProjectProvider';
import { NewDocumentArea } from 'onboarding/NewDocumentArea';
import { NewRepresentationArea } from 'onboarding/NewRepresentationArea';
import { RepresentationsArea } from 'onboarding/RepresentationsArea';
import { Permission } from 'project/Permission';
import styles from './OnboardArea.module.css';
import gql from 'graphql-tag';

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
`.loc.source.body;

const propTypes = {
  selection: PropTypes.object,
  setSelection: PropTypes.func.isRequired,
};

export const OnboardArea = ({ selection, setSelection }) => {
  const { id } = useProject() as any;
  const initialState = {
    stereotypeDescriptions: [],
    representationDescriptions: [],
    representations: [],
  };
  const [state, setState] = useState(initialState);
  const { stereotypeDescriptions, representationDescriptions, representations } = state;

  const classId = selection ? selection.kind : '';

  const [getOnboardData, onboardData] = useLazyQuery(getOnboardDataQuery, {}, 'getOnboardData');
  useEffect(() => {
    getOnboardData({ projectId: id, classId });
  }, [id, classId, getOnboardData]);
  useEffect(() => {
    if (!onboardData.loading) {
      const result = onboardData.data.data.viewer;
      let representationDescriptions = result.representationDescriptions.edges.map((edge) => edge.node);
      setState({
        representations: result.project.representations,
        stereotypeDescriptions: result.stereotypeDescriptions,
        representationDescriptions,
      });
    }
  }, [id, classId, onboardData]);

  const maxDisplay = 5;
  return (
    <div className={styles.onboardArea}>
      <div className={styles.onboardContent}>
        <Permission requiredAccessLevel="EDIT">
          <NewDocumentArea
            stereotypeDescriptions={stereotypeDescriptions}
            projectId={id}
            setSelection={setSelection}
            maxDisplay={maxDisplay}
          />
        </Permission>
        <Permission requiredAccessLevel="EDIT">
          <NewRepresentationArea
            representationDescriptions={representationDescriptions}
            projectId={id}
            selection={selection}
            setSelection={setSelection}
            maxDisplay={maxDisplay}
          />
        </Permission>
        <RepresentationsArea
          representations={representations}
          // projectId={id} TODO RepresentationsArea has no such prop
          setSelection={setSelection}
          maxDisplay={maxDisplay}
        />
      </div>
    </div>
  );
};

OnboardArea.propTypes = propTypes;
